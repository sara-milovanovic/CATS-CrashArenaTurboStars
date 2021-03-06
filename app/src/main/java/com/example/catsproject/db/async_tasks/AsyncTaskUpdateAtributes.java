package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;

public class AsyncTaskUpdateAtributes extends AsyncTask<Integer, Void, Void> {

    MyViewModel model;

    public AsyncTaskUpdateAtributes(MyViewModel model) {
        this.model=model;
    }

    @Override
    protected Void doInBackground(Integer... atributes) {
        //heart, sword, thunder
        MainActivity.appDatabase.playerDao().updateHeart(atributes[0],model.getCurrentPlayerId().getValue());
        MainActivity.appDatabase.playerDao().updateSword(atributes[1],model.getCurrentPlayerId().getValue());
        MainActivity.appDatabase.playerDao().updateThunder(atributes[2],model.getCurrentPlayerId().getValue());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
    }
}
