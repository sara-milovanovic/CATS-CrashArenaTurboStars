package com.example.catsproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.catsproject.db.Player;

public class CreateCarFragment extends Fragment {

    MyViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.create_car_fragment, container, false);
        LinearLayout parent=view.findViewById(R.id.create_carFragment);
        LinearLayout gallery=view.findViewById(R.id.gallery);
        LayoutInflater inflater2=LayoutInflater.from(getActivity());

        /*adding weapons*/

        View v=inflater2.inflate(R.layout.weapon,gallery,false);
        ImageView i=v.findViewById(R.id.w);
        i.setImageResource(R.drawable.vehicle);
        gallery.addView(v);
        v=inflater2.inflate(R.layout.weapon,gallery,false);
        i=v.findViewById(R.id.w);
        i.setImageResource(R.drawable.wheels);
        gallery.addView(v);
        v=inflater2.inflate(R.layout.weapon,gallery,false);
        i=v.findViewById(R.id.w);
        i.setImageResource(R.drawable.stinger);
        gallery.addView(v);
        v=inflater2.inflate(R.layout.weapon,gallery,false);
        i=v.findViewById(R.id.w);
        i.setImageResource(R.drawable.forklift);

        gallery.addView(v);
        v=inflater2.inflate(R.layout.weapon,gallery,false);
        i=v.findViewById(R.id.w);
        i.setImageResource(R.drawable.chainsaw);

        gallery.addView(v);

        v=inflater2.inflate(R.layout.weapon,gallery,false);
        i=v.findViewById(R.id.w);
        i.setImageResource(R.drawable.blade);

        gallery.addView(v);

        v=inflater2.inflate(R.layout.weapon,gallery,false);
        i=v.findViewById(R.id.w);
        i.setImageResource(R.drawable.rocket);

        gallery.addView(v);

        /*...*/

        parent.setBackgroundResource(R.drawable.bg);

        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);


        return view;
    }
}

