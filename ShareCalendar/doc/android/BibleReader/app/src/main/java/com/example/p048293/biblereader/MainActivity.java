package com.example.p048293.biblereader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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
/*
    // back 버튼을 클릭시 종료 할건지에 대해 묻는다
    public boolean onBackPressed(int keyCode, KeyEvent event){
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK:
                String alertTitle = getResources().getString(R.string.app_name);
                String buttonMessage = getResources().getString(R.string.alert_msg_exit);
                String buttonYes = getResources().getString(R.string.button_yes);
                String buttonNo = getResources().getString(R.string.button_no);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(alertTitle)
                        .setMessage(buttonMessage)
                        .setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                moveTaskToBack(true);
                                finish();
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }
                        })
                        .setNegativeButton(buttonNo, null)
                        .show();
        }
        return true;
    }
*/

}
