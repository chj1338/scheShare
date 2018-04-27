package com.example.p048293.biblereader;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class KinderReadActivity extends AppCompatActivity {
    int nowYear = 0;    // 올해
    int nowMon = 0;     // 당월
    int nowDate = 0;    // 오늘
    int nowDay = 0;     // 오늘 요일

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_read);
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
        tableLayout1.removeAllViewsInLayout();

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
                if (nowYear % 4 == 0) {
                    firstWeek++;
                }
                if (nowYear % 100 == 0) {
                    firstWeek--;
                }
                if (nowYear % 400 == 0) {
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

        TableRow row[] = new TableRow[trCt];     // 테이블 ROW 생성
        TextView dayTtext[][] = new TextView[trCt][tdCt]; // 데이터

        int tempDay = 1;    // 달력에 표시할 날짜
        int tempColumn = 0; // 전체 칸수

        for(int tr=0; tr<trCt; tr++) {
            row[tr] = new TableRow(this);
            row[tr].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

            for (int td = 0; td < tdCt; td++) {
                dayTtext[tr][td] = new TextView(this);
                TableRow.LayoutParams tdParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                tdParams.setMargins(1, 1, 1, 1);

                if(tr == 0) {
                    dayTtext[tr][td].setText(dayName[td]);
                    dayTtext[tr][td].setTextColor(dayColor[td]);     // 폰트컬러
                    dayTtext[tr][td].setTextSize(20);                // 폰트사이즈
                    dayTtext[tr][td].setGravity(Gravity.CENTER);    // 폰트정렬
                    dayTtext[tr][td].setBackgroundColor(Color.GRAY);  // 배경색

                    dayTtext[tr][td].setLayoutParams(tdParams);
                    row[tr].addView(dayTtext[tr][td]);
                } else {
                    tempColumn++;

                    // 날짜 셋팅
                    if (firstWeek != 0 && tempColumn < firstWeek) {            // 앞쪽 빈칸 셋팅
                        dayTtext[tr][td].setText("");
                    } else if (tempDay <= lastDay) {                 // 날짜 셋팅
                        dayTtext[tr][td].setText(tempDay + "");
                        tempDay++;
                    } else {                                        // 마지막 빈칸 셋팅
                        dayTtext[tr][td].setText("");
                    }

                    // 속성 지정
                    dayTtext[tr][td].setTextSize(20);                // 폰트사이즈
                    dayTtext[tr][td].setGravity(Gravity.CENTER);    // 폰트정렬
                    dayTtext[tr][td].setBackgroundColor(Color.GRAY);  // 배경색
                    dayTtext[tr][td].setTextColor(dayColor[td]);     // 폰트컬러

                    dayTtext[tr][td].setLayoutParams(tdParams);
                    row[tr].addView(dayTtext[tr][td]);
                }
            }
            tableLayout1.addView(row[tr]);
        }

    }

}
