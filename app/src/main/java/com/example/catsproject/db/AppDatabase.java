package com.example.catsproject.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Player.class, Components.class,UserComponents.class, UserAddedComponents.class, Score.class, UserBoxes.class}, version = 21, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerDao playerDao();
    public abstract ComponentsDao componentsDao();
    public abstract UserComponentsDao userComponentsDao();
    public abstract UserAddedComponentsDao userAddedComponentsDao();
    public abstract ScoreDao scoreDao();
    public abstract UserBoxesDao userBoxesDao();

    private static AppDatabase appDatabase;

    public static AppDatabase getInstance(final Context c){

        if(appDatabase==null){

            synchronized (AppDatabase.class){

                if(appDatabase==null){
                    appDatabase= Room.databaseBuilder(c.getApplicationContext(),AppDatabase.class, "CATS_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .build();
                }

            }

        }
        return appDatabase;

    }

    public static RoomDatabase.Callback callback=new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            //init database
            //new MyAsyncTask(appDatabase).execute();

        }
    };

    private static class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        AppDatabase appDatabase;

        public MyAsyncTask(AppDatabase appDatabase){
            this.appDatabase=appDatabase;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //updates
            /*appDatabase.componentsDao().updateEntireComponent(1,"blade",10,10,6);
            appDatabase.componentsDao().updateEntireComponent(2,"forklift",10,10,2);
            appDatabase.componentsDao().updateEntireComponent(3,"wheels",10,25,10);
            appDatabase.componentsDao().updateEntireComponent(4,"chainsaw",23,10,6);
            appDatabase.componentsDao().updateEntireComponent(5,"rocket",18,10,7);
            appDatabase.componentsDao().updateEntireComponent(6,"stinger",37,10,7);*/

            //init


            final Components k1=new Components();
            k1.energy=6;
            k1.power=10;
            k1.health=10;
            k1.name="blade";

            final Components k2=new Components();
            k2.energy=2;
            k2.power=10;
            k2.health=10;
            k2.name="forklift";

            final Components k3=new Components();
            k3.energy=10;
            k3.power=10;
            k3.health=10;
            k3.name="wheels";

            final Components k4=new Components();
            k4.energy=6;
            k4.power=23;
            k4.health=10;
            k4.name="chainsaw";

            final Components k5=new Components();
            k5.energy=4;
            k5.power=7;
            k5.health=10;
            k5.name="rocket";

            final Components k6=new Components();
            k6.energy=4;
            k6.power=9;
            k6.health=10;
            k6.name="stinger";

            appDatabase.componentsDao().insertAll(k1,k2,k3,k4,k5,k6);



            //appDatabase.componentsDao().deleteGreaterThan(6);

            return null;
        }
    }



}
