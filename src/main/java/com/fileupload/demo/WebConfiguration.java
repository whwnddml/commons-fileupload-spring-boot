package com.fileupload.demo;

import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	  
	  @PostConstruct
	  protected void init() {}
	  
	  @Bean
	  public CommonsMultipartResolver multipartResolver(){
	      CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
	      commonsMultipartResolver.setDefaultEncoding("UTF-8");
	      commonsMultipartResolver.setMaxUploadSize(2000 * 1024 * 1024); // 2GB
	      return commonsMultipartResolver;
	  }
	  
	  @Bean
	  public ServletContextInitializer clearJsession() {
	    return new ServletContextInitializer() {
	        public void onStartup(ServletContext servletContext) throws ServletException {
	          servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));
	          SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
	          sessionCookieConfig.setHttpOnly(true);
	        }
	      };
	  }
	  
	  @Override
	  public void addResourceHandlers(ResourceHandlerRegistry registry) {
		  registry.addResourceHandler("/resources/**")
		  .addResourceLocations("classpath:/static/")
		  .setCachePeriod(20);
	  }
	  
	  @Override
      public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/examples/scripting/basic").setViewName("examples/scripting/basic");
        registry.addViewController("/upload").setViewName("file-upload");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/home2").setViewName("home2");
        registry.addViewController("/home3").setViewName("home3");
        registry.addViewController("/home4").setViewName("home4");
        registry.addViewController("/home4multi").setViewName("home4multi");
        
      }

}
