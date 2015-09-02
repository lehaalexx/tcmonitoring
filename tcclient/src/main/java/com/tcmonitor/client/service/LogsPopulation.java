package com.tcmonitor.client.service;

import com.tcmonitor.model.Logs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LogsPopulation {

    @Value("${tcmonitor.vm.name}")
    private String vmTag;

    public Logs populate(String message, String fileName, String scope){
        Logs logs = new Logs();
        logs.setLogMessage(message);
        logs.setVmTag(vmTag);
        logs.setFileName(fileName);
        logs.setScope(scope);
        return logs;
    }
}
