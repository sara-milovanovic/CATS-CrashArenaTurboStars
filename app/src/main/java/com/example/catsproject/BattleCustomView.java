package com.example.catsproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.catsproject.db.Components;
import com.example.catsproject.db.async_tasks.AsyncTaskUpdateScore;

import java.util.ArrayList;

public class BattleCustomView extends View {
    ArrayList<Coordinate> coordinates=new ArrayList<>();
    ArrayList<Coordinate> coordinates2=new ArrayList<>();
    Paint paint, paint2;

    ArrayList<Drawable> toDraw=new ArrayList<Drawable>();
    ArrayList<Drawable> toDraw2=new ArrayList<Drawable>();

    //0-blade 1-forklift 2-wheels -chainsaw 4-rocket
    public static ArrayList<Coordinate> c=new ArrayList<Coordinate>();
    public static ArrayList<Coordinate> c_default=new ArrayList<Coordinate>();
    public static ArrayList<Coordinate> c_place=new ArrayList<Coordinate>();

    Drawable blade=getResources().getDrawable(R.drawable.blade); //slika
    Drawable forklift=getResources().getDrawable(R.drawable.forklift2);
    Drawable wheels=getResources().getDrawable(R.drawable.wheels);
    Drawable wheels2=getResources().getDrawable(R.drawable.wheels);
    Drawable rocket=getResources().getDrawable(R.drawable.rocket);
    Drawable chainsaw=getResources().getDrawable(R.drawable.chainsaw);
    Drawable stinger=getResources().getDrawable(R.drawable.stinger);
    Drawable bg=getResources().getDrawable(R.drawable.bg);
    Drawable heart=getResources().getDrawable(R.drawable.heart);

    float car2X=860, car1X=315;
    float lengthWeapon=80;

    float leftWallX=-70, leftWallY=300;
    float rightWallX=1100, rightWallY=300;

    int leftWallHeight=300, rightWallHeight=300;
    int leftWallWidth=180, rightWallWidth=180;

    MyViewModel model;

    ArrayList<Boolean> placed=new ArrayList<>();

    ArrayList<Components> components;

    boolean sudar=false;
    int lost=0;

    int angle=0;//blade
    int angleF1=0,angleF2=0;//forklift
    float pivotX= (float) (265), pivotY= (float) (440);

    float rocket1X =10000, rocket2X =-10000;
    float rocket1Y =500, rocket2Y =500;
    float inc_rocket1Y =0, inc_rocket2Y =0;

    Drawable car=getResources().getDrawable(R.drawable.vehicle_2);
    Drawable l=getResources().getDrawable(R.drawable.lost);

    Vehicle vehicle=new Vehicle();
    public boolean moveWalls=false;
    public boolean incWalls=false;
    private boolean enemieHaveWheels;
    private boolean haveWheels;
    private boolean enemieHaveRocket=false;
    private boolean haveRocket=false;
    private boolean enemieHaveForklift;
    private boolean haveForklift;
    private float forkliftY1=570;
    private float forkliftY2=570;
    private float forkliftX1=130;
    private float forkliftX2=1070;
    private int rotationAngle1=-5;
    private int rotationAngle2=5;
    private int counter;
    private int inc1=-1, inc2=1;
    private int counter2;

    public int wall1Dec;
    public int wall2Dec;

    int t=200;
    private boolean enemieHaveBlade;
    private boolean haveBlade;

    public boolean dontHaveWeapon(){
        boolean flag=true;
        if(toDraw.size()>1){
            if(toDraw.size()==2 &&
                    ((toDraw.get(0).equals(car) && toDraw.get(1).equals(wheels))||(toDraw.get(1).equals(car) && toDraw.get(0).equals(wheels)))){
                flag=true;
            }
            else flag=false;
        }
        return flag;
    }

    public boolean enemieDontHaveWeapon(){
        boolean flag=true;
        if(toDraw2.size()>1){
            if(toDraw2.size()==2 &&
                    ((toDraw2.get(0).equals(car) && toDraw2.get(1).equals(wheels))||(toDraw2.get(1).equals(car) && toDraw2.get(0).equals(wheels)))){
                flag=true;
            }
            else flag=false;
        }
        return flag;
    }


    public BattleCustomView(Context context) {
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

    public BattleCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BattleCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void setModel(MyViewModel m){
        model=m;

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
        vehicle.c=model.context.getValue();
        vehicle.model=model;
        vehicle.init();

        vehicle.setScreenDimensions(model.activity.getValue());

        genrateEnemieCar();

    }

    private void genrateEnemieCar() {
        boolean wheels_random=Math.random()<0.35?true:false;
        boolean forklift_random=Math.random()<0.35?true:false;
        double right_component=Math.random();
        boolean blade_random=Math.random()<0.35?true:false;
        System.out.println("WHEELS: "+wheels_random);

        toDraw2.add(car);
        if(blade_random) toDraw2.add(blade);
        if(wheels_random) toDraw2.add(wheels);
        if(forklift_random) toDraw2.add(forklift);
        if(right_component>0.4) {
            if(right_component<0.6) toDraw2.add(rocket);
            else{
                if(right_component<0.8) toDraw2.add(stinger);
                else toDraw2.add(chainsaw);
            }
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


    public void setWallsDec(){
        this.wall1Dec=(int)(Math.random()*20);
        this.wall2Dec=(int)(Math.random()*20);
    }

    public void decreseHealthByWalls() {
        System.out.println("dec by walls with dec1="+wall1Dec+" and dec2="+wall2Dec);
        System.out.println("dec by walls with lwx="+leftWallX+" and c1x="+vehicle.carX);
        System.out.println("dec by walls with rwx="+rightWallX+" and c2x="+vehicle.carXX);
        if((haveWheels&&leftWallX>300) || (!haveWheels)) model.postHeart(model.getHeart().getValue()-wall1Dec<0?0:model.getHeart().getValue()-wall1Dec);
        if((enemieHaveWheels&&rightWallX<600) || (!enemieHaveWheels)) model.postHeart2(model.getHeart2().getValue()-wall2Dec<0?0:model.getHeart2().getValue()-wall2Dec);

    }

    public void decreseHealth() {
        int toDec1=0;
        int toDec2=0;
        for(Drawable d:toDraw){
            if(d.equals(stinger)){
                toDec2+=Vehicle.sword[5];
            }
            if(d.equals(chainsaw)){
                toDec2+=Vehicle.sword[3];
            }
            if(d.equals(blade)){
                toDec2+=Vehicle.sword[0];
            }
            if(d.equals(forklift)){
                toDec2+=Vehicle.sword[2];
            }
        }
        for(Drawable d:toDraw2){
            if(d.equals(stinger)){
                toDec1+=Vehicle.sword[5];
            }
            if(d.equals(chainsaw)){
                toDec1+=Vehicle.sword[3];
            }
            if(d.equals(blade)){
                toDec1+=Vehicle.sword[0];
            }
            if(d.equals(forklift)){
                toDec1+=Vehicle.sword[2];
            }
        }
        System.out.println("to dec 1 = "+toDec1);
        System.out.println("to dec 2 = "+toDec2);
        model.postHeart(model.getHeart().getValue()-toDec1<0?0:model.getHeart().getValue()-toDec1);
        model.postHeart2(model.getHeart2().getValue()-toDec2<0?0:model.getHeart2().getValue()-toDec2);


    }

    public void drawLost() {
        lost=1;
        System.out.println("***********************************************************LOST");
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

    public void increseLength(ArrayList<Drawable> toDraw,int minus, int car){
        for(Drawable d:toDraw){
            if(d.equals(stinger) || (d.equals(chainsaw))){
                if(car==1) car1X=car1X+minus*lengthWeapon;
                if(car==2) car2X=car2X+minus*lengthWeapon;
                break;
            }
            else{
                if(d.equals(rocket)){
                    if(car==1) car1X=car1X+minus*20;
                    if(car==2) car2X=car2X+minus*20;
                    break;
                }
            }
        }
    }

    public int calculateHealth(ArrayList<Drawable> dr){
        int h=85;
        int index=0;
        for(Drawable d:dr){
            if(d.equals(wheels)){
                index=0;
            }
            else if(d.equals(forklift)){
                index=1;
            }
            else index=-1;
            if(index==0) h+=2*Vehicle.heart[index];
            if(index==1) h+=Vehicle.heart[index];
        }
        return h;
    }


    public boolean enemieHaveWheels() {
        boolean flag=false;
        for(Drawable c:toDraw2){
            if(c.equals(wheels)) flag=true;
        }
        enemieHaveWheels=flag;
        return flag;
    }

    public boolean haveWheels() {
        boolean flag=false;
        for(Drawable c:toDraw){
            if(c.equals(wheels)) flag=true;
        }
        haveWheels=flag;
        return flag;
    }

    public boolean enemieHaveRocket() {
        boolean flag=false;
        for(Drawable c:toDraw2){
            if(c.equals(rocket)) flag=true;
        }
        enemieHaveRocket=flag;
        return flag;
    }

    public boolean haveRocket() {
        boolean flag=false;
        for(Drawable c:toDraw){
            if(c.equals(rocket)) flag=true;
        }
        haveRocket=flag;
        return flag;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        counter++;

        if(counter>=20){
            counter2=1;
            counter=0;
            inc1=-1;
            inc2=1;
        }
        if(counter2!=0){
            counter2++;
        }
        if(counter2>=10){
            inc1=1;
            inc2=-1;
            counter2=0;
        }


        System.out.println("ROCKET1 Y:"+rocket1Y);
        System.out.println("ROCKET2 Y:"+rocket2Y);
        if((rocket1X >car2X && rocket2X <car1X && haveRocket && enemieHaveRocket)||(rocket2X <car1X && enemieHaveRocket && (!haveRocket || model.player_controls.getValue()))||(rocket2Y <0 && enemieHaveRocket && !haveRocket)||(rocket1X >car2X && haveRocket && !enemieHaveRocket)||(rocket1Y <0 && haveRocket && !enemieHaveRocket)){
            model.setGenerateRocket(true);
            System.out.println("+++++++++++++++GENERISI OPET++++++++++++++++");
            System.out.println("+++++++++++++++UDAR++++++++++++++++");
            System.out.println("+++++++++++++++rocket1X++++++++++++++++"+rocket1X);
            if(haveRocket && !model.player_controls.getValue()){
                int dec=model.getHeart2().getValue()-Vehicle.sword[5];
                if(dec>=0)
                    model.setHeart2(dec);
                else model.setHeart2(0);
            }
            else if(haveRocket && model.getUserGenerateRocket().getValue()){
                model.setUserGenerateRocket(false);
                int dec=model.getHeart2().getValue()-Vehicle.sword[5];
                if(dec>=0)
                    model.setHeart2(dec);
                else model.setHeart2(0);
            }
            if(enemieHaveRocket){
                int dec=model.getHeart().getValue()-Vehicle.sword[5];
                if(dec>=0)
                    model.setHeart(dec);
                else model.setHeart(0);
            }
        }

        if(haveRocket && (model.generateRocket.getValue() || (model.player_controls.getValue() && !model.getUserGenerateRocket().getValue())) ){
            rocket1X =car1X;
            inc_rocket1Y= (float) (Math.sin(Math.toDegrees(rotationAngle1)));/*+Vehicle.carWidth)*/;
            System.out.println("+++++++++++++++incY="+inc_rocket1Y+"++++++++++++++++");
            System.out.println("+++++++++++++++rotationAngle="+Math.toDegrees(rotationAngle1)%360+"++++++++++++++++");
            //System.out.println("+++++++++++++++INIT KOORDINATA 1++++++++++++++++");
        }


        if(enemieHaveRocket && model.generateRocket.getValue()){
            rocket2X =car2X;
            //System.out.println("+++++++++++++++INIT KOORDINATA 2++++++++++++++++");
        }


        if(lost==1){
            int h, w;
            int left;

            h=110;
            w=110;

            if(model.getHeart().getValue()<=0){
                left=(int)car1X;
            }
            else{
                left=(int)car2X;
            }

            l.setBounds(left,t,left+w,t+h);
            l.draw(canvas);
            t+=5;
            //return;
        }

        if(enemieHaveRocket && !sudar){
            rocket2X -=15;
            //System.out.println("+++++++++++++++UPDATE KOORDINATA 2++++++++++++++++");
            //System.out.println("+++++++++++++++DRAW 1++++++++++++++++");
            Drawable enemieRocket=getResources().getDrawable(R.drawable.bullet,null);
            Bitmap er=((BitmapDrawable)(enemieRocket)).getBitmap();
            Matrix m=new Matrix();
            m.setTranslate(rocket2X,500);
            m.postScale((float)(50)/(er.getWidth()),(float)(50)/(er.getHeight()), rocket2X,500);
            canvas.drawBitmap(er,m,null);
        }



        if((enemieHaveRocket || haveRocket)&&(model.generateRocket.getValue()/*||model.getUserGenerateRocket().getValue()*/)){
            model.setGenerateRocket(false);
            System.out.println("+++++++++++++++NE GENERISI OPET++++++++++++++++");
        }

        if(haveRocket && !sudar && !model.player_controls.getValue()){
            rocket1X +=15;
            //rocket1Y-=inc_rocket1Y*20;
            System.out.println("+++++++++++++++UPDATE KOORDINATA 1++++++++++++++++");
            //System.out.println("+++++++++++++++DRAW 2++++++++++++++++");
            System.out.println("+++++++++++++++DRAW 2++++++++++++++++");
            Drawable rocket=getResources().getDrawable(R.drawable.bullet2,null);
            Bitmap r=((BitmapDrawable)(rocket)).getBitmap();
            Matrix m=new Matrix();
            m.setTranslate(rocket1X,rocket1Y);
            m.postScale((float)(50)/(r.getWidth()),(float)(50)/(r.getHeight()), rocket1X,rocket1Y);
            canvas.drawBitmap(r,m,null);
        }
        if(haveRocket && !sudar && model.player_controls.getValue() && model.getUserGenerateRocket().getValue()){
            rocket1X +=15;
            //rocket1Y-=inc_rocket1Y*20;
            System.out.println("+++++++++++++++UPDATE KOORDINATA 1++++++++++++++++");
            //System.out.println("+++++++++++++++DRAW 2++++++++++++++++");
            System.out.println("+++++++++++++++DRAW 2++++++++++++++++");
            Drawable rocket=getResources().getDrawable(R.drawable.bullet2,null);
            Bitmap r=((BitmapDrawable)(rocket)).getBitmap();
            Matrix m=new Matrix();
            m.setTranslate(rocket1X,rocket1Y);
            m.postScale((float)(50)/(r.getWidth()),(float)(50)/(r.getHeight()), rocket1X,rocket1Y);
            canvas.drawBitmap(r,m,null);
        }


        //System.out.println(model.getHeart().getValue()+"-********************************************");

        if(model.go.getValue()) updatePosition(vehicle);



        if(car1X>=car2X){
            model.setGo(false);
            //System.out.println("!!!!!!!!!!!!!!!!!SUDAR!!!!!!!!!!!!!!!!!");
            if(haveForklift()){
                //rotate(1);
            }
            if(enemieHaveForklift()){
                //rotate(2);
            }
            sudar=true;
        }
        else{
            model.setGo(true);
        }

        System.out.println("car1: "+car1X+" car2:"+car2X);

        Drawable leftWall=getResources().getDrawable(R.drawable.wall,null);
        Bitmap bg=((BitmapDrawable)(leftWall)).getBitmap();
        Matrix mat_bg = new Matrix();
        mat_bg.setTranslate(leftWallX,leftWallY);
        mat_bg.postScale((float)(leftWallWidth)/(bg.getWidth()),(float)(leftWallHeight)/(bg.getHeight()),leftWallX,leftWallY);
        canvas.drawBitmap(bg,mat_bg,null);

        Drawable rightWall=getResources().getDrawable(R.drawable.wall2,null);
        Bitmap bg2=((BitmapDrawable)(rightWall)).getBitmap();
        Matrix mat_bg2 = new Matrix();
        mat_bg2.setTranslate(rightWallX,rightWallY);
        mat_bg2.postScale((float)(rightWallWidth)/(bg2.getWidth()),(float)(rightWallHeight)/(bg2.getHeight()),rightWallX,rightWallY);
        canvas.drawBitmap(bg2,mat_bg2,null);

        for(Drawable drawable:toDraw){
            Bitmap b=((BitmapDrawable)(drawable)).getBitmap();
            Matrix mat = new Matrix();
            if(drawable.equals(car)){
                mat.setTranslate(vehicle.carX,vehicle.carY);

                float imageHeight = b.getHeight();
                float imageWidth = b.getWidth();

                float scaleWidth = ((float) (Vehicle.carWidth)) / imageWidth;
                float scaleHeight = ((float) (Vehicle.carHeight)) / imageHeight;

                mat.postScale(scaleWidth,scaleHeight,vehicle.carX,vehicle.carY);
                mat.postScale(0.5f,0.5f,vehicle.carX,vehicle.carY);
                mat.postTranslate(0,100);
                if(enemieHaveForklift && sudar ){
                    mat.postRotate((rotationAngle1),forkliftX1,forkliftY1);
                }
                canvas.drawBitmap(b,mat,null);
            }
        }

        for(Drawable drawable:toDraw){
            Bitmap b=((BitmapDrawable)(drawable)).getBitmap();
            Matrix mat = new Matrix();
            int index=0;
            if(drawable.equals(blade)){
                index=0;
            }
            if(drawable.equals(forklift)){
                index=1;
            }
            if(drawable.equals(wheels)){
                index=2;
            }
            if(drawable.equals(chainsaw)){
                index=3;
            }
            if(drawable.equals(rocket)){
                index=4;
            }
            if(drawable.equals(stinger)){
                index=5;
            }
            b=((BitmapDrawable)(drawable)).getBitmap();

            mat.setTranslate(vehicle.x[index],vehicle.y[index]);

            float imageHeight = b.getHeight();
            float imageWidth = b.getWidth();

            float scaleWidth = ((float) (Vehicle.width[index])) / imageWidth;
            float scaleHeight = ((float) (Vehicle.height[index])) / imageHeight;

            mat.postScale(scaleWidth,scaleHeight,vehicle.x[index],vehicle.y[index]);


            if(drawable.equals(blade)){

                mat.postRotate(angle,vehicle.carX+Vehicle.carWidth/2+60,vehicle.carY+Vehicle.carHeight/2/2+100+16);
                angle=(angle+15)%360;

            }
            if(drawable.equals(forklift)){

                mat.postRotate(-angleF1,vehicle.carX+Vehicle.carWidth/2+130,vehicle.carY+Vehicle.carHeight/2/2+70);
                angleF1=(angleF1+20)%360;

            }
            mat.postScale(0.5f,0.5f,vehicle.carX,vehicle.carY);

            mat.postTranslate(0,100);
            if(enemieHaveForklift && sudar ){
                mat.postRotate((rotationAngle1),forkliftX1,forkliftY1);//enemie treba
            }
            if(!drawable.equals(car))canvas.drawBitmap(b,mat,null);
            if(drawable.equals(wheels)) {
                mat.setTranslate(vehicle.x[index]+325,vehicle.y[index]);
                mat.postScale(0.5f,0.5f,vehicle.carX,vehicle.carY);
                mat.postTranslate(0,100);
                if(enemieHaveForklift && sudar ){
                   mat.postRotate((rotationAngle1),forkliftX1,forkliftY1);//enemie treba
                }
                canvas.drawBitmap(b,mat,null);
            }
            if(rotationAngle1>(-90) && sudar && rotationAngle2<90) {
                if(enemieHaveForklift()){
                    if(inc1==1){
                        rotationAngle1=rotationAngle1+(inc1)*1;
                    }
                    else rotationAngle1=rotationAngle1+(inc1)*2;
                    if(rotationAngle1>0) rotationAngle1=0;
                }
            }
            if(rotationAngle2==90){
                rotationAngle1=0;
                model.setHeart2(0);
            }





        }

        //PROTIVNIK

        for(Drawable drawable:toDraw2){
            Bitmap b=((BitmapDrawable)(drawable)).getBitmap();
            Matrix mat = new Matrix();
            if(drawable.equals(car)){
                mat.setTranslate(vehicle.carXX,vehicle.carY);

                float imageHeight = b.getHeight();
                float imageWidth = b.getWidth();

                float scaleWidth = ((float) (Vehicle.carWidth)) / imageWidth;
                float scaleHeight = ((float) (Vehicle.carHeight)) / imageHeight;

                mat.postScale(scaleWidth,scaleHeight,vehicle.carXX,vehicle.carY);
                mat.postScale(0.5f,0.5f,vehicle.carXX,vehicle.carY);
                mat.postTranslate(0,100);
                mat.postScale(-1,1,vehicle.carWidth/2,vehicle.carHeight/2);
                mat.postTranslate(700,0);
                if(haveForklift && sudar ){
                    mat.postRotate((rotationAngle2),forkliftX2,forkliftY1);
                }
                canvas.drawBitmap(b,mat,null);
            }
        }

        for(Drawable drawable:toDraw2){
            Bitmap b=((BitmapDrawable)(drawable)).getBitmap();
            Matrix mat = new Matrix();
            int index=0;
            if(drawable.equals(blade)){
                index=0;
            }
            if(drawable.equals(forklift)){
                index=1;
            }
            if(drawable.equals(wheels)){
                index=2;
            }
            if(drawable.equals(chainsaw)){
                index=3;
            }
            if(drawable.equals(rocket)){
                index=4;
            }
            if(drawable.equals(stinger)){
                index=5;
            }
            b=((BitmapDrawable)(drawable)).getBitmap();

            mat.setTranslate(vehicle.x_[index],vehicle.y[index]);

            float imageHeight = b.getHeight();
            float imageWidth = b.getWidth();

            float scaleWidth = ((float) (Vehicle.width[index])) / imageWidth;
            float scaleHeight = ((float) (Vehicle.height[index])) / imageHeight;

            mat.postScale(scaleWidth,scaleHeight,vehicle.x_[index],vehicle.y[index]);


            if(drawable.equals(blade)){

                mat.postRotate(angle,vehicle.carXX+Vehicle.carWidth/2+60,vehicle.carY+Vehicle.carHeight/2/2+100+16);
                angle=(angle+15)%360;

            }
            if(drawable.equals(forklift)){

                mat.postRotate(-angleF2,vehicle.carXX+Vehicle.carWidth/2+130,vehicle.carY+Vehicle.carHeight/2/2+70);
                angleF2=(angleF2+20)%360;

            }
            mat.postScale(0.5f,0.5f,vehicle.carXX,vehicle.carY);

            mat.postTranslate(0,100);
            mat.postScale(-1,1,vehicle.carWidth/2,vehicle.carHeight/2);

            if(!drawable.equals(car)){
                mat.postTranslate(700,0);
                if(haveForklift && sudar ){
                    mat.postRotate((rotationAngle2),forkliftX2,forkliftY1);//enemie treba
                }
                canvas.drawBitmap(b,mat,null);
            }
            else{
                if(haveForklift && sudar ){
                    mat.postRotate((rotationAngle2),forkliftX2,forkliftY1);//enemie treba
                }
            }

            if(drawable.equals(wheels)) {
                mat.setTranslate(vehicle.x_[index]+325,vehicle.y[index]);
                mat.postScale(0.5f,0.5f,vehicle.carXX,vehicle.carY);
                mat.postScale(-1,1,vehicle.carWidth/2,vehicle.carHeight/2);
                mat.postTranslate(0,100);
                mat.postTranslate(700,0);
                if(haveForklift && sudar ){
                    mat.postRotate((rotationAngle2),forkliftX2,forkliftY2);//enemie treba
                }
                canvas.drawBitmap(b,mat,null);
            }


        }
        if(rotationAngle2<(90) && sudar && rotationAngle1>-90) {
            if(haveForklift()){
                if(inc2==-1){
                    rotationAngle2=rotationAngle2+(inc2)*1;
                }
                else rotationAngle2=rotationAngle2+(inc2)*2;
                if(rotationAngle2<0) rotationAngle2=0;
            }
        }
        System.out.println(rotationAngle1+"rangle1");
        System.out.println(rotationAngle2+"rangle2");
        if(rotationAngle1==-90){
            rotationAngle2=0;
            model.setHeart(0);
        }

        if(rotationAngle1<-55){
            rotationAngle1=-90;
        }

        super.onDraw(canvas);

    }

    public boolean haveForklift() {
        boolean flag=false;
        for(Drawable c:toDraw){
            if(c.equals(forklift)) flag=true;
        }
        haveForklift=flag;
        return flag;
    }

    public boolean haveBlade() {
        boolean flag=false;
        for(Drawable c:toDraw){
            if(c.equals(blade)) flag=true;
        }
        haveBlade=flag;
        return flag;
    }

    public boolean enemieHaveForklift() {
        boolean flag=false;
        for(Drawable c:toDraw2){
            if(c.equals(forklift)) flag=true;
        }
        enemieHaveForklift=flag;
        return flag;
    }

    public boolean enemieHaveBlade() {
        boolean flag=false;
        for(Drawable c:toDraw2){
            if(c.equals(blade)) flag=true;
        }
        enemieHaveBlade=flag;
        return flag;
    }

    private void rotate(int i) {
        switch (i){
            case 1:{
                forkliftY1=vehicle.carY+50;
                forkliftX1=vehicle.carX-200;
                System.out.println("----------------------------------Rotacija oko: "+forkliftX1+", "+forkliftY1+"----------------------------------");
                break;
            }
            case 2:{
                forkliftY2=vehicle.carY+50;
                forkliftX2=vehicle.carXX+200;
                System.out.println("----------------------------------Rotacija oko: "+forkliftX2+", "+forkliftY2+"----------------------------------");
                break;
            }
        }


    }

    private void updatePosition(Vehicle vehicle) {
        if(moveWalls){
            if(incWalls){
                leftWallX+=5;
                rightWallX-=5;
            }
            car1X+=5;
            car2X-=5;
            vehicle.carX+=5;
            vehicle.carXX+=5;
            forkliftX1+=5;
            forkliftX2-=5;
            for(int i=0;i<vehicle.x_.length;i++){
                vehicle.x_[i]+=5;
            }
            for(int i=0;i<vehicle.x.length;i++){
                vehicle.x[i]+=5;
            }
        }

        if(haveWheels){
            for(int i=0;i<vehicle.x.length;i++){
                vehicle.x[i]+=10;
            }
            vehicle.carX+=10;
            car1X+=10;
        }
        if(enemieHaveWheels()){
            for(int i=0;i<vehicle.x_.length;i++){
                vehicle.x_[i]+=10;
            }
            vehicle.carXX+=10;
            car2X-=10;
        }
        if(haveWheels){
            forkliftX1+=10;
        }
        if(enemieHaveWheels){
            forkliftX2-=10;
        }

    }


}

