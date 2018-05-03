package com.example.p048293.biblereader;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

public class AdultTotalReadActivity extends AppCompatActivity {
    int nowYear = 0;    // 올해
    int nowMon = 0;     // 당월
    int nowDate = 0;    // 오늘
    int nowDay = 0;     // 오늘 요일

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adult_total_read);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   // 화면꺼짐 방지

        final TextView calText = (TextView)findViewById(R.id.calText);
        Button btnPreYear = (Button)findViewById(R.id.btnPreYear);
        Button btnPreMonth = (Button)findViewById(R.id.btnPreMonth);
        Button btnPostMonth = (Button)findViewById(R.id.btnPostMonth);
        Button btnPostYear = (Button)findViewById(R.id.btnPostYear);

        final TableLayout tableLayout1 = (TableLayout)findViewById(R.id.tableLayout1);
        tableLayout1.removeAllViewsInLayout();
        tableLayout1.setStretchAllColumns(true);

        // 오늘에 날짜를 세팅 해준다.
        Date date = new Date();
        nowYear = date.getYear() + 1900;
        nowMon = date.getMonth() + 1;
        nowDate = date.getDate();
        nowDay = date.getDay();    // 요일

        makeCalendar(tableLayout1, calText);

        btnPreYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowYear--;
                makeCalendar(tableLayout1, calText);
            }
        });

        btnPreMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nowMon == 1) {
                    nowYear--;
                    nowMon = 12;
                } else {
                    nowMon--;
                }
                makeCalendar(tableLayout1, calText);
            }
        });

        btnPostMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nowMon == 12) {
                    nowYear++;
                    nowMon = 1;
                } else {
                    nowMon++;
                }
                makeCalendar(tableLayout1, calText);
            }
        });

        btnPostYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowYear++;
                makeCalendar(tableLayout1, calText);
            }
        });
    }

    // 달력 데이터 생성
    public void makeCalendar(TableLayout tableLayout1, TextView calText) {
        // 오늘날짜 표시
        calText.setText(nowYear + "-" + nowMon);

        int[] lastMonthDay = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int lastDay = lastMonthDay[nowMon - 1];

        // 이번달이 2월이면 윤달여부 체크
        if(nowMon == 2) {
            if(nowYear % 100 == 0 && nowYear % 400 != 0) {
                lastDay = lastMonthDay[nowMon - 1];
            } else if(nowYear % 4 == 0) {
                lastDay = 29;
            }
        }

        // 전체 일수로 이번달 첫번째 요일 검색(빈칸 셋팅용)
        int firstWeek = nowYear * 365;  // 작년까지의 날수 (0년부터 시작)

        // 작년까지의 윤달 날짜 추가
        for(int i=1; i<(nowYear-1); i++) {
            if(i%4 == 0) {
                firstWeek++;
            }
            if(i%100 == 0) {
                firstWeek--;
            }
            if(i%400 == 0) {
                firstWeek++;
            }
        }

        // 올해 지난달 까지 날짜 추가
        for(int i=1; i<nowMon; i++) {
            firstWeek += lastMonthDay[i-1];

            if(i==2) {  // 2월은 올해 년도를 체크해서 윤달인지 확인
                if(nowYear % 4 == 0) {
                    firstWeek++;
                }
                if(nowYear % 100 == 0) {
                    firstWeek--;
                }
                if(nowYear % 400 == 0) {
                    firstWeek++;
                }
            }
        }

        firstWeek++;                // 이번달 1일
        int tempCheck = firstWeek;
        firstWeek = firstWeek % 7;  // 이번달 1일 요일

        String dayName[] = {"일", "월", "화", "수", "목", "금", "토"};
        int dayColor[] = {Color.RED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLUE};

        // 달력 만들기
        int trCt = 6;   // 표시 주(1주 ~ 6주까지)
        int tdCt = 7;   // 표시 요일(일 ~ 토)

        tableLayout1.removeAllViewsInLayout();

        TableRow row[] = new TableRow[trCt];     // 테이블 ROW 생성
        TextView dayTtext[][] = new TextView[trCt][tdCt]; // 데이터

        int tempDay = 1;    // 달력에 표시할 날짜
        int tempColumn = 0; // 전체 칸수


        //읽은 페이지 기록 파일
        ArrayList<String> childReadStr = new ArrayList<String>();
        try {
            String line = "";
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "/childReadHist_" + nowYear + ".txt"));
            while ((line = br.readLine()) != null) {
                childReadStr.add(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        for(int tr=0; tr<trCt; tr++) {
            row[tr] = new TableRow(this);
            row[tr].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1));

            for(int td = 0; td < tdCt; td++) {
                dayTtext[tr][td] = new TextView(this);
                TableRow.LayoutParams tdParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1);
                tdParams.setMargins(1, 1, 1, 1);

                if(tr == 0) {   // 요일 셋팅
                    dayTtext[tr][td].setHeight(50);
                    dayTtext[tr][td].setText(dayName[td]);
                    dayTtext[tr][td].setTextColor(dayColor[td]);     // 폰트컬러
                    dayTtext[tr][td].setTextSize(20);                // 폰트사이즈
                    dayTtext[tr][td].setGravity(Gravity.CENTER);    // 폰트정렬
                    dayTtext[tr][td].setBackgroundColor(Color.DKGRAY);  // 배경색

                    dayTtext[tr][td].setLayoutParams(tdParams);
                    row[tr].addView(dayTtext[tr][td]);
                } else {
                    // 셋팅할 날짜 증가
                    tempColumn++;

                    // 오늘 읽을 구절
                    String readStr = "";
                    String tempDate = "";

                    // 달이 1자리 이면 앞에 0 붙이기
                    if(nowMon < 10) {
                        tempDate += "0" + nowMon;
                    } else {
                        tempDate += "" + nowMon;
                    }

                    // 날짜가 1자리 이면 앞에 0 붙이기
                    if(tempColumn < 10) {
                        tempDate += "0" + tempColumn;
                    } else  {
                        tempDate += "" + tempColumn;
                    }

                    String readStrTemp = ""; // 성경읽기 화면으로 넘겨줄 내용

                    try {
                        String line = "";
                        InputStream is = getResources().openRawResource(R.raw.child_read_list);
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        while ((line = br.readLine()) != null) {
                            if(line.indexOf(tempDate) > -1) {
                                readStrTemp += line + "\n"; // 완독 기록용

                                String[] tempDayStr = line.split(":");

                                readStr += "\n" + tempDayStr[2] + "\n";
                                readStr += tempDayStr[3];
                            }
                        }
                        br.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 날짜 셋팅
                    if(firstWeek != 0 && tempColumn < firstWeek) {       // 앞쪽 빈칸 셋팅
                        dayTtext[tr][td].setText("");
                    } else if(tempDay <= lastDay) {                     // 날짜 셋팅
                        dayTtext[tr][td].setText(tempDay + readStr);
                        tempDay++;
                    } else {                                            // 마지막 빈칸 셋팅
                        dayTtext[tr][td].setText("");
                    }

                    // 속성 지정
//                    dayTtext[tr][td].setMaxWidth(95);
//                    dayTtext[tr][td].setMinHeight(280);
                    dayTtext[tr][td].setTextSize(15);                // 폰트사이즈
                    dayTtext[tr][td].setGravity(Gravity.RIGHT|Gravity.TOP);       // 폰트정렬
                    dayTtext[tr][td].setBackgroundColor(Color.GRAY); // 배경색
                    dayTtext[tr][td].setTextColor(dayColor[td]);     // 폰트컬러
                    dayTtext[tr][td].setPadding(1,1,1,1);

                    //읽은 페이지가 존재할 경우 색칠
                    for(int z=0; z<childReadStr.size(); z++) {
                        String[] childReadStrTemp = childReadStr.get(z).split(":");
                        String today = nowYear + tempDate;

                        if (today.equals(childReadStrTemp[0]) || today == childReadStrTemp[0]) {
                            dayTtext[tr][td].setBackgroundColor(Color.CYAN);
                        }
                    }

                    // 읽기 페이지로 이동
                    final String testaStr= readStrTemp;
                    dayTtext[tr][td].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                String configFile = "/childRead.txt";
                                File file = new File(getFilesDir() + configFile);
                                if(file.exists()) {
                                    file.delete();
                                }

                                BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                                bw.write(testaStr);
                                bw.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(AdultTotalReadActivity.this, AdultTotalTestamemtActivity.class);
                            startActivity(intent);
                        }
                    });

                    dayTtext[tr][td].setLayoutParams(tdParams);
                    row[tr].addView(dayTtext[tr][td]);
                }
            }
            tableLayout1.addView(row[tr]);
        }

    }

    // back 버튼을 클릭시
    public void onBackPressed() {
        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
    }
}
