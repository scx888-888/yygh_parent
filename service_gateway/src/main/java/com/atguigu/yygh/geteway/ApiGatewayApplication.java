package com.atguigu.yygh.geteway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {
	public static void main(String[] args) {
		//System.out.println("SpringBoot Start....");
		try {
			SpringApplication.run(ApiGatewayApplication.class, args);
		}catch(Throwable e) {
			e.printStackTrace();
		}
	}
}