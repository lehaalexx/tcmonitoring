package com.tcmonitor.model;

public class Logs {
    private String logMessage;
    private String fileName;
    private String vmTag;
    private String scope;

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public String getVmTag() {
        return vmTag;
    }

    public void setVmTag(String vmTag) {
        this.vmTag = vmTag;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
