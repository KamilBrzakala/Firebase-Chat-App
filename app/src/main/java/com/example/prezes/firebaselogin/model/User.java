package com.example.prezes.firebaselogin.model;

import com.google.firebase.auth.FirebaseUser;

public  class User  {

    public String name;
    public String sender;
    public String email;
    public String userId;
    public String chatWith = "";

    public User() {
// Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public User(String sender, String chatWith) {
        this.sender = sender;
        this.chatWith = chatWith;

    }
    public User(String userId, String name, String email) {
        this.name = name;
        this.email = email;
        this.userId = userId;
    }

    public String getChatWith() { return chatWith; }

    public void setChatWith(String chatWith) { this.chatWith = chatWith; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String email) {
        this.userId = userId;
    }


}
