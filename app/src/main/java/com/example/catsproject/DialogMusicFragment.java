package com.example.catsproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

public class DialogMusicFragment extends DialogFragment {

    ArrayList<Integer> selectedItems;
    MyViewModel model;
    String state="stopped";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);

        selectedItems = new ArrayList();  // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        String[] toppings=new String[2];
        toppings[0]="music on/off";
        toppings[1]="player controls the game";
        boolean[] options=new boolean[2];

        options[0]=model.music.getValue();
        options[1]=model.player_controls.getValue();
        final Context c=getActivity();

        builder.setTitle("Settings")
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(toppings, options,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    Log.v("which=",which+"");
                                    // If the user checked the item, add it to the selected items
                                    selectedItems.add(which);
                                    if(which==0){
                                        model.setMusic_my(true);
                                        Intent intent = new Intent(c, PlayerService.class);
                                        intent.setType("play");
                                        getActivity().startService(intent);
                                        state="started";
                                    }
                                    else{
                                        model.setPlayerControls_my(true);
                                    }
                                } else {
                                    if(which==0){
                                        model.setMusic_my(false);
                                        Intent intent = new Intent(c, PlayerService.class);
                                        getActivity().stopService(intent);
                                        state="stopped";
                                    }
                                    else{
                                        model.setPlayerControls_my(false);
                                    }
                                    if (selectedItems.contains(which)) {
                                    Log.v("which=",which+"");
                                    // Else, if the item is already in the array, remove it
                                    selectedItems.remove(Integer.valueOf(which));

                                }
                                }
                            }
                        })
                // Set the action buttons
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the selectedItems results somewhere
                        // or return them to the component that opened the dialog
                   //...
                    }
                })
                /*.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                   //...
                    }
                })*/;

        return builder.create();
    }
}