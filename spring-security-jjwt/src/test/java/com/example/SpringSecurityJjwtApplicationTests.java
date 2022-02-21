package com.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class SpringSecurityJjwtApplicationTests {

	@Test
	void testJwt() {
		JwtBuilder jwtBuilder = Jwts.builder()
				// 唯一 ID {"id": "888"}
				.setId("888")
				// 接受的用戶 {"sub": "Rose"}
				.setSubject("Rose")
				// 簽發時間 {"iat": "..."}
				.setIssuedAt(new Date())
				// 簽名算法及密鑰
				.signWith(SignatureAlgorithm.HS256, "xxxx");
		// 簽發 token
		String token = jwtBuilder.compact();
		System.out.println(token);

		System.out.println("==========================================");
		String[] split = token.split("\\.");
		System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
		System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
		// 這個會亂碼
		System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
	}

	@Test
	void testParse() {
//		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjQ1NDI4NjA2fQ.7tIeaSWJuT4BZHw-qiKi0vFaTRESmg2KGgP-Af6Fju4";
//		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjQ1NDMxNTM1LCJleHAiOjE2NDU0MzE1OTV9.atY2hc8R9KaQIdUv4tA5jCf6zlDYgZYCWZQ65eFB7Xs";
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiJSb3NlIiwiaWF0IjoxNjQ1NDMyMDI1LCJuYW1lIjoiSmFjayIsImxvZ28iOiJ4eHgucG5nIn0.Kx7Wn0six-l_8p6GRWNbEXKCfVZJckVpsOg2LOraAEA";

		// 解析 token，獲取 Claims (JWT 中荷載申明的對象)
		Claims claims = (Claims) Jwts.parser()
				// 簽發時的秘鑰
				.setSigningKey("xxxx")
				.parse(token)
				.getBody();
		System.out.println("id=" + claims.getId());
		System.out.println("sub=" + claims.getSubject());
		System.out.println("iat=" + claims.getIssuedAt());
		System.out.println("exp=" + claims.getExpiration());

		System.out.println("name=" + claims.get("name"));
		System.out.println("logo=" + claims.get("logo"));
	}

	@Test
	void testJwtHasExpire() {
		// 當前時間
		long date = System.currentTimeMillis();
		// 失效時間
		long exp = date + 60 * 1000;

		JwtBuilder jwtBuilder = Jwts.builder()
				// 唯一 ID {"id": "888"}
				.setId("888")
				// 接受的用戶 {"sub": "Rose"}
				.setSubject("Rose")
				// 簽發時間 {"iat": "..."}
				.setIssuedAt(new Date())
				// 簽名算法及密鑰
				.signWith(SignatureAlgorithm.HS256, "xxxx")
				.setExpiration(new Date(exp));

		// 簽發 token
		String token = jwtBuilder.compact();
		System.out.println(token);

		System.out.println("==========================================");
		String[] split = token.split("\\.");
		System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
		System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
		// 這個會亂碼
		System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
	}


	@Test
	void testJwtHasEnhancer() {
		JwtBuilder jwtBuilder = Jwts.builder()
				// 唯一 ID {"id": "888"}
				.setId("888")
				// 接受的用戶 {"sub": "Rose"}
				.setSubject("Rose")
				// 簽發時間 {"iat": "..."}
				.setIssuedAt(new Date())
				// 簽名算法及密鑰
				.signWith(SignatureAlgorithm.HS256, "xxxx")
				// 自定義申明
				.claim("name", "Jack")
				.claim("logo", "xxx.png");
//				.addClaims(map);

		// 簽發 token
		String token = jwtBuilder.compact();
		System.out.println(token);

		System.out.println("==========================================");
		String[] split = token.split("\\.");
		System.out.println(Base64Codec.BASE64.decodeToString(split[0]));
		System.out.println(Base64Codec.BASE64.decodeToString(split[1]));
		// 這個會亂碼
		System.out.println(Base64Codec.BASE64.decodeToString(split[2]));
	}
}
