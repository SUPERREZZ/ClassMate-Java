package com.eduapp.edumanagerapp.models;

public class Message {
    private final String sender;
    private final String content;
    private final String date;

    public Message(String sender, String date, String content) {
        this.sender = sender;
        this.content = content;
        this.date = date;

    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}
