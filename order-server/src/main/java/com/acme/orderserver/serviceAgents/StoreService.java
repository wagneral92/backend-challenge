package com.acme.orderserver.serviceAgents;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Getter
public class StoreService {

    @Value("${app.toreService.baseUrl}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public StoreService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
