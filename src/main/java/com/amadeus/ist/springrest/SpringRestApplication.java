package com.amadeus.ist.springrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class SpringRestApplication {
	public static void main(String[] args) {
		// close() => closing the app context to shut down the ExecutorService
		SpringApplication.run(SpringRestApplication.class, args);
	}

	@Bean
	public Executor taskExecutor(){ // specific method name Spring will search for
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(5);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("concurrentRest-");
		executor.initialize();
		return executor;
	}
}
