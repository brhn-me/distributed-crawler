package com.brhn.me.distributedcrawler;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class NodeRegistration {

    private final Config config;

    CrawlerNode node;


    private long visited;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Logger logger = LoggerFactory.getLogger(NodeRegistration.class);

    private List<CrawlerNode> nodes = new ArrayList<>();

    @Autowired
    public NodeRegistration(Config config) {
        this.config = config;
        this.node = new CrawlerNode(UUID.randomUUID().toString(), Util.getHostAddress(), config.getPort(), false, LocalDateTime.now());

    }

    @PostConstruct
    public void register() {
        restTemplate.postForObject(config.getRegistryServer() + "/register", node, CrawlerNode.class);
        logger.info("Node registered successfully");
    }

    @PreDestroy
    public void deregister() {
        restTemplate.postForObject(config.getRegistryServer() + "/deregister", node.getNodeId(), Void.class);
        logger.info("Node de-registered successfully");
    }


    @Scheduled(fixedRate = 5000) // Sends a heartbeat every 5 seconds
    public void sendHeartbeat() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(node.getNodeId(), headers);

        try {
            restTemplate.postForObject(config.getRegistryServer() + "/heartbeat", entity, Void.class);
            logger.debug("Sending heartbeat...");
        } catch (RestClientException e) {
            logger.error("Failed to send heartbeat to registry service: {}", e.getMessage());
        }
    }

    @Scheduled(fixedRate = 300000) // 5 minutes
    public void discoverNodes() {
        try {
            ResponseEntity<CrawlerNode[]> response = restTemplate.getForEntity(config.getRegistryServer() + "/discover", CrawlerNode[].class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                this.nodes = Arrays.stream(response.getBody())
                        .filter(n -> !n.getNodeId().equals(this.node.getNodeId()))
                        .collect(Collectors.toList());
                logger.info("Available nodes: {}", this.nodes.size());
            }
        } catch (RestClientException e) {
            logger.error("Failed to discover nodes: {}", e.getMessage());
        }
    }

    public void sendFile(String host, String hash, String file) {
        FileData fd = new FileData();
        fd.setHost(host);
        fd.setHash(hash);
        fd.setFile(file);

        if (nodes != null && !nodes.isEmpty()) {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<FileData> entity = new HttpEntity<>(fd, headers);

            nodes.forEach(node -> {
                if (!node.getNodeId().equals(this.node.getNodeId())) {
                    try {
                        restTemplate.postForObject(node.getAddress() + ":" + node.getPort() + "/receiveFile", entity, String.class);
                        logger.info("File sent successfully to node: {}", node.getNodeId());
                    } catch (RestClientException e) {
                        logger.error("Failed to send file to node {}: {}", node.getNodeId(), e.getMessage());
                    }
                }
            });
        }
    }

    @PostMapping("/receiveFile")
    public ResponseEntity<Void> receiveFile(@RequestBody FileData fileData) {
        try {
            Path directory = Paths.get(config.getDataDir(), fileData.getHost());
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            Path file = directory.resolve(fileData.getHash() + ".txt");
            byte[] fileContent = Base64.getDecoder().decode(fileData.getFile());
            Files.write(file, fileContent);
            logger.info("Fault tolerance file received: {}", file);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            logger.error("Failed to save fault tolerance file: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public long getVisited() {
        return visited;
    }

    public void setVisited(long visited) {
        this.visited = visited;
    }
}
