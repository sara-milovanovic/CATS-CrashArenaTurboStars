package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.R;

public class AsyncTaskGetPlayerPassword extends AsyncTask<Integer, Void, Void> {

    MyViewModel model;

    public AsyncTaskGetPlayerPassword(MyViewModel m){
        model=m;
    }
    @Override
    protected Void doInBackground(Integer... users) {
        model.postPassword(MainActivity.appDatabase.playerDao().findPasswordById(users[0]));
        System.out.println("ZAVRSENO");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        System.out.println("ON POST EXECUTE");
        model.postPasswordUpdated(true);
    }
}
