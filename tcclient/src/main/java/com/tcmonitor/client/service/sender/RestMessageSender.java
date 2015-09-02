package com.tcmonitor.client.service.sender;

import com.tcmonitor.client.service.LogsPopulation;
import com.tcmonitor.model.Health;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestMessageSender implements MessageSender{

    @Value("${tcmonitor.server.endpoint}")
    private String endpoint;

    @Value("${rest.log.url}")
    private String restLogUrl;

    @Value("${rest.health.url}")
    private String restHealthUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LogsPopulation logsPopulation;

    @Override
    public void send(String message, String fileName, String scope) {
        restTemplate.exchange(endpoint + restLogUrl, HttpMethod.POST,
                new HttpEntity(logsPopulation.populate(message, fileName, scope)), String.class);
    }

    @Override
    public void healthSend(Health health) {
        restTemplate.exchange(endpoint + restHealthUrl, HttpMethod.POST,
                new HttpEntity(health), String.class);
    }
}
