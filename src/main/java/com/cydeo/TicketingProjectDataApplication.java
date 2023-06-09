package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketingProjectDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketingProjectDataApplication.class, args);
    }

    //I am trying to add bean in the container through@Bean annotation
    //Steps to create @Bean annotation
        //Create class, annotated with Configuration
        //Write a method which return the object that you trying to add in the container
        //Annotate this method with @Bean

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper ();
    }

}
