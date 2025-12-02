package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int sec ;
    private boolean running ;

    private TextView timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        timer = findViewById(R.id.txttimer);


        runstropwatch();

    }

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



    public void ONClickStartButton(View view) {
        running = true;
        runstropwatch();
    }


    public void ONClickStopButton(View view) {
          running = false;
          runstropwatch();
    }

    public void ONClickRestartButton(View view) {
        running = false;
        sec =0 ;
        runstropwatch();

    }
}