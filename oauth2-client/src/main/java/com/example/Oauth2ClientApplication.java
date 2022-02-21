package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

// 開啟單點登入
@EnableOAuth2Sso
@SpringBootApplication
public class Oauth2ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2ClientApplication.class, args);
	}

}
