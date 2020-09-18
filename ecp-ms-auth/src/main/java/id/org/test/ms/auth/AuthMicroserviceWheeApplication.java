package id.org.test.ms.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import id.org.test.ms.auth.repository.UserAccountRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
		"id.org.test.ms.auth.repository" }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {
				UserAccountRepository.class }))
//@EnableDiscoveryClient
public class AuthMicroserviceWheeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthMicroserviceWheeApplication.class, args);
	}

}
