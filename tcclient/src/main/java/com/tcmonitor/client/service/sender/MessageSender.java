package com.tcmonitor.client.service.sender;

import com.tcmonitor.model.Health;

public interface MessageSender {
    void send(String message, String fileName, String scope);
    void healthSend(Health health);
}
