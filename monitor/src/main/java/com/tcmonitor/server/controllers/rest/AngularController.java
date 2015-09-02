package com.tcmonitor.server.controllers.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("${rest.angular.url}")
public class AngularController {

    @Value("${tcmonitor.log.storage}")
    private String logPathStorage;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getVms(){
        File file = new File(logPathStorage);
        List<String> vms = new ArrayList<>();
        List<File> files = Arrays.asList(file.listFiles());
        for(File f : files){
            if(f.isDirectory()){
                vms.add(f.getName());
            }
        }
        return vms;
    }
}
