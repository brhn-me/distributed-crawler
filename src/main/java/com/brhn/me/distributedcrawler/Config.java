package com.brhn.me.distributedcrawler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${registry.server}")
    private String registryServer;

    @Value("${server.port}")
    private int port;


    private String dataDir;

    public String getRegistryServer() {
        return registryServer;
    }

    public int getPort() {
        return port;
    }

    public String getDataDir() {
        return dataDir;
    }
}
