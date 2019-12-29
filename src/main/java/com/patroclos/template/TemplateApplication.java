package com.patroclos.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;


@SpringBootApplication
@ComponentScan("com.patroclos")
public class TemplateApplication {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
		 
	public static void main(String[] args) {
		SpringApplication.run(TemplateApplication.class, args);
	}
	
	
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() throws Exception {
	    System.out.println("App Started...");
	    run();
	}
	
	
	public void run(String... args) throws Exception {

    }
 

}
