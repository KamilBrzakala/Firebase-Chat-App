package com.example.prezes.firebaselogin.model;

import java.util.Date;

public class ChatMessage {

    private String messageText;
    private String receiver;
    private String sender;
    private long messageTime;

    public ChatMessage(String messageText, String receiver, String sender) {
        this.messageText = messageText;
        this.receiver = receiver;
        this.sender = sender;
        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}