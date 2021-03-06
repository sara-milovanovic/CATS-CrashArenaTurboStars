package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.R;

public class AsyncTaskGetPlayerId extends AsyncTask<String, Void, Void> {

    MyViewModel model;
    View view;
    boolean navigate;

    public AsyncTaskGetPlayerId(MyViewModel m, View view, boolean b){
        model=m;
        this.view=view;
        this.navigate=b;
    }
    @Override
    protected Void doInBackground(String... users) {
        model.postCurrentPlayerId(MainActivity.appDatabase.playerDao().findByNameOnly(users[0]));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(navigate) Navigation.findNavController(view).navigate(R.id.action_registrationFragment_to_garageFragment);

    }
}
