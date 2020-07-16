package com.phc.cssd;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends Activity {
    protected int seconds = 2;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }

        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 1500);

    }

    private Runnable runnable = new Runnable() {
        public void run() {
            long currentMilliseconds = System.currentTimeMillis();
            seconds--;

            if (seconds > 0) {
                handler.postAtTime(this, currentMilliseconds);
                handler.postDelayed(runnable, 1500);
            } else {
                init();
                handler.removeCallbacks(runnable);
                finish();
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            handler.removeCallbacks(runnable);
            init();
            finish();
        }
        return true;
    }

    public void init(){
        //Intent it = new Intent(MainActivity.this, USBActivity.class);
        Intent it = new Intent(MainActivity.this, Login.class);
        startActivity(it);

        // Service
        /*
        Intent intent = new Intent(this, gps_service.class);
        startService(intent);
        */
    }
}