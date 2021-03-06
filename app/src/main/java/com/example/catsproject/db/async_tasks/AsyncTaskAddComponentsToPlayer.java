package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.db.UserComponents;

public class AsyncTaskAddComponentsToPlayer extends AsyncTask<Integer, Void, Void> {

    MyViewModel model;

    public AsyncTaskAddComponentsToPlayer(MyViewModel m){
        model=m;
    }

    @Override
    protected Void doInBackground(Integer... components) {
        UserComponents uc;
        for(int i=0;i<components.length;i++) {
            int id=model.getCurrentPlayerId().getValue();
            uc = new UserComponents(id, components[i]);
            MainActivity.appDatabase.userComponentsDao().insertAll(uc);
        }
        return null;
    }
}
