package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.db.Player;
import com.example.catsproject.db.UserBoxes;

public class AsyncTaskSaveBoxes extends AsyncTask<Integer, Void, Void> {

    MyViewModel model;
    int i;

    public AsyncTaskSaveBoxes(MyViewModel m){
        model=m;
    }
    @Override
    protected Void doInBackground(Integer... users) {

        MainActivity.appDatabase.userBoxesDao().removeForUser(users[0]);
        i=users[1];
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {


        new AsyncTaskSaveBoxes2(model).execute(model.getCurrentPlayerId().getValue(),i);

    }
}
