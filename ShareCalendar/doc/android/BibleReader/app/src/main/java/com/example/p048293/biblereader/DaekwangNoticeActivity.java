package com.example.p048293.biblereader;

import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DaekwangNoticeActivity extends AppCompatActivity {

    private String backColor = "-16777216";    // 배경색
    private String fontColor = "-4342339";     // 글자색
    private float fontSize = 20;                // 글자크기
    private int scrollSpeed = 5;                // 스크롤 속도
    int scrollDist = 7139;                        // 스크롤 전체길이 7139
    int scrollTime = 145000;                      // 스크롤 시간  145000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daekwang_notice);

        final TableLayout tableLayout = (TableLayout)findViewById(R.id.tableLayout1);
        tableLayout.removeAllViewsInLayout();
        tableLayout.setStretchAllColumns(true);

        Log.d("==========" , "시작");
        //String tempContent[trCt][tdCt] = webParsData();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    webParsData(tempContent);

                    Message message = handler.obtainMessage();
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // 여기에서 UI 수정
                readBookHan(tableLayout);
            }
        };



        Log.d("==========" , "끝");


    }

    // book index 조합으로 찾기 (한글)
    String tempContent[][];
    public void readBookHan(TableLayout tableLayout) {

        tableLayout.removeAllViewsInLayout();

        try {
            int trCt = 10;
            int tdCt = 3;

            // 그리드 생성
            TableRow row[] = new TableRow[trCt];            // 테이블 ROW 생성
            final TextView text[][] = new TextView[trCt][tdCt];   // 데이터


            for (int tr = 0; tr < trCt; tr++) {
                row[tr] = new TableRow(this);
                row[tr].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

                for(int td=0; td<tdCt; td++) {
                    text[tr][td] = new TextView(this);

                    TableRow.LayoutParams textParam = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    textParam.setMargins(5, 5, 5, 5);
                    textParam.weight = 1;
                    text[tr][td].setLayoutParams(textParam);

                    text[tr][td].setText(tempContent[tr][td]);

                    // 속성 지정
                    text[tr][td].setId(td);
                    text[tr][td].setTextSize(fontSize);                // 폰트사이즈
                    //text[tr][td].setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                    text[tr][td].setGravity(Gravity.LEFT);    // 폰트정렬
                    text[tr][td].setBackgroundDrawable(new ColorDrawable(Integer.parseInt(backColor)));
                    text[tr][td].setTextColor(Integer.parseInt(fontColor));

                    row[tr].addView(text[tr][td]);
                }
                tableLayout.addView(row[tr]);

                // 파란줄 추가
                TableRow tempRow = new TableRow(this);
                for(int k=0; k<tdCt; k++) {
                    TextView tempTv = new TextView(this);
                    tempTv.setText("");
                    tempTv.setTextSize(1);                     // 폰트사이즈
                    tempTv.setBackgroundColor(Color.BLUE);
                    tempRow.addView(tempTv);
                }
                tableLayout.addView(tempRow);
            }

            // 여백 추가
            TableRow tempRow = new TableRow(this);
            for(int k=0; k<3; k++) {
                TextView tempTv = new TextView(this);
                tempTv.setText(" ");
                tempTv.setTextSize(20);                     // 폰트사이즈
                tempRow.addView(tempTv);
            }
            tableLayout.addView(tempRow);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 일정 가져오기
    Handler handler = new Handler();
    StringBuilder stringBuilder = new StringBuilder();

    public void webParsData(String[][] tempContent) {
        tempContent = new String[10][3];

        try {
            URL url = new URL("http://www.daekwang.org/");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            if(conn!=null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                int resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader reader = new InputStreamReader(conn.getInputStream(), "euc-kr");
                    BufferedReader br = new BufferedReader(reader);
                    String pageContent = null;

                    int maxNo = 0;

                    while((pageContent = br.readLine()) != null) {
                        Log.d("==========" , pageContent);
                        if(pageContent.indexOf("www.daekwang.org/board/dgboard.php") > -1) {
                            String tempStr[] = pageContent.split("=");
                            String tempNo = tempStr[4].replace("&call", "");

                            if(maxNo < Integer.parseInt(tempNo)) maxNo = Integer.parseInt(tempNo);
                        }
                    }

                    conn.disconnect();

                    for(int j=(maxNo-10); j<=maxNo; j++) {
                        url = new URL("http://www.daekwang.org/board/dgboard.php?board=dk_02&mode=view&no=" + j + "&call=y");
                        conn = (HttpURLConnection)url.openConnection();
                        reader = new InputStreamReader(conn.getInputStream(), "euc-kr");
                        br = new BufferedReader(reader);
                        String line = null;

                        int checkNum = 0;
                        while((line = br.readLine()) != null) {
                            if(checkNum == 1) {
                                String lineStr = line.replaceAll("<br>","").replaceAll("</td>","")
                                        .replaceAll("\t","").replaceAll("  ","").replaceAll("  ","");
                                String arrayStr1[] = lineStr.split("/");
                                String arrayStr2[] = arrayStr1[0].split(":");

                                if(arrayStr1.length == 2) {
                                    Log.d(j + "==========" , arrayStr2[0] + " | " + arrayStr2[1] + " | " + arrayStr1[1]);
                                    tempContent[j][0] = arrayStr2[0];
                                    tempContent[j][1] = arrayStr2[1];
                                    tempContent[j][2] = arrayStr1[1];
                                } else {
                                    Log.d(j + "==========" ,arrayStr2[0] + " | " + arrayStr2[1]);
                                    tempContent[j][0] = arrayStr2[0];
                                    tempContent[j][1] = arrayStr2[1];
                                    tempContent[j][2] = "";
                                }

                                checkNum = 0;
                            }

                            if(line.indexOf("<td colspan=2 height=100 valign=top>") > -1) {
                                checkNum = 1;
                            } else {
                                checkNum = 0;
                            }
                        }
                    }
                }

            }




        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}