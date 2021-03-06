package com.example.catsproject.db.async_tasks;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.Navigation;

import com.example.catsproject.MainActivity;
import com.example.catsproject.MyViewModel;
import com.example.catsproject.R;
import com.example.catsproject.db.Components;
import com.example.catsproject.db.Player;
import com.example.catsproject.db.Score;

import java.util.List;

public class AsyncTaskGetStatistics extends AsyncTask<Integer, Void, Void> {

    MyViewModel model;
    View view;
    Context context;
    Score score;
    String player;
    int alert;

    public AsyncTaskGetStatistics(MyViewModel m, View v, Context context){
        model=m; view=v;
        this.context=context;
    }
    @Override
    protected Void doInBackground(Integer... users) {

        score=MainActivity.appDatabase.scoreDao().getScore(users[0]);
        player=MainActivity.appDatabase.playerDao().findById(model.getCurrentPlayerId().getValue());
        alert=users[1];
        model.postWins(score.win);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(alert==1){
            new AlertDialog.Builder(context)
                    .setTitle("Statistics for user "+player)
                    .setMessage(score.win+" win "+score.lose+" lose")

                    .setNegativeButton("Ok", null)
                    .setIcon(R.drawable.statistics)
                    .show();
        }
        else{
            System.out.println("---+++++----"+model.getPlayedAfterLogin().getValue());
            System.out.println("---+++++----"+model.getWin().getValue());
            if(model.getPlayedAfterLogin().getValue() && model.getWin().getValue()==1 && model.getWins().getValue()%3==0 && model.getWins().getValue()!=0)
                if(model.getGenerateBox().getValue()==0)
                    {
                        new AsyncTaskUpdateGenerateBox(model).execute(1);
                        model.setPlayed(false);
                    }
            if(model.getGenerateBox().getValue()==1) {
                model.activateBox();
                new AsyncTaskUpdateGenerateBox(model).execute(0);
            }
        }
    }

}
