package com.example.p048293.biblereader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class ReadHistActivity extends AppCompatActivity {
    CommonActivity commonActivity = new CommonActivity();

    String bookSe = "OLD";
    int status = 1;

    Spinner spinner1;
    Spinner spinner2;
    TableLayout tableLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_hist);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   // 화면꺼짐 방지

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        final ScrollView scrollView3 = (ScrollView) findViewById(R.id.scrollView3);

        tableLayout1 = (TableLayout)findViewById(R.id.tableLayout1);
        tableLayout1.removeAllViewsInLayout();
        tableLayout1.setStretchAllColumns(true);

        String nowDate = commonActivity.toDate();
        Toast.makeText(this, nowDate, Toast.LENGTH_SHORT).show();

        initSpinner(spinner1, spinner2);

        spinner1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                status = 1;
                return false;
            }
        });

        spinner2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                status = 1;
                return false;
            }
        });

        // spinner1 리스너
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner1.getSelectedItemPosition() == 0) {
                    bookSe = "OLD";
                } else {
                    bookSe = "NEW";
                }

                // spinner2 child 세팅
                try {
                    ArrayList<String> items = new ArrayList<>();
                    int tempItem = 0;

                    String line = "";
                    BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/readHist" + bookSe + "List.txt"));
                    while((line=br.readLine())!=null) {
                        items.add((tempItem + 1) + "");
                        tempItem++;
                    }
                    br.close();

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(ReadHistActivity.this, android.R.layout.simple_spinner_item, items);
                    //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    adapter2.setDropDownViewResource(R.layout.spinner_item);
                    spinner2.setAdapter((SpinnerAdapter) adapter2);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                spinner2.setSelection(0);

                if(status == 1) {
                    //setGrid(spinner2, tableLayout1);
                    setGridThread();
                    status = 0;
                }
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
                if(status == 1) {
                    //setGrid(spinner2, tableLayout1);
                    setGridThread();
                    //scrollView3.setScrollY(0);
                    status = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // 스피너 초기화
    public void initSpinner(Spinner spinner1, Spinner spinner2) {
        // spinner1 child 셋팅
        ArrayList<String> bookSeItems = new ArrayList<>();
        bookSeItems.add("구약");
        bookSeItems.add("신약");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bookSeItems);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner1.setAdapter((SpinnerAdapter) adapter);

        // spinner2 child 세팅
        try {
            ArrayList<String> items = new ArrayList<>();
            int tempItem = 0;

            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/readHist" + bookSe + "List.txt"));
            while((line=br.readLine())!=null) {
                items.add((tempItem + 1) + "");
                tempItem++;
            }
            br.close();

            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
            //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            adapter2.setDropDownViewResource(R.layout.spinner_item);
            spinner2.setAdapter((SpinnerAdapter) adapter2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // book index 조합으로 찾기
    // ProgressDialog 사용을 위해 Thread 사용
    private ProgressDialog loagindDialog;
    private void setGridThread() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 1. 필요한 작업
                // Thread에서는 UI 컨트롤이 불가하다. 필요시 runOnUiThread 사용
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //setGrid(spinner2, tableLayout1);
                        loagindDialog = ProgressDialog.show(ReadHistActivity.this, null, "0진행중...");
                    }
                });

                handler.sendEmptyMessage(0);
            }
        });
        thread.start();
    }
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            setGrid(spinner2, tableLayout1);

            loagindDialog.dismiss(); // 다이얼로그 삭제
            // 2. 이후 처리
        }
    };


    // book index 조합으로 찾기
    // ProgressDialog 사용을 위해 Thread 사용
    private void setGridThread2() {
        loagindDialog = ProgressDialog.show(this, null, "Loading...");

        try {
            Thread.sleep(3000);

            // 1. 필요한 작업
            // Thread에서는 UI 컨트롤이 불가하다. 필요시 runOnUiThread 사용
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setGrid(spinner2, tableLayout1);

                    handler.sendEmptyMessage(0);
                }
            });

        } catch(Exception e) {
            e.printStackTrace();
        }
    }



    // 읽기 표 생성
    public void setGrid(Spinner spinner2, TableLayout tableLayout1) {
        ArrayAdapter adapter_ac_name;
        ArrayAdapter adapter_page;

        if(bookSe.equals("OLD")) {
            adapter_ac_name = ArrayAdapter.createFromResource (this, R.array.bible_old_name_kor_ac, android.R.layout.simple_spinner_item);
            adapter_page = ArrayAdapter.createFromResource (this, R.array.bible_old_page, android.R.layout.simple_spinner_item);
        } else {
            adapter_ac_name = ArrayAdapter.createFromResource (this, R.array.bible_new_name_kor_ac, android.R.layout.simple_spinner_item);
            adapter_page = ArrayAdapter.createFromResource (this, R.array.bible_new_page, android.R.layout.simple_spinner_item);
        }

        tableLayout1.removeAllViewsInLayout();

        //읽은 페이지 기록 파일
        int chaSu = spinner2.getSelectedItemPosition() + 1;
        ArrayList<String> readStr = new ArrayList<String>();
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/readHist" + bookSe + "_" + chaSu + ".txt"));

            while ((line = br.readLine()) != null) {
                readStr.add(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 성경 책 수 만큼 반복
        for(int i=0; i<adapter_ac_name.getCount(); i++) {
            String nowBook = (String) adapter_ac_name.getItem(i);               // 현재 책
            int lastPage = Integer.parseInt((String) adapter_page.getItem(i));  // 현재 책의 마지막 page

            int trCt = lastPage / 10;   // 10칸씩 그릴 것이라 10으로 나눈 줄수
            int tdCt = 11;              // 책 이름까지 11칸

            // 10으로 나눈 나머지 page가 존재할 경우
            if(lastPage % 10 > 0) {
                trCt++;
            }

            TableRow row[] = new TableRow[trCt];     // 테이블 ROW 생성
            TextView text[][] = new TextView[trCt][tdCt]; // 데이터

            for (int tr = 0; tr < trCt; tr++) {                  // for문을 이용한 줄수 (TR)
                row[tr] = new TableRow(this);
                row[tr].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

                int nowPage = tr * 10;

                for (int td = 0; td < tdCt; td++) {              // for문을 이용한 칸수 (TD)
                    text[tr][td] = new TextView(this);

                    String tempPage = (nowPage + td) + "";

                    if (tr == 0 && td == 0) {   // 첫째줄 첫째칸
                        text[tr][td].setText(nowBook);
                    } else if (tr != 0 && td == 0) {    // 첫째줄이 아닌 첫째칸
                        text[tr][td].setText("");
                    } else if( tr == (trCt - 1) &&  ((nowPage + td) <= lastPage)) {     // 마지막줄 페이지
                        text[tr][td].setText(tempPage);
                    } else if( tr == (trCt - 1) &&  ((nowPage + td) > lastPage)) {      // 마지막줄 페이지를 뺀 빈칸들
                        text[tr][td].setText("");
                    } else {
                        text[tr][td].setText(tempPage);                     // 중간 페이지들
                    }

                    TableRow.LayoutParams trParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                    // 속성 지정
                    text[tr][td].setTextSize(20);                // 폰트사이즈
                    text[tr][td].setGravity(Gravity.CENTER);    // 폰트정렬
                    trParams.setMargins(1, 1, 1, 1);

                    if(td == 0) {
                        text[tr][td].setBackgroundColor(Color.DKGRAY);  // 배경색
                        text[tr][td].setTextColor(Color.WHITE);     // 폰트컬러
                    } else {
                        text[tr][td].setBackgroundColor(Color.GRAY);  // 배경색
                        text[tr][td].setTextColor(Color.BLACK);     // 폰트컬러
                    }

                    //읽은 페이지가 존재할 경우 색칠
                    for(int readHist=0; readHist<readStr.size(); readHist++) {
                        String[] readStrTemp = readStr.get(readHist).split(":");

                        try {
                            if (td == 0 || (nowPage + td) == 0) {
                                ;
                            } else if (nowBook.equals(readStrTemp[1]) && (nowPage + td) == Integer.parseInt(readStrTemp[2]) ) {
                                text[tr][td].setBackgroundColor(Color.CYAN);

                                readHist = readStr.size(); // 찾았으면 종료
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // 클릭하면 해당 페이지로 이동
                    int bookPosition = adapter_ac_name.getPosition(nowBook);
                    final String nowStr = bookSe + ":" + bookPosition + ":" + (Integer.parseInt(tempPage) - 1);
                    text[tr][td].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Toast.makeText(ReadHistActivity.this, nowStr, Toast.LENGTH_SHORT).show();

                            try {
                                String configFile = "";
                                if(bookSe.equals("OLD")) {
                                    configFile = "/configOld.txt";
                                } else {
                                    configFile = "/configNew.txt";
                                }

                                File file = new File(getFilesDir() + configFile);
                                file.delete();

                                BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                                bw.write(nowStr);
                                bw.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Intent intent = null;
                            if(bookSe.equals("OLD")) {
                                intent = new Intent(ReadHistActivity.this, OldTestamentActivity.class);
                            } else {
                                intent = new Intent(ReadHistActivity.this, NewTestamentActivity.class);
                            }
                            startActivity(intent);
                        }
                    });

                    text[tr][td].setLayoutParams(trParams);
                    row[tr].addView(text[tr][td]);
                } // td for end

                tableLayout1.addView(row[tr]);
            } // tr for end
            // 파란줄 추가
            TableRow tempRow = new TableRow(this);
            for(int k=0; k<11; k++) {
                TextView tempTv = new TextView(this);
                tempTv.setText("");
                tempTv.setTextSize(1);                     // 폰트사이즈
                tempTv.setBackgroundColor(Color.BLUE);
                tempRow.addView(tempTv);
            }
            tableLayout1.addView(tempRow);
        }
        // 여백 추가
        TableRow tempRow = new TableRow(this);
        for(int k=0; k<11; k++) {
            TextView tempTv = new TextView(this);
            tempTv.setText("");
            tempTv.setTextSize(20);                     // 폰트사이즈
            tempRow.addView(tempTv);
        }
        tableLayout1.addView(tempRow);
    }

}
