package id.org.test.restful.config;

import static id.org.test.common.constant.AppConstant.ACTUATOR_V1_URL_WHITELIST;
import static id.org.test.common.constant.AppConstant.SWAGGER_URL_WHITELIST;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import id.org.test.common.builder.JwtAccessTokenConverterBuilder;


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	public static final String ALLOWED_URL_WHITELIST[] = { "/account/validateBusinessName", 
//															"/account/validateEmail",
//															"/system/resetPassword", 
															"/api/auth/login", 
															"/api/auth/logout", 
															"/province/**", 
															"/api/area/provinsi/**", 
//															"/health/**", 
//															"/registration", 
															"/api/version", 
															"/api/signup/**",
															"/api/signin/**"
															,"/referral/**",
															"/api/auth/v2/login"};



	@Value("${security.oauth2.resource.id:whee-mapi-gw}")
	private String oauthResourceId;
	
	@Value("${security.oauth2.resource.token-info-uri:http://localhost:19100/whee-ms-auth/oauth/check_token}")
	private String oauthTokenInfoUri;
	
	@Bean
	@Qualifier("JwtAccessTokenConverter")
	public JwtAccessTokenConverter accessTokenConverter() {
		return JwtAccessTokenConverterBuilder.build();
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	@Primary // Making this primary to avoid any accidental duplication with another token service instance of the same name
	public ResourceServerTokenServices tokenServices() {
		
		RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
		remoteTokenServices.setCheckTokenEndpointUrl(oauthTokenInfoUri);
		remoteTokenServices.setClientId("client");
		remoteTokenServices.setClientSecret("password");
		remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
		return remoteTokenServices;
		
//		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//		defaultTokenServices.setTokenStore(tokenStore());
//		defaultTokenServices.setSupportRefreshToken(true);
//		return defaultTokenServices;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources
			.tokenServices(tokenServices())
			.resourceId(oauthResourceId);
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
//		.cors().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().authorizeRequests()
			.antMatchers(ALLOWED_URL_WHITELIST).permitAll()
			.antMatchers(SWAGGER_URL_WHITELIST).permitAll()
			.antMatchers(ACTUATOR_V1_URL_WHITELIST).permitAll()
		.anyRequest().authenticated();
	}
	
}

