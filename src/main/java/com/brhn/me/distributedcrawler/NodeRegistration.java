package com.brhn.me.distributedcrawler;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class NodeRegistration {

    private final Config config;

    CrawlerNode node;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public NodeRegistration(Config config) {
        this.config = config;
        this.node = new CrawlerNode(UUID.randomUUID().toString(), Util.getHostAddress(), config.getPort(), false, LocalDateTime.now());

    }

    @PostConstruct
    public void register() {
        restTemplate.postForObject(config.getRegistryServer() + "/register", node, CrawlerNode.class);
    }

    @PreDestroy
    public void deregister() {
        restTemplate.postForObject(config.getRegistryServer() + "/deregister", node.getNodeId(), Void.class);
    }


    @Scheduled(fixedRate = 5000) // Sends a heartbeat every 5 seconds
    public void sendHeartbeat() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(node.getNodeId(), headers);

        try {
            restTemplate.postForObject(config.getRegistryServer() + "/heartbeat", entity, Void.class);
        } catch (RestClientException e) {
            System.err.println("Failed to send heartbeat to registry service: " + e.getMessage());
            // Handle the error appropriately
        }
    }
}
