package org.example.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:hikari.properties")
})
public class PostAppBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostAppBeApplication.class, args);
    }

}
