package com.example.catsproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.catsproject.db.Components;
import com.example.catsproject.db.async_tasks.AsyncTaskAtributes;
import com.example.catsproject.db.async_tasks.AsyncTaskGetAttachedComponentsForPlayer;
import com.example.catsproject.db.async_tasks.AsyncTaskGetComponents;
import com.example.catsproject.db.async_tasks.AsyncTaskGetPlayerId;
import com.example.catsproject.db.async_tasks.AsyncTaskGetPlayerPassword;
import com.example.catsproject.db.async_tasks.AsyncTaskRemoveAttachedComponentToPlayer;
import com.example.catsproject.db.async_tasks.AsyncTaskRemoveComponentToPlayer;

import java.util.ArrayList;

public class BFragment extends Fragment {

    static MyViewModel model;
    static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.b_fragment, container, false);
        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        final Vehicle vehicle=new Vehicle();
        vehicle.model=model;
        vehicle.view=view;
        vehicle.c=getActivity();
        vehicle.init();


        return view;
    }


}

