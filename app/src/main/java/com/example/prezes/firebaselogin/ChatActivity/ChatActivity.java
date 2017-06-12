package com.example.prezes.firebaselogin.ChatActivity;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.prezes.firebaselogin.R;
import com.example.prezes.firebaselogin.model.ChatMessage;
import com.example.prezes.firebaselogin.model.User;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layout;
    RelativeLayout layout_2;
    FloatingActionButton sendButton;
    EditText input;
    ScrollView scrollView;
    private DatabaseReference reference1, reference2;

    private EditText msg_edittext;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser currentUser;
    FirebaseDatabase firebaseDatabase;
    private FirebaseListAdapter<ChatMessage> adapter;

    private FirebaseAuth mAuth;

    public static ArrayList<ChatMessage> chatList = new ArrayList<>();
    ListView listOfMessages;
    private ChatMessage message;
    ArrayAdapter arrayAdapter;
    User user;
    private String sender;
    private String receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        receiver = getIntent().getStringExtra("text");
        toolbar.setTitle(receiver);
        setSupportActionBar(toolbar);

//        layout = (LinearLayout) findViewById(R.id.layout1);
        listOfMessages = (ListView) findViewById(R.id.list_of_messages);
        sendButton = (FloatingActionButton)findViewById(R.id.fab);
        input = (EditText)findViewById(R.id.input);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference1 = firebaseDatabase.getReference();
        reference2 = firebaseDatabase.getReference();

        //getting current logged user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        sender = usernameFromEmail(currentUser.getEmail());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = input.getText().toString();

                if(!messageText.equals("")){
//                    ChatMessage chatMessage = new ChatMessage(messageText, receiver);
//                    reference1.child("message").child(sender).push().setValue(chatMessage);

                    reference1.child("messages").push().setValue(new ChatMessage(messageText,receiver, sender));
                    input.setText("");
                }
            }
        });

        displayChatMessages();


//        reference1.child("message").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                //Map map = dataSnapshot.getValue(Map.class);
////                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
////                String message = map.get("message").toString();
////                String userName = map.get("user").toString();
////
////                if(userName.equals(User.name)){
////                    addMessageBox("You:-\n" + message, 1);
////                } else {
////                    addMessageBox(User.chatWith  + ":-\n" + message, 2);
////                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

    }

    private void displayChatMessages() {
//        ListView listOfMessages = (ListView) findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.message_area, FirebaseDatabase.getInstance().getReference().child("messages")) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {

                if((model.getSender().equals(receiver) && model.getReceiver().equals(sender)) ||
                        (model.getSender().equals(sender) && model.getReceiver().equals(receiver))
                        ) {
                    // Get references to the views of message.xml
                    TextView messageText = (TextView) v.findViewById(R.id.message_text);
                    TextView messageUser = (TextView) v.findViewById(R.id.message_user);
                    TextView messageTime = (TextView) v.findViewById(R.id.message_time);

                    // Set their text
                    messageText.setText(model.getMessageText());
                    messageUser.setText(model.getSender());

                    // Format the date before showing it
                    messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                            model.getMessageTime()));
                }
            }
        };

        listOfMessages.setAdapter(adapter);

//        adapter.registerDataSetObserver(new DataSetObserver() {
//            @Override
//            public void onChanged() {
//                super.onChanged();
//                listOfMessages.setSelection(adapter.getCount() - 1);
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }

    private void showData(DataSnapshot dataSnapshot){

        for(DataSnapshot ds : dataSnapshot.getChildren()){
            ChatMessage cMessage = new ChatMessage();
          //  cMessage.setReceiver(ds.child(receiver).getValue(ChatMessage.class).getReceiver());

         //   System.out.println("receiver " + cMessage.getReceiver());
        }

    }

    public void addMessageBox(String message, int type){

        TextView textView = new TextView(ChatActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1){
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        } else {
            lp2.gravity = Gravity.RIGHT;
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

        if (v.getId() == R.id.fab) {
        }

    }
}
