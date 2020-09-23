package id.org.test.ms.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
		"id.org.test.ms.auth.repository" })
public class AuthMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthMicroserviceApplication.class, args);
	}

}
