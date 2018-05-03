package com.example.p048293.biblereader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileCheckActivity extends AppCompatActivity {
    String bookSe = "OLD";
    int nowPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_check);

        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final Button btnReset = (Button) findViewById(R.id.btnReset);
        final TextView textView001 = (TextView) findViewById(R.id.textView001);
        final TextView textView002 = (TextView) findViewById(R.id.textView002);
//        final TextView textView003 = (TextView)findViewById(R.id.textView003);

        // spinner1 child 셋팅
        ArrayList<String> bookSeItems = new ArrayList<>();
        bookSeItems.add("구약");
        bookSeItems.add("신약");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bookSeItems);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.spinner_item_left);
        spinner1.setAdapter((SpinnerAdapter) adapter);

        readHistList(textView001, spinner2);
        readHist(textView002, nowPosition);

        // spinner1 리스너
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner1.getSelectedItemPosition() == 0) {
                    bookSe = "OLD";
                } else {
                    bookSe = "NEW";
                }

                readHistList(textView001, spinner2);
                readHist(textView002, nowPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // spinner2 리스너
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 장 combo item 셋팅
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                readHist(textView002, spinner2.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 초기화 버튼 리스너
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(FileCheckActivity.this)
                        .setTitle("확인")
                        .setMessage( (spinner2.getSelectedItemPosition() + 1) + "차 성경읽기 기록을 삭제하시겠습니까?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteFile(spinner2, textView001, textView002); // hist 파일 모두 삭제
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

    //readHistList 파일 읽어서 반영
    public void readHistList(TextView textView, Spinner spinner) {
        try {
            String line = "";
            String readText = "";

            ArrayList<String> items = new ArrayList<>();
            int tempItem = 0;

            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/readHist" + bookSe + "List.txt"));

            while((line=br.readLine())!=null) {
                readText += line + "\n";

                items.add((tempItem + 1) + "");
                tempItem++;
            }
            br.close();

            textView.setText(readText);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
            //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(R.layout.spinner_item);
            spinner.setAdapter((SpinnerAdapter) adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //readHist 파일 읽어서 반영
    public void readHist(TextView textView, int nowPosition) {
        try {
            String line = "";
            String readText = "";

            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/readHist" + bookSe + "_" + (nowPosition+1) + ".txt"));
            while((line=br.readLine())!=null) {
                readText += line + "\n";
            }
            br.close();

            textView.setText(readText);
        } catch (Exception e) {
            e.printStackTrace();

            textView.setText("파일없음");
        }
    }

    public void deleteFile(Spinner spinner2, TextView textView001, TextView textView002) {
        try {
            int spinner2Pos = spinner2.getSelectedItemPosition() + 1;

            // 기존 list 파일 읽기
            String line = "";
            String newList = "";
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/readHist" + bookSe + "List.txt"));
            while((line=br.readLine())!=null) {
                if( !line.equals("readHist" + bookSe + "_" + spinner2Pos + ".txt")) {
                    newList += line + "\n";
                }
            }
            br.close();

            String externalPath = getExternalPath() + "/Download";
//            File downloadDir = new File(externalPath + "/BibleReader");  // Download/BibleReader 폴더

            // 리스트파일에서 선택한 차수 빼고 나머지 다시 기록
            File fileList = new File(getFilesDir() + "/readHist" + bookSe + "List.txt");
  //          File fileListDown = new File(downloadDir + "/readHist" + bookSe + "List.txt");  // /Download/ 몰더 백업파일

            if (fileList.exists()) {
                fileList.delete();
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileList, true));
                bw.write(newList);
                bw.close();

/*
                fileListDown.delete();
                BufferedWriter bwDown = new BufferedWriter(new FileWriter(fileListDown, true));
                bwDown.write(newList);
                bwDown.close();
*/

            }

            // 선택한 차수 읽기 기록 삭제
            File file = new File(getFilesDir() + "/readHist" + bookSe + "_" + spinner2Pos + ".txt");
            if (file.exists()) {
                file.delete();
            }
/*
            File fileDown = new File(downloadDir + "/readHist" + bookSe + "_" + spinner2Pos + ".txt");
            if (fileDown.exists()) {
                fileDown.delete();
            }
*/

            readHistList(textView001, spinner2);
            readHist(textView002, nowPosition);
        } catch (Exception e) {
            e.printStackTrace();
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
}
