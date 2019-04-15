package com.example.review.backend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Klaus Groenbaek
 * Created  15/04/2019
 */
@SpringBootApplication
public class Application {


    public static void main(String[] args) {

        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        builder.run(args);
    }

}
