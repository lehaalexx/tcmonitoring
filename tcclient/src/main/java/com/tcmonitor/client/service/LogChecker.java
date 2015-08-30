package com.tcmonitor.client.service;

import com.tcmonitor.client.service.sender.MessageSender;
import com.tcmonitor.client.service.sender.RestMessageSender;
import com.tcmonitor.client.service.sender.S3MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class LogChecker {

    private String tcLogsDir;
    private Map<String, Long> files = new HashMap();
    private MessageSender sender;

    @Value("${tcmonitor.tcpath}")
    private String tcPath;

    @Value("${tcmonitor.s3.bucket}")
    private String s3Bucket;

    @Autowired
    private RestMessageSender restMessageSender;

    @Autowired
    private S3MessageSender s3MessageSender;

    @PostConstruct
    public void init(){
        tcLogsDir = getTCPath();
        if(s3Bucket.isEmpty()){
            this.sender = restMessageSender;
        } else {
            this.sender = s3MessageSender;
        }
    }

    //Check logs every 5 seconds
    @Scheduled(cron = "*/5 * * * * *")
    public void check() {
        getListOfFiles();
    }

    private static String parseTCPath(String path){
        int index = path.indexOf("webapps");
        if(index == -1){
            throw new RuntimeException("Tomcat path is not initialized");
        }
        return path.substring(0, index) + "logs";
    }

    private String getTCPath(){
        if(tcPath.isEmpty()){
            return parseTCPath(String.valueOf(LogChecker.class.getResource("LogChecker.class")));
        }
        return tcPath;
    }

    private void getListOfFiles() {
        File folder = new File(tcLogsDir);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File listOfFile : listOfFiles) {
                refreshMap(listOfFile);
            }
        }
    }

    private void refreshMap(File file){
        if(files.containsKey(file.getName())){
            if(files.get(file.getName()) != file.length()){
                reversOrderReadLog(file);
                files.put(file.getName(), file.length());
            }
        } else {
            readLog(file);
            files.put(file.getName(), file.length());

        }
    }

    private void readLog(File file){
        if(file.getName().contains(".DS_Store")){
            return;
        }
        StringBuilder fullLog = new StringBuilder();
        String sCurrentLine;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            while ((sCurrentLine = br.readLine()) != null) {
                fullLog.append(sCurrentLine).append("\n");
            }
            br.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("Log read exception", e);
        }
        sender.send(fullLog.toString(), file.getName());
    }

    private void reversOrderReadLog(File file){
        StringBuilder fullLog = new StringBuilder();
        String sCurrentLine;
        try {
            RandomAccessFile rFile = new RandomAccessFile(file, "r");
            rFile.seek(files.get(file.getName()));
            while ((sCurrentLine = rFile.readLine()) != null) {
                if(sCurrentLine.isEmpty()){
                    continue;
                }
                fullLog.append(sCurrentLine).append("\n");
            }
            rFile.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException("Log read exception", e);
        }
        sender.send(fullLog.toString(), file.getName());
    }

}

