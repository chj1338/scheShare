package com.example.p048293.biblereader;

import android.app.AlertDialog;
import android.content.Context;
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

    public void btnGoReadHist(View v) {
        Intent intent005 = new Intent(this, ReadHistActivity.class);
        startActivity(intent005);
    }

    // back 버튼을 클릭시 종료여부 확인
    public void onBackPressed() {
        // TODO Auto-generated method stub
        // super.onBackPressed(); //지워야 실행됨

        AlertDialog.Builder d = new AlertDialog.Builder(this);
        d.setMessage("종료하시겠습니까?");
        d.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();   // process전체 종료
            }
        });

        d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        d.show();
    }
}
