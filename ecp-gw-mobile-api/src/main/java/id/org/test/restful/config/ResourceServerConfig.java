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
import id.org.test.common.constant.AppConstant;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	public static final String ALLOWED_URL_WHITELIST[] = { "/auth/login", "/auth/registers", "/api/product/list",
			"/api/product/{productId}" };

	@Value("${security.oauth2.resource.id:ecp-rest-gw}")
	private String oauthResourceId;

	@Value("${security.oauth2.resource.token-info-uri:http://localhost:19100/ecp-ms-auth/oauth/check_token}")
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
	@Primary
	public ResourceServerTokenServices tokenServices() {

		RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
		remoteTokenServices.setCheckTokenEndpointUrl(oauthTokenInfoUri);
		remoteTokenServices.setClientId(AppConstant.OAuthClientDetails.MobileApi.ID);
		remoteTokenServices.setClientSecret(AppConstant.OAuthClientDetails.MobileApi.SECRET);
		remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
		return remoteTokenServices;

	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenServices(tokenServices()).resourceId(oauthResourceId);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(ALLOWED_URL_WHITELIST).permitAll().antMatchers(SWAGGER_URL_WHITELIST).permitAll()
				.antMatchers(ACTUATOR_V1_URL_WHITELIST).permitAll().anyRequest().authenticated();
	}

}
