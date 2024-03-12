package com.brhn.me.distributedcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DistributedCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedCrawlerApplication.class, args);
	}

}
