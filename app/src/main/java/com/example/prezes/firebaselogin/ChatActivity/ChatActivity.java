package com.example.prezes.firebaselogin.ChatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.prezes.firebaselogin.R;
import com.example.prezes.firebaselogin.SignedIn.ContactListActivity;
import com.example.prezes.firebaselogin.SignedInUserActivity.AccountActivity;
import com.example.prezes.firebaselogin.model.ChatMessage;
import com.example.prezes.firebaselogin.model.User;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;


public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButtonn;
    EditText messageArea;

  //  FloatingActionButton sendButton;
    EditText input;
    ScrollView scrollView;
    private DatabaseReference reference1, reference2;

    private FirebaseUser currentUser;
    FirebaseDatabase firebaseDatabase;
    private FirebaseListAdapter<ChatMessage> adapter;


    ListView listOfMessages;

    private String sender;
    private String receiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        receiver = getIntent().getStringExtra("text");
//        toolbar.setTitle(receiver);
        setSupportActionBar(toolbar);

        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
       // sendButtonn = (ImageView)findViewById(R.id.sendButton);
        sendButtonn = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference1 = firebaseDatabase.getReference();
        reference2 = firebaseDatabase.getReference();

        //getting current logged user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        sender = usernameFromEmail(currentUser.getEmail());


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // uncomment if you want to use displayChatMessages2();


        sendButtonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){

                    reference1.child("messages").child(sender+"_"+receiver).push().setValue(new ChatMessage(messageText,receiver, sender));
                    reference2.child("messages").child(receiver+"_"+sender).push().setValue(new ChatMessage(messageText,receiver, sender));

                    reference1.child("chats").child(sender).child(receiver).setValue(new User(sender, receiver));
                    reference2.child("chats").child(receiver).child(sender).setValue(new User(receiver, sender));

                    messageArea.setText("");

                }
            }
        });

        displayChatMessages();


    }

    private void displayChatMessages() {
        reference1.child("messages").child(sender+"_"+receiver).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                String message = map.get("messageText").toString();
                String userName = map.get("receiver").toString();

                if(map.get("sender").equals(sender)){
                    addMessageBox("You:-\n" + message, 1);
                } else {
                    addMessageBox(map.get("receiver")  + ":-\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    private void displayChatMessages2() {
//
//        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
//                R.layout.message_area, FirebaseDatabase.getInstance().getReference().child("messages").child(sender+"_"+receiver)) {
//            @Override
//            protected void populateView(View v, ChatMessage model, int position) {
//
//              //  if((sender.equals(model.getSender()) && (receiver.equals(model.getReceiver())))
////                        (model.getSender().equals(sender) && model.getReceiver().equals(sender)) ||
////                        (model.getSender().equals(sender) && model.getReceiver().equals(receiver))
//                     //   ) {
//                    // Get references to the views of message.xml
//                    TextView messageText = (TextView) v.findViewById(R.id.message_text);
//                    TextView messageUser = (TextView) v.findViewById(R.id.message_user);
//                    TextView messageTime = (TextView) v.findViewById(R.id.message_time);
//
//                    // Set their text
//                    messageText.setText(model.getMessageText());
//
//                    if(model.getSender().equals(sender)){
//                        messageUser.setText("You");
//                    } else {
//                        messageUser.setText(model.getSender());
//                    }
//
//
//                    // Format the date before showing it
//                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
//                            model.getMessageTime()));
//              //  }
//            }
//        };
//
//        listOfMessages.setAdapter(adapter);
//
//
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        adapter.cleanup();  // uncomment if you want to use displayChatMessages2();
    }


    public void addMessageBox(String message, int type){

        TextView textView = new TextView(ChatActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1){
            lp2.gravity = Gravity.END;
            textView.setBackgroundResource(R.drawable.bubble_in);
        } else {
            lp2.gravity = Gravity.START;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);

    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    @Override
    public void onClick(View v) {

//        if (v.getId() == R.id.fab) {// uncomment if you want to use displayChatMessages2();
//        }

    }
}
