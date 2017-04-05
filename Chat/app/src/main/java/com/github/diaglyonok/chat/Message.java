package com.github.diaglyonok.chat;

import java.util.Date;

/**
 * Created by Олег on 02.01.2017.
 */

public class Message {
    private String textMessage;
    private String author;
    private long timeMessage;

    public Message(String textMessage, String author) {
        this.textMessage = textMessage;
        this.author = author;

        timeMessage = new Date().getTime();
    }


    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public long getTimeMessage() {
        return timeMessage;
    }

    public void setTimeMessage(long timeMessage) {
        this.timeMessage = timeMessage;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Message() {
    }
}
