package com.fileupload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication(exclude={MultipartAutoConfiguration.class})
public class CommonFileuploadSpringBootApplication {

	@Value("${spring.servlet.multipart.max-file-size}") String maxFileSize;
	@Value("${spring.servlet.multipart.max-request-size}") String maxRequestSize;
	
	public static void main(String[] args) {
		SpringApplication.run(CommonFileuploadSpringBootApplication.class, args);
	}
	
	@Bean
	public CommonsMultipartResolver commonsMultipartResolver(){
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(DataSize.parse(maxRequestSize).toBytes());
		commonsMultipartResolver.setMaxUploadSizePerFile(DataSize.parse(maxFileSize).toBytes());
		return commonsMultipartResolver;
	}

}
