package id.org.test.ms.auth.config;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import id.org.test.common.builder.PasswordEncoderBuilder;
import id.org.test.common.constant.AppConstant;
import id.org.test.ms.auth.repository.UserRepository;
import id.org.test.ms.auth.service.SecurityContextService;
import id.org.test.ms.auth.service.CustomUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SecurityContextService securityContextService;
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/error/**", 
        		"/actuator/**",
        		"/token/**",
        		"/oauth/is-login",
        		"/oauth/token/revoke",
        		"/oauth/token/kick",
        		"/oauth/ping",
        		"/user/**"
        		);
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
	    	http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().exceptionHandling().authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
			.and().authorizeRequests()
				.antMatchers(AppConstant.SWAGGER_URL_WHITELIST).permitAll()
			.anyRequest().authenticated()
			.and().httpBasic().realmName(AppConstant.SECURITY_HTTP_BASIC_REALM_NAME)
			.and().csrf().disable();
    }
	 
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService())
        	.passwordEncoder(passwordEncoder());
	    		
	}
	 
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Bean
    public UserDetailsService userDetailsService() {
		CustomUserDetailsManager customUserDtlsMgr = null;
		try {
			customUserDtlsMgr = new CustomUserDetailsManager(userRepository, securityContextService, authenticationManagerBean());
		} catch (Exception e) {
			log.error("Failed creating bean UserDetailsService", e);
		}
    	return customUserDtlsMgr;
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderBuilder.build();
	}
	
}
