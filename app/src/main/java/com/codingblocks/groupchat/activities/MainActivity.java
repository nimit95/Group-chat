package com.codingblocks.groupchat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codingblocks.groupchat.FirebaseReference;
import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.model.Group;
import com.codingblocks.groupchat.model.User;
import com.codingblocks.groupchat.sharedPref.SuperPrefs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private SuperPrefs superPrefs;
    private ArrayList<Group> usersGroupList;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button createNewGroup, joinGroup;
        final EditText groupName;

        createNewGroup = (Button) findViewById(R.id.create_new_group);
        joinGroup = (Button) findViewById(R.id.join_group);
        groupName = (EditText) findViewById(R.id.group_name);

        superPrefs = new SuperPrefs(this);
        usersGroupList = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        retrieveUser();



        createNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create new group

                Group group = createGroupFirebase(groupName.getText().toString());

                //Add group to the user
                usersGroupList.add(group);
                for(Group g:usersGroupList) {
                    Log.e("nimit", "onCreate: "+ g.getGroupID() );
                }
                saveGroupToUser();


                groupName.setText("");
            }
        });

        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private Group createGroupFirebase(String groupName) {
        /// How to follow MVC here ?
        DatabaseReference newGroupRef = FirebaseReference.groupsReference.push();
        Group group = new Group(groupName, newGroupRef.getKey(), "");
        newGroupRef.setValue(group);
        return group;
    }

    private void saveGroupToUser() {
        ArrayList<String> groupIDs = new ArrayList<>();
        for (int i = 0; i < usersGroupList.size(); i++)
            groupIDs.add(usersGroupList.get(i).getGroupID());
        FirebaseReference.userReference.child(currentUser.getUserId()).child("usersGroup")
                .setValue(groupIDs);
    }

    private void retrieveGroups() {
        final ArrayList<String> groupIDs = new ArrayList<>();
        FirebaseReference.userReference.child(currentUser.getUserId()).child("usersGroup").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot elem : dataSnapshot.getChildren()) {
                    groupIDs.add(elem.getValue(String.class));
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        for (String groupId : groupIDs) {
            FirebaseReference.groupsReference.child(groupId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Add elements to usersGroupList
                    usersGroupList.add(dataSnapshot.getValue(Group.class));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    private void retrieveUser() {
        Log.e("nimit","knm" +getFirebaseUserId());
        FirebaseReference.userReference.child(getFirebaseUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                Log.e("retrieve user", "onDataChange: " + currentUser.getName() );
                retrieveGroups();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private String getFirebaseUserId() {
        return superPrefs.getString("user-id");
    }

}
