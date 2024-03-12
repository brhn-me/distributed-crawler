package com.brhn.me.distributedcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

@Service
public class Crawler {
    /**
     * TODO:
     *      - show visited value in registry
     *      - show redis queue and visited size in registry
     *      - test
     */

    private final URLQueueRedis urlQueue;
    private final Config config;

    private final String dataDir;
    private static final Logger LOGGER = Logger.getLogger(Crawler.class.getName());

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3";

    private long visited = 0;


    @Autowired
    public Crawler(URLQueueRedis urlQueue, Config config) {
        this.urlQueue = urlQueue;
        this.config = config;
        this.dataDir = config.getDataDir();
    }

    public void start() {
        while (!urlQueue.isEmpty()) {
            String url = urlQueue.getNextUrl();
            if (url != null && !url.isEmpty()) {
                crawl(url);
            }
        }
    }

    public void seed() {
        try {
            Resource resource = new ClassPathResource("seeds.txt");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                reader.lines().forEach(line -> {
                    if (!line.trim().isEmpty()) {
                        urlQueue.addUrl(line.trim());
                    }
                });
            }
        } catch (IOException e) {
            LOGGER.severe("Failed to read seed URLs: " + e.getMessage());
        }
    }


    private void crawl(String url) {
        try {
            Document document = Jsoup.connect(url).userAgent(USER_AGENT).get();
            LOGGER.info("Crawling URL: " + url);
            Elements links = document.select("a[href]");

            for (Element link : links) {
                String absUrl = link.attr("abs:href");
                urlQueue.addUrl(absUrl);
            }

            String textContent = document.text();
            URL u = new URL(url);
            Path hostDir = Paths.get(dataDir, u.getHost());
            if (!Files.exists(hostDir)) {
                Files.createDirectories(hostDir);
            }
            String fileName = hashURL(url) + ".txt";
            Path filePath = hostDir.resolve(fileName);

            try (FileWriter writer = new FileWriter(filePath.toFile(), StandardCharsets.UTF_8)) {
                writer.write(textContent);
            }
            urlQueue.setVisited(url);
            visited++;

        } catch (IOException e) {
            LOGGER.warning("Failed to crawl " + url + ": " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warning("Failed to hash url " + url + ": " + e.getMessage());
        }
    }

    private String hashURL(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}