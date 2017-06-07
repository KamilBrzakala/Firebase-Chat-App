package com.example.prezes.firebaselogin.model;

/**
 * Created by prezes on 2017-05-22.
 */

public class Message {
    private String mName;
    private String mText;
    private String mUid;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Message(String uid,  String text, String name ) {
        mName = name;
        mText = text;
        mUid = uid;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }
}
