package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.db.Score;

public class AsyncTaskUpdateScore extends AsyncTask<Integer, Void, Void> {

    MyViewModel model;

    public AsyncTaskUpdateScore(MyViewModel model) {
        this.model=model;
    }

    @Override
    protected Void doInBackground(Integer... win) {
        if(MainActivity.appDatabase.scoreDao().getScore(model.getCurrentPlayerId().getValue())!=null){
            if(win[0]==1) MainActivity.appDatabase.scoreDao().incWins(model.getCurrentPlayerId().getValue());
            else MainActivity.appDatabase.scoreDao().incLose(model.getCurrentPlayerId().getValue());
        }
        else{
            Score score=new Score(model.getCurrentPlayerId().getValue());
            if(win[0]==1){
                score.win=1;
                score.lose=0;
            }
            else{
                score.win=0;
                score.lose=1;
            }
            MainActivity.appDatabase.scoreDao().insertAll(score);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
    }
}
