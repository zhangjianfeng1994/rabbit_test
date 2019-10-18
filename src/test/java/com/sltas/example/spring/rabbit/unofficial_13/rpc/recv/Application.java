package com.sltas.example.spring.rabbit.unofficial_13.rpc.recv;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.TimeUnit;

@ComponentScan
public class Application {
    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        System.out.println("===server startup======");
        TimeUnit.SECONDS.sleep(120);
        context.close();
    }
}
