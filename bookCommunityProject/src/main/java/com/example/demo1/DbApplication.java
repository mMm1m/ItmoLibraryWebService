package com.example.demo1;



import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class DbApplication {

	public static void main(String[] args) throws IOException, IncorrectBookYear, IncorrectBookISBN, IncorrectBookID {
		SpringApplication.run(DbApplication.class, args);
	}

}
