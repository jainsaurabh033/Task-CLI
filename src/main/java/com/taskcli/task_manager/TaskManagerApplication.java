package com.taskcli.task_manager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class TaskManagerApplication {

	public static void main(String[] args) {

		System.out.println("Hello World!");
		SpringApplication.run(TaskManagerApplication.class, args);
	}

}
