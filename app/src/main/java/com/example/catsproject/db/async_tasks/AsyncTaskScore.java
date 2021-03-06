package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.db.Score;

public class AsyncTaskScore extends AsyncTask<Integer, Void, Void> {

    MyViewModel model;

    public AsyncTaskScore(MyViewModel model) {
        this.model=model;
    }

    @Override
    protected Void doInBackground(Integer... win) {
        if(MainActivity.appDatabase.scoreDao().getScore(model.getCurrentPlayerId().getValue())==null){
            Score score=new Score(model.getCurrentPlayerId().getValue());

            score.win=0;
            score.lose=0;

            MainActivity.appDatabase.scoreDao().insertAll(score);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
    }
}
