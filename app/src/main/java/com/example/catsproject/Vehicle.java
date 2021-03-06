package com.example.catsproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.catsproject.db.Components;

import java.time.Duration;
import java.util.ArrayList;

public class Vehicle {
    public static int height[]={200,200,200,200,200,200};
    public static int width[]={200,300,300,300,300,300};

    public static int heart[]={10,10,10,10,10,10};
    public static int sword[]={10,10,10,23,7,9};
    public static int thunder[]={6,2,10,6,4,4};

    public int x[]={240,250,25,480,400,400};
    public int x_[]={240,250,25,480,400,400};
    public int y[]={430,400,465,390,390,390};

    public static int carHeight=300;
    public static int carWidth=500;

    public int carX=100;
    public int carXX=100;
    public int carY=350;

    public int wheel2X=220;
    public int wheel2Y=465;

    public int screenHeight, screenWidth;

    MyViewModel model;
    BattleCustomView battleCustomView;
    View view;
    Context c;

    float pivotX= (float) (width[0]*0.83), pivotY= (float) (height[0]*0.52);
    float xx,yy;

    Drawable blade, forklift, wheels, wheels2, rocket, chainsaw, stinger, bg, heart_;

    ArrayList<Drawable> toDraw=new ArrayList<Drawable>();

    ArrayList<Components> components;

    ArrayList<Boolean> placed=new ArrayList<>();

    Drawable car;
    private float imageWidth;
    private float imageHeight;
    private float scaleWidth;
    private float scaleHeight;
    private float angle;

    public static int getIndex(String name){
        switch (name){
            case "blade":{
                return 0;
            }
            case "forklift":{
                return 1;
            }
            case "wheels":{
                return 2;
            }
            case "chainsaw":{
                return 3;
            }
            case "rocket":{
                return 4;
            }
            case "stinger":{
                return 5;
            }

        }
        return 0;
    }

    public static int getIndex(Integer name){
        switch (name){
            case R.drawable.blade:{
                return 0;
            }
            case R.drawable.forklift:{
                return 1;
            }
            case R.drawable.wheels:{
                return 2;
            }
            case R.drawable.chainsaw:{
                return 3;
            }
            case R.drawable.rocket:{
                return 4;
            }
            case R.drawable.stinger:{
                return 5;
            }

        }
        return 0;
    }

    void setScreenDimensions(Activity a){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }

    public void init(){

        blade=c.getResources().getDrawable(R.drawable.blade); //slika
        forklift=c.getResources().getDrawable(R.drawable.forklift);
        wheels=c.getResources().getDrawable(R.drawable.wheels);
        wheels2=c.getResources().getDrawable(R.drawable.wheels);
        rocket=c.getResources().getDrawable(R.drawable.rocket);
        chainsaw=c.getResources().getDrawable(R.drawable.chainsaw);
        stinger=c.getResources().getDrawable(R.drawable.stinger);
        bg=c.getResources().getDrawable(R.drawable.bg);
        heart_=c.getResources().getDrawable(R.drawable.heart);

        car=c.getResources().getDrawable(R.drawable.vehicle_2);

        for(int i=0;i<6;i++){
            placed.add(false);
        }
        components= (ArrayList<Components>) model.getCurrentPlayerAttachedComponents().getValue();
        for(int i=0;model.getCurrentPlayerAttachedComponents().getValue()!=null && i<model.getCurrentPlayerAttachedComponents().getValue().size();i++){
            int index=model.getCurrentPlayerAttachedComponents().getValue().get(i).cid;
            placed.set(index-1,true);
        }
        if(placed.get(0)){
            toDraw.add(blade);
        }
        if(placed.get(1)){
            toDraw.add(forklift);
        }
        if(placed.get(2)){
            toDraw.add(wheels);
        }
        if(placed.get(3)){
            toDraw.add(chainsaw);
        }
        if(placed.get(4)){
            toDraw.add(rocket);
        }
        if(placed.get(5)){
            toDraw.add(stinger);
        }

        toDraw.add(car);
    }

  private boolean cointainsWheels(ArrayList<Drawable> toDraw) {
        boolean c=false;
        for(Drawable d: toDraw){
            if(d.equals(wheels)){
                c=true;
            }
        }
        return c;
    }

}
