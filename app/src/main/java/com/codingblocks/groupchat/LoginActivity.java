package com.codingblocks.groupchat;

import android.*;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codingblocks.groupchat.activities.MainActivity;
import com.codingblocks.groupchat.location.CurrentLocation;
import com.codingblocks.groupchat.model.Group;
import com.codingblocks.groupchat.model.User;
import com.codingblocks.groupchat.sharedPref.SuperPrefs;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1001;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    CurrentLocation currentLocation = new CurrentLocation(LoginActivity.this);
                    currentLocation.setCurrentLocationAndMoveToNextActivity();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    askForPermission();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                //.enableAutoManage(, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        askForPermission();
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
       // Log.e("yeh chal rha hai",currentUser.toString());
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            //askForPermission();

            if(isLocationPresent())
                startMainActivity();
            else
                askForPermission();
        }
        if(currentUser==null){
            Log.e(TAG, "updateUI: null aa rha hai" );
        }
    }
    private void startMainActivity(){

        SuperPrefs prefs = new SuperPrefs(LoginActivity.this);
        Log.e(TAG, "longitude from Prefs "+prefs.getString("lon") );
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void login(final FirebaseUser currentUser) {

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference userIdToFirebaseRef = mDatabase.child("userIdToUser");
        Log.e(TAG, "login: "+userIdToFirebaseRef.getRef() );


       userIdToFirebaseRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               if(!dataSnapshot.hasChild(currentUser.getUid())){

                   if(isLocationPresent())
                       createNewUser(currentUser,mDatabase);
                   else
                       askForPermission();
               }
               else{
                   getFirebaseUserId(userIdToFirebaseRef.child(currentUser.getUid()));
               }
               updateUI(currentUser);
               userIdToFirebaseRef.removeEventListener(this);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

    }
    Boolean isLocationPresent(){
        SuperPrefs prefs = new SuperPrefs(LoginActivity.this);

        if(!prefs.stringExists("lat"))
            return false;
        return true;
    }
    private void createNewUser(FirebaseUser currentUser,DatabaseReference mDatabase) {
        DatabaseReference users = mDatabase.child("users").push();

        User user = new User(users.getKey(),
                currentUser.getDisplayName(), new ArrayList<String>());
        users.setValue(user);

        HashMap<String, String> hm = new HashMap<>();
        hm.put(currentUser.getUid(), users.getKey());
        mDatabase.child("userIdToUser").child(currentUser.getUid()).setValue(users.getKey());
        Log.e("user-id-create new", users.getKey());

        SuperPrefs pref = new SuperPrefs(LoginActivity.this);
        pref.setString("user-id", users.getKey());
        pref.setString("user-name", user.getName());
    }
    private void getFirebaseUserId(final DatabaseReference currentUserIdToFirebaseRef) {


        currentUserIdToFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //HashMap<String,String> hm = (HashMap<String, String>) dataSnapshot.getValue();
                Log.e("user-id-getFirebase", dataSnapshot.getValue(String.class));
                new SuperPrefs(LoginActivity.this).setString("user-id",dataSnapshot.getValue(String.class));
                currentUserIdToFirebaseRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.e(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            login(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                    }
                });
    }

    private void askForPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
}
