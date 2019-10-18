package com.sltas.example.spring.rabbit.unofficial_7.Consumer_Acknowledgements;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.TimeUnit;

@ComponentScan
public class Application {

    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        TimeUnit.SECONDS.sleep(100);
        System.out.println("message container startup");

        context.close();
    }
}
