package com.learn.selfeditor;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		String path = "selfEditor.xml";
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(path);
		Customer bean = ac.getBean(Customer.class);
		System.out.println(bean);
	}

}
