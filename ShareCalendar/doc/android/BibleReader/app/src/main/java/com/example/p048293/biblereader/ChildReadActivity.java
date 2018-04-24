package com.example.p048293.biblereader;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class ChildReadActivity extends AppCompatActivity {
    int nowYear = 0;
    int nowMon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_read);

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
        int year = date.getYear() + 1900;
        int mon = date.getMonth() + 1;
        int todate = date.getDate();
        int day = date.getDay();    // 요일

        nowYear = year;
        nowMon = mon;

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

        int firstWeek = nowYear * 365;  // 0년 부터 시작이니까
        for(int i=0; i<nowMon; i++) {
            if(nowYear%400 == 0 && nowMon == 2) {
                firstWeek += 29;
            } else if(nowYear%100 !=0) {
                if(nowYear%4 == 0 && nowMon == 2) {
                    firstWeek += 29;
                }
            } else {
                firstWeek += lastMonthDay[i];
            }
        }


        // 윤달 체크
        if(nowYear%400 == 0 && nowMon == 2) {
            lastDay = 29;
        } else if(nowYear%100 !=0) {
            if(nowYear%4 == 0 && nowMon == 2) {
                lastDay = 29;
            }
        }

        tableLayout1.removeAllViewsInLayout();

        int trCt = 5;
        int tdCt = 7;

        TableRow row[] = new TableRow[trCt];     // 테이블 ROW 생성
        TextView dayTtext[][] = new TextView[trCt][tdCt]; // 데이터

        int tempDay = 1;    // 달력에 표시할 날짜

        for(int tr=0; tr<trCt; tr++) {
            row[tr] = new TableRow(this);
            row[tr].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

            for (int td = 0; td < tdCt; td++) {
                dayTtext[tr][td] = new TextView(this);
                TableRow.LayoutParams tdParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                // 날짜 셋팅
                if(firstWeek != 7 && td<firstWeek) {    // 빈칸 셋팅
                    dayTtext[tr][td].setText("");
                } else if(tempDay <= lastDay) {                         // 날짜 셋팅
                    dayTtext[tr][td].setText(tempDay + "");
                    tempDay++;
                } else {                                                // 마지막 빈칸 셋팅
                    dayTtext[tr][td].setText("");
                }

                // 속성 지정
                dayTtext[tr][td].setTextSize(20);                // 폰트사이즈
                dayTtext[tr][td].setGravity(Gravity.CENTER);    // 폰트정렬
                dayTtext[tr][td].setBackgroundColor(Color.GRAY);  // 배경색
                dayTtext[tr][td].setTextColor(Color.BLACK);     // 폰트컬러

                tdParams.setMargins(1, 1, 1, 1);
                dayTtext[tr][td].setLayoutParams(tdParams);
                row[tr].addView(dayTtext[tr][td]);
            }
            tableLayout1.addView(row[tr]);
        }

    }

}
