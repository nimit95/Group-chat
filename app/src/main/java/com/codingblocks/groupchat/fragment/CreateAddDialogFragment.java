package com.codingblocks.groupchat.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.codingblocks.groupchat.R;
import com.codingblocks.groupchat.activities.MainActivity;

public class CreateAddDialogFragment extends DialogFragment {


    private static final String groupActionParam = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String groupAction;
    private String mParam2;

    public CreateAddDialogFragment() {
        // Required empty public constructor
    }


    public static CreateAddDialogFragment newInstance(String param1, String param2) {
        CreateAddDialogFragment fragment = new CreateAddDialogFragment();
        Bundle args = new Bundle();
        args.putString(groupActionParam, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupAction = getArguments().getString(groupActionParam);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return inflater.inflate(R.layout.fragment_create_add_dialog, container, false);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_create_add_dialog, null);

        final EditText groupDetail = view.findViewById(R.id.group_detail);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        if(groupAction.compareToIgnoreCase("create")==0) {
            groupDetail.setHint("Enter Group Name");
            alertDialogBuilder.setTitle("Create A New Group");
        }
        else{
            groupDetail.setHint("Enter group Unique Key");
            alertDialogBuilder.setTitle("Join A Group");
        }


        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton(groupAction, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(groupAction.compareToIgnoreCase("create")==0) {
                        ((MainActivity) getActivity()).createGroup(groupDetail.getText().toString());
                }
                else {
                    ((MainActivity) getActivity()).joinGroup(groupDetail.getText().toString());
                }
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return alertDialogBuilder.create();
    }

}
