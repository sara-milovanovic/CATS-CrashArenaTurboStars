package com.example.catsproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.catsproject.db.Components;
import com.example.catsproject.db.async_tasks.AsyncTaskAddAttachedComponentToPlayer;
import com.example.catsproject.db.async_tasks.AsyncTaskAddComponentsToPlayer;
import com.example.catsproject.db.async_tasks.AsyncTaskGetGenerate;
import com.example.catsproject.db.async_tasks.AsyncTaskRemoveAttachedComponentToPlayer;
import com.example.catsproject.db.async_tasks.AsyncTaskUpdateGenerateBox;

import java.util.ArrayList;

public class EditVehicleCustomView extends View {
    ArrayList<Coordinate> coordinates=new ArrayList<>();
    ArrayList<Coordinate> coordinates2=new ArrayList<>();
    Paint paint, paint2;

    public static int numOfWeapons=6;

    ArrayList<Drawable> toDraw=new ArrayList<Drawable>();

    //0-blade 1-forklift 2-wheels -chainsaw 4-rocket
    public static ArrayList<Coordinate> c=new ArrayList<Coordinate>();
    public static ArrayList<Coordinate> c_default=new ArrayList<Coordinate>();
    public static ArrayList<Coordinate> c_place=new ArrayList<Coordinate>();

    public static Coordinate leftC=new Coordinate(50, 250);
    public static Coordinate rightC=new Coordinate(1150, 250);
    public static Coordinate vehicleC=new Coordinate(400,400); //trenutna pozicija dok se prevlaci
    public static Coordinate weaponAtributesC=new Coordinate(50,300);

    static int weaponAtributesWidth=250;
    static int weaponAtributesHeight=350;


    Drawable blade; //slika
    Drawable blade_blur; //slika
    Drawable forklift;
    Drawable forklift_blur;
    Drawable wheels;
    Drawable wheels2;
    Drawable wheels_blur;
    Drawable rocket;
    Drawable rocket_blur;
    Drawable chainsaw;
    Drawable chainsaw_blur;
    Drawable stinger;
    Drawable stinger_blur;
    Drawable left;
    Drawable right;
    Drawable bg;
    Drawable save;
    Drawable weaponAtributes;
    Drawable heart;
    Drawable thunder;
    Drawable sword;

    MyViewModel model;

    ArrayList<Boolean> placed=new ArrayList<>();

    ArrayList<Components> components;

    int vehicle;

    Drawable car;
    int toMove=-1;//-1-init 0-null 1-blade 2-forklift 3-wheels 4-chainsaw//sta se trenutno pomera

    public EditVehicleCustomView(Context context) {
        super(context);
        init();
    }

    public void add(Coordinate c,int list){
        Log.v("pozvnao za: ",list+"");
        if(list==0){
            coordinates.add(c);
        }
        else{
            coordinates2.add(c);
        }
    }

    public void clearLists(){
        coordinates.clear();
        coordinates2.clear();
    }

    public EditVehicleCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditVehicleCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void setModel(MyViewModel m){
        model=m;
        numOfWeapons=model.getCurrentPlayerComponents().getValue().size();
        components= (ArrayList<Components>) model.getCurrentPlayerComponents().getValue();
        for(int i=0;model.getCurrentPlayerAttachedComponents().getValue()!=null && i<model.getCurrentPlayerAttachedComponents().getValue().size();i++){
            int index=model.getCurrentPlayerAttachedComponents().getValue().get(i).cid;
            placed.set(index-1,true);
        }
    }

    void initCoordinates(){

        for(int i=0;i<6;i++){
            placed.add(false);
        }

        Coordinate c1=new Coordinate(50,50);
        Coordinate c2=new Coordinate(350,50);
        Coordinate c3=new Coordinate(650,50);
        Coordinate c4=new Coordinate(950,50);
        Coordinate c5=new Coordinate(1250,50);
        Coordinate c6=new Coordinate(1550,50);
        c.add(c1);
        c.add(c2);
        c.add(c3);
        c.add(c4);
        c.add(c5);
        c.add(c6);

        Coordinate c11=new Coordinate(50,50);
        Coordinate c21=new Coordinate(350,50);
        Coordinate c31=new Coordinate(650,50);
        Coordinate c41=new Coordinate(950,50);
        Coordinate c51=new Coordinate(1250,50);
        Coordinate c61=new Coordinate(1550,50);

        c_default.add(c11);
        c_default.add(c21);
        c_default.add(c31);
        c_default.add(c41);
        c_default.add(c51);
        c_default.add(c61);

        Coordinate c12=new Coordinate(150,150);
        Coordinate c22=new Coordinate(450,150);
        Coordinate c32=new Coordinate(650,150);

        c_place.add(c12);
        c_place.add(c22);
        c_place.add(c32);

    }

    void init(){
        paint=new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint2=new Paint();
        paint2.setStrokeWidth(5);
        paint2.setColor(getResources().getColor(R.color.colorPrimary));
        initCoordinates();
    }

    public static class Coordinate{
        float x, y;

        public Coordinate(float xx, float yy){
            x=xx;
            y=yy;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }

    void moveBlade(){
        toMove=1;
    }

    void moveForklift(){
        toMove=2;

    }

    void moveWheels(){
        toMove=3;

    }

    void moveChainsaw(){

        toMove=4;
    }

    void moveRocket(){

        toMove=5;
    }

    void moveStinger(){

        toMove=6;
    }

    void makeNewCar(int weapon,boolean updateAtributes){
        if(weapon>=0 && updateAtributes){
            updateAtributes(weapon,true);
        }
        if(weapon==6){
            stinger.setBounds(780,440,1000,640);
            return;
        }
        if(weapon==-6){
            stinger.setBounds((int)c_default.get(5).getX(),(int)c_default.get(5).getY(),(int)c_default.get(5).getX()+300,(int)c_default.get(5).getY()+200);
            return;
        }
        if(weapon==5){
            rocket.setBounds(700,440,1000,640);
            return;
        }
        if(weapon==-5){
            rocket.setBounds((int)c_default.get(4).getX(),(int)c_default.get(4).getY(),(int)c_default.get(4).getX()+300,(int)c_default.get(4).getY()+200);
            return;
        }
        if(weapon==4){
            chainsaw.setBounds(780,440,1080,640);
            return;
        }
        if(weapon==-4){
            chainsaw.setBounds((int)c_default.get(3).getX(),(int)c_default.get(3).getY(),(int)c_default.get(3).getX()+300,(int)c_default.get(3).getY()+200);
            return;
        }
        if(weapon==3){
            wheels.setBounds(325,515,625,715);
            return;
        }
        if(weapon==-3){
            wheels.setBounds((int)c_default.get(2).getX(),(int)c_default.get(2).getY(),(int)c_default.get(2).getX()+300,(int)c_default.get(2).getY()+200);
            return;
        }
        if(weapon==2){
            forklift.setBounds(550,450,850,650);
            return;
        }
        if(weapon==-2){
            forklift.setBounds((int)c_default.get(1).getX(),(int)c_default.get(1).getY(),(int)c_default.get(1).getX()+300,(int)c_default.get(1).getY()+200);
            return;
        }
        if(weapon==1){
            blade.setBounds(540,480,740,680);
            return;
        }
        if(weapon==-1){ //uklanja blade
            blade.setBounds((int) c_default.get(0).getX(), (int) c_default.get(0).getY(), (int) c_default.get(0).getX() + 200, (int) c_default.get(0).getY() + 200);
            return;
        }

    }

    void moveNothing(){

        toMove=0;
    }

    void moveLeft(){
        for(int i=0;i<c_default.size();i++){
            c_default.get(i).setX(c_default.get(i).getX()+50);
            c.get(i).setX(c.get(i).getX()+50);
        }
    }

    void moveRight(){
        for(int i=0;i<c_default.size();i++){
            c_default.get(i).setX(c_default.get(i).getX()-50);
            c.get(i).setX(c.get(i).getX()-50);
        }
    }

    void updateAtributes(int id, boolean add){
        int i=add?1:-1;
        switch (id){
            case 1:{
                model.setThunder(model.getThunder().getValue()-(Weapons.thunder[id-1])*i);
                model.setSword(model.getSword().getValue()+(Weapons.sword[id-1])*i);
                break;
            }
            case 2:{
                model.setThunder(model.getThunder().getValue()-(Weapons.thunder[id-1])*i);
                model.setHeart(model.getHeart().getValue()+(Weapons.heart[id-1])*i);
                break;
            }
            case 3:{
                model.setHeart(model.getHeart().getValue()+(Weapons.heart[id-1])*i);
                break;
            }
            case 4:{
                model.setThunder(model.getThunder().getValue()-(Weapons.thunder[id-1])*i);
                model.setSword(model.getSword().getValue()+(Weapons.sword[id-1])*i);
                break;
            }
            case 5:{
                model.setThunder(model.getThunder().getValue()-(Weapons.thunder[id-1])*i);
                model.setSword(model.getSword().getValue()+(Weapons.sword[id-1])*i);
                break;
            }
            case 6:{
                model.setThunder(model.getThunder().getValue()-(Weapons.thunder[id-1])*i);
                model.setSword(model.getSword().getValue()+(Weapons.sword[id-1])*i);
                break;
            }
        }
    }

    void removeWeapon(int w){
        updateAtributes(w,false);
        switch (w){
            case 1:{
                makeNewCar(-1,true);
                toDraw.remove(blade_blur);
                placed.set(0,false);
                invalidate();
                break;
            }
            case 2:{
                makeNewCar(-2,true);
                toDraw.remove(forklift_blur);
                placed.set(1,false);
                invalidate();
                break;
            }
            case 3:{
                makeNewCar(-3,true);
                toDraw.remove(wheels_blur);
                placed.set(2,false);
                invalidate();
                break;
            }
            case 4:{
                makeNewCar(-4,true);
                toDraw.remove(chainsaw_blur);
                placed.set(3,false);
                invalidate();
                break;
            }
            case 5:{
                makeNewCar(-5,true);
                toDraw.remove(rocket_blur);
                placed.set(4,false);
                invalidate();
                break;
            }
            case 6:{
                makeNewCar(-6,true);
                toDraw.remove(stinger_blur);
                placed.set(5,false);
                invalidate();
                break;
            }
        }
    }

    void drawArrows(Canvas canvas){
        //80x50 dimenzija
        bg.draw(canvas);
        save.draw(canvas);
        left = getResources().getDrawable(R.drawable.left, null);
        left.setBounds((int)leftC.getX(),(int)leftC.getY(),(int)leftC.getX()+80,(int)leftC.getY()+50);
        left.draw(canvas);
        right = getResources().getDrawable(R.drawable.right, null);
        right.setBounds((int)rightC.getX(),(int)rightC.getY(),(int)rightC.getX()+80,(int)rightC.getY()+50);
        right.draw(canvas);
    }

    void replaceWeaponAtributes(int weapon, Canvas canvas){
        toDraw.remove(weaponAtributes);
        switch(weapon){
            case 1:{
                weaponAtributes = getResources().getDrawable(R.drawable.blade_atributes, null);
                break;
            }
            case 2:{
                weaponAtributes = getResources().getDrawable(R.drawable.forklift_atributes, null);
                break;
            }
            case 3:{
                weaponAtributes = getResources().getDrawable(R.drawable.wheels_atributes, null);
                break;
            }
            case 4:{
                weaponAtributes = getResources().getDrawable(R.drawable.chainsaw_stributes, null);
                break;
            }case 5:{
                weaponAtributes = getResources().getDrawable(R.drawable.rocket_atributes, null);
                break;
            }
            case 6:{
                weaponAtributes = getResources().getDrawable(R.drawable.stinger_atributes, null);
                break;
            }

        }
        weaponAtributes.setBounds((int)weaponAtributesC.getX(),(int)weaponAtributesC.getY(),(int)weaponAtributesC.getX()+weaponAtributesWidth,(int)weaponAtributesC.getY()+weaponAtributesHeight);
        weaponAtributes.draw(canvas);
        toDraw.add(weaponAtributes);
    }

    boolean componentExist(int c){
        boolean flag=false;
        for(int i=0;i<components.size();i++){
            if(components.get(i).cid==c) flag=true;
        }
        return flag;
    }

    void saveCar(){
        Log.v("car","saved");
        ArrayList<Components> components=new ArrayList<>();
        for(int i=1;i<7;i++){
            if(placed.get(i-1)) {
                Components c = Component.makeFromId(i);
                components.add(c);
            }
        }
        ArrayList<Components> toAdd=new ArrayList<>();
        for(int i=0;i<components.size();i++){
            boolean flag=true;
            for(int j=0;j<model.getCurrentPlayerAttachedComponents().getValue().size();j++){
                if(model.getCurrentPlayerAttachedComponents().getValue().get(j).cid==components.get(i).cid){
                    flag=false;
                }
            }
            if(flag){
                toAdd.add(components.get(i));
            }
        }
        ArrayList<Components> toRemove=new ArrayList<>();
        for(int i=0;i<model.getCurrentPlayerAttachedComponents().getValue().size();i++){
            boolean flag=true;
            for(int j=0;j<components.size();j++){
                if(model.getCurrentPlayerAttachedComponents().getValue().get(i).cid==components.get(j).cid){
                    flag=false;
                }
            }
            if(flag){
                toRemove.add(model.getCurrentPlayerAttachedComponents().getValue().get(i));
            }
        }
        model.setCurrentPlayerAttachedComponents(components);
        Components[] toAddArray=new Components[toAdd.size()];
        for(int i=0;i<toAdd.size();i++){
            toAddArray[i]=toAdd.get(i);
        }
        Components[] toRemoveArray=new Components[toRemove.size()];
        for(int i=0;i<toRemove.size();i++){
            toRemoveArray[i]=toRemove.get(i);
        }
        //update baze
        new AsyncTaskAddAttachedComponentToPlayer(model).execute(toAddArray);
        new AsyncTaskRemoveAttachedComponentToPlayer(model).execute(toRemoveArray);

        /*-------------------------------------------*/


        //timer
        //new MyAsyncTask(model).execute();

        /*-------------------------------------------*/


    }



    class MyAsyncTask extends AsyncTask<Void, Integer, String> {
        String p="";
        int i=0;
        MyViewModel model;

        public MyAsyncTask(MyViewModel m){
            model=m;
        }
        @Override protected String doInBackground(Void... params) {
            while (i < 10) {
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return p;
        }

        @Override protected void onPostExecute(String result) {
            //progress.setText("kraj");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("sara" + " onProgressUpdate", "You are in progress update ... " + values[0]);
            model.decTime();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.v("TAG","toMove="+toMove);
        Log.v("TAG","wPlaced="+placed.get(2));
        Log.v("TAG","fPlaced="+placed.get(1));
        Log.v("TAG","bPlaced="+placed.get(0));



        if(toMove==0){
            toDraw.remove(weaponAtributes);
            drawArrows(canvas);
            if(!placed.get(0) && c.get(0).getX()>vehicleC.getX() && c.get(0).getX()<vehicleC.getX()+500 && c.get(0).getY()>vehicleC.getY() && c.get(0).getY()<vehicleC.getY()+300 ){ //ako je na svojoj poziciji ok, a inace vrati ga na default

                placed.set(0,true);
                //stavi prividni
                toDraw.remove(blade_blur);
                blade_blur = getResources().getDrawable(R.drawable.blade_blur, null);
                blade_blur.setBounds((int) c_default.get(0).getX(), (int) c_default.get(0).getY(), (int) c_default.get(0).getX() + 200, (int) c_default.get(0).getY() + 200);
                toDraw.add(blade_blur);
                //****

                c.get(0).setX(c_default.get(0).getX());
                c.get(0).setY(c_default.get(0).getY());
                //novi auto
                toDraw.remove(car);
                makeNewCar(1,true);
                car = getResources().getDrawable(vehicle, null);
                car.setBounds((int)vehicleC.getX(),(int)vehicleC.getY(),(int)vehicleC.getX()+500,(int)vehicleC.getY()+300);
                toDraw.add(car);

                if(!placed.get(2)){
                    toDraw.remove(wheels);
                    if(componentExist(3)) wheels = getResources().getDrawable(R.drawable.wheels, null);
                    else  wheels = getResources().getDrawable(R.drawable.wheels_not, null);                    wheels.setBounds((int)c_default.get(2).getX(),(int)c_default.get(2).getY(),(int)c_default.get(2).getX()+300,(int)c_default.get(2).getY()+200);
                    toDraw.add(wheels);
                }
                if(!placed.get(1)){
                    toDraw.remove(forklift);
                    if(componentExist(2)) forklift = getResources().getDrawable(R.drawable.forklift, null);
                    else  forklift = getResources().getDrawable(R.drawable.forklift_not, null);
                    forklift.setBounds((int)c_default.get(1).getX(),(int)c_default.get(1).getY(),(int)c_default.get(1).getX()+300,(int)c_default.get(1).getY()+200);
                    toDraw.add(forklift);
                }
                else{
                    toDraw.remove(forklift_blur);
                    forklift_blur = getResources().getDrawable(R.drawable.forklift_blur, null);
                    forklift_blur.setBounds((int) c_default.get(1).getX(), (int) c_default.get(1).getY(), (int) c_default.get(1).getX() + 300, (int) c_default.get(1).getY() + 200);
                    toDraw.add(forklift_blur);
                }

            }
            else{
                if(!placed.get(0)) {
                    toDraw.remove(blade);
                    if(componentExist(1)) blade = getResources().getDrawable(R.drawable.blade, null);
                    else  blade = getResources().getDrawable(R.drawable.blade_not, null);
                    blade.setBounds((int) c_default.get(0).getX(), (int) c_default.get(0).getY(), (int) c_default.get(0).getX() + 200, (int) c_default.get(0).getY() + 200);
                    toDraw.add(blade);
                }
                else{
                    toDraw.remove(blade_blur);
                    blade_blur = getResources().getDrawable(R.drawable.blade_blur, null);
                    blade_blur.setBounds((int) c_default.get(0).getX(), (int) c_default.get(0).getY(), (int) c_default.get(0).getX() + 200, (int) c_default.get(0).getY() + 200);
                    toDraw.add(blade_blur);
                }
                //stari auto

                if(!placed.get(1) && c.get(1).getX()>vehicleC.getX() && c.get(1).getX()<vehicleC.getX()+500 && c.get(1).getY()>vehicleC.getY() && c.get(1).getY()<vehicleC.getY()+300 ){ //ako je na svojoj poziciji ok, a inace vrati ga na default

                    //stavi prividni
                    toDraw.remove(forklift_blur);
                    forklift_blur = getResources().getDrawable(R.drawable.forklift_blur, null);
                    forklift_blur.setBounds((int) c_default.get(1).getX(), (int) c_default.get(1).getY(), (int) c_default.get(1).getX() + 300, (int) c_default.get(1).getY() + 200);
                    toDraw.add(forklift_blur);
                    //****

                    c.get(1).setX(c_default.get(1).getX());
                    c.get(1).setY(c_default.get(1).getY());
                    placed.set(1,true);
                    //novi auto
                    makeNewCar(2,true);

                    if(!placed.get(2)){
                        toDraw.remove(wheels);
                        if(componentExist(3)) wheels = getResources().getDrawable(R.drawable.wheels, null);
                        else  wheels = getResources().getDrawable(R.drawable.wheels_not, null);                        wheels.setBounds((int)c_default.get(2).getX(),(int)c_default.get(2).getY(),(int)c_default.get(2).getX()+300,(int)c_default.get(2).getY()+200);
                        toDraw.add(wheels);
                    }
                    toDraw.remove(car);
                    car = getResources().getDrawable(vehicle, null);
                    car.setBounds((int)vehicleC.getX(),(int)vehicleC.getY(),(int)vehicleC.getX()+500,(int)vehicleC.getY()+300);
                    toDraw.add(car);

                }
                else{
                    if(!placed.get(1)) {
                        toDraw.remove(forklift);
                        if(componentExist(2)) forklift = getResources().getDrawable(R.drawable.forklift, null);
                        else  forklift = getResources().getDrawable(R.drawable.forklift_not, null);
                        forklift.setBounds((int) c_default.get(1).getX(), (int) c_default.get(1).getY(), (int) c_default.get(1).getX() + 300, (int) c_default.get(1).getY() + 200);
                        toDraw.add(forklift);
                    }
                    else{
                        toDraw.remove(forklift_blur);
                        forklift_blur = getResources().getDrawable(R.drawable.forklift_blur, null);
                        forklift_blur.setBounds((int) c_default.get(1).getX(), (int) c_default.get(1).getY(), (int) c_default.get(1).getX() + 300, (int) c_default.get(1).getY() + 200);
                        toDraw.add(forklift_blur);
                    }
                    //stari auto

                    if(!placed.get(2) && c.get(2).getX()>vehicleC.getX() && c.get(2).getX()<vehicleC.getX()+500 && c.get(2).getY()>vehicleC.getY() && c.get(2).getY()<vehicleC.getY()+300 ){ //ako je na svojoj poziciji ok, a inace vrati ga na default

                        //stavi prividni
                        toDraw.remove(wheels_blur);
                        wheels_blur = getResources().getDrawable(R.drawable.wheels_blur, null);
                        wheels_blur.setBounds((int) c_default.get(2).getX(), (int) c_default.get(2).getY(), (int) c_default.get(2).getX() + 300, (int) c_default.get(2).getY() + 200);
                        toDraw.add(wheels_blur);
                        //****

                        c.get(2).setX(c_default.get(2).getX());
                        c.get(2).setY(c_default.get(2).getY());
                        placed.set(2,true);
                        //novi auto
                        makeNewCar(3,true);
                        toDraw.remove(car);
                        car = getResources().getDrawable(vehicle, null);
                        car.setBounds((int)vehicleC.getX(),(int)vehicleC.getY(),(int)vehicleC.getX()+500,(int)vehicleC.getY()+300);
                        toDraw.add(car);

                    }
                    else{
                        if(!placed.get(2)) {
                            toDraw.remove(wheels);
                            if(componentExist(3)) wheels = getResources().getDrawable(R.drawable.wheels, null);
                            else  wheels = getResources().getDrawable(R.drawable.wheels_not, null);                            wheels.setBounds((int) c_default.get(2).getX(), (int) c_default.get(2).getY(), (int) c_default.get(2).getX() + 300, (int) c_default.get(2).getY() + 200);
                            toDraw.add(wheels);
                        }
                        else{
                            toDraw.remove(wheels_blur);
                            wheels_blur = getResources().getDrawable(R.drawable.wheels_blur, null);
                            wheels_blur.setBounds((int) c_default.get(2).getX(), (int) c_default.get(2).getY(), (int) c_default.get(2).getX() + 300, (int) c_default.get(2).getY() + 200);
                            //blade_blur.draw(canvas);
                            toDraw.add(wheels_blur);
                        }
                        /***************************************************/
                        if(!placed.get(3) && c.get(3).getX()>vehicleC.getX() && c.get(3).getX()<vehicleC.getX()+500 && c.get(3).getY()>vehicleC.getY() && c.get(3).getY()<vehicleC.getY()+300 ){ //ako je na svojoj poziciji ok, a inace vrati ga na default

                            //stavi prividni
                            toDraw.remove(chainsaw_blur);
                            chainsaw_blur = getResources().getDrawable(R.drawable.chainsaw_blur, null);
                            chainsaw_blur.setBounds((int) c_default.get(3).getX(), (int) c_default.get(3).getY(), (int) c_default.get(3).getX() + 300, (int) c_default.get(3).getY() + 200);
                            toDraw.add(chainsaw_blur);
                            //****

                            c.get(3).setX(c_default.get(3).getX());
                            c.get(3).setY(c_default.get(3).getY());
                            placed.set(3,true);
                            //novi auto
                            makeNewCar(4,true);
                            toDraw.remove(car);
                            car = getResources().getDrawable(vehicle, null);
                            car.setBounds((int)vehicleC.getX(),(int)vehicleC.getY(),(int)vehicleC.getX()+500,(int)vehicleC.getY()+300);
                            toDraw.add(car);

                        }
                        else {
                            if (!placed.get(3)) {
                                toDraw.remove(chainsaw);
                                if(componentExist(4)) chainsaw = getResources().getDrawable(R.drawable.chainsaw, null);
                                else  chainsaw = getResources().getDrawable(R.drawable.chainsaw_not, null);
                                chainsaw.setBounds((int) c_default.get(3).getX(), (int) c_default.get(3).getY(), (int) c_default.get(3).getX() + 300, (int) c_default.get(3).getY() + 200);
                                toDraw.add(chainsaw);
                            } else {
                                toDraw.remove(chainsaw_blur);
                                chainsaw_blur = getResources().getDrawable(R.drawable.chainsaw_blur, null);
                                chainsaw_blur.setBounds((int) c_default.get(3).getX(), (int) c_default.get(3).getY(), (int) c_default.get(3).getX() + 300, (int) c_default.get(3).getY() + 200);
                                toDraw.add(chainsaw_blur);
                            }
                            if(!placed.get(4) && c.get(4).getX()>vehicleC.getX() && c.get(4).getX()<vehicleC.getX()+500 && c.get(4).getY()>vehicleC.getY() && c.get(4).getY()<vehicleC.getY()+300 ){ //ako je na svojoj poziciji ok, a inace vrati ga na default

                                //stavi prividni
                                toDraw.remove(rocket_blur);
                                rocket_blur = getResources().getDrawable(R.drawable.rocket_blur, null);
                                rocket_blur.setBounds((int) c_default.get(4).getX(), (int) c_default.get(4).getY(), (int) c_default.get(4).getX() + 300, (int) c_default.get(4).getY() + 200);
                                toDraw.add(rocket_blur);
                                //****

                                c.get(4).setX(c_default.get(4).getX());
                                c.get(4).setY(c_default.get(4).getY());
                                placed.set(4,true);
                                //novi auto
                                makeNewCar(5,true);
                                toDraw.remove(car);
                                car = getResources().getDrawable(vehicle, null);
                                car.setBounds((int)vehicleC.getX(),(int)vehicleC.getY(),(int)vehicleC.getX()+500,(int)vehicleC.getY()+300);
                                toDraw.add(car);

                            }
                            else {
                                if (!placed.get(4)) {
                                    toDraw.remove(rocket);
                                    if(componentExist(5)) rocket = getResources().getDrawable(R.drawable.rocket, null);
                                    else  rocket = getResources().getDrawable(R.drawable.rocket_not, null);
                                    rocket.setBounds((int) c_default.get(4).getX(), (int) c_default.get(4).getY(), (int) c_default.get(4).getX() + 300, (int) c_default.get(4).getY() + 200);
                                    toDraw.add(rocket);
                                } else {
                                    toDraw.remove(rocket_blur);
                                    rocket_blur = getResources().getDrawable(R.drawable.rocket_blur, null);
                                    rocket_blur.setBounds((int) c_default.get(4).getX(), (int) c_default.get(4).getY(), (int) c_default.get(4).getX() + 300, (int) c_default.get(4).getY() + 200);
                                    toDraw.add(rocket_blur);
                                }
                                if(!placed.get(5) && c.get(5).getX()>vehicleC.getX() && c.get(5).getX()<vehicleC.getX()+500 && c.get(5).getY()>vehicleC.getY() && c.get(5).getY()<vehicleC.getY()+300 ){ //ako je na svojoj poziciji ok, a inace vrati ga na default

                                    //stavi prividni
                                    toDraw.remove(stinger_blur);
                                    stinger_blur = getResources().getDrawable(R.drawable.stinger_blur, null);
                                    stinger_blur.setBounds((int) c_default.get(5).getX(), (int) c_default.get(5).getY(), (int) c_default.get(5).getX() + 300, (int) c_default.get(5).getY() + 200);
                                    toDraw.add(stinger_blur);
                                    //****

                                    c.get(5).setX(c_default.get(5).getX());
                                    c.get(5).setY(c_default.get(5).getY());
                                    placed.set(5,true);
                                    //novi auto
                                    makeNewCar(6,true);
                                    toDraw.remove(car);
                                    car = getResources().getDrawable(vehicle, null);
                                    car.setBounds((int)vehicleC.getX(),(int)vehicleC.getY(),(int)vehicleC.getX()+500,(int)vehicleC.getY()+300);
                                    toDraw.add(car);

                                }
                                else {
                                    if (!placed.get(5)) {
                                        toDraw.remove(stinger);
                                        if(componentExist(6)) stinger = getResources().getDrawable(R.drawable.stinger, null);
                                        else  stinger = getResources().getDrawable(R.drawable.stinger_not, null);
                                        stinger.setBounds((int) c_default.get(5).getX(), (int) c_default.get(5).getY(), (int) c_default.get(5).getX() + 300, (int) c_default.get(5).getY() + 200);
                                        toDraw.add(stinger);
                                    } else {
                                        toDraw.remove(stinger_blur);
                                        stinger_blur = getResources().getDrawable(R.drawable.stinger_blur, null);
                                        stinger_blur.setBounds((int) c_default.get(5).getX(), (int) c_default.get(5).getY(), (int) c_default.get(5).getX() + 300, (int) c_default.get(5).getY() + 200);
                                        toDraw.add(stinger_blur);
                                    }
                                }

                                //stari auto
                                toDraw.remove(car);
                                car = getResources().getDrawable(vehicle, null);
                                car.setBounds((int)vehicleC.getX(),(int)vehicleC.getY(),(int)vehicleC.getX()+500,(int)vehicleC.getY()+300);
                                toDraw.add(car);
                            }
                        }
                        /***************************************************/

                    }
                }
            }

            toMove=0;
        }

        if(toMove==-1){//init
            for(int i=0;model.getCurrentPlayerAttachedComponents().getValue()!=null && i<model.getCurrentPlayerAttachedComponents().getValue().size();i++){
                int index=model.getCurrentPlayerAttachedComponents().getValue().get(i).cid;
                placed.set(index-1,true);
            }
            if(componentExist(1)) blade = getResources().getDrawable(R.drawable.blade, null);
            else  blade = getResources().getDrawable(R.drawable.blade_not, null);
            blade.setBounds((int)c_default.get(0).getX(),(int)c_default.get(0).getY(),(int)c_default.get(0).getX()+200,(int)c_default.get(0).getY()+200);
            if(placed.get(0)){
                makeNewCar(1,false);
                blade_blur = getResources().getDrawable(R.drawable.blade_blur, null);
                blade_blur.setBounds((int) c_default.get(0).getX(), (int) c_default.get(0).getY(), (int) c_default.get(0).getX() + 200, (int) c_default.get(0).getY() + 200);
                toDraw.add(blade_blur);
            }
            toDraw.add(blade);
            if(componentExist(2)) forklift = getResources().getDrawable(R.drawable.forklift, null);
            else  forklift = getResources().getDrawable(R.drawable.forklift_not, null);
            forklift.setBounds((int)c_default.get(1).getX(),(int)c_default.get(1).getY(),(int)c_default.get(1).getX()+300,(int)c_default.get(1).getY()+200);
            if(placed.get(1)){
                makeNewCar(2,false);
                forklift_blur = getResources().getDrawable(R.drawable.forklift_blur, null);
                forklift_blur.setBounds((int) c_default.get(1).getX(), (int) c_default.get(1).getY(), (int) c_default.get(1).getX() + 200, (int) c_default.get(1).getY() + 200);
                toDraw.add(forklift_blur);
            }
            toDraw.add(forklift);
            if(componentExist(3)) wheels = getResources().getDrawable(R.drawable.wheels, null);
            else  wheels = getResources().getDrawable(R.drawable.wheels_not, null);
            wheels.setBounds((int)c_default.get(2).getX(),(int)c_default.get(2).getY(),(int)c_default.get(2).getX()+300,(int)c_default.get(2).getY()+200);
            if(placed.get(2)){
                makeNewCar(3,false);
                wheels_blur = getResources().getDrawable(R.drawable.wheels_blur, null);
                wheels_blur.setBounds((int) c_default.get(2).getX(), (int) c_default.get(2).getY(), (int) c_default.get(2).getX() + 300, (int) c_default.get(2).getY() + 200);
                toDraw.add(wheels_blur);
            }
            toDraw.add(wheels);
            if(componentExist(4)) chainsaw = getResources().getDrawable(R.drawable.chainsaw, null);
            else  chainsaw = getResources().getDrawable(R.drawable.chainsaw_not, null);
            chainsaw.setBounds((int)c_default.get(3).getX(),(int)c_default.get(3).getY(),(int)c_default.get(3).getX()+300,(int)c_default.get(3).getY()+200);
            if(placed.get(3)){
                makeNewCar(4,false);
                chainsaw_blur = getResources().getDrawable(R.drawable.chainsaw_blur, null);
                chainsaw_blur.setBounds((int) c_default.get(3).getX(), (int) c_default.get(3).getY(), (int) c_default.get(3).getX() + 300, (int) c_default.get(3).getY() + 200);
                toDraw.add(chainsaw_blur);
            }
            toDraw.add(chainsaw);
            if(componentExist(5)) rocket = getResources().getDrawable(R.drawable.rocket, null);
            else  rocket = getResources().getDrawable(R.drawable.rocket_not, null);
            rocket.setBounds((int)c_default.get(4).getX(),(int)c_default.get(4).getY(),(int)c_default.get(4).getX()+300,(int)c_default.get(4).getY()+200);
            if(placed.get(4)){
                makeNewCar(5,false);
                rocket_blur = getResources().getDrawable(R.drawable.rocket_blur, null);
                rocket_blur.setBounds((int) c_default.get(4).getX(), (int) c_default.get(4).getY(), (int) c_default.get(4).getX() + 300, (int) c_default.get(4).getY() + 200);
                toDraw.add(rocket_blur);
            }
            toDraw.add(rocket);
            if(componentExist(6)) stinger = getResources().getDrawable(R.drawable.stinger, null);
            else  stinger = getResources().getDrawable(R.drawable.stinger_not, null);
            stinger.setBounds((int)c_default.get(5).getX(),(int)c_default.get(5).getY(),(int)c_default.get(5).getX()+300,(int)c_default.get(5).getY()+200);
            if(placed.get(5)){
                makeNewCar(6,false);
                stinger_blur = getResources().getDrawable(R.drawable.stinger_blur, null);
                stinger_blur.setBounds((int) c_default.get(5).getX(), (int) c_default.get(5).getY(), (int) c_default.get(5).getX() + 300, (int) c_default.get(5).getY() + 200);
                toDraw.add(stinger_blur);
            }
            toDraw.add(stinger);

            vehicle=R.drawable.vehicle_2;
            car = getResources().getDrawable(vehicle, null);
            car.setBounds((int)vehicleC.getX(),(int)vehicleC.getY(),(int)vehicleC.getX()+500,(int)vehicleC.getY()+300);
            toDraw.add(car);
            int background=R.drawable.bg;
            bg = getResources().getDrawable(background, null);
            bg.setBounds(0,0,1300,800);
            int s=R.drawable.save;
            save = getResources().getDrawable(s, null);
            save.setBounds(500,610,740,760);

            drawArrows(canvas);

            toMove=0;
        }

        if(toMove==1){

            replaceWeaponAtributes(1,canvas);
            drawArrows(canvas);
            toDraw.remove(car);
            car = getResources().getDrawable(vehicle, null);
            car.setBounds((int)vehicleC.getX(),(int)vehicleC.getY(),(int)vehicleC.getX()+500,(int)vehicleC.getY()+300);
            toDraw.add(car);
            if(placed.get(1)){
                toDraw.remove(forklift_blur);
                forklift_blur = getResources().getDrawable(R.drawable.forklift_blur, null);
                forklift_blur.setBounds((int) c_default.get(1).getX(), (int) c_default.get(1).getY(), (int) c_default.get(1).getX() + 300, (int) c_default.get(1).getY() + 200);
                toDraw.add(forklift_blur);
            }
            else{
                toDraw.remove(forklift);
                if(componentExist(2)) forklift = getResources().getDrawable(R.drawable.forklift, null);
                else  forklift = getResources().getDrawable(R.drawable.forklift_not, null);
                forklift.setBounds((int)c_default.get(1).getX(),(int)c_default.get(1).getY(),(int)c_default.get(1).getX()+300,(int)c_default.get(1).getY()+200);
                toDraw.add(forklift);
            }
            if(placed.get(2)){
                toDraw.remove(wheels_blur);
                wheels_blur = getResources().getDrawable(R.drawable.wheels_blur, null);
                wheels_blur.setBounds((int) c_default.get(2).getX(), (int) c_default.get(2).getY(), (int) c_default.get(2).getX() + 300, (int) c_default.get(2).getY() + 200);
                toDraw.add(wheels_blur);
            }
            else{
                toDraw.remove(wheels);
                if(componentExist(3)) wheels = getResources().getDrawable(R.drawable.wheels, null);
                else  wheels = getResources().getDrawable(R.drawable.wheels_not, null);                wheels.setBounds((int)c_default.get(2).getX(),(int)c_default.get(2).getY(),(int)c_default.get(2).getX()+300,(int)c_default.get(2).getY()+200);
                toDraw.add(wheels);
            }
            if(!placed.get(3)){
                toDraw.remove(chainsaw);
                if(componentExist(4)) chainsaw = getResources().getDrawable(R.drawable.chainsaw, null);
                else  chainsaw = getResources().getDrawable(R.drawable.chainsaw_not, null);
                chainsaw.setBounds((int)c_default.get(3).getX(),(int)c_default.get(3).getY(),(int)c_default.get(3).getX()+300,(int)c_default.get(3).getY()+200);
                toDraw.add(chainsaw);
            }
            else{
                toDraw.remove(chainsaw_blur);
                chainsaw_blur = getResources().getDrawable(R.drawable.chainsaw_blur, null);
                chainsaw_blur.setBounds((int) c_default.get(3).getX(), (int) c_default.get(3).getY(), (int) c_default.get(3).getX() + 300, (int) c_default.get(3).getY() + 200);
                toDraw.add(chainsaw_blur);

            }
            if(placed.get(4)){
                toDraw.remove(rocket_blur);
                rocket_blur = getResources().getDrawable(R.drawable.rocket_blur, null);
                rocket_blur.setBounds((int) c_default.get(4).getX(), (int) c_default.get(4).getY(), (int) c_default.get(4).getX() + 300, (int) c_default.get(4).getY() + 200);
                toDraw.add(rocket_blur);
            }
            else{
                toDraw.remove(rocket);
                if(componentExist(5)) rocket = getResources().getDrawable(R.drawable.rocket, null);
                else  rocket = getResources().getDrawable(R.drawable.rocket_not, null);
                rocket.setBounds((int) c_default.get(4).getX(), (int) c_default.get(4).getY(), (int) c_default.get(4).getX() + 300, (int) c_default.get(4).getY() + 200);
                toDraw.add(rocket);
            }
            if(!placed.get(5)){
                toDraw.remove(stinger);
                if(componentExist(6)) stinger = getResources().getDrawable(R.drawable.stinger, null);
                else  stinger = getResources().getDrawable(R.drawable.stinger_not, null);
                stinger.setBounds((int)c_default.get(5).getX(),(int)c_default.get(5).getY(),(int)c_default.get(5).getX()+300,(int)c_default.get(5).getY()+200);
                toDraw.add(stinger);
            }
            else{
                toDraw.remove(stinger_blur);
                stinger_blur = getResources().getDrawable(R.drawable.stinger_blur, null);
                stinger_blur.setBounds((int) c_default.get(5).getX(), (int) c_default.get(5).getY(), (int) c_default.get(5).getX() + 300, (int) c_default.get(5).getY() + 200);
                toDraw.add(stinger_blur);

            }

            if(coordinates.size()==0){

                c.get(0).setX(50);
                c.get(0).setY(50);
            }
            else{

                c.get(0).setX(coordinates.get(coordinates.size()-1).getX());
                c.get(0).setY(coordinates.get(coordinates.size()-1).getY());
            }
            toDraw.remove(blade);
            if(componentExist(1)) blade = getResources().getDrawable(R.drawable.blade, null);
            else  blade = getResources().getDrawable(R.drawable.blade_not, null);
            blade.setBounds((int)c.get(0).getX()-100,(int)c.get(0).getY()-100,(int)c.get(0).getX()+100,(int)c.get(0).getY()+100);
            toDraw.add(blade);


        }
        if(toMove==2){
            replaceWeaponAtributes(2,canvas);
            drawArrows(canvas);
            toDraw.remove(car);
            car = getResources().getDrawable(vehicle, null);
            car.setBounds((int)vehicleC.getX(),(int)vehicleC.getY(),(int)vehicleC.getX()+500,(int)vehicleC.getY()+300);
            toDraw.add(car);
            if(!placed.get(0)){
                toDraw.remove(blade);
                if(componentExist(1)) blade = getResources().getDrawable(R.drawable.blade, null);
                else  blade = getResources().getDrawable(R.drawable.blade_not, null);
                blade.setBounds((int)c_default.get(0).getX(),(int)c_default.get(0).getY(),(int)c_default.get(0).getX()+200,(int)c_default.get(0).getY()+200);
                toDraw.add(blade);
            }
            else{
                toDraw.remove(blade_blur);
                blade_blur = getResources().getDrawable(R.drawable.blade_blur, null);
                blade_blur.setBounds((int) c_default.get(0).getX(), (int) c_default.get(0).getY(), (int) c_default.get(0).getX() + 200, (int) c_default.get(0).getY() + 200);
                toDraw.add(blade_blur);
            }
            if(!placed.get(2)){
                toDraw.remove(wheels);
                if(componentExist(3)) wheels = getResources().getDrawable(R.drawable.wheels, null);
                else  wheels = getResources().getDrawable(R.drawable.wheels_not, null);                wheels.setBounds((int)c_default.get(2).getX(),(int)c_default.get(2).getY(),(int)c_default.get(2).getX()+300,(int)c_default.get(2).getY()+200);
                toDraw.add(wheels);
            }
            else{
                toDraw.remove(wheels_blur);
                wheels_blur = getResources().getDrawable(R.drawable.wheels_blur, null);
                wheels_blur.setBounds((int) c_default.get(2).getX(), (int) c_default.get(2).getY(), (int) c_default.get(2).getX() + 300, (int) c_default.get(2).getY() + 200);
                toDraw.add(wheels_blur);
            }
            if(!placed.get(3)){
                toDraw.remove(chainsaw);
                if(componentExist(4)) chainsaw = getResources().getDrawable(R.drawable.chainsaw, null);
                else  chainsaw = getResources().getDrawable(R.drawable.chainsaw_not, null);
                chainsaw.setBounds((int)c_default.get(3).getX(),(int)c_default.get(3).getY(),(int)c_default.get(3).getX()+300,(int)c_default.get(3).getY()+200);
                toDraw.add(chainsaw);
            }
            else{
                toDraw.remove(chainsaw_blur);
                chainsaw_blur = getResources().getDrawable(R.drawable.chainsaw_blur, null);
                chainsaw_blur.setBounds((int) c_default.get(3).getX(), (int) c_default.get(3).getY(), (int) c_default.get(3).getX() + 300, (int) c_default.get(3).getY() + 200);
                toDraw.add(chainsaw_blur);
            }
            if(placed.get(4)){
                toDraw.remove(rocket_blur);
                rocket_blur = getResources().getDrawable(R.drawable.rocket_blur, null);
                rocket_blur.setBounds((int) c_default.get(4).getX(), (int) c_default.get(4).getY(), (int) c_default.get(4).getX() + 300, (int) c_default.get(4).getY() + 200);
                toDraw.add(rocket_blur);
            }
            else{
                toDraw.remove(rocket);
                if(componentExist(5)) rocket = getResources().getDrawable(R.drawable.rocket, null);
                else  rocket = getResources().getDrawable(R.drawable.rocket_not, null);
                rocket.setBounds((int) c_default.get(4).getX(), (int) c_default.get(4).getY(), (int) c_default.get(4).getX() + 300, (int) c_default.get(4).getY() + 200);
                toDraw.add(rocket);
            }
            if(!placed.get(5)){
                toDraw.remove(stinger);
                if(componentExist(6)) stinger = getResources().getDrawable(R.drawable.stinger, null);
                else  stinger = getResources().getDrawable(R.drawable.stinger_not, null);
                stinger.setBounds((int)c_default.get(5).getX(),(int)c_default.get(5).getY(),(int)c_default.get(5).getX()+300,(int)c_default.get(5).getY()+200);
                toDraw.add(stinger);
            }
            else{
                toDraw.remove(stinger_blur);
                stinger_blur = getResources().getDrawable(R.drawable.stinger_blur, null);
                stinger_blur.setBounds((int) c_default.get(5).getX(), (int) c_default.get(5).getY(), (int) c_default.get(5).getX() + 300, (int) c_default.get(5).getY() + 200);
                toDraw.add(stinger_blur);

            }

            if(coordinates.size()==0){

                c.get(1).setX(50);
                c.get(1).setY(50);
            }
            else{

                c.get(1).setX(coordinates.get(coordinates.size()-1).getX());
                c.get(1).setY(coordinates.get(coordinates.size()-1).getY());
            }
            toDraw.remove(forklift);
            if(componentExist(2)) forklift = getResources().getDrawable(R.drawable.forklift, null);
            else  forklift = getResources().getDrawable(R.drawable.forklift_not, null);
            forklift.setBounds((int)c.get(1).getX()-150,(int)c.get(1).getY()-100,(int)c.get(1).getX()+150,(int)c.get(1).getY()+100);
            toDraw.add(forklift);

        }
        if(toMove==3){
            replaceWeaponAtributes(3,canvas);
            drawArrows(canvas);
            toDraw.remove(car);
            car = getResources().getDrawable(vehicle, null);
            car.setBounds((int)vehicleC.getX(),(int)vehicleC.getY(),(int)vehicleC.getX()+500,(int)vehicleC.getY()+300);
            toDraw.add(car);
            if(!placed.get(0)){
                toDraw.remove(blade);
                if(componentExist(1)) blade = getResources().getDrawable(R.drawable.blade, null);
                else  blade = getResources().getDrawable(R.drawable.blade_not, null);
                blade.setBounds((int)c_default.get(0).getX(),(int)c_default.get(0).getY(),(int)c_default.get(0).getX()+200,(int)c_default.get(0).getY()+200);
                toDraw.add(blade);
            }
            else{
                toDraw.remove(blade_blur);
                blade_blur = getResources().getDrawable(R.drawable.blade_blur, null);
                blade_blur.setBounds((int) c_default.get(0).getX(), (int) c_default.get(0).getY(), (int) c_default.get(0).getX() + 200, (int) c_default.get(0).getY() + 200);
                toDraw.add(blade_blur);
            }
            if(!placed.get(1)){//?
                toDraw.remove(forklift);
                if(componentExist(2)) forklift = getResources().getDrawable(R.drawable.forklift, null);
                else  forklift = getResources().getDrawable(R.drawable.forklift_not, null);
                forklift.setBounds((int)c_default.get(1).getX(),(int)c_default.get(1).getY(),(int)c_default.get(1).getX()+300,(int)c_default.get(1).getY()+200);
                toDraw.add(forklift);
            }
            else{
                toDraw.remove(forklift_blur);
                forklift_blur = getResources().getDrawable(R.drawable.forklift_blur, null);
                forklift_blur.setBounds((int) c_default.get(1).getX(), (int) c_default.get(1).getY(), (int) c_default.get(1).getX() + 300, (int) c_default.get(1).getY() + 200);
                toDraw.add(forklift_blur);
            }
            if(!placed.get(3)){
                toDraw.remove(chainsaw);
                if(componentExist(4)) chainsaw = getResources().getDrawable(R.drawable.chainsaw, null);
                else  chainsaw = getResources().getDrawable(R.drawable.chainsaw_not, null);
                chainsaw.setBounds((int)c_default.get(3).getX(),(int)c_default.get(3).getY(),(int)c_default.get(3).getX()+300,(int)c_default.get(3).getY()+200);
                toDraw.add(chainsaw);
            }
            else{
                toDraw.remove(chainsaw_blur);
                chainsaw_blur = getResources().getDrawable(R.drawable.chainsaw_blur, null);
                chainsaw_blur.setBounds((int) c_default.get(3).getX(), (int) c_default.get(3).getY(), (int) c_default.get(3).getX() + 300, (int) c_default.get(3).getY() + 200);
                toDraw.add(chainsaw_blur);
            }
            if(placed.get(4)){
                toDraw.remove(rocket_blur);
                rocket_blur = getResources().getDrawable(R.drawable.rocket_blur, null);
                rocket_blur.setBounds((int) c_default.get(4).getX(), (int) c_default.get(4).getY(), (int) c_default.get(4).getX() + 300, (int) c_default.get(4).getY() + 200);
                toDraw.add(rocket_blur);
            }
            else{
                toDraw.remove(rocket);
                if(componentExist(5)) rocket = getResources().getDrawable(R.drawable.rocket, null);
                else  rocket = getResources().getDrawable(R.drawable.rocket_not, null);
                rocket.setBounds((int) c_default.get(4).getX(), (int) c_default.get(4).getY(), (int) c_default.get(4).getX() + 300, (int) c_default.get(4).getY() + 200);
                toDraw.add(rocket);
            }
            if(!placed.get(5)){
                toDraw.remove(stinger);
                if(componentExist(6)) stinger = getResources().getDrawable(R.drawable.stinger, null);
                else  stinger = getResources().getDrawable(R.drawable.stinger_not, null);
                stinger.setBounds((int)c_default.get(5).getX(),(int)c_default.get(5).getY(),(int)c_default.get(5).getX()+300,(int)c_default.get(5).getY()+200);
                toDraw.add(stinger);
            }
            else{
                toDraw.remove(stinger_blur);
                stinger_blur = getResources().getDrawable(R.drawable.stinger_blur, null);
                stinger_blur.setBounds((int) c_default.get(5).getX(), (int) c_default.get(5).getY(), (int) c_default.get(5).getX() + 300, (int) c_default.get(5).getY() + 200);
                toDraw.add(stinger_blur);

            }
            if(coordinates.size()==0){

                c.get(2).setX(50);
                c.get(2).setY(50);
            }
            else{

                c.get(2).setX(coordinates.get(coordinates.size()-1).getX());
                c.get(2).setY(coordinates.get(coordinates.size()-1).getY());
            }
            toDraw.remove(wheels);
            if(componentExist(3)) wheels = getResources().getDrawable(R.drawable.wheels, null);
            else  wheels = getResources().getDrawable(R.drawable.wheels_not, null);
            wheels.setBounds((int)c.get(2).getX()-150,(int)c.get(2).getY()-100,(int)c.get(2).getX()+150,(int)c.get(2).getY()+100);
            //wheels.draw(canvas);
            toDraw.add(wheels);


        }
        if(toMove==4){
            if(placed.get(4)){
                removeWeapon(5);
            }
            if(placed.get(5)){
                removeWeapon(6);
            }
            replaceWeaponAtributes(4,canvas);
            drawArrows(canvas);
            toDraw.remove(car);
            car = getResources().getDrawable(vehicle, null);
            car.setBounds((int)vehicleC.getX(),(int)vehicleC.getY(),(int)vehicleC.getX()+500,(int)vehicleC.getY()+300);
            toDraw.add(car);
            if(!placed.get(0)){
                toDraw.remove(blade);
                if(componentExist(1)) blade = getResources().getDrawable(R.drawable.blade, null);
                else  blade = getResources().getDrawable(R.drawable.blade_not, null);
                blade.setBounds((int)c_default.get(0).getX(),(int)c_default.get(0).getY(),(int)c_default.get(0).getX()+200,(int)c_default.get(0).getY()+200);
                toDraw.add(blade);
            }
            else{
                toDraw.remove(blade_blur);
                blade_blur = getResources().getDrawable(R.drawable.blade_blur, null);
                blade_blur.setBounds((int) c_default.get(0).getX(), (int) c_default.get(0).getY(), (int) c_default.get(0).getX() + 200, (int) c_default.get(0).getY() + 200);
                toDraw.add(blade_blur);
            }
            if(placed.get(1)){
                toDraw.remove(forklift_blur);
                forklift_blur = getResources().getDrawable(R.drawable.forklift_blur, null);
                forklift_blur.setBounds((int) c_default.get(1).getX(), (int) c_default.get(1).getY(), (int) c_default.get(1).getX() + 300, (int) c_default.get(1).getY() + 200);
                toDraw.add(forklift_blur);
            }
            else{
                toDraw.remove(forklift);
                if(componentExist(2)) forklift = getResources().getDrawable(R.drawable.forklift, null);
                else  forklift = getResources().getDrawable(R.drawable.forklift_not, null);
                forklift.setBounds((int)c_default.get(1).getX(),(int)c_default.get(1).getY(),(int)c_default.get(1).getX()+300,(int)c_default.get(1).getY()+200);
                toDraw.add(forklift);
            }
            if(placed.get(2)){
                toDraw.remove(wheels_blur);
                wheels_blur = getResources().getDrawable(R.drawable.wheels_blur, null);
                wheels_blur.setBounds((int) c_default.get(2).getX(), (int) c_default.get(2).getY(), (int) c_default.get(2).getX() + 300, (int) c_default.get(2).getY() + 200);
                toDraw.add(wheels_blur);
            }
            else{
                toDraw.remove(wheels);
                if(componentExist(3)) wheels = getResources().getDrawable(R.drawable.wheels, null);
                else  wheels = getResources().getDrawable(R.drawable.wheels_not, null);
                wheels.setBounds((int)c_default.get(2).getX(),(int)c_default.get(2).getY(),(int)c_default.get(2).getX()+300,(int)c_default.get(2).getY()+200);
                toDraw.add(wheels);
            }
            if(placed.get(4)){
                toDraw.remove(rocket_blur);
                rocket_blur = getResources().getDrawable(R.drawable.rocket_blur, null);
                rocket_blur.setBounds((int) c_default.get(4).getX(), (int) c_default.get(4).getY(), (int) c_default.get(4).getX() + 300, (int) c_default.get(4).getY() + 200);
                toDraw.add(rocket_blur);
            }
            else{
                toDraw.remove(rocket);
                if(componentExist(5)) rocket = getResources().getDrawable(R.drawable.rocket, null);
                else  rocket = getResources().getDrawable(R.drawable.rocket_not, null);
                rocket.setBounds((int) c_default.get(4).getX(), (int) c_default.get(4).getY(), (int) c_default.get(4).getX() + 300, (int) c_default.get(4).getY() + 200);
                toDraw.add(rocket);
            }

            if(!placed.get(5)){
                toDraw.remove(stinger);
                if(componentExist(6)) stinger = getResources().getDrawable(R.drawable.stinger, null);
                else  stinger = getResources().getDrawable(R.drawable.stinger_not, null);
                stinger.setBounds((int)c_default.get(5).getX(),(int)c_default.get(5).getY(),(int)c_default.get(5).getX()+300,(int)c_default.get(5).getY()+200);
                toDraw.add(stinger);
            }
            else{
                toDraw.remove(stinger_blur);
                stinger_blur = getResources().getDrawable(R.drawable.stinger_blur, null);
                stinger_blur.setBounds((int) c_default.get(5).getX(), (int) c_default.get(5).getY(), (int) c_default.get(5).getX() + 300, (int) c_default.get(5).getY() + 200);
                toDraw.add(stinger_blur);

            }

            if(coordinates.size()==0){

                c.get(3).setX(50);
                c.get(3).setY(50);
            }
            else{

                c.get(3).setX(coordinates.get(coordinates.size()-1).getX());
                c.get(3).setY(coordinates.get(coordinates.size()-1).getY());
            }
            toDraw.remove(chainsaw);
            if(componentExist(4)) chainsaw = getResources().getDrawable(R.drawable.chainsaw, null);
            else  chainsaw = getResources().getDrawable(R.drawable.chainsaw_not, null);
            chainsaw.setBounds((int)c.get(3).getX()-150,(int)c.get(3).getY()-100,(int)c.get(3).getX()+150,(int)c.get(3).getY()+100);
            toDraw.add(chainsaw);


        }
        if(toMove==5){
            if(placed.get(5)){
                removeWeapon(6);
            }
            if(placed.get(3)){
                removeWeapon(4);
            }
            replaceWeaponAtributes(5,canvas);
            drawArrows(canvas);
            toDraw.remove(car);
            car = getResources().getDrawable(vehicle, null);
            car.setBounds((int)vehicleC.getX(),(int)vehicleC.getY(),(int)vehicleC.getX()+500,(int)vehicleC.getY()+300);
            toDraw.add(car);
            if(!placed.get(0)){
                toDraw.remove(blade);
                if(componentExist(1)) blade = getResources().getDrawable(R.drawable.blade, null);
                else  blade = getResources().getDrawable(R.drawable.blade_not, null);
                blade.setBounds((int)c_default.get(0).getX(),(int)c_default.get(0).getY(),(int)c_default.get(0).getX()+200,(int)c_default.get(0).getY()+200);
                toDraw.add(blade);
            }
            else{
                toDraw.remove(blade_blur);
                blade_blur = getResources().getDrawable(R.drawable.blade_blur, null);
                blade_blur.setBounds((int) c_default.get(0).getX(), (int) c_default.get(0).getY(), (int) c_default.get(0).getX() + 200, (int) c_default.get(0).getY() + 200);
                toDraw.add(blade_blur);
            }
            if(placed.get(1)){
                toDraw.remove(forklift_blur);
                forklift_blur = getResources().getDrawable(R.drawable.forklift_blur, null);
                forklift_blur.setBounds((int) c_default.get(1).getX(), (int) c_default.get(1).getY(), (int) c_default.get(1).getX() + 300, (int) c_default.get(1).getY() + 200);
                toDraw.add(forklift_blur);
            }
            else{
                toDraw.remove(forklift);
                if(componentExist(2)) forklift = getResources().getDrawable(R.drawable.forklift, null);
                else  forklift = getResources().getDrawable(R.drawable.forklift_not, null);
                forklift.setBounds((int)c_default.get(1).getX(),(int)c_default.get(1).getY(),(int)c_default.get(1).getX()+300,(int)c_default.get(1).getY()+200);
                toDraw.add(forklift);
            }
            if(placed.get(2)){
                toDraw.remove(wheels_blur);
                wheels_blur = getResources().getDrawable(R.drawable.wheels_blur, null);
                wheels_blur.setBounds((int) c_default.get(2).getX(), (int) c_default.get(2).getY(), (int) c_default.get(2).getX() + 300, (int) c_default.get(2).getY() + 200);
                toDraw.add(wheels_blur);
            }
            else{
                toDraw.remove(wheels);
                if(componentExist(3)) wheels = getResources().getDrawable(R.drawable.wheels, null);
                else  wheels = getResources().getDrawable(R.drawable.wheels_not, null);
                wheels.setBounds((int)c_default.get(2).getX(),(int)c_default.get(2).getY(),(int)c_default.get(2).getX()+300,(int)c_default.get(2).getY()+200);
                toDraw.add(wheels);
            }
            if(!placed.get(3)){
                toDraw.remove(chainsaw);
                if(componentExist(4)) chainsaw = getResources().getDrawable(R.drawable.chainsaw, null);
                else  chainsaw = getResources().getDrawable(R.drawable.chainsaw_not, null);
                chainsaw.setBounds((int)c_default.get(3).getX(),(int)c_default.get(3).getY(),(int)c_default.get(3).getX()+300,(int)c_default.get(3).getY()+200);
                toDraw.add(chainsaw);
            }
            else{
                toDraw.remove(chainsaw_blur);
                chainsaw_blur = getResources().getDrawable(R.drawable.chainsaw_blur, null);
                chainsaw_blur.setBounds((int) c_default.get(3).getX(), (int) c_default.get(3).getY(), (int) c_default.get(3).getX() + 300, (int) c_default.get(3).getY() + 200);
                toDraw.add(chainsaw_blur);

            }
            if(!placed.get(5)){
                toDraw.remove(stinger);
                if(componentExist(6)) stinger = getResources().getDrawable(R.drawable.stinger, null);
                else  stinger = getResources().getDrawable(R.drawable.stinger_not, null);
                stinger.setBounds((int)c_default.get(5).getX(),(int)c_default.get(5).getY(),(int)c_default.get(5).getX()+300,(int)c_default.get(5).getY()+200);
                toDraw.add(stinger);
            }
            else{
                toDraw.remove(stinger_blur);
                stinger_blur = getResources().getDrawable(R.drawable.stinger_blur, null);
                stinger_blur.setBounds((int) c_default.get(5).getX(), (int) c_default.get(5).getY(), (int) c_default.get(5).getX() + 300, (int) c_default.get(5).getY() + 200);
                toDraw.add(stinger_blur);

            }


            if(coordinates.size()==0){

                c.get(4).setX(50);
                c.get(4).setY(50);
            }
            else{

                c.get(4).setX(coordinates.get(coordinates.size()-1).getX());
                c.get(4).setY(coordinates.get(coordinates.size()-1).getY());
            }
            toDraw.remove(rocket);
            if(componentExist(5)) rocket = getResources().getDrawable(R.drawable.rocket, null);
            else  rocket = getResources().getDrawable(R.drawable.rocket_not, null);
            rocket.setBounds((int)c.get(4).getX()-150,(int)c.get(4).getY()-100,(int)c.get(4).getX()+150,(int)c.get(4).getY()+100);
            toDraw.add(rocket);


        }
        if(toMove==6){
            if(placed.get(4)){
                removeWeapon(5);
            }
            if(placed.get(3)){
                removeWeapon(4);
            }
            replaceWeaponAtributes(6,canvas);
            drawArrows(canvas);
            toDraw.remove(car);
            car = getResources().getDrawable(vehicle, null);
            car.setBounds((int)vehicleC.getX(),(int)vehicleC.getY(),(int)vehicleC.getX()+500,(int)vehicleC.getY()+300);
            toDraw.add(car);
            if(!placed.get(0)){
                toDraw.remove(blade);
                if(componentExist(1)) blade = getResources().getDrawable(R.drawable.blade, null);
                else  blade = getResources().getDrawable(R.drawable.blade_not, null);
                blade.setBounds((int)c_default.get(0).getX(),(int)c_default.get(0).getY(),(int)c_default.get(0).getX()+200,(int)c_default.get(0).getY()+200);
                toDraw.add(blade);
            }
            else{
                toDraw.remove(blade_blur);
                blade_blur = getResources().getDrawable(R.drawable.blade_blur, null);
                blade_blur.setBounds((int) c_default.get(0).getX(), (int) c_default.get(0).getY(), (int) c_default.get(0).getX() + 200, (int) c_default.get(0).getY() + 200);
                toDraw.add(blade_blur);
            }
            if(placed.get(1)){
                toDraw.remove(forklift_blur);
                forklift_blur = getResources().getDrawable(R.drawable.forklift_blur, null);
                forklift_blur.setBounds((int) c_default.get(1).getX(), (int) c_default.get(1).getY(), (int) c_default.get(1).getX() + 300, (int) c_default.get(1).getY() + 200);
                toDraw.add(forklift_blur);
            }
            else{
                toDraw.remove(forklift);
                if(componentExist(2)) forklift = getResources().getDrawable(R.drawable.forklift, null);
                else  forklift = getResources().getDrawable(R.drawable.forklift_not, null);
                forklift.setBounds((int)c_default.get(1).getX(),(int)c_default.get(1).getY(),(int)c_default.get(1).getX()+300,(int)c_default.get(1).getY()+200);
                toDraw.add(forklift);
            }
            if(placed.get(2)){
                toDraw.remove(wheels_blur);
                wheels_blur = getResources().getDrawable(R.drawable.wheels_blur, null);
                wheels_blur.setBounds((int) c_default.get(2).getX(), (int) c_default.get(2).getY(), (int) c_default.get(2).getX() + 300, (int) c_default.get(2).getY() + 200);
                toDraw.add(wheels_blur);
            }
            else{
                toDraw.remove(wheels);
                if(componentExist(3)) wheels = getResources().getDrawable(R.drawable.wheels, null);
                else  wheels = getResources().getDrawable(R.drawable.wheels_not, null);
                wheels.setBounds((int)c_default.get(2).getX(),(int)c_default.get(2).getY(),(int)c_default.get(2).getX()+300,(int)c_default.get(2).getY()+200);
                toDraw.add(wheels);
            }
            if(!placed.get(3)){
                toDraw.remove(chainsaw);
                if(componentExist(4)) chainsaw = getResources().getDrawable(R.drawable.chainsaw, null);
                else  chainsaw = getResources().getDrawable(R.drawable.chainsaw_not, null);
                chainsaw.setBounds((int)c_default.get(3).getX(),(int)c_default.get(3).getY(),(int)c_default.get(3).getX()+300,(int)c_default.get(3).getY()+200);
                toDraw.add(chainsaw);
            }
            else{
                toDraw.remove(chainsaw_blur);
                chainsaw_blur = getResources().getDrawable(R.drawable.chainsaw_blur, null);
                chainsaw_blur.setBounds((int) c_default.get(3).getX(), (int) c_default.get(3).getY(), (int) c_default.get(3).getX() + 300, (int) c_default.get(3).getY() + 200);
                toDraw.add(chainsaw_blur);

            }
            if(placed.get(4)){
                toDraw.remove(rocket_blur);
                rocket_blur = getResources().getDrawable(R.drawable.rocket_blur, null);
                rocket_blur.setBounds((int) c_default.get(4).getX(), (int) c_default.get(4).getY(), (int) c_default.get(4).getX() + 300, (int) c_default.get(4).getY() + 200);
                toDraw.add(rocket_blur);
            }
            else{
                toDraw.remove(rocket);
                if(componentExist(5)) rocket = getResources().getDrawable(R.drawable.rocket, null);
                else  rocket = getResources().getDrawable(R.drawable.rocket_not, null);
                rocket.setBounds((int) c_default.get(4).getX(), (int) c_default.get(4).getY(), (int) c_default.get(4).getX() + 300, (int) c_default.get(4).getY() + 200);
                toDraw.add(rocket);
            }


            if(coordinates.size()==0){

                c.get(5).setX(50);
                c.get(5).setY(50);
            }
            else{

                c.get(5).setX(coordinates.get(coordinates.size()-1).getX());
                c.get(5).setY(coordinates.get(coordinates.size()-1).getY());
            }
            toDraw.remove(stinger);
            if(componentExist(6)) stinger = getResources().getDrawable(R.drawable.stinger, null);
            else  stinger = getResources().getDrawable(R.drawable.stinger_not, null);
            stinger.setBounds((int)c.get(5).getX()-150,(int)c.get(5).getY()-100,(int)c.get(5).getX()+150,(int)c.get(5).getY()+100);
            toDraw.add(stinger);


        }


        //prvo nacrtaj auto
        for(int i=0;i<toDraw.size();i++){
            if(toDraw.get(i).equals(car)) toDraw.get(i).draw(canvas);
        }
        for(int i=0;i<toDraw.size();i++){
            if(!toDraw.get(i).equals(car)) toDraw.get(i).draw(canvas);
            if(toDraw.get(i).equals(wheels)){
                if(wheels.getBounds().left==325){
                    wheels2 = getResources().getDrawable(R.drawable.wheels, null);
                    wheels2.setBounds(620,515,920,715);
                    wheels2.draw(canvas);
                }
            }
        }

        int x,y;
        x=1150; y=600;

        Paint p=new Paint();
        p.setColor(getResources().getColor(R.color.colorPrimaryDark));
        p.setTextSize(38);
        p.setFakeBoldText(true);
        canvas.drawText(""+model.getHeart().getValue(),x,y,p);
        canvas.drawText(""+model.getSword().getValue(),x,y+50,p);
        canvas.drawText(""+model.getThunder().getValue(),x,y+100,p);

        heart = getResources().getDrawable(R.drawable.heart, null);
        sword = getResources().getDrawable(R.drawable.sword, null);
        thunder = getResources().getDrawable(R.drawable.thunder, null);

        heart.setBounds(x+50,y-35,x+50+30,y-35+30);
        sword.setBounds(x+50,y+15,x+50+30,y+30+15);
        thunder.setBounds(x+50,y+65,x+50+30,y+30+65);

        heart.draw(canvas);
        sword.draw(canvas);
        thunder.draw(canvas);


        super.onDraw(canvas);
    }



}

