package com.tcmonitor.server.service;

import com.tcmonitor.model.Logs;
import org.springframework.stereotype.Service;

@Service
public class LogProcessor {

    public void processLogs(Logs log){
        System.out.println("START-----------------------");
        System.out.println(log.getVmTag());
        System.out.println(log.getFileName());
        System.out.println(log.getLogMessage());
        System.out.println("END-----------------------");
    }
}
