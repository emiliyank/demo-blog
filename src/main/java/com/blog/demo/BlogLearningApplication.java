package com.blog.demo;

import com.blog.demo.blog.BlogJDBCTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogLearningApplication {
	@Autowired
	BlogJDBCTemplate blogJDBCTemplate;

	public static void main(String[] args) {
		SpringApplication.run(BlogLearningApplication.class, args);
	}

}
