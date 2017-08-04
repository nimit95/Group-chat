package com.codingblocks.groupchat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.model.Group;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button createNewGroup, joinGroup;

        createNewGroup = (Button) findViewById(R.id.create_new_group);
        joinGroup = (Button) findViewById(R.id.join_group);

        //mDatabase = FirebaseDatabase.getInstance().getReference();

        createNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mDatabase.child("groups").setValue(new Group("bhai",""));
            }
        });

        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
