package com.example.prezes.firebaselogin.ChatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prezes.firebaselogin.R;
import com.example.prezes.firebaselogin.SignInActivity.MainActivity;
import com.example.prezes.firebaselogin.SignedInUserActivity.AccountActivity;
import com.example.prezes.firebaselogin.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity {

    Message message;
    EditText editText;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    List<String> usernameList = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView userListView;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        text = getIntent().getStringExtra("text");
        toolbar.setTitle(text);
        setSupportActionBar(toolbar);
        //toolbar.setTitle(); // ustawic tu kliknieta osobe z listy (chyba przez intent)

        userListView = (ListView) findViewById(R.id.userListView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.sendMessage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = (EditText) findViewById(R.id.editText);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("text", editText.getText().toString());

            }
        });




//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mAuth = FirebaseAuth.getInstance();
//
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//
//                   // sendMessage(user.getUid(), user.getEmail(), user.getEmail());
//
//                    Toast.makeText(getApplicationContext(),"user signed in", Toast.LENGTH_LONG).show();
//                    Log.d("Status", "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Toast.makeText(getApplicationContext(),"user signed out", Toast.LENGTH_LONG).show();
//                    Log.d("Status", "onAuthStateChanged:signed_out");
//                }
//
//
//            }
//        };







    }

    private void sendMessage(String name, String text, String uid) {

      //   user = firebaseAuth.getCurrentUser();

    }

}
