package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.db.Player;

public class AsyncTaskAddPlayer extends AsyncTask<Player, Void, Void> {

    MyViewModel model;

    public AsyncTaskAddPlayer(MyViewModel m){
        model=m;
    }
    @Override
    protected Void doInBackground(Player... users) {

        MainActivity.appDatabase.playerDao().insertAll(users[0]);
        model.postCurrentPlayerId(MainActivity.appDatabase.playerDao().findByNameOnly(users[0].username));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        new AsyncTaskAddComponentsToPlayer(model).execute(1,3,4);

    }
}
