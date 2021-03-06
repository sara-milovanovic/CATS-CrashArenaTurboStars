package com.example.catsproject;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.catsproject.db.Components;
import com.example.catsproject.db.Player;
import com.example.catsproject.db.async_tasks.AsyncTaskAddPlayer;
import com.example.catsproject.db.async_tasks.AsyncTaskGetAttachedComponentsForPlayer;
import com.example.catsproject.db.async_tasks.AsyncTaskGetComponents2;
import com.example.catsproject.db.async_tasks.AsyncTaskUpdateAtributes;
import com.example.catsproject.db.async_tasks.AsyncTaskUpdateScore;

import java.time.Duration;
import java.util.ArrayList;

public class BattleFragment extends Fragment {

    MyViewModel model;
    BattleCustomView battleCustomView;
    View view;
    ImageView shoot;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.battle_fragment, container, false);




        battleCustomView=view.findViewById(R.id.battleCustomView);
        model = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        new AsyncTaskGetAttachedComponentsForPlayer(model).execute(model.currentPlayerId.getValue());
        model.setActivity(getActivity());
        model.context.setValue(getActivity());
        battleCustomView.setModel(model);
        battleCustomView.init();

        model.setWin(0);//9.6.

        shoot=view.findViewById(R.id.paw1);

        if(model.player_controls.getValue() && battleCustomView.haveRocket()) shoot.setImageResource(R.drawable.paw);
        else shoot.setImageResource(R.drawable.paw2);

        model.setUserGenerateRocket(false);

        shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(battleCustomView.lost==0) model.setUserGenerateRocket(true);
            }
        });

        battleCustomView.increseLength(battleCustomView.toDraw,1,1);
        battleCustomView.increseLength(battleCustomView.toDraw2,-1,2);

        final ProgressBar pb1=view.findViewById(R.id.progressBar1);
        final ProgressBar pb2=view.findViewById(R.id.progressBar2);

        pb1.setMax(100);
        pb2.setMax(100);

        final int max1,max2;

        pb1.setProgress(50);
        pb2.setProgress(80);

        model.setPlayer1Health(model.getHeart().getValue());
        model.setHeart2(battleCustomView.calculateHealth(battleCustomView.toDraw2));
        model.setPlayer2Health(model.getHeart2().getValue());
        max1=model.getHeart().getValue();
        max2=model.getHeart2().getValue();

        final TextView pl1=view.findViewById(R.id.progress1);
        final TextView pl2=view.findViewById(R.id.progress2);

        model.getHeart().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                pl1.setText(""+integer);
                pb1.setProgress(100*integer/max1);
            }
        });

        model.getHeart2().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                pl2.setText(""+integer);
                pb2.setProgress(100*integer/max2);
            }
        });

        battleCustomView.enemieHaveRocket();
        battleCustomView.haveRocket();
        battleCustomView.haveForklift();
        battleCustomView.enemieHaveForklift();
        model.generateRocket.setValue(false);

        model.postGo(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int counter=0;
                boolean run=true;
                boolean updated_score=false;
                boolean walls=false;
                if((!battleCustomView.enemieHaveWheels() &&
                        !battleCustomView.haveWheels()  &&
                        !battleCustomView.enemieHaveRocket() && !battleCustomView.haveRocket())||
                        (battleCustomView.dontHaveWeapon() && battleCustomView.enemieDontHaveWeapon())){
                    int setWallsDec=0;
                    while(!battleCustomView.sudar) {
                        if(setWallsDec==0){
                            setWallsDec=1;
                            battleCustomView.setWallsDec();
                        }
                        System.out.println("................................................................ZIDOVI");
                        battleCustomView.moveWalls=true;
                        battleCustomView.incWalls=true;
                        battleCustomView.postInvalidate();
                        try{
                            Thread.sleep(30);
                        }
                        catch (Exception e){}
                    }
                    battleCustomView.moveWalls=false;
                    battleCustomView.incWalls=false;
                    int g=0;
                    while(true){
                        battleCustomView.postInvalidate();
                        if(g%10==0){
                            if(model.getHeart().getValue()>0 && model.getHeart2().getValue()>0) {
                                if(battleCustomView.dontHaveWeapon() && battleCustomView.enemieDontHaveWeapon()){
                                    battleCustomView.decreseHealthByWalls();
                                }
                                else battleCustomView.decreseHealth();
                            }
                            else{
                                battleCustomView.drawLost();
                                int c=0;
                                while(c<10){
                                    battleCustomView.postInvalidate();
                                    try {
                                        Thread.sleep(200);
                                        c++;
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if(model.getHeart().getValue()<=0){
                                    model.postWin(0);

                                    new AsyncTaskUpdateScore(model).execute(0);

                                }
                                else{
                                    model.postWin(1);


                                    new AsyncTaskUpdateScore(model).execute(1);

                                }
                                break;
                            }
                        }
                        g++;
                        try{
                            Thread.sleep(30);
                        }
                        catch (InterruptedException e){}
                    }

                    walls=true;
                }
                if((!walls)&&(battleCustomView.haveWheels() || battleCustomView.enemieHaveWheels() || battleCustomView.enemieHaveForklift() || battleCustomView.haveForklift() || battleCustomView.enemieHaveBlade() || battleCustomView.haveBlade() || battleCustomView.enemieHaveRocket() || battleCustomView.haveRocket())){

                    while(run){//DODATI ZAUSTAVLJANJE NITI
                        if(model.go.getValue()) {
                            battleCustomView.postInvalidate();
                            if(model.getHeart().getValue()<=0 || model.getHeart2().getValue()<=0)
                            {
                                run=false;
                                battleCustomView.drawLost();
                                if(model.getHeart().getValue()<=0){
                                    model.postWin(0);
                                    new AsyncTaskUpdateScore(model).execute(0);

                                }
                                else{
                                    model.postWin(1);
                                    new AsyncTaskUpdateScore(model).execute(1);
                                }
                            }
                        }
                        else{
                            battleCustomView.postInvalidate();
                            counter++;

                            if((counter%10)==0){
                                counter=0;
                                if(model.getHeart().getValue()>0 && model.getHeart2().getValue()>0) battleCustomView.decreseHealth();
                                else{
                                    run=false;
                                    battleCustomView.drawLost();
                                    int c=0;
                                    while(c<10){
                                        battleCustomView.postInvalidate();
                                        try {
                                            Thread.sleep(200);
                                            c++;
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                            }
                        }
                        try{
                            Thread.sleep(30);
                        }
                        catch (InterruptedException e){}
                    }

                    if(model.getHeart().getValue()<=0 && !updated_score){
                        updated_score=true;
                        model.postWin(0);
                        new AsyncTaskUpdateScore(model).execute(0);

                    }
                    else{
                        model.postWin(1);
                        new AsyncTaskUpdateScore(model).execute(1);
                    }
                }


            }
        }).start();

        ImageView back=view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setPlayed(true);
                Navigation.findNavController(view).navigate(R.id.action_battleFragment_to_garageFragment);
            }
        });

        GestureDetector.SimpleOnGestureListener onGestureListener=
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onDown(MotionEvent e){return true;}
                };

        final GestureDetector gestureDetector=new GestureDetector(getActivity(),onGestureListener);

        battleCustomView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Log.v("Koordinate-------->: ",motionEvent.getX()+", "+motionEvent.getY());

                //editVehicleCustomView.invalidate();

                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:{
                        /*if(editVehicleCustomView.componentExist(1)&&(!editVehicleCustomView.placed.get(0) && motionEvent.getX()> EditVehicleCustomView.c_default.get(0).getX() && motionEvent.getX()<200+ EditVehicleCustomView.c_default.get(0).getX()&& motionEvent.getY()> EditVehicleCustomView.c_default.get(0).getY() && motionEvent.getY()<200+ EditVehicleCustomView.c_default.get(0).getY())){
                            editVehicleCustomView.moveBlade();
                        }
                        else {
                            if (editVehicleCustomView.componentExist(1)&&(editVehicleCustomView.placed.get(0) && motionEvent.getX()> EditVehicleCustomView.c.get(0).getX() && motionEvent.getX()<200+ EditVehicleCustomView.c.get(0).getX()&& motionEvent.getY()> EditVehicleCustomView.c.get(0).getY() && motionEvent.getY()<200+ EditVehicleCustomView.c.get(0).getY())) {
                                //vrati deo na mesto
                                editVehicleCustomView.removeWeapon(1);

                            } else {
                                if (editVehicleCustomView.componentExist(2)&&(!editVehicleCustomView.placed.get(1) && motionEvent.getX() > EditVehicleCustomView.c_default.get(1).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c_default.get(1).getX() && motionEvent.getY() > EditVehicleCustomView.c_default.get(1).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c_default.get(1).getY())
                                ){
                                    editVehicleCustomView.moveForklift();
                                }
                                else{
                                    if(editVehicleCustomView.componentExist(2)&&(editVehicleCustomView.placed.get(1) && motionEvent.getX() > EditVehicleCustomView.c.get(1).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c.get(1).getX() && motionEvent.getY() > EditVehicleCustomView.c.get(1).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c.get(1).getY())) {
                                        editVehicleCustomView.removeWeapon(2);
                                    }
                                    else {
                                        if (editVehicleCustomView.componentExist(3)&&(!editVehicleCustomView.placed.get(2) && motionEvent.getX() > EditVehicleCustomView.c_default.get(2).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c_default.get(2).getX() && motionEvent.getY() > EditVehicleCustomView.c_default.get(2).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c_default.get(2).getY())
                                        ) {
                                            editVehicleCustomView.moveWheels();
                                        }
                                        else{
                                            if(editVehicleCustomView.componentExist(3)&&(editVehicleCustomView.placed.get(2) && motionEvent.getX() > EditVehicleCustomView.c.get(2).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c.get(2).getX() && motionEvent.getY() > EditVehicleCustomView.c.get(2).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c.get(2).getY())){
                                                editVehicleCustomView.removeWeapon(3);
                                            }
                                            else {
                                                if (editVehicleCustomView.componentExist(4)&&(!editVehicleCustomView.placed.get(3) && motionEvent.getX() > EditVehicleCustomView.c_default.get(3).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c_default.get(3).getX() && motionEvent.getY() > EditVehicleCustomView.c_default.get(3).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c_default.get(3).getY())) {
                                                    editVehicleCustomView.moveChainsaw();
                                                }
                                                else{
                                                    if(editVehicleCustomView.componentExist(4)&&(editVehicleCustomView.placed.get(3) && motionEvent.getX() > EditVehicleCustomView.c.get(3).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c.get(3).getX() && motionEvent.getY() > EditVehicleCustomView.c.get(3).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c.get(3).getY())){
                                                        editVehicleCustomView.removeWeapon(4);
                                                    }
                                                    else{
                                                        if (editVehicleCustomView.componentExist(5)&&(!editVehicleCustomView.placed.get(4) && motionEvent.getX() > EditVehicleCustomView.c_default.get(4).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c_default.get(4).getX() && motionEvent.getY() > EditVehicleCustomView.c_default.get(4).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c_default.get(4).getY())) {
                                                            editVehicleCustomView.moveRocket();
                                                        }
                                                        else{
                                                            if(editVehicleCustomView.componentExist(5)&&(editVehicleCustomView.placed.get(4) && motionEvent.getX() > EditVehicleCustomView.c.get(4).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c.get(4).getX() && motionEvent.getY() > EditVehicleCustomView.c.get(4).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c.get(4).getY())){
                                                                editVehicleCustomView.removeWeapon(5);
                                                            }
                                                            else{
                                                                if (editVehicleCustomView.componentExist(6)&&(!editVehicleCustomView.placed.get(5) && motionEvent.getX() > EditVehicleCustomView.c_default.get(5).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c_default.get(5).getX() && motionEvent.getY() > EditVehicleCustomView.c_default.get(5).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c_default.get(5).getY())) {
                                                                    editVehicleCustomView.moveStinger();
                                                                }
                                                                else{
                                                                    if(editVehicleCustomView.componentExist(6)&&(editVehicleCustomView.placed.get(5) && motionEvent.getX() > EditVehicleCustomView.c.get(5).getX() && motionEvent.getX() < 200 + EditVehicleCustomView.c.get(5).getX() && motionEvent.getY() > EditVehicleCustomView.c.get(5).getY() && motionEvent.getY() < 200 + EditVehicleCustomView.c.get(5).getY())){
                                                                        editVehicleCustomView.removeWeapon(6);
                                                                    }
                                                                    else{
                                                                        if ((EditVehicleCustomView.c.get(0).getX() < 50 && motionEvent.getX() > EditVehicleCustomView.leftC.getX() && motionEvent.getX() < 80 + EditVehicleCustomView.leftC.getX() && motionEvent.getY() > EditVehicleCustomView.leftC.getY() && motionEvent.getY() < 50 + EditVehicleCustomView.leftC.getY())) {
                                                                            editVehicleCustomView.moveLeft();
                                                                        } else {
                                                                            if ((EditVehicleCustomView.c.get(5).getX() > 900 && motionEvent.getX() > EditVehicleCustomView.rightC.getX() && motionEvent.getX() < 80 + EditVehicleCustomView.rightC.getX() && motionEvent.getY() > EditVehicleCustomView.rightC.getY() && motionEvent.getY() < 50 + EditVehicleCustomView.rightC.getY())) {
                                                                                //numOfWeapons-1 zamenjeno sa 5
                                                                                editVehicleCustomView.moveRight();
                                                                            } else {
                                                                                if(motionEvent.getX()>500 && motionEvent.getX()<740 && motionEvent.getY()>610 && motionEvent.getY()<760){
                                                                                    editVehicleCustomView.saveCar();
                                                                                    new AsyncTaskGetComponents2(model).execute(model.getCurrentPlayerId().getValue());
                                                                                    new AsyncTaskUpdateAtributes(model).execute(model.getHeart().getValue(), model.getSword().getValue(), model.getThunder().getValue());
                                                                                    Navigation.findNavController(view).navigate(R.id.action_canvasFragment_to_garageFragment);
                                                                                }
                                                                                else{
                                                                                    System.out.println("nista");
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }



                                    }
                                }


                            }
                        }*/
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        //editVehicleCustomView.moveNothing();
                        //editVehicleCustomView.clearLists();
                        break;
                    }
                    /*case MotionEvent.ACTION_MOVE:{
                       if((EditVehicleCustomView.c.get(0).getX()>50 &&motionEvent.getX()> EditVehicleCustomView.leftC.getX() && motionEvent.getX()<80+ EditVehicleCustomView.leftC.getX()&& motionEvent.getY()> EditVehicleCustomView.leftC.getY() && motionEvent.getY()<50+ EditVehicleCustomView.leftC.getY())){
                            editVehicleCustomView.moveLeft();
                        }
                        else {
                            if((EditVehicleCustomView.c.get(5).getX()>900 &&  motionEvent.getX()> EditVehicleCustomView.rightC.getX() && motionEvent.getX()<80+ EditVehicleCustomView.rightC.getX()&& motionEvent.getY()> EditVehicleCustomView.rightC.getY() && motionEvent.getY()<50+ EditVehicleCustomView.rightC.getY())){
                                editVehicleCustomView.moveRight();
                            }
                            else {
                                System.out.println("nista");
                            }
                        }

                        int tActionIndex=motionEvent.getActionIndex();
                        int tPointerCount=motionEvent.getPointerCount();
                        Log.v("cnt",motionEvent.getPointerCount()+"");
                        for(int i=0;i<tPointerCount;i++){
                            EditVehicleCustomView.Coordinate c=new EditVehicleCustomView.Coordinate(motionEvent.getX(i),motionEvent.getY(i));
                            editVehicleCustomView.add(c,motionEvent.getPointerId(i));
                            //Log.v("petlja",i+"");
                        }

                        battleCustomView.invalidate();
                        break;

                    }*/



                }


                return  true;
            }
        });


        return view;
    }


}
