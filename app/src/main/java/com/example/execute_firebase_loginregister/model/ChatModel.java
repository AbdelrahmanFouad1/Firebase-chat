package com.example.execute_firebase_loginregister.model;

public class ChatModel {

    private String message;
    private String senderName;
    private String senderUid;
    private String receiverName;
    private String receiverUid;

    public ChatModel(String message, String senderName, String senderUid, String receiverName, String receiverUid) {
        this.message = message;
        this.senderName = senderName;
        this.senderUid = senderUid;
        this.receiverName = receiverName;
        this.receiverUid = receiverUid;
    }

    public ChatModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverUid() {
        return receiverUid;
    }

    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }
}
