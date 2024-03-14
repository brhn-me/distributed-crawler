package com.brhn.me.distributedcrawler;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DistributedCrawlerApplication {

    private static final Logger logger = LoggerFactory.getLogger(DistributedCrawlerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DistributedCrawlerApplication.class, args);
    }

    @Autowired
    private RedisProperties redisProperties;

    @PostConstruct
    public void logRedisProperties() {
        logger.info("Connecting to Redis at {}:{}", redisProperties.getHost(), redisProperties.getPort());
    }

}
