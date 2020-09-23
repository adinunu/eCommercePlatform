package id.org.test.ms.auth.config;

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
import id.org.test.common.constant.AppConstant;


@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Value("${security.oauth2.resource.id:ecp-ms-auth-oauth-resource}")
	private String oauthResourceId;
	
	@Value("${security.oauth2.resource.token-info-uri:http://localhost:19100/ecp-ms-auth/oauth/check_token}")
	private String oauthTokenInfoUri;
	
	@Bean
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
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().authorizeRequests()
			.antMatchers(AppConstant.SWAGGER_URL_WHITELIST).permitAll()
		.anyRequest().authenticated();
	}
	
}
