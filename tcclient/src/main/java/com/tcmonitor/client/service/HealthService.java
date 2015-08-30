package com.tcmonitor.client.service;

import com.tcmonitor.model.Health;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    @Value("${tcmonitor.vm.name}")
    private String vmTag;

    public Health getHealth(){
        Health health  = new Health();
        health.setVmTag(vmTag);
        return health;
    }

}
