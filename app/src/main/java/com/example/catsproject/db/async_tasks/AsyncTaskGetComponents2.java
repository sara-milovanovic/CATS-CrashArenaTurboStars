package com.example.catsproject.db.async_tasks;

import android.os.AsyncTask;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.R;
import com.example.catsproject.db.Components;

import java.util.List;

public class AsyncTaskGetComponents2 extends AsyncTask<Integer, Void, Void> {

    MyViewModel model;

    public AsyncTaskGetComponents2(MyViewModel m){
        model=m;
    }
    @Override
    protected Void doInBackground(Integer... users) {

        List<Components> l= MainActivity.appDatabase.userComponentsDao().getComponentsForPlayer(users[0]);
        model.postCurrentPlayerComponents(l);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //Navigation.findNavController(view).navigate(R.id.action_garageFragment_to_canvasFragment);
    }
}
