package com.shsunedu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.shsunedu.security.LoginInterceptor;

@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private LoginInterceptor userLoginInterceptor;

	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userLoginInterceptor).addPathPatterns("/**");
	}
}
