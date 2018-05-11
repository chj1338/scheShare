package com.example.p048293.biblereader;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class SettingMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_menu);

        // 화면꺼짐 방지
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // 액션바 타이틀 관련
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_title_bar);

        Button btnReset = (Button) findViewById(R.id.button3);

        // 초기화 버튼 리스너
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(SettingMenuActivity.this)
                        .setTitle("확인")
                        .setMessage( "가장 최근의 성경읽기 기록으로 복구하시겠습니까?" + "\n" + "(현재 저장된 읽기 기록은 삭제됩니다.)")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                restoreFile();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        })
                        .create()
                        .show();
            }
        });
    }


    // 읽기기록 삭제
    public void btnGoFileCheck(View v) {
        Intent intent006 = new Intent(this, FileCheckActivity.class);
        startActivity(intent006);
    }

    // 보기 설정
    public void btnGoDisplay(View v) {
        Intent intent001 = new Intent(this, DispalySettingActivity.class);
        startActivity(intent001);
    }

    // 대광교회
    public void btnGoDaekwang(View v) {
        Intent intent001 = new Intent(this, DaekwangNoticeActivity.class);
        startActivity(intent001);
    }

    // 도움말
    public void btnGoHelp(View v) {
        Intent intent001 = new Intent(this, HelpActivity.class);
        startActivity(intent001);
    }

    // 파일복원
    public void restoreFile() {
        String externalPath = getExternalPath() + "/Download";
        File downloadDir = new File(externalPath + "/BibleReader");  // Download/BibleReader 폴더

        File[] fileArray = downloadDir.listFiles();

        for(int i=0; i<fileArray.length; i++) {
            File fileNameOrg = new File(getFilesDir() + "/" + fileArray[i].getName().toString()); // 원본파일
            File fileNameDown = new File(downloadDir + "/" + fileArray[i].getName().toString());  // Download/BibleReader 내 파일

//            Log.d("=======", fileNameOrg.getPath() + " : " + fileNameDown.getName());

            // 초기 복원
            try {
                fileNameOrg.delete();
                filecopy(fileNameDown, fileNameOrg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 외부메모리 경로
    public String getExternalPath(){
        String sdPath = "";
        String ext = Environment.getExternalStorageState();
        if(ext.equals(Environment.MEDIA_MOUNTED)){
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                    "/";
            //sdPath = "/mnt/sdcard/";
        }
        else
            sdPath = getFilesDir() + "";
        //Toast.makeText(this,sdPath,Toast.LENGTH_SHORT).show();
        return sdPath;
    }

    // file 복사
    private static void filecopy(File from, File to) throws Exception{
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel in = null;
        FileChannel out = null;

        try {
            fis = new FileInputStream(from);
            fos = new FileOutputStream(to);
            in = fis.getChannel();
            out = fos.getChannel();
            in.transferTo(0, in.size(), out);
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            if(out != null) out.close();
            if(in != null) in.close();
            if(fis != null) fis.close();
            if(fos != null) fos.close();
        }
    }

    // 설정메뉴로 이동
    public void btnGoSettingMenu(View v) {
        Intent intent006 = new Intent(this, SettingMenuActivity.class);
        startActivity(intent006);
    }
}
