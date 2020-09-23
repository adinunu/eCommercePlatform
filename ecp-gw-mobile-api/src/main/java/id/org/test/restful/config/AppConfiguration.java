package id.org.test.restful.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "appAuditorAware", modifyOnCreate = false)
public class AppConfiguration {

	@Value("${spring.application.home}")
	private String homePath;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
