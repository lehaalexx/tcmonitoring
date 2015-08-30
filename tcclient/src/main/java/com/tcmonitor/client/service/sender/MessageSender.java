package com.tcmonitor.client.service.sender;

public interface MessageSender {
    void send(String message, String fileName);
}
