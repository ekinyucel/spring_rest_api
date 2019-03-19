package com.amadeus.ist.springrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class SpringRestApplication {
    public static void main(String[] args) {
		SpringApplication.run(SpringRestApplication.class, args);
	}

    @Bean(name = "threadPoolExecutor")
    public TaskExecutor taskExecutor() { // specific method name Spring will search for
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
		executor.setQueueCapacity(500); // set the capacity for the BlockingQueue of spring. it is a thread safe data structure.
        executor.setKeepAliveSeconds(10000);
		executor.setThreadNamePrefix("concurrentRest-");
		executor.initialize();
		return executor;
	}
}
