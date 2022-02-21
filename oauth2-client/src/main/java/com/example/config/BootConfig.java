package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties
public class BootConfig {
	@Value("${server.port:}")
	private String port;

	@PostConstruct
	public void showInfo() {
		System.out.println("port = " + port);
	}
}
