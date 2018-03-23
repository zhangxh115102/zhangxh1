package com.shsunedu;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:env.xml")
public class Application extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	public void customize(ConfigurableEmbeddedServletContainer container) {
		//设置启动端口
		container.setPort(8083);
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("128MB");//单个文件不能超过128M
		factory.setMaxRequestSize("256MB");//单次文件总大小不能超过256M
		return factory.createMultipartConfig();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
