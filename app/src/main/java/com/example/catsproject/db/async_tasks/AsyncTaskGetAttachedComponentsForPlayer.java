package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;

import com.example.catsproject.GarageFragment;
import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.db.Components;

import java.util.ArrayList;

public class AsyncTaskGetAttachedComponentsForPlayer extends AsyncTask<Integer, Void, Void> {

    MyViewModel model;

    public AsyncTaskGetAttachedComponentsForPlayer(MyViewModel m){
        model=m;
    }

    @Override
    protected Void doInBackground(Integer... player) {
        int id=model.getCurrentPlayerId().getValue();
        ArrayList<Components> c= (ArrayList<Components>) MainActivity.appDatabase.userAddedComponentsDao().getComponentsForPlayer(id);
        model.postCurrentPlayerAttachedComponents(c);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        GarageFragment.setVehicleImage();
    }
}
