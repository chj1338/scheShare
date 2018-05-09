package com.example.p048293.biblereader;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

public class KinderTestamemtActivity extends AppCompatActivity {

    private String backColor = "-16777216";      // 배경색
    private String fontColor = "-4342339";      // 글자색
    private float fontSize = 20;         // 글자크기
    private int scrollSpeed = 5;        // 스크롤 속도
    int scrollDist = 7139;      // 스크롤 전체길이 7139
    int scrollTime = 145000;    // 스크롤 시간  145000

    int nowYear = 0;    // 올해
    int nowMon = 0;     // 당월
    int nowDate = 0;    // 오늘
    int nowDay = 0;     // 오늘 요일

    String kinderReadHist = "";
    String bookSe = "";         // 신구약 구분
    String nowBookPage = "";    // 책 장
    String minSentence = "";    // 절 : ~ 부터
    String maxSentence = "";    // 절 : ~ 까지

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kinder_testamemt);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   // 화면꺼짐 방지

        final TextView textView = (TextView)findViewById(R.id.textView001);
        final TextView calText = (TextView)findViewById(R.id.calText);
        final TextView sentence = (TextView)findViewById(R.id.sentence);
        final Button btnFontSize = (Button)findViewById(R.id.btnFontSize);
        final Button btnReadHist = (Button)findViewById(R.id.btnReadHist);
        final ScrollView scrollView3 = (ScrollView)findViewById(R.id.scrollView3);
        final Button btnAutoScroll = (Button)findViewById(R.id.btnAutoScroll);
        final Button btnStopScroll = (Button)findViewById(R.id.btnStopScroll);

        final ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollView3, "scrollY", 0);

        readConfig(textView, calText, sentence);

        readBook(textView);

        //폰트 사이즈 리스너
        btnFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float fontSize = textView.getTextSize();
//                Toast.makeText(NewTestamentActivity.this, "현재크기 : " + fontSize, Toast.LENGTH_LONG).show();

                if(fontSize > 80) {
                    fontSize = 20;
                } else {
                    fontSize = fontSize + 5;
                }

//                Toast.makeText(NewTestamentActivity.this, "변경크기 : " + fontSize, Toast.LENGTH_LONG).show();
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
            }
        });

        // 자동 스크롤 시작
        btnAutoScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollDist = textView.getMeasuredHeight();
                scrollTime =  textView.getMeasuredHeight() * 145000 / 7139;   // 최초 기준값 - 길이:3719, 시간:145000

                objectAnimator.setIntValues(scrollDist); // 전체수행길이
                objectAnimator.setDuration(scrollTime * (6 - scrollSpeed));    // 전체수행시간
                objectAnimator.setInterpolator(new LinearInterpolator());   // 일정속도로
                objectAnimator.start();

                btnAutoScroll.setVisibility(View.INVISIBLE);
                btnStopScroll.setVisibility(View.VISIBLE);
            }
        });

        // 자동 스크롤 중지
        btnStopScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectAnimator.cancel();

                btnAutoScroll.setVisibility(View.VISIBLE);
                btnStopScroll.setVisibility(View.INVISIBLE);
            }
        });


        // 완독 저장
        btnReadHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStopScroll.callOnClick();    // 자동스크롤 중지

                Date date = new Date();
                int nowYear = date.getYear() + 1900;

                try {
                    File fileList = new File(getFilesDir() + "/kinderReadHist_" + nowYear + ".txt");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fileList, true));
                    bw.write(nowYear + kinderReadHist + "\n\n");
                    bw.close();

                    Toast.makeText(KinderTestamemtActivity.this, "완독 저장했습니다.", Toast.LENGTH_SHORT).show();

                    btnReadHist.setEnabled(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //config 파일 읽어서 반영
    public void readConfig(TextView textView, TextView calText, TextView sentence) {
        try {
            String line = null;

            FileReader fr = new FileReader(getFilesDir() + "/kinderRead.txt");
            BufferedReader br = new BufferedReader(fr);
            while((line=br.readLine())!=null) {
                String newConfig[] = line.split(":");
                String sectence[] = newConfig[3].split("~");

                kinderReadHist = line;
                bookSe = newConfig[1];
                nowBookPage = newConfig[2];
                minSentence = sectence[0];
                maxSentence = sectence[1];

                nowMon = Integer.parseInt(newConfig[0].substring(0, 2));
                nowDate = Integer.parseInt(newConfig[0].substring(2, 4));

                Date date = new Date();
                nowYear = date.getYear() + 1900;

                if(newConfig.length > 4) {
                    calText.setText("<" + newConfig[4] + ">  " + nowYear + "-" + nowMon + "-" + nowDate);
                } else {
                    calText.setText(nowYear + "-" + nowMon + "-" + nowDate);
                }

                sentence.setText(nowBookPage + ":" + minSentence + "~" + maxSentence);
            }
            br.close();

            // 화면설정 읽어서 반영
            String lineDisp = "";
            File fileDisp = new File(getFilesDir() + "/configDisp.txt");

            if(fileDisp.exists()) {
                BufferedReader brDisp = new BufferedReader(new FileReader(fileDisp));
                while ((lineDisp = brDisp.readLine()) != null) {
                    String dispConfig[] = lineDisp.split(":");
                    if (dispConfig.length == 2 && dispConfig[0].equals("backColor") && dispConfig[1] != null) {
                        backColor = dispConfig[1];
                    } else if (dispConfig.length == 2 && dispConfig[0].equals("fontColor") && dispConfig[1] != null) {
                        fontColor = dispConfig[1];
                    } else if (dispConfig.length == 2 && dispConfig[0].equals("fontSize") && dispConfig[1] != null) {
                        fontSize = Float.parseFloat(dispConfig[1]);
                    } else if (dispConfig.length == 2 && dispConfig[0].equals("scrollSpeed") && dispConfig[1] != null) {
                        scrollSpeed = Integer.parseInt(dispConfig[1]);
                    }
                }
                brDisp.close();

                textView.setBackgroundColor(Integer.parseInt(backColor));
                textView.setTextColor(Integer.parseInt(fontColor));
                textView.setTextSize(fontSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // book index 조합으로 찾기
    public void readBook(TextView textView) {
        try {
            //raw 폴더 읽기
            InputStream is = null;
            if(bookSe.equals("OLD")) {
                is = getResources().openRawResource(R.raw.bible_old);
            } else {
                is = getResources().openRawResource(R.raw.bible_new);
            }
            BufferedReader br2 = new BufferedReader(new InputStreamReader(is));

            String readStr ="";
            String line = "";

            while((line=br2.readLine())!=null) {
                for(int i=Integer.parseInt(minSentence); i<=Integer.parseInt(maxSentence); i++) {
                    String findStr = nowBookPage + ":" + i + " ";

                    if(line.indexOf(findStr) > -1) {
                        readStr += line + "\n\n";
                    }
                }
            }
            br2.close();

            readStr += "\n\n\n\n\n";

            textView.setText(readStr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            textView.setText("파일을 찾을 수 없습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView3);
        scrollView.setScrollY(0);
    }

    // back 버튼을 클릭시
    public void onBackPressed() {
        Intent intentMain = new Intent(this, KinderReadActivity.class);
        startActivity(intentMain);
    }
}
