package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.db.Score;

public class AsyncTaskUpdateGenerateBox extends AsyncTask<Integer, Void, Void> {

    MyViewModel model;

    public AsyncTaskUpdateGenerateBox(MyViewModel model) {
        this.model=model;
    }

    @Override
    protected Void doInBackground(Integer... win) {
        if(win[0]==1) MainActivity.appDatabase.playerDao().trueGenerate(model.getCurrentPlayerId().getValue());
        else MainActivity.appDatabase.playerDao().falseGenerate(model.getCurrentPlayerId().getValue());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
    }
}
