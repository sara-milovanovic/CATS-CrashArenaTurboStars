package com.example.catsproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.catsproject.db.async_tasks.AsyncTaskGetBoxes;
import com.example.catsproject.db.async_tasks.AsyncTaskGetComponents;
import com.example.catsproject.db.async_tasks.AsyncTaskGetComponents2;
import com.example.catsproject.db.async_tasks.AsyncTaskGetGenerate;
import com.example.catsproject.db.async_tasks.AsyncTaskGetPlayerId;
import com.example.catsproject.db.async_tasks.AsyncTaskGetPlayerPassword;
import com.example.catsproject.db.async_tasks.AsyncTaskGetStatistics;
import com.example.catsproject.db.async_tasks.AsyncTaskRemoveAttachedComponentToPlayer;
import com.example.catsproject.db.async_tasks.AsyncTaskRemoveComponentToPlayer;
import com.example.catsproject.db.async_tasks.AsyncTaskSaveBoxes;
import com.example.catsproject.db.async_tasks.AsyncTaskScore;
import com.example.catsproject.db.async_tasks.AsyncTaskUpdateGenerateBox;
import com.example.catsproject.db.async_tasks.AsyncTaskUpdateScore;

import java.util.ArrayList;

public class GarageFragment extends Fragment {

    static MyViewModel model;
    static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.garage_fragment, container, false);
        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        new AsyncTaskGetAttachedComponentsForPlayer(model).execute(model.getCurrentPlayerId().getValue());
        System.out.println("GARAGE");
        new AsyncTaskGetComponents2(model).execute(model.getCurrentPlayerId().getValue());
        //box
        new AsyncTaskGetGenerate(model).execute(model.getCurrentPlayerId().getValue());
        new AsyncTaskScore(model).execute();
        new AsyncTaskGetStatistics(model,view,getActivity()).execute(model.getCurrentPlayerId().getValue(),0);



        ImageView music_control=view.findViewById(R.id.music_control);
        ImageView createCar=view.findViewById(R.id.createCar);
        ImageView runBattle=view.findViewById(R.id.runBattle);
        ImageView logout=view.findViewById(R.id.logout);
        ImageView vehicle=view.findViewById(R.id.vehicle);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTaskSaveBoxes(model).execute(model.getCurrentPlayerId().getValue(),0);
                Navigation.findNavController(view).navigate(R.id.action_garageFragment_to_startFragment);
            }
        });

        runBattle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTaskSaveBoxes(model).execute(model.getCurrentPlayerId().getValue(),1);

                Toast.makeText(getActivity(),"Run game",Toast.LENGTH_SHORT).show();
                Components[] c=new Components[model.currentPlayerAttachedComponents.getValue().size()];
                for(int i=0;i<c.length;i++){
                    c[i]=new Components();
                    c[i].cid=model.currentPlayerAttachedComponents.getValue().get(i).cid;
                    c[i].name=model.currentPlayerAttachedComponents.getValue().get(i).name;
                    c[i].power=model.currentPlayerAttachedComponents.getValue().get(i).power;
                    c[i].energy=model.currentPlayerAttachedComponents.getValue().get(i).energy;
                    c[i].health=model.currentPlayerAttachedComponents.getValue().get(i).health;
                }
                //new AsyncTaskRemoveComponentToPlayer(model).execute(c);
                new AsyncTaskRemoveAttachedComponentToPlayer(model).execute(c);
                Navigation.findNavController(view).navigate(R.id.action_garageFragment_to_battleFragment);
            }
        });

        vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Run game",Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_garageFragment_to_battleFragment);
            }
        });

        createCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigation.findNavController(view).navigate(R.id.action_garageFragment_to_createCarFragment);
                new AsyncTaskSaveBoxes(model).execute(model.getCurrentPlayerId().getValue(),1);

                new AsyncTaskGetComponents(model,view).execute(model.getCurrentPlayerId().getValue());
                new AsyncTaskGetAttachedComponentsForPlayer(model).execute(model.getCurrentPlayerId().getValue());
            }
        });

        music_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DialogMusicFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "missiles");
            }
        });

        ConstraintLayout parent=view.findViewById(R.id.garageFragment);
        parent.setBackgroundResource(R.drawable.bg);

        new AsyncTaskGetBoxes(model).execute(model.getCurrentPlayerId().getValue());

        model.boxActive.observe(getActivity(), new Observer<ArrayList<Boolean>>() {
            @Override
            public void onChanged(ArrayList<Boolean> booleans) {
                String box="box_";
                String open="open_";
                for(int i=0;i<4;i++){
                    if(booleans.get(i)){
                        int b=calculateIdBox(i+1);
                        int o=calculateIdOpen(i+1);
                        ImageView imageView=view.findViewById(b);
                        imageView.setImageResource(R.drawable.box);
                        //Button button=view.findViewById(o);
                        //button.setEnabled(false);
                    }
                }
            }
        });



        model.timeRemaining.observe(getActivity(), new Observer<ArrayList<Integer>>() {
            @Override
            public void onChanged(ArrayList<Integer> integers) {
                String t="text_";
                String open="open_";
                for(int i=0;i<3;i++){
                    if(model.boxActive.getValue().get(i)){
                        int txt=calculateIdText(i+1);
                        TextView textView=view.findViewById(txt);
                        if((model.getBoxTime().getValue().get(i)-model.timeRemaining.getValue().get(i))<=0){
                            int o=calculateIdOpen(i+1);
                            //Button button=view.findViewById(o);
                            //button.setEnabled(true);
                            textView.setText("Ready to open");
                        }
                        else textView.setText(model.getBoxTime().getValue().get(i)-model.timeRemaining.getValue().get(i)+" seconds");
                    }
                }
            }
        });

        view.findViewById(R.id.open_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBox(R.id.open_1,R.id.text_1,R.id.box_1,0);
            }
        });

        view.findViewById(R.id.open_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBox(R.id.open_2,R.id.text_2,R.id.box_2,1);
            }
        });

        view.findViewById(R.id.open_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBox(R.id.open_3,R.id.text_3,R.id.box_3,2);
            }
        });

        if(model.getMusic().getValue()){
            Intent intent = new Intent(getActivity(), PlayerService.class);
            intent.setType("play");
            getActivity().startService(intent);
        }

        /*Button open1=view.findViewById(R.id.open_1);
        open1.setEnabled(false);*/
        /*SNAGA VOZILA IZ BAZE*/
        new AsyncTaskAtributes(model).execute(model.getCurrentPlayerId().getValue());
        /**/
        //OBRISANO

        ImageView statistics=view.findViewById(R.id.statistics);

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTaskScore(model).execute();
                new AsyncTaskGetStatistics(model,view,getActivity()).execute(model.getCurrentPlayerId().getValue(),1);
            }
        });

        return view;
    }

    public static void setVehicleImage() {
        ImageView blade=view.findViewById(R.id.blade);
        blade.setVisibility(View.INVISIBLE);
        ImageView forklift=view.findViewById(R.id.forklift);
        forklift.setVisibility(View.INVISIBLE);
        ImageView wheels1=view.findViewById(R.id.wheels1);
        wheels1.setVisibility(View.INVISIBLE);
        ImageView wheels2=view.findViewById(R.id.wheels2);
        wheels2.setVisibility(View.INVISIBLE);
        ImageView stinger=view.findViewById(R.id.stinger);
        stinger.setVisibility(View.INVISIBLE);
        ImageView chainsaw=view.findViewById(R.id.chainsaw);
        chainsaw.setVisibility(View.INVISIBLE);
        ImageView rocket=view.findViewById(R.id.rocket);
        rocket.setVisibility(View.INVISIBLE);

        for(int i=0;i<model.currentPlayerAttachedComponents.getValue().size();i++){
            switch (model.currentPlayerAttachedComponents.getValue().get(i).cid){
                case 1:{
                    blade.setVisibility(View.VISIBLE);
                    break;
                }
                case 2:{
                    forklift.setVisibility(View.VISIBLE);
                    break;
                }
                case 3:{
                    wheels1.setVisibility(View.VISIBLE);
                    wheels2.setVisibility(View.VISIBLE);
                    break;
                }
                case 4:{
                    chainsaw.setVisibility(View.VISIBLE);
                    break;
                }
                case 5:{
                    rocket.setVisibility(View.VISIBLE);
                    break;
                }
                case 6:{
                    stinger.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }

    }

    private int calculateIdBox(int i) {
        switch (i){
            case 1: return R.id.box_1;
            case 2: return R.id.box_2;
            case 3: return R.id.box_3;
        }
        return R.id.box_1;
    }

    private int calculateIdOpen(int i) {
        switch (i){
            case 1: return R.id.open_1;
            case 2: return R.id.open_2;
            case 3: return R.id.open_3;
        }
        return R.id.open_1;
    }

    private int calculateIdText(int i) {
        switch (i){
            case 1: return R.id.text_1;
            case 2: return R.id.text_2;
            case 3: return R.id.text_3;
        }
        return 0;
    }

    private void openBox(int open, int text, int box, int index){
        if(model.getCurrentPlayerComponents().getValue().size()==6){
            Toast.makeText(getActivity(),"You already have all components",Toast.LENGTH_SHORT).show();
        }
        else{
            int comp=model.generateRandomComponent();
            Log.v("box",comp+"");
            ImageView button=view.findViewById(open);
            //button.setEnabled(false);
            TextView textView=view.findViewById(text);
            textView.setText("Not active");
            ImageView imageView=view.findViewById(box);
            imageView.setImageResource(R.drawable.box_blur);
            model.timeRemaining.getValue().set(index,0);
            model.boxActive.getValue().set(index,false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    if(model.getMusic().getValue()){
                        Intent intent = new Intent(getActivity(), PlayerService.class);
                        intent.setType("pause");
                        getActivity().startService(intent);

                    }
                    Navigation.findNavController(view).navigate(R.id.startFragment);
                    return true;
                }
                return false;
            }
        });
    }
}

