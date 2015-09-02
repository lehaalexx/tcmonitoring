package com.tcmonitor.server.service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.tcmonitor.model.Health;
import com.tcmonitor.model.Logs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Iterator;
import java.util.List;

@Service
public class LogProcessor {
    private static final String FULL_SCOPE = "full";
    private static final String PART_SCOPE = "part";

    @Value("${tcmonitor.log.storage}")
    private String logPathStorage;

    public void processLogs(Logs log){
        checkOrCreateVmFolder(log.getVmTag());
        if(FULL_SCOPE.equals(log.getScope())){
            File file = new File(logPathStorage + "/" + log.getVmTag() + "/" + log.getFileName());
            if(file.exists()){
                checkAndAppend(log);
            } else {
                writeFullFile(log);
            }
        }
        if(PART_SCOPE.equals(log.getScope())){
            append(log);
        }
    }

    private void checkAndAppend(Logs log) {
        String lastLine = check(log);
        if(lastLine != null){
            append(findDif(log, lastLine));
        }

    }

    private String check(Logs log){
        String lastLine = null;
        String currentLIne = "";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(logPathStorage + "/" + log.getVmTag() + "/" + log.getFileName()), "UTF-8"));
            while ((currentLIne = br.readLine()) != null){
                if(!currentLIne.isEmpty() && !"null".equals(currentLIne)){
                    lastLine = currentLIne;
                }
            }
            br.close();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Iterable<String> messageIterable = Splitter.on("\n").trimResults().omitEmptyStrings().split(log.getLogMessage());
        List<String> messageList = Lists.newArrayList(messageIterable);
        String lastMessage = null;
        if(!messageList.isEmpty()){
            lastMessage = messageList.get(messageList.size()-1);
        }
        if(lastLine != null && lastLine.equals(lastMessage)){
            return null;
        }
        return lastLine;
    }

    private Logs findDif(Logs log, String lastLine){
        Logs newLog = new Logs();
        newLog.setVmTag(log.getVmTag());
        newLog.setFileName(log.getFileName());
        Iterable<String> messageIterable = Splitter.on("\n").trimResults().omitEmptyStrings().split(log.getLogMessage());
        List<String> messageList = Lists.newArrayList(messageIterable);
        StringBuilder sb = new StringBuilder();
        boolean needAdd = false;
        String prefix = "\n";
        for(String message : messageList){
            if(needAdd){
                sb.append(prefix);
                sb.append(message);
            }
            if(message.equals(lastLine)){
                needAdd = true;
            }
        }

        newLog.setLogMessage(sb.toString());
        return newLog;
    }

    private void append(Logs log){
        Writer writer;
        try {
            writer = new BufferedWriter(new FileWriter(logPathStorage + "/" + log.getVmTag() + "/" + log.getFileName(), true));
            writer.append(log.getLogMessage());
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void processHealth(Health health){
        System.out.println("START-----------------------");
        System.out.println(health.getVmTag());
        System.out.println("END-----------------------");
    }

    private void checkOrCreateVmFolder(String vmTag){
        File vmFolder = new File(logPathStorage + "/" + vmTag);
        if(!vmFolder.exists()){
            vmFolder.mkdir();
        }
    }

    private void writeFullFile(Logs log){
        PrintWriter writer;
        try {
            writer = new PrintWriter(logPathStorage + "/" + log.getVmTag() + "/" + log.getFileName(), "UTF-8");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        writer.println(log.getLogMessage());
        writer.close();
    }


}
