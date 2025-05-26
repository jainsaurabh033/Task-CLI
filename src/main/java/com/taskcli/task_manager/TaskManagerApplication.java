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

//	@Bean
//	public CommandLineRunner logAllBeans(ApplicationContext ctx) {
//		return args -> {
//			System.out.println("📦 Loaded Beans:");
//			String[] beans = ctx.getBeanDefinitionNames();
//			Arrays.sort(beans);
//			for (String bean : beans) {
//				if (bean.toLowerCase().contains("controller")) {
//					System.out.println("🔹 " + bean);
//				}
//			}
//		};
//	}
}
