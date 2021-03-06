package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.db.Components;
import com.example.catsproject.db.Player;
import com.example.catsproject.db.UserAddedComponents;

public class AsyncTaskAddAttachedComponentToPlayer extends AsyncTask<Components, Void, Void> {

    MyViewModel model;

    public AsyncTaskAddAttachedComponentToPlayer(MyViewModel m){
        model=m;
    }
    @Override
    protected Void doInBackground(Components... components) {
        UserAddedComponents u=new UserAddedComponents();
        for(int i=0;i<components.length;i++){
            u.userId=model.getCurrentPlayerId().getValue();
            u.compId=components[i].cid;
            MainActivity.appDatabase.userAddedComponentsDao().insertAll(u);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
    }
}
