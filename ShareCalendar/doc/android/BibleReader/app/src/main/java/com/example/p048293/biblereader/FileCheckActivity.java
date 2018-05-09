package com.example.p048293.biblereader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

        // 읽기기록 삭제 관련
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final Button btnReset = (Button) findViewById(R.id.btnReset);
        final TextView textView001 = (TextView) findViewById(R.id.textView001);
        final TextView textView002 = (TextView) findViewById(R.id.textView002);

        // 읽기기록 추가 관련
        final Spinner spinner11 = (Spinner) findViewById(R.id.spinner11);   // 신구약 구분
        final Spinner spinner12 = (Spinner) findViewById(R.id.spinner12);   // 책 목록
        final Spinner spinner13 = (Spinner) findViewById(R.id.spinner13);   // 부터 장 목록
        final Spinner spinner14 = (Spinner) findViewById(R.id.spinner14);   // 까지 장 목록
        final Button btnReadAdd = (Button) findViewById(R.id.btnReadAdd);   // 읽기 기록 추가

        // spinner1 child 셋팅
        ArrayList<String> bookSeItems = new ArrayList<>();
        bookSeItems.add("구약");
        bookSeItems.add("신약");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bookSeItems);
        adapter.setDropDownViewResource(R.layout.spinner_item_left);
        spinner1.setAdapter((SpinnerAdapter) adapter);  // 읽기 기록 삭제
        spinner11.setAdapter((SpinnerAdapter) adapter); // 읽기 기록 추가

        readHistList(textView001, spinner2);
        readHist(textView002, spinner2);

        // 읽기 추가 스피너 초기화
        initSpinnerBook(spinner11, spinner12, spinner13, spinner14);
        initSpinnerPage(spinner11, spinner12, spinner13, spinner14);

        // spinner1 리스너
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner1.getSelectedItemPosition() == 0) {
                    bookSe = "OLD";
                    spinner11.setSelection(0);
                } else {
                    bookSe = "NEW";
                    spinner11.setSelection(1);
                }

                readHistList(textView001, spinner2);
                readHist(textView002, spinner2);
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
                readHist(textView002, spinner2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 삭제 버튼 리스너
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(FileCheckActivity.this)
                        .setTitle("확인")
                        .setMessage( spinner2.getSelectedItem() + "차 성경읽기 기록을 삭제하시겠습니까?")
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


        // 읽기 기록 추가 : 신약/구약 구분
        spinner11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    bookSe = "OLD";
                    spinner1.setSelection(0);
                } else {
                    bookSe = "NEW";
                    spinner1.setSelection(1);
                }

                initSpinnerBook(spinner11, spinner12, spinner13, spinner14);
                initSpinnerPage(spinner11, spinner12, spinner13, spinner14);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 읽기 기록 추가 : 책 선택
        spinner12.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                initSpinnerPage(spinner11, spinner12, spinner13, spinner14);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 읽기 기록 추가 : 장 ( ~ 부터)
        spinner13.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 읽기 기록 추가 : 장 ( ~ 까지)
        spinner14.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnReadAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(FileCheckActivity.this)
                        .setTitle("선택")
                        .setMessage( "선택하신 부분을 읽기 기록에 추가하시겠습니까?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addReadHist();
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
    public void readHistList(TextView textView, Spinner spinner2) {
        try {
            String line = "";
            String readText = "";
            ArrayList<String> items = new ArrayList<>();

            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/readHist" + bookSe + "List.txt"));
            while((line=br.readLine())!=null) {
                readText += line + "\n";

                items.add(line.replaceAll("readHist", "").replaceAll(bookSe, "").replaceAll("_", "").replaceAll(".txt", ""));
            }
            br.close();

            textView.setText(readText);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
            //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(R.layout.spinner_item);
            spinner2.setAdapter((SpinnerAdapter) adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //readHist 파일 읽어서 반영
    public void readHist(TextView textView, Spinner spinner2) {
        try {
            String line = "";
            String readText = "";
            String nowItem = spinner2.getSelectedItem().toString();

            if( !nowItem.equals("") && nowItem != "" && !nowItem.equals(null) && nowItem != null ) {
                BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/readHist" + bookSe + "_" + spinner2.getSelectedItem() + ".txt"));
                while ((line = br.readLine()) != null) {
                    readText += line + "\n";
                }
                br.close();
            }

            textView.setText(readText);
        } catch (Exception e) {
            e.printStackTrace();

            textView.setText("파일없음");
        }
    }

    // 파일 삭제
    public void deleteFile(Spinner spinner2, TextView textView001, TextView textView002) {
        try {
            String spinner2Item = spinner2.getSelectedItem().toString();

            // 기존 list 파일 읽기
            String line = "";
            String newList = "";
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/readHist" + bookSe + "List.txt"));
            while((line=br.readLine())!=null) {
                if( !line.equals("readHist" + bookSe + "_" + spinner2Item + ".txt")) {
                    newList += line + "\n";
                }
            }
            br.close();

            // 리스트파일에서 선택한 차수 빼고 나머지 다시 기록
            File fileList = new File(getFilesDir() + "/readHist" + bookSe + "List.txt");
            if (fileList.exists()) {
                fileList.delete();

                BufferedWriter bw = new BufferedWriter(new FileWriter(fileList, true));
                bw.write(newList);
                bw.close();
/*
            String externalPath = getExternalPath() + "/Download";
            File downloadDir = new File(externalPath + "/BibleReader");  // Download/BibleReader 폴더
            File fileListDown = new File(downloadDir + "/readHist" + bookSe + "List.txt");  // /Download/ 폴더 백업파일

            fileListDown.delete();
            BufferedWriter bwDown = new BufferedWriter(new FileWriter(fileListDown, true));
            bwDown.write(newList);
            bwDown.close();
*/
            }

            // 선택한 차수 읽기 기록파일 삭제
            File file = new File(getFilesDir() + "/readHist" + bookSe + "_" + spinner2Item + ".txt");
            if (file.exists()) {
                file.delete();
            }
/*
            File fileDown = new File(downloadDir + "/readHist" + bookSe + "_" + spinner2Item + ".txt");
            if (fileDown.exists()) {
                fileDown.delete();
            }
*/
            readHistList(textView001, spinner2);
            readHist(textView002, spinner2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 외장메모리 경로 가져오기
    public String getExternalPath(){
        String sdPath = "";
        String ext = Environment.getExternalStorageState();
        if(ext.equals(Environment.MEDIA_MOUNTED)){
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                    "/";
        }
        else
            sdPath = getFilesDir() + "";
//        Toast.makeText(this,sdPath,Toast.LENGTH_SHORT).show();
        return sdPath;
    }


    // 읽기기록 추가 책 spinner 초기화
    public void initSpinnerBook(Spinner spinner11, Spinner spinner12, Spinner spinner13, Spinner spinner14) {
        // spinner12 child 세팅
        ArrayAdapter adapterBook = null;
        if (bookSe.equals("OLD")) {
            adapterBook = ArrayAdapter.createFromResource(this, R.array.bible_old_name_kor, android.R.layout.simple_spinner_item);
        } else {
            adapterBook = ArrayAdapter.createFromResource(this, R.array.bible_new_name_kor, android.R.layout.simple_spinner_item);
        }

        adapterBook.setDropDownViewResource(R.layout.spinner_item);
        spinner12.setAdapter(adapterBook);

        spinner12.setSelection(0);
    }

    // 읽기기록 추가 장 spinner 초기화
    public void initSpinnerPage(Spinner spinner11, Spinner spinner12, Spinner spinner13, Spinner spinner14) {
        // spinner13, 14 last page item
        ArrayAdapter adapter_page;
        if(bookSe.equals("OLD")) {
            adapter_page = ArrayAdapter.createFromResource (this, R.array.bible_old_page, android.R.layout.simple_spinner_item);
        } else {
            adapter_page = ArrayAdapter.createFromResource (this, R.array.bible_new_page, android.R.layout.simple_spinner_item);
        }

        int sp1Position = spinner12.getSelectedItemPosition();
        int lastPage = Integer.parseInt((String)adapter_page.getItem(sp1Position));

        ArrayList<String> item2 = new ArrayList<>();
        for(int i=1; i<=lastPage; i++) {
            item2.add(i+"");
        }

        ArrayAdapter<String> adapter13 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item2);
        adapter13.setDropDownViewResource(R.layout.spinner_item);
        spinner13.setAdapter(adapter13);
        spinner14.setAdapter(adapter13);

        spinner13.setSelection(0);
        spinner14.setSelection(spinner14.getCount() - 1);
    }


    // 읽기 기록 추가
    public void addReadHist() {

        Spinner bookSpinner = (Spinner)findViewById(R.id.spinner12);
        Spinner startPageSpinner = (Spinner)findViewById(R.id.spinner13);
        Spinner endPageSpinner = (Spinner)findViewById(R.id.spinner14);

        ArrayAdapter adapter;
        if(bookSe.equals("OLD")) {
            adapter = ArrayAdapter.createFromResource(FileCheckActivity.this, R.array.bible_old_name_kor_ac, android.R.layout.simple_spinner_item);
        } else {
            adapter = ArrayAdapter.createFromResource(FileCheckActivity.this, R.array.bible_new_name_kor_ac, android.R.layout.simple_spinner_item);
        }
        String nowBook = (String)adapter.getItem(bookSpinner.getSelectedItemPosition());
        int startIndex = Integer.parseInt(startPageSpinner.getSelectedItem().toString());
        int endIndex = Integer.parseInt(endPageSpinner.getSelectedItem().toString());

        try {
            for(int i=startIndex;i<=endIndex; i++) {
                // 리스트 파일이 없을 경우 생성
                File fileList = new File(getFilesDir() + "/readHist" + bookSe + "List.txt");
                if( !fileList.exists() ) {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fileList, true));
                    bw.write("");
                    bw.close();
                }

                // 몇 차수 까지 존재하는지 확인 (한번 읽으면 1차, 두번 읽으면 2차)
                int chaSu = 1;  // 몇차에 저장해야 하는지 확인용
                int chaSuCnt = 0;   // 전체 몇차까지 있는지 확인용

                String line = null;
                BufferedReader br = new BufferedReader(new FileReader(fileList));
                while((line=br.readLine())!=null) {
                    int temp = Integer.parseInt(line.replaceAll("readHist", "").replaceAll(bookSe, "").replaceAll("_", "").replaceAll(".txt", ""));

                    if(chaSuCnt < temp) {
                        chaSuCnt = temp;
                    }
                }
                br.close();

                // 현재의 strHist 내용이 몇차까지 기록되었는지 확인
                for(int j=1; j<=chaSuCnt; j++) {
                    try {
                        File histFile = new File(getFilesDir() + "/readHist" + bookSe + "_" + j + ".txt");
                        if (histFile.exists()) {
                            line = "";

                            BufferedReader br2 = new BufferedReader(new FileReader(histFile));
                            while ((line = br2.readLine()) != null) {
                                if (line.indexOf(nowBook + ":" + i) != -1) {
                                    chaSu++;
                                    break;
                                }
                            }
                            br2.close();
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }

                // 현재 차수 리스트에 있는 읽기차수 보다 초과하면 그다음 파일명 저장
                if(chaSu > chaSuCnt) {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fileList, true));
                    bw.write("readHist" + bookSe + "_" + chaSu + ".txt" + "\n");
                    bw.close();
                }

                // 읽기 리스트에 읽은 책:장 저장
                BufferedWriter bw2 = new BufferedWriter(new FileWriter(getFilesDir() + "/readHist" + bookSe + "_" + chaSu + ".txt", true));
                bw2.write(bookSe + ":" + nowBook + ":" + i + "\n");
                bw2.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Toast.makeText(FileCheckActivity.this, nowBook + " " + startIndex + "~" + endIndex + "을 읽기 기록에 추가하였습니다.", Toast.LENGTH_SHORT).show();
    }


}
