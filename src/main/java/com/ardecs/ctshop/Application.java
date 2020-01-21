package com.ardecs.ctshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//extends SpringBootServletInitializer нужен для запуска приложений Spring Boot из классического архива WAR, но не мне.
public class Application  {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

    }

}
