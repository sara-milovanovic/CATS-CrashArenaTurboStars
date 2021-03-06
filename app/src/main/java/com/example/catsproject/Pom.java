package com.example.catsproject;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
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
import com.example.catsproject.db.async_tasks.AsyncTaskAddPlayer;

public class Pom extends Fragment {

    MyViewModel model;
    BattleCustomView battleCustomView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.pom, container, false);

        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);

        Drawable[] dr=new Drawable[2];

        dr[0]=getResources().getDrawable(R.drawable.blade);
        dr[1]=getResources().getDrawable(R.drawable.forklift);
        //dr[0].setBounds(Vehicle.x[0],Vehicle.y[0],Vehicle.x[0]+Vehicle.width[0],Vehicle.y[0]+Vehicle.height[0]);
        //dr[1].setBounds(Vehicle.x[0],Vehicle.y[0],Vehicle.x[0]+Vehicle.width[0],Vehicle.y[0]+Vehicle.height[0]);
        LayerDrawable ld=new LayerDrawable(dr);

        ImageView iv=new ImageView(getActivity());
        iv.setImageDrawable(ld);


        float sx=(float)-1;
        float sy=(float)-1;
        iv.setAdjustViewBounds(true);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setScaleX(sx);
        iv.setScaleY(sy);
        LinearLayout linearLayout=view.findViewById(R.id.pom);
        linearLayout.addView(iv);



        return view;
    }



}
