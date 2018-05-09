package com.example.p048293.biblereader;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.Date;

public class Adult6TestamemtActivity extends AppCompatActivity {

    private String backColor = "-16777216";      // 배경색
    private String fontColor = "-4342339";      // 글자색
    private float fontSize = 20;         // 글자크기
    private int scrollSpeed = 5;        // 스크롤 속도
    int scrollDist = 7139;      // 스크롤 전체길이 7139
    long scrollTime = 145000;    // 스크롤 시간  145000

    int nowYear = 0;    // 올해
    int nowMon = 0;     // 당월
    int nowDate = 0;    // 오늘
    int nowDay = 0;     // 오늘 요일

    String readHist6 = "";       // 6장반 완독 저장용
    ArrayList<String> readHist = new ArrayList<String>();       // 성경읽기표 완독 저장용

    String bookSe = "";         // 신구약 구분
    String nowBook = "";        // 책 장
    String minSentence = "";    // 절 : ~ 부터
    String maxSentence = "";    // 절 : ~ 까지

    Button btnAutoScroll;
    Button btnStopScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adult_6_testamemt);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   // 화면꺼짐 방지

        final TextView textView = (TextView)findViewById(R.id.textView001);
        final TextView calText = (TextView)findViewById(R.id.calText);
        final TextView sentence = (TextView)findViewById(R.id.sentence);
        final Button btnFontSize = (Button)findViewById(R.id.btnFontSize);
        final Button btnReadHist = (Button)findViewById(R.id.btnReadHist);
        final ScrollView scrollView3 = (ScrollView)findViewById(R.id.scrollView3);
        btnAutoScroll = (Button)findViewById(R.id.btnAutoScroll);
        btnStopScroll = (Button)findViewById(R.id.btnStopScroll);

        final ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollView3, "scrollY", 0);

        readConfig(textView);

        readBook(textView, calText, sentence);

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
                scrollTime =  textView.getMeasuredHeight() / 7139 * 145000;   // 최초 기준값 - 길이:3719, 시간:145000

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
                    // 6장반 표시용 완독
                    File fileList = new File(getFilesDir() + "/adult6ReadHist_" + nowYear + ".txt");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fileList, true));
                    bw.write(nowYear + readHist6 + "\n");
                    bw.close();

                    // 성경읽기표 용 완독
                    saveReadHist();

                    Toast.makeText(Adult6TestamemtActivity.this, "완독 저장했습니다.", Toast.LENGTH_SHORT).show();

                    btnReadHist.setEnabled(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //config 파일 읽어서 반영
    public void readConfig(TextView textView) {
        try {
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
    public void readBook(TextView textView, TextView calText, TextView sentence) {
        try {
            String line = "";
            String readStr ="";

            FileReader fr = new FileReader(getFilesDir() + "/adult6Read.txt");
            BufferedReader br = new BufferedReader(fr);
            while((line=br.readLine())!=null) {
                readHist6 = line;   // 6장반 완독 저장용

                String newConfig[] = line.split(":");
                String tempStentence = "";  // 화면 표시용

                for(int i=1; i<newConfig.length; i++) {
                    readHist.add(newConfig[i].replaceAll(" ", ":"));    // 성경읽기표 완독 저장용

                    String tempReadStr[] = newConfig[i].split(" ");

                    bookSe = tempReadStr[0];
                    nowBook = tempReadStr[1];
                    minSentence = tempReadStr[2];

                    // 책 full name 찾기 ---  내용 첫줄에 표시할 책이름, 장 찾기
                    ArrayAdapter adapter;
                    ArrayAdapter adapter2;

                    if(bookSe.equals("OLD")) {
                        adapter = ArrayAdapter.createFromResource(Adult6TestamemtActivity.this, R.array.bible_old_name_kor_ac, android.R.layout.simple_spinner_item);
                        adapter2 = ArrayAdapter.createFromResource(Adult6TestamemtActivity.this, R.array.bible_old_name_kor, android.R.layout.simple_spinner_item);
                    } else {
                        adapter = ArrayAdapter.createFromResource(Adult6TestamemtActivity.this, R.array.bible_new_name_kor_ac, android.R.layout.simple_spinner_item);
                        adapter2 = ArrayAdapter.createFromResource(Adult6TestamemtActivity.this, R.array.bible_new_name_kor, android.R.layout.simple_spinner_item);
                    }

                    int bookIndex = adapter.getPosition(nowBook);
                    String bookFullName = (String)adapter2.getItem(bookIndex);
                    String bookLastName = "";

                    if(nowBook.equals("시")) {
                        bookLastName += "편";
                    } else {
                        bookLastName += "장";
                    }

                    if(tempStentence.equals("") || tempStentence == "") {
                        ;
                    } else {
                        tempStentence += ", ";
                    }

                    // 상단에 오늘 읽을 6장 표시
                    tempStentence += nowBook + " " + minSentence;
                    sentence.setTextSize(15);
                    sentence.setText(tempStentence);

                    readHist.add(bookSe + ":" + nowBook + ":" + minSentence); // 성경읽기표 저장용

                    readStr += "\n" + "<" + bookFullName + " " + minSentence + bookLastName + ">" + "\n"; // 내용 첫줄에 표시할 책이름, 장
                    String findStr = nowBook + " " + minSentence + ":";

                    //raw 폴더 읽기
                    InputStream is = null;
                    if(bookSe.equals("OLD")) {
                        is = getResources().openRawResource(R.raw.bible_old);
                    } else {
                        is = getResources().openRawResource(R.raw.bible_new);
                    }
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(is));

                    String line2 = "";
                    while((line2=br2.readLine()) != null) {
                        if(line2.indexOf(findStr) > -1) {
                            readStr += line2 + "\n\n";
                        }
                    }
                    br2.close();
                }

                // 날짜셋팅
                Date date = new Date();
                nowYear = date.getYear() + 1900;
                nowMon = Integer.parseInt(newConfig[0].substring(0, 2));
                nowDate = Integer.parseInt(newConfig[0].substring(2, 4));
                calText.setText(nowYear + "-" + nowMon + "-" + nowDate);

                readStr += "\n\n\n\n\n";
                textView.setText(readStr);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            textView.setText("파일을 찾을 수 없습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView3);
        scrollView.setScrollY(0);
    }


    // 읽기표 완독 저장
    public void saveReadHist() {
        btnStopScroll.callOnClick();    // 자동스크롤 중지

        try {
            for(int i=0;i<readHist.size(); i++) {
                String tempReadHist[] = readHist.get(i).split(":");
                bookSe = tempReadHist[0];

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
                                if (line.indexOf(tempReadHist[1] + " " + tempReadHist[2]) != -1) {
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
                bw2.write(readHist.get(i) + "\n");
                bw2.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // back 버튼을 클릭시
    public void onBackPressed() {
        Intent intentMain = new Intent(this, Adult6ReadActivity.class);
        startActivity(intentMain);
    }
}
