package com.example.prezes.firebaselogin.SignedInUser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prezes.firebaselogin.SignInView.MainActivity;
import com.example.prezes.firebaselogin.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseUser user;
    private static final String TAG = "UserList" ;
    private DatabaseReference userlistReference;
    private ValueEventListener mUserListListener;

    ArrayList<String> usernameList = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ListView userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        userListView = (ListView) findViewById(R.id.userListView);

        //User newUser = new User(username, email);
        //usernameList.add(myRef.child("users").child(userId).get(newUser);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, usernameList);

        userListView.setAdapter(arrayAdapter);


        //Add toolbar
        Toolbar toolbar = (Toolbar)  findViewById(R.id.toolbar);
        toolbar.setTitle("Contact List");
        setSupportActionBar(toolbar);

        // [START initialize_database_ref]

        // [END initialize_database_ref]
        //myRef.setValue("Hello");


        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {

                    startActivity(new Intent(AccountActivity.this, MainActivity.class));

                }
            }
        };

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.menuId){

            signOut();
            mAuth.signOut();

            return true;
        }

        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(authStateListener);
        mGoogleApiClient.connect();

    }



    @Override
    public void onClick(View v) {

//        if (v.getId() == R.id.logOutButn) {
//            signOut();
//            mAuth.signOut();
//        }

    }

    private void updateUI(boolean isLogin){


    }

    private void signOut() {

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Log.i("Sds","asdfs");
            }
        });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


}
