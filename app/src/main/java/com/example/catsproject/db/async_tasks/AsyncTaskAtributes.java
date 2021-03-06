package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.R;

public class AsyncTaskAtributes extends AsyncTask<Integer, Void, Void> {

    MyViewModel model;

    public AsyncTaskAtributes(MyViewModel m){
        model=m;
    }
    @Override
    protected Void doInBackground(Integer... users) {
        model.postHeart(MainActivity.appDatabase.playerDao().findHeart(users[0]));
        model.postSword(MainActivity.appDatabase.playerDao().findSword(users[0]));
        model.postThunder(MainActivity.appDatabase.playerDao().findThunder(users[0]));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
    }
}
