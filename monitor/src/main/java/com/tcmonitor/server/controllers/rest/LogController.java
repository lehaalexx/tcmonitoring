package com.tcmonitor.server.controllers.rest;

import com.tcmonitor.model.Logs;
import com.tcmonitor.server.service.LogProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${rest.log.url}")
public class LogController {

    @Autowired
    private LogProcessor logProcessor;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getLogs(@RequestBody Logs logs){
        logProcessor.processLogs(logs);
        return "ok";
    }
}
