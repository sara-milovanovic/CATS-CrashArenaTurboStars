package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.R;

public class AsyncTaskGetGenerate extends AsyncTask<Integer, Void, Void> {

    MyViewModel model;

    public AsyncTaskGetGenerate(MyViewModel m){
        model=m;
    }
    @Override
    protected Void doInBackground(Integer... users) {
        model.postToGenerateBox(MainActivity.appDatabase.playerDao().toGenerate(users[0]));
        System.out.println("postded generate to = "+model.getGenerateBox().getValue());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

    }
}
