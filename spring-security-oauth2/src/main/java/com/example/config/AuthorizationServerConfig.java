package com.example.config;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	@Autowired
	private JwtTokenEnhancer jwtTokenEnhancer;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// 設置 JET 增強內容
		TokenEnhancerChain chain = new TokenEnhancerChain();
		List<TokenEnhancer> delegates = new ArrayList<>();
		delegates.add(jwtTokenEnhancer);
		delegates.add(jwtAccessTokenConverter);
		chain.setTokenEnhancers(delegates);

		endpoints.authenticationManager(authenticationManager)
				.userDetailsService(userService)
				// accessToken 轉成 JWT token
				.tokenStore(tokenStore)
				.accessTokenConverter(jwtAccessTokenConverter)
				.tokenEnhancer(chain);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
				// 實務上可能不放內存
				.inMemory()
				// 客戶端 ID
				.withClient("client")
				// 秘鑰
				.secret(passwordEncoder.encode("112233"))
				// 重定向地址
//				.redirectUris("https://www.google.com/")
				.redirectUris("http://localhost:8081/login")
				// 授權範圍
				.scopes("all")
				// accessToken 失效時間
				.accessTokenValiditySeconds(60)
				// refreshToken 失效時間
				.refreshTokenValiditySeconds(86400)
				/**
				 * 授權類型
				 * authorization_code: 授權碼模式
				 * password: 密碼模式
				 * refresh_token: 刷新令牌
				 */
				.authorizedGrantTypes("authorization_code", "password", "refresh_token")
				// 自動授權
				.autoApprove(true);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// 獲取秘鑰，必須要身分認證 (配置單點登入必備)
		security.tokenKeyAccess("isAuthenticated()");
	}
}
