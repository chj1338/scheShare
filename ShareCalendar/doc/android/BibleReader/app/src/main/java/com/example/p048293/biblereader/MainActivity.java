package com.example.p048293.biblereader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final long FINSH_INTERVAL_TIME = 2000; //2초
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnGoNewBook(View v) {
        Intent intent001 = new Intent(this, NewTestamentActivity.class);
        startActivity(intent001);
    }

    public void btnGoOldBook(View v) {
        Intent intent002 = new Intent(this, OldTestamentActivity.class);
        startActivity(intent002);
    }

    public void btnGoSearch(View v) {
        Intent intent003 = new Intent(this, WordSearchActivity.class);
        startActivity(intent003);
    }

    public void btnGoSong(View v) {
        Intent intent004 = new Intent(this, SongActivity.class);
        startActivity(intent004);
    }

    public void btnGoReadHist(View v) {
        Intent intent005 = new Intent(this, ReadHistActivity.class);
        startActivity(intent005);
    }

    public void btnGoEngHanOld(View v) {
        Intent intent006 = new Intent(this, EngHanOldActivity.class);
        startActivity(intent006);
    }


    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "\'뒤로\' 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
