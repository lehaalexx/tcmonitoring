package com.tcmonitor.server.controllers.rest;

import com.tcmonitor.model.Health;
import com.tcmonitor.server.service.LogProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${rest.health.url}")
public class HealthController {

    @Autowired
    private LogProcessor logProcessor;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getLogs(@RequestBody Health health){
        logProcessor.processHealth(health);
        return "ok";
    }
}
