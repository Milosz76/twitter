package mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@ComponentScan(basePackages = {"mvc.model", "mvc.controller", "mvc.service","mvc.repository","mvc.controller", "mvc.configuration"})
@EntityScan(basePackages = {"mvc.model.entity"})
@EnableJpaRepositories(basePackages = {"mvc.repository"})
public class TwitterApp  {
    public static void main(String[] args) {
        SpringApplication.run(TwitterApp.class, args);
    }
}
