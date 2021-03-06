package com.example.catsproject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.widget.Toast;

public class PlayerService extends Service {
    private Looper serviceLooper;
    boolean playing=false;
    MediaPlayer mediaPlayer;
    Context c;
    private ServiceHandler serviceHandler;


    public PlayerService(){
    }

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.

            new Thread(new Runnable() {
                @Override
                public void run() {

                    mediaPlayer.start();
                }
            }).start();
            //Toast.makeText(c.getApplicationContext(), "iteration " + i, Toast.LENGTH_SHORT).show(); ???kako

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            SystemClock.sleep(5000);
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service. Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block. We also make it
        // background priority so CPU-intensive work doesn't disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        final Context c=this;

        String tip=intent.getType();
        if(tip.equals("play")){
            if(!playing) mediaPlayer=MediaPlayer.create(c,R.raw.music);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
            playing=true;

            Toast.makeText(this, "music on", Toast.LENGTH_SHORT).show();
        }
        else{
            if(tip.equals("pause")){
                if(mediaPlayer!=null) mediaPlayer.pause();
                playing=false;
                Toast.makeText(this, "music off", Toast.LENGTH_SHORT).show();
            }
            else{
                if(tip.equals("skip f")){
                    if(mediaPlayer!=null && mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        int current=mediaPlayer.getCurrentPosition();//u milisek vraca
                        int max=mediaPlayer.getDuration();
                        int new_position=current+5000;
                        if(new_position<max) mediaPlayer.seekTo(new_position);
                        else mediaPlayer.seekTo(max);
                        mediaPlayer.start();
                    }
                }
                else{
                    if(mediaPlayer!=null && mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        int current=mediaPlayer.getCurrentPosition();//u milisek vraca

                        int new_position=current-5000;
                        if(new_position<0) mediaPlayer.seekTo(0);
                        else mediaPlayer.seekTo(new_position);
                        mediaPlayer.start();
                    }
                }
            }
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // We don't provide binding, so return null
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "music off", Toast.LENGTH_SHORT).show();
        if(mediaPlayer!=null) mediaPlayer.stop();
        playing=false;
        if(mediaPlayer!=null) mediaPlayer.release();
    }
}

