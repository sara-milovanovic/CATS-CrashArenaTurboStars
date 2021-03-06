package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.db.Components;
import com.example.catsproject.db.UserAddedComponents;
import com.example.catsproject.db.UserComponents;

public class AsyncTaskRemoveComponentToPlayer extends AsyncTask<Components, Void, Void> {

    MyViewModel model;

    public AsyncTaskRemoveComponentToPlayer(MyViewModel m){
        model=m;
    }
    @Override
    protected Void doInBackground(Components... components) {
        UserComponents u=new UserComponents();
        for(int i=0;i<components.length;i++){
            u.userId=model.getCurrentPlayerId().getValue();
            u.compId=components[i].cid;
            MainActivity.appDatabase.userComponentsDao().deleteAll(u);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
    }
}
