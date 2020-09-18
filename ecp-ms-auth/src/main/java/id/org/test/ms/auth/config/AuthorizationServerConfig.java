package id.org.test.ms.auth.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.token.ClientKeyGenerator;
import org.springframework.security.oauth2.client.token.DefaultClientKeyGenerator;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.google.common.collect.Lists;

import id.org.test.common.builder.JwtAccessTokenConverterBuilder;
import id.org.test.common.exception.AppAuthException;
import id.org.test.ms.auth.service.CustomClientDetailsService;
import id.org.test.ms.auth.service.CustomUserDetailsManager;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private static final Logger log = LoggerFactory.getLogger(AuthorizationServerConfig.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomUserDetailsManager wheeUserDetailsManager;
	@Autowired
	private CustomClientDetailsService wheeClientDetailsService;
	@Autowired
	@Qualifier("CustomTokenEnhancer")
	private TokenEnhancer tokenEnhancer;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
			.allowFormAuthenticationForClients()
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()")
			.passwordEncoder(passwordEncoder); // this is client's password encoder
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(wheeClientDetailsService);
//		clients.inMemory()
//			.withClient("client")
//			.authorizedGrantTypes("password", "refresh_token", "implicit", "authorization_code", "client_credentials")
//			.secret("{bcrypt}$2a$11$.UkTiJCGL4AYCpxftuWmjumosFZZXjJonTMqGVERY7D9ey4CHNaZC") //<- plain text of this bcrypt = "password"
//			.scopes("read", "write", "trust");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		log.info("Configuring AuthorizationServerEndpoints");
		endpoints
			.authenticationManager(authenticationManager)
			.userDetailsService(wheeUserDetailsManager)
			.tokenStore(wheeTokenStore())
			.tokenEnhancer(tokenEnhancerChain())
			.reuseRefreshTokens(false) // this line made every refresh_token grant invoked, it will generate new refresh_token 
			.exceptionTranslator(e -> {
	            if (e instanceof OAuth2Exception) {
	                OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
	                return ResponseEntity
	                        .status(oAuth2Exception.getHttpErrorCode())
	                        .body(new AppAuthException(oAuth2Exception.getMessage()));
	            } else if(e instanceof InternalAuthenticationServiceException) {
            	 	return ResponseEntity
                         .status(HttpStatus.UNAUTHORIZED.value())
                         .body(new AppAuthException(""));
	            } else {
	                throw e;
	            }
	        })
			;
	}
	
	@Bean
	public AuthenticationKeyGenerator authenticationKeyGenerator() {
        return new DefaultAuthenticationKeyGenerator();
    }
	
	@Bean
    public ClientKeyGenerator clientKeyGenerator(){
        return new DefaultClientKeyGenerator();
    }
	
	@Bean
	protected JwtAccessTokenConverter jwtAccessTokenConverter() {
		return JwtAccessTokenConverterBuilder.build();
	}
	
	@Bean
    public TokenEnhancerChain tokenEnhancerChain(){
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Lists.newArrayList(tokenEnhancer, jwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }
	 
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
	    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	    defaultTokenServices.setTokenStore(wheeTokenStore());
	    defaultTokenServices.setSupportRefreshToken(true);
//	    defaultTokenServices.setReuseRefreshToken(true); // somehow this has no effect, maybe its a bug 
	    return defaultTokenServices;
	}

	@Bean
	public JdbcTokenStore jdbcTokenStore() {
		return new JdbcTokenStore(dataSource);
	}
	
	 private TokenStore wheeTokenStore() {
		 return jdbcTokenStore();
//		 return new InMemoryTokenStore();
//		 return new WheeTokenStore(wheeAccessTokenRepository, authenticationKeyGenerator());
	 }
}