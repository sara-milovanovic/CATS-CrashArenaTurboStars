package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.db.UserBoxes;

public class AsyncTaskSaveBoxes2 extends AsyncTask<Integer, Void, Void> {

    MyViewModel model;

    public AsyncTaskSaveBoxes2(MyViewModel m){
        model=m;
    }
    @Override
    protected Void doInBackground(Integer... users) {

        int id=MainActivity.appDatabase.userBoxesDao().getMaxId(model.getCurrentPlayerId().getValue());
        System.out.println("-----------------------------------------------------MAX ID JE : "+id);
        if(users[1]==1){
            for(int i=0;i<4;i++){
                if(model.getBoxActive().getValue().get(i)){

                    UserBoxes ub=new UserBoxes();
                    ub.savedTime=System.currentTimeMillis();
                    ub.userId=model.getCurrentPlayerId().getValue();
                    ub.boxId=++id;
                    ub.currentTime=model.getBoxTime().getValue().get(i)/*-model.getTimeRemaining().getValue().get(i)*/;
                    ub.elapsedTime=model.getTimeRemaining().getValue().get(i);
                    MainActivity.appDatabase.userBoxesDao().insertAll(ub);
                }
            }
        }
        else{
            for(int i=0;i<4;i++){
                if(model.getBoxActive().getValue().get(i)){

                    UserBoxes ub=new UserBoxes();
                    ub.savedTime=System.currentTimeMillis();
                    ub.userId=model.getCurrentPlayerId().getValue();
                    ub.boxId=++id;
                    ub.currentTime=model.getBoxTime().getValue().get(i)-model.getTimeRemaining().getValue().get(i);
                    ub.elapsedTime=model.getTimeRemaining().getValue().get(i);
                    MainActivity.appDatabase.userBoxesDao().insertAll(ub);
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {




    }
}
