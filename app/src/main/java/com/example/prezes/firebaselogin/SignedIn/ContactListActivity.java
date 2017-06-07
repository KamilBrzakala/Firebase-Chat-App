package com.example.prezes.firebaselogin.SignedIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prezes.firebaselogin.ChatActivity.ChatActivity;
import com.example.prezes.firebaselogin.R;
import com.example.prezes.firebaselogin.SignInActivity.MainActivity;
import com.example.prezes.firebaselogin.SignedInUserActivity.AccountActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.prezes.firebaselogin.R.id.userListView;

public class ContactListActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient mGoogleApiClient;
    ProgressBar progressBar;

    private static final String TAG = "UserList" ;

    private ValueEventListener mUserListListener;

    private FirebaseUser currentUser;
    DatabaseReference myRef;
    FirebaseDatabase firebaseDatabase;

    List<String> usernameList2 = new ArrayList<>();
    ArrayAdapter arrayAdapter2;
    ListView userListView2;
    private FirebaseAuth firebaseAuth;

    String userName;
    String userID;
    String loggedUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Contacts");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userListView2 = (ListView) findViewById(R.id.userListView2);
        usernameList2.add("aa");



        //Firebase

        initFirebase();

        //getting current logged user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        //populating list of users
        addEventFirebaseListener(currentUser);

        onClickListener(userListView2);


        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {

                    startActivity(new Intent(ContactListActivity.this, MainActivity.class));

                }
            }
        };

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

    }


    private void initFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference();

    }

    private void onClickListener(final ListView userListView2){

        userListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ContactListActivity.this, ChatActivity.class);

                // selected item
                String selectedFromList =(String) (userListView2.getItemAtPosition(position));

                intent.putExtra("text", selectedFromList);

                startActivity(intent);

            }
        });

    }

    private void addEventFirebaseListener(final FirebaseUser currentUser){

        progressBar = (ProgressBar) findViewById(R.id.circular_progress);
        progressBar.setVisibility(View.VISIBLE);

        myRef.child("users").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, Object> users = (HashMap<String, Object>) dataSnapshot.getValue();

                if(usernameList2.size() > 0){
                    usernameList2.clear();
                }

                for(Object user : users.values()) {
                    HashMap<String, Object> userMap = (HashMap<String, Object>) user;

                    userName = userMap.get("name").toString();
                    userID = userMap.get("userId").toString();
                    loggedUserId = currentUser.getUid();

                    if(!userID.equals(loggedUserId)){
                        usernameList2.add(userName);
                    }
                }

                progressBar.setVisibility(View.INVISIBLE);

                arrayAdapter2 = new ArrayAdapter(ContactListActivity.this, android.R.layout.simple_list_item_1, usernameList2);
                userListView2.setAdapter(arrayAdapter2);
                arrayAdapter2.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        mAuth.addAuthStateListener(authStateListener);
//        mGoogleApiClient.connect();
//
//    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
