package com.example.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("user")
public class UserController {

	@GetMapping("/getCurrentUser")
	public Object getCurrentUser(Authentication authentication) {
		return authentication.getPrincipal();
	}

	@GetMapping("/parseJwtToken")
	public Object parseJwtToken(Authentication authentication, HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		String token = header.substring(header.lastIndexOf("Bearer") + 7);
		return Jwts.parser()
				// 如果秘鑰有中文，加上 getBytes(StandardCharsets.UTF_8) 可防止亂碼
				.setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
				.parseClaimsJws(token)
				.getBody();
	}
}
