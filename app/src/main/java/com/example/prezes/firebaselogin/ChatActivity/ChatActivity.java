package com.example.prezes.firebaselogin.ChatActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.example.prezes.firebaselogin.model.ChatMessage;
import com.example.prezes.firebaselogin.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    private DatabaseReference reference1, reference2;

    private EditText msg_edittext;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser currentUser;
    FirebaseDatabase firebaseDatabase;

    private FirebaseAuth mAuth;

    public static ArrayList<ChatMessage> chatList = new ArrayList<>();
    ListView msgListView;
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

        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
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
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
//                    Map<String, String> map = new HashMap<String, String>();
//                    map.put("message", messageText);
//                    map.put("sender", User.name );
                    ChatMessage chatMessage = new ChatMessage( sender,  receiver,  messageText, receiver);
                    reference1.child("message").child(receiver).push().setValue(chatMessage);
//                    reference1.child("message").child(receiver).push().setValue(map);
                    //reference1.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });

        reference1.child("message").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Map map = dataSnapshot.getValue(Map.class);
//                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
//                String message = map.get("message").toString();
//                String userName = map.get("user").toString();
//
//                if(userName.equals(User.name)){
//                    addMessageBox("You:-\n" + message, 1);
//                } else {
//                    addMessageBox(User.chatWith  + ":-\n" + message, 2);
//                }
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

        if (v.getId() == R.id.sendButton) {
        }

    }
}
