package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.R;
import com.example.catsproject.db.Components;
import com.example.catsproject.db.UserBoxes;

import java.util.List;

public class AsyncTaskGetBoxes extends AsyncTask<Integer, Void, Void> {

    MyViewModel model;

    public AsyncTaskGetBoxes(MyViewModel m){
        model=m;
    }
    @Override
    protected Void doInBackground(Integer... users) {

        List<UserBoxes> l= MainActivity.appDatabase.userBoxesDao().getComponentsForPlayer(users[0]);
        model.postCurrentPlayerBoxes(l);
        for(int i=0;i<4;i++){
            model.getBoxActive().getValue().set(i,false);
            model.getBoxTime().getValue().set(i,300);
        }
        long time=System.currentTimeMillis();
        for(int i=0;i<l.size();i++){
            model.postActivateBox();
            model.getBoxTime().getValue().set(i,l.get(i).currentTime);
            System.out.println("za kutiju "+i+model.getBoxTime().getValue().get(i)+"-"+(int)((time-l.get(i).savedTime)/1000)+"-"+l.get(i).elapsedTime+"(elapsed)");
            model.getBoxTime().getValue().set(i,(int)(model.getBoxTime().getValue().get(i)-/*fali minus koliko je isteklo do tad*/(int)(time-l.get(i).savedTime)/1000)/*-l.get(i).elapsedTime*/);
        }
        MainActivity.appDatabase.userBoxesDao().removeForUser(model.getCurrentPlayerId().getValue());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
    }
}
