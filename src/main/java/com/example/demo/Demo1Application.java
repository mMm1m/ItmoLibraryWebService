package com.example.demo;

import controller.RequestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.LabyrinthITParsingImpl;

import java.io.IOException;


@SpringBootApplication
public class Demo1Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(Demo1Application.class, args);
    }
}
