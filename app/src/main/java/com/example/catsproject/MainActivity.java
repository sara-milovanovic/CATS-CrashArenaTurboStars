package com.example.catsproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.catsproject.db.AppDatabase;

public class MainActivity extends AppCompatActivity {
    public static AppDatabase appDatabase;
    MyViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase=AppDatabase.getInstance(this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        model = new ViewModelProvider(this).get(MyViewModel.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i=0;
                    while(true){
                        Thread.sleep(1000);
                        model.decTime();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this,"back",Toast.LENGTH_SHORT).show();
    }
}
