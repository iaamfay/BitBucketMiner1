package aiss.bitbucketminer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "aiss.bitbucketminer1")
public class Bitbucketminer1Application {

    public static void main(String[] args) {
        SpringApplication.run(Bitbucketminer1Application.class, args);
    }

}
