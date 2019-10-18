package com.sltas.example.spring.rabbit.unofficial_2.MessageListenerAdapter.emit;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Application {
	
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) throws Exception {
		
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        logger.info("=====start up======");
        
        TimeUnit.SECONDS.sleep(600);
        
        context.close();

    }

}
