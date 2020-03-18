package com.edusol.retailbanking.application;

import com.edusol.retailbanking.application.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {return  new BCryptPasswordEncoder();
    }
    @Bean
    public SpringAppicationContext springAppicationContext()
    {return new SpringAppicationContext();}

    @Bean("AppProperties")
    public AppProperties getAppProperties()
    {return  new AppProperties();}
}
