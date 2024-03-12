package com.brhn.me.distributedcrawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class URLQueueRedis {

    private static final String URL_QUEUE_KEY = "urlQueue";
    private static final String VISITED_URLS_KEY = "visitedUrls";

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public URLQueueRedis(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public synchronized void addUrl(String url) {
        Boolean isVisited = redisTemplate.opsForSet().isMember(VISITED_URLS_KEY, url);
        if (Boolean.FALSE.equals(isVisited)) {
            redisTemplate.opsForList().rightPush(URL_QUEUE_KEY, url);
        }
    }

    public synchronized String getNextUrl() {
        return redisTemplate.opsForList().leftPop(URL_QUEUE_KEY);
    }

    public synchronized void setVisited(String url) {
        redisTemplate.opsForSet().add(VISITED_URLS_KEY, url);
    }

    public synchronized long getQueueSize() {
        Long size = redisTemplate.opsForList().size(URL_QUEUE_KEY);
        return (size != null) ? size : 0L;
    }

    public synchronized long getVisitedSize() {
        Long size = redisTemplate.opsForSet().size(VISITED_URLS_KEY);
        return (size != null) ? size : 0L;
    }

    public synchronized boolean isEmpty() {
        return getQueueSize() == 0;
    }
}


