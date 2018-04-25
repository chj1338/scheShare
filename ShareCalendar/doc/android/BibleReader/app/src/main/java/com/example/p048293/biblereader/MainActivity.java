package com.example.p048293.biblereader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final long FINSH_INTERVAL_TIME = 2000; //2초
    private long backPressedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        configBackup(); // 시작하면 주요파일 백업
    }

    void checkPermission() {
        int permissioninfo = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissioninfo == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(getApplicationContext(), "SDCard 쓰기 권한 있음", Toast.LENGTH_SHORT).show();
        } else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                Toast.makeText(getApplicationContext(), "권한의 필요성 설명", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    public void onRequestPerMissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "SD카드 쓰기권한 승인", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "SD카드 쓰기권한 거부", Toast.LENGTH_SHORT).show();
            }
        }
    }

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
//        Toast.makeText(this,sdPath,Toast.LENGTH_SHORT).show();
        return sdPath;
    }

    // config, Hist 파일 백업
    public void configBackup() {
        try {
            //String downloadName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            String externalPath = getExternalPath() + "/Download";
            File downloadDir = new File(externalPath + "/BibleReader");  // Download/BibleReader 폴더

            //new java.io.File(downloadDir.getPath()).setReadable(true, false);

            // Download/BibleReader 폴더가 없으면 생성
            if(!downloadDir.exists()){
                downloadDir.mkdir();
                //Log.d("===== <backup>", downloadDir + " 생성 !!!");
            }

            ArrayList<String> fileArray = new ArrayList<String>();
            fileArray.add("configDisp.txt");
            fileArray.add("configOld.txt");
            fileArray.add("configNew.txt");
            fileArray.add("readHistOLDList.txt");
            fileArray.add("readHistNEWList.txt");

            String line = "";
            // 구약
            if(new File(getFilesDir() + "/readHistOLDList.txt").exists()) { // 신버젼 파일 존재여부
                BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/readHistOLDList.txt"));
                while ((line = br.readLine()) != null) {
                    fileArray.add(line);
                }
                br.close();
            } else if(new File(getFilesDir() + "readHistOLDList.txt").exists()) { // 구버젼 파일 존재여부
                BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "readHistOLDList.txt"));
                while ((line = br.readLine()) != null) {
                    fileArray.add(line);
                }
                br.close();
            }

            // 신약
            if(new File(getFilesDir() + "/readHistNEWList.txt").exists()) { // 신버젼 파일 존재여부
                BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/readHistNEWList.txt"));
                while ((line = br.readLine()) != null) {
                    fileArray.add(line);
                }
                br.close();
            } else if(new File(getFilesDir() + "readHistNEWList.txt").exists()) { // 구버젼 파일 존재여부
                BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "readHistNEWList.txt"));
                while ((line = br.readLine()) != null) {
                    fileArray.add(line);
                }
                br.close();
            }

            // 신버젼용
            //Log.d("===== <backup>", " 신버젼 백업");
            for(int i=0; i<fileArray.size(); i++) {
                File fileNameOrg = new File(getFilesDir() + "/" + fileArray.get(i).toString()); // 원본파일
                File fileNameDown = new File(downloadDir + "/" + fileArray.get(i).toString());  // Download/BibleReader 내 파일

                // 파일 백업
                if (fileNameOrg.exists()) {
                    if (fileNameDown.exists()) {
                        fileNameDown.delete();                    // Download/BibleReader 폴더내 파일이 있으면 삭제
                    }

                    try {
                        filecopy(fileNameOrg, fileNameDown);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            // 구버젼용
            //Log.d("===== <backup>", " 구버젼 백업");
            for(int i=0; i<fileArray.size(); i++) {
                File fileNameOrg = new File(getFilesDir() + fileArray.get(i).toString());       // 원본파일
                File fileNameDown = new File(downloadDir + "/" + fileArray.get(i).toString());  // Download/BibleReader 내 파일

                // 구버젼이 존재하고 백업이 없으면.
                if(fileNameOrg.exists() && !fileNameDown.exists()) {
                    try {
                        filecopy(fileNameOrg, fileNameDown);
                        fileNameOrg.delete();                    // 구버젼은 삭제
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


            // 복원용
            //Log.d("===== <backup>", " 복원");
            for(int i=0; i<fileArray.size(); i++) {
                File fileNameOrg = new File(getFilesDir() + "/" + fileArray.get(i).toString()); // 원본파일
                File fileNameDown = new File(downloadDir + "/" + fileArray.get(i).toString());  // Download/BibleReader 내 파일

                // 초기 복원
                if(!fileNameOrg.exists() && fileNameDown.exists()) {
                //if(fileNameDown.exists()) {
                    try {
                        filecopy(fileNameDown, fileNameOrg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            //Toast.makeText(this, "주요 파일을 백업했습니다.", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
            e.printStackTrace();
        }
    }

    // file 복사
    private static void filecopy(File from, File to) throws Exception{
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel in = null;
        FileChannel out = null;

        //Log.d("===== <filecopy>", from + " ==> " + to);

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


    // 뒤로가기 두번 누르면 종료
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


    public void btnGoNewBook(View v) {
        Intent intent001 = new Intent(this, NewTestamentActivity.class);
        startActivity(intent001);
    }

    public void btnGoOldBook(View v) {
        Intent intent002 = new Intent(this, OldTestamentActivity.class);
        startActivity(intent002);
    }

    public void btnGoHanEngOldBook(View v) {
        Intent intent001 = new Intent(this, EngHanOldActivity.class);
        startActivity(intent001);
    }

    public void btnGoHanEngNewBook(View v) {
        Intent intent002 = new Intent(this, EngHanNewActivity.class);
        startActivity(intent002);
    }

    public void btnGoSearch(View v) {
        Intent intent003 = new Intent(this, WordSearch2Activity.class);
        startActivity(intent003);
    }

    public void btnGoSearch2(View v) {
        Intent intent0032 = new Intent(this, WordSearch2Activity.class);
        startActivity(intent0032);
    }

    public void btnGoSong(View v) {
        Intent intent004 = new Intent(this, SongActivity.class);
        startActivity(intent004);
    }

    public void btnGoReadHist(View v) {
        Intent intent005 = new Intent(this, ReadHistActivity.class);
        startActivity(intent005);
    }

    public void btnGoSettingMenu(View v) {
        Intent intent006 = new Intent(this, SettingMenuActivity.class);
        startActivity(intent006);
    }

    public void btnGoKinderRead(View v) {
        Intent intent007 = new Intent(this, KinderReadActivity.class);
        startActivity(intent007);
    }

    public void btnGoChildRead(View v) {
        Intent intent008 = new Intent(this, ChildReadActivity.class);
        startActivity(intent008);
    }

}
