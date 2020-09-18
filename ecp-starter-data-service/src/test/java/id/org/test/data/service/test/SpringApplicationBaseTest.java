package id.org.test.data.service.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

/**
 * Created by josescalia on 06/03/15.
 */

@Configuration
@ComponentScan(basePackages = "com.pst.whee.pos")
@EnableJpaRepositories(basePackages = {"com.pst.whee.pos.backend.repository","com.pst.whee.core.security.repository"})
@EnableAutoConfiguration
@EnableTransactionManagement
@PropertySource("classpath:application-test.properties")
public class SpringApplicationBaseTest {

    @Autowired
    public static ConfigurableApplicationContext context;

    public static void main(String[] args) throws IOException {
        context = SpringApplication.run(SpringApplicationBaseTest.class);
        context.close();
    }


}
