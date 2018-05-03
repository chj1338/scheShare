package com.alarm.alarmtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int[] mlayout = new int[]{
            R.id.layout01, R.id.layout02, R.id.layout03, R.id.layout04, R.id.layout05,
            R.id.layout06, R.id.layout07, R.id.layout08, R.id.layout09, R.id.layout10,
            R.id.layout11, R.id.layout12, R.id.layout13, R.id.layout14, R.id.layout15,
            R.id.layout16};

    private runBuffer runBf;
    private TextView textView;
    private int gg= -1;
    private int alertCnt = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runBf = new runBuffer();
        runBf.select = new boolean[alertCnt];

        for (int i = 0; i < alertCnt; ++i) {
            runBf.select[i] = false; // unselect
            findViewById(mlayout[i]).setBackgroundResource(R.drawable.alarm_off);
            findViewById(mlayout[i]).setClickable(true);
            findViewById(mlayout[i]).setOnTouchListener(mTouchListener);
        }
    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction()==MotionEvent.ACTION_DOWN) {
                v.setBackgroundResource(R.drawable.alarm_select);
                for (int i=0; i<16; ++i) {
                    if (v.getId() == mlayout[i]) {
                        gg=i;
                        break;
                    }
                }
            }
            else {
                if (event.getAction()==MotionEvent.ACTION_MOVE) {
                    if (v.isPressed()==false) {
                        for (int i=0; i<16; ++i) {
                            if (v.getId()==mlayout[i]) {
                                if (runBf.select[i] == true) { // selected?
                                    v.setBackgroundResource(R.drawable.alarm_run);
                                } else {
                                    v.setBackgroundResource(R.drawable.alarm_off);
                                }
                                break;
                            }
                        }
                        gg= -1;
                    }
                } else {
                    if (event.getAction()==MotionEvent.ACTION_UP) {
                        if (gg != -1) {
                            for (int i = 0; i < 16; ++i) {
                                if (v.getId() == mlayout[i]) {
                                    if (runBf.select[i] == true) { // selected?
                                        runBf.select[i] = false;
                                        v.setBackgroundResource(R.drawable.alarm_run);
                                    } else {
                                        runBf.select[i] = true;
                                        v.setBackgroundResource(R.drawable.alarm_run);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
    };

    private class runBuffer {
        public boolean[] select;
    };
}


