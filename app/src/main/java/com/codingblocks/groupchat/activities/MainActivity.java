package com.codingblocks.groupchat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codingblocks.groupchat.FirebaseReference;
import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.adapters.recyclerAdapters.GroupFeedRecyclerAdapter;
import com.codingblocks.groupchat.model.Group;
import com.codingblocks.groupchat.model.Message;
import com.codingblocks.groupchat.model.User;
import com.codingblocks.groupchat.sharedPref.SuperPrefs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;


public class MainActivity extends AppCompatActivity {


    private SuperPrefs superPrefs;
    private ArrayList<Group> usersGroupList;
    private User currentUser;
    private RecyclerView groupFeedRecyclerView;
    private GroupFeedRecyclerAdapter groupFeedRecyclerAdapter;
    private EditText groupName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button createNewGroup, joinGroup;

        createNewGroup = (Button) findViewById(R.id.create_new_group);
        joinGroup = (Button) findViewById(R.id.join_group);
        groupName = (EditText) findViewById(R.id.group_name);

        superPrefs = new SuperPrefs(this);
        usersGroupList = new ArrayList<>();

        setUpFAB();

        groupFeedRecyclerView = (RecyclerView) findViewById(R.id.group_feed_recycler);
        groupFeedRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        groupFeedRecyclerView.setHasFixedSize(true);
        groupFeedRecyclerAdapter = new GroupFeedRecyclerAdapter(MainActivity.this, usersGroupList);


        retrieveUser();

        addGroupsToView();

        createNewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create new group on firebase groups

                if(groupName.getText().toString().compareTo("")==0){
                    Toast.makeText(MainActivity.this,"Enter a group name",Toast.LENGTH_SHORT).show();
                }
                else {
                    Group group = createGroupFirebase(groupName.getText().toString());

                    //Add group to the user
                    usersGroupList.add(group);


                    for (Group g : usersGroupList) {
                        Log.e("nimit", "user's group are: " + g.getGroupName());
                    }
                    saveGroupToUser();


                    groupName.setText("");
                }
            }
        });

        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String groupId = groupName.getText().toString();

                if(checkAlreadyadded(groupId)){
                    Toast.makeText(MainActivity.this, "Already added to this group", Toast.LENGTH_SHORT).show();
                }

                else {
                    //if not alread added
                    checkAndInFirebase(groupId);

                }

            }
        });

    }

    private boolean checkAlreadyadded(String groupId) {
        for(Group group : usersGroupList) {
            if(group.getGroupID().compareToIgnoreCase(groupId)==0) {
                return true;
            }
        }
        return false;
    }

    private void checkAndInFirebase(final String groupId) {

        FirebaseReference.groupsReference.child(groupId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null) {
                    usersGroupList.add(dataSnapshot.getValue(Group.class));
                    saveGroupToUser();
                    groupName.setText("");
                    FirebaseReference.groupsReference.child(groupId).removeEventListener(this);
                }
                else{
                    Toast.makeText(MainActivity.this, "Wrong goupID", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addGroupsToView() {
        groupFeedRecyclerView.setAdapter(groupFeedRecyclerAdapter);
    }

    private void refreshGroup() {
        groupFeedRecyclerAdapter.notifyDataSetChanged();
    }
    private Group createGroupFirebase(String groupName) {
        /// How to follow MVC here ?
        DatabaseReference newGroupRef = FirebaseReference.groupsReference.push();
        Group group = new Group(groupName, newGroupRef.getKey(), new ArrayList<Message>());
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


        Log.e("App", "retrive Group function called");

        FirebaseReference.userReference.child(currentUser.getUserId()).child("usersGroup").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("count",dataSnapshot.getChildrenCount()+"");
                ArrayList<String> groupIDs = new ArrayList<>();
                for (DataSnapshot elem : dataSnapshot.getChildren()) {
                    Log.e("dkfn",elem.getValue(String.class));
                    groupIDs.add(elem.getValue(String.class));

                }
                Log.e("groupIds count", groupIDs.size()+"");
                groupIdToGroups(groupIDs);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void groupIdToGroups(ArrayList<String> groupIDs) {
        for(int i=0;i<groupIDs.size();i++) {
            Log.e("groupIdToGroups", groupIDs.get(i) );
        }
        for (String groupId : groupIDs) {
            Log.e("group",groupId);
            final String finalGroupID = groupId;
            usersGroupList.clear();
            FirebaseReference.groupsReference.child(groupId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Add elements to usersGroupList
                    usersGroupList.add(dataSnapshot.getValue(Group.class));
                    Log.e("list size", usersGroupList.size() + "");
                    refreshGroup();
                    FirebaseReference.groupsReference.child(finalGroupID).removeEventListener(this);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
/*        FirebaseReference.groupsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usersGroupList.clear();
                for(DataSnapshot groupShot:dataSnapshot.getChildren()) {
                    usersGroupList.add(groupShot.getValue(Group.class));
                }
                refreshGroup();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }
    private void retrieveUser() {
        Log.e("nimit","knm" +getFirebaseUserId());
        FirebaseReference.userReference.child(getFirebaseUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentUser = dataSnapshot.getValue(User.class);
                Log.e("retrieve user", "onDataChange: " + currentUser.getName() );
                retrieveGroups();
                FirebaseReference.userReference.child(getFirebaseUserId()).removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private String getFirebaseUserId() {
        return superPrefs.getString("user-id");
    }



    private void setUpFAB() {
        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fab_speed_dial);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity
                switch (menuItem.getItemId()) {
                    case
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                //newGame();
                return true;
            case R.id.help:
                //showHelp();
                return true;
            case R.id.feedback:
                return true;
            case R.id.sign_out:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
