package com.learn.service.impl;

import com.learn.service.WelcomeService;

public class WelcomeServiceImpl implements WelcomeService {
	@Override
	public String sayHello(String name) {
		System.out.println("欢迎您: " + name);
		return "success";
	}
}
