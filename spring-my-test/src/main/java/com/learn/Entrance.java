package com.learn;

import com.learn.service.WelcomeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Entrance {
	public static void main(String[] args) {
		System.out.println("hello world");
		String xmlPath = "E:\\Code\\Black_end\\spring-framework-5.2.13-main\\spring-framework-5.2.13-main\\spring-my-test\\src\\main\\resources\\spring\\spring-config.xml";
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(xmlPath);
		WelcomeService welcomeService = (WelcomeService) applicationContext.getBean("welcomeService");
		welcomeService.sayHello("强大的spring框架");
	}
}
