package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private HandlerThread stopwatchfunction;
    private Handler backgroudHandler ;
    private Handler UIHandler = new Handler(Looper.getMainLooper());
    private int sec ;
    private boolean running ;

    private TextView timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        timer = findViewById(R.id.txttimer);
        stopwatchfunction = new HandlerThread("stop watch ");
        stopwatchfunction.start();
        backgroudHandler = new Handler(stopwatchfunction.getLooper());
        CheackInstanceState(savedInstanceState);
        //runstropwatch();

    }

/*
    private void runstropwatch() {
        final Handler handler = new Handler();
        handler.post(() ->{
            if(running){
                sec++;
            }

            int hours = sec/3600;
            int min = (sec% 3600)/60;
            int sece = sec %60;
            timer.setText( hours + ":"+ min +":" + sece );

            handler.postDelayed(this::runstropwatch, 1000);
        });

    }
*/

    // diffrent way to use the Thread to make app stable
    private final  Runnable RunStopeWatchWithThread = new Runnable() {
        @Override
        public void run() {
            if(running){
                sec++;
            }

            // This is a thread for UI
            UIHandler.post(()->{

                int hours = sec / 3600;
                int min = (sec % 3600) / 60;
                int sece = sec % 60;

                timer.setText(hours + ":" + min + ":" + sece);
            });
            // This is a thread for background function for stop watch
            backgroudHandler.postDelayed(this , 1000);
        }
    };



    protected void CheackInstanceState(Bundle savedInstanceState){
        if(savedInstanceState != null){
            running = savedInstanceState.getBoolean("running");
            sec = savedInstanceState.getInt("sec");
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("running", running);
        outState.putInt("sec" , sec);
    }

    public void ONClickStartButton(View view) {
        running = true;
        backgroudHandler.post(RunStopeWatchWithThread) ;
       // runstropwatch();
    }


    public void ONClickStopButton(View view) {
          running = false;
         // runstropwatch();
    }

    public void ONClickRestartButton(View view) {
        running = false;
        sec =0 ;
      //  runstropwatch();
    }
}