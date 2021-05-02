package com.learn;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext("test.xml");
		String name = ac.getApplicationName();
		System.out.println(name);
		Object a = ac.getBean("a");
		if (a instanceof A)
			System.out.println("True");
//		A bean = ac.getBean(A.class);
//        System.out.println(bean);
//		B bean1 = ac.getBean(B.class);
//        System.out.println(bean1);
	}
}
