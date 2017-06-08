package com.example.prezes.firebaselogin.model;

/**
 * Created by prezes on 2017-05-22.
 */

public class ChatMessage {

    public String message, sender, receiver, title;


    public ChatMessage() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public ChatMessage(String Sender, String Receiver, String Message, String Title) {

        message = Message;
        sender = Sender;
        receiver = Receiver;
        title = Title;

    }

}
