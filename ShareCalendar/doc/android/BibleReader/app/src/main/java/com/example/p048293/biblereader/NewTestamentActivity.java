package com.example.p048293.biblereader;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
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

public class NewTestamentActivity extends AppCompatActivity {
    String configFile = "/configNew.txt";
    int nowPage = 0;    // spinner2 변경용

    private String backColor = "-16777216";      // 배경색
    private String fontColor = "-4342339";      // 글자색
    private float fontSize = 20;         // 글자크기
    private int scrollSpeed = 1;        // 스크롤 속도
    int scrollDist = 7139;      // 스크롤 전체길이 7139
    int scrollTime = 145000;    // 스크롤 시간  145000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_testament);

        final TextView textView = (TextView)findViewById(R.id.textView001);
        final Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);    // 책 select
        final Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);    // 장 select
        final Button btnFontSize = (Button)findViewById(R.id.btnFontSize);
        final Button btnPrePage = (Button)findViewById(R.id.btnPrePage);
        final Button btnNextPage = (Button)findViewById(R.id.btnNextPage);
        final Button btnReadHist = (Button)findViewById(R.id.btnReadHist);
        final ScrollView scrollView3 = (ScrollView)findViewById(R.id.scrollView3);
        final Button btnAutoScroll = (Button)findViewById(R.id.btnAutoScroll);
        final Button btnStopScroll = (Button)findViewById(R.id.btnStopScroll);

        final ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollView3, "scrollY", 0);

        // 책 combo item 셋팅
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.bible_new_name_kor, android.R.layout.simple_spinner_item);
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.bible_new_name_kor, R.layout.spinner_item_left);
        adapter.setDropDownViewResource(R.layout.spinner_item_left);
        spinner1.setAdapter(adapter);

        // 장 combo item 셋팅
        setLastPage(spinner1, spinner2);

        //config 파일을 읽어서 이전 정보 반영
        readConfig(spinner1, spinner2, textView);

        // 자동 스크롤 시작
        btnAutoScroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int maxHeight = textView.getMeasuredHeight();
                int maxTime = 1000 * (scrollView3.getHeight() / 5);

                objectAnimator.setIntValues(maxHeight);
                objectAnimator.setDuration(maxTime);    // 전체수행시간
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

        // spinner1 리스너
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 장 combo item 셋팅
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);

                setLastPage(spinner1, spinner2);
                nowPage = 0;
                readBook(view, spinner1, spinner2, textView);

                btnReadHist.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // spinner2 리스너
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(objectAnimator.isRunning()) {
                    btnStopScroll.callOnClick();    // 자동스크롤 중지
                }

                readBook(view, spinner1, spinner2, textView);
                btnReadHist.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        //이전 페이지
        btnPrePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStopScroll.callOnClick();    // 자동스크롤 중지

                int position = spinner2.getSelectedItemPosition();

                if(position > 0) {
                    position--;
                    spinner2.setSelection(position);
                    readBook(v, spinner1, spinner2, textView);
                } else {
                    Toast.makeText(NewTestamentActivity.this, "맨 처음 입니다.", Toast.LENGTH_SHORT).show();
                }

                btnReadHist.setEnabled(true);
            }
        });

        //다음 페이지
        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStopScroll.callOnClick();    // 자동스크롤 중지

                int position = spinner2.getSelectedItemPosition();
                int lastPosition = spinner2.getCount() - 1;

                if(position < lastPosition) {
                    position++;
                    spinner2.setSelection(position);
                    readBook(v, spinner1, spinner2, textView);
                } else {
                    Toast.makeText(NewTestamentActivity.this, "마지막 입니다.", Toast.LENGTH_SHORT).show();
                }

                btnReadHist.setEnabled(true);
            }
        });

        // 완독 저장
        btnReadHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStopScroll.callOnClick();    // 자동스크롤 중지

                String bookSe = "NEW";
                int bookIndex = spinner1.getSelectedItemPosition();
                int page = spinner2.getSelectedItemPosition() + 1;

                ArrayAdapter adapter = ArrayAdapter.createFromResource(NewTestamentActivity.this, R.array.bible_new_name_kor_ac, android.R.layout.simple_spinner_item);
                String book = (String)adapter.getItem(bookIndex);

                String strHist = bookSe + ":" + book + ":" +  page;

                try {
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
                        chaSuCnt++;
                    }
                    br.close();

                    // 현재의 strHist 내용이 몇차까지 기록되었는지 확인
                    for(int i=1; i<=chaSuCnt; i++) {
                        line = null;
                        BufferedReader br2 = new BufferedReader(new FileReader(getFilesDir() + "/readHist" + bookSe + "_" + i + ".txt"));
                        while((line=br2.readLine())!=null) {
                            if(line.indexOf(strHist) != -1 ) {
                                chaSu = i + 1;
                                break;
                            }
                        }
                        br2.close();
                    }

                    // 차수 리스트에 읽기차수 파일명 저장
                    if(chaSu > chaSuCnt) {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(fileList, true));
                        bw.write("readHist" + bookSe + "_" + chaSu + ".txt" + "\n");
                        bw.close();
                    }

                    // 읽기 리스트에 읽은 책:장 저장
                    BufferedWriter bw2 = new BufferedWriter(new FileWriter(getFilesDir() + "/readHist" + bookSe + "_" + chaSu + ".txt", true));
                    bw2.write(strHist + "\n");
                    bw2.close();

                    Toast.makeText(NewTestamentActivity.this, chaSu + "차 완독 저장했습니다.", Toast.LENGTH_SHORT).show();

                    btnReadHist.setEnabled(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    // 장 combo item 셋팅
    public void setLastPage(Spinner spinner1, Spinner spinner2) {
        // last page item
        ArrayAdapter adapter_page = ArrayAdapter.createFromResource (this, R.array.bible_new_page, android.R.layout.simple_spinner_item);

        int sp1Position = spinner1.getSelectedItemPosition();
        int lastPage = Integer.parseInt((String)adapter_page.getItem(sp1Position));

        ArrayList<String> item2 = new ArrayList<>();
        for(int i=1; i<=lastPage; i++) {
            item2.add(i+"");
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item2);
        //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(R.layout.spinner_item);
        spinner2.setAdapter(adapter2);
        spinner2.setSelection(nowPage);
    }


    // book index 조합으로 찾기
    public void readBook(View v, Spinner spinner1, Spinner spinner2, TextView textView) {
        // last page item
        ArrayAdapter adapter_ac_name = ArrayAdapter.createFromResource (this, R.array.bible_new_name_kor_ac, android.R.layout.simple_spinner_item);
        int sp1Position = spinner1.getSelectedItemPosition();

        String book = (String)adapter_ac_name.getItem(sp1Position);
        String index = (String)spinner2.getSelectedItem();

        try {
            //raw 폴더 읽기
            InputStream is = getResources().openRawResource(R.raw.bible_new);
            BufferedReader br2 = new BufferedReader(new InputStreamReader(is));

            String readStr ="";
            String line = "";

            while((line=br2.readLine())!=null) {
                String rowData[] = line.split(":");

                if(rowData[0].equals(book + " " + index)) {
                    readStr += line + "\n\n";
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

    //config 파일 읽어서 반영
    public void readConfig(Spinner spinner1, Spinner spinner2, TextView textView) {
        try {
            String line = null;
            String newConfig2[] = null;

            BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + configFile));
            while((line=br.readLine())!=null) {
                //Toast.makeText(NewTestamentActivity.this, "2 : " + line, Toast.LENGTH_SHORT).show();

                String newConfig[] = line.split(":");
                newConfig2 = newConfig;
            }
            br.close();

            // spinner2 변경용
            // 아래 spinner1 변경 후 리스너 동작시 반영
//            Toast.makeText(NewTestamentActivity.this, newConfig2[2] + " : " + newConfig2[2]+" : " + newConfig2[2], Toast.LENGTH_LONG).show();

            nowPage = Integer.parseInt(newConfig2[2]);

            spinner1.setSelection(Integer.parseInt(newConfig2[1]));
            spinner1.refreshDrawableState();

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

    // back 버튼을 클릭시
    public void onBackPressed() {
        Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);    // 책 select
        Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);    // 장 select
        TextView textView = (TextView)findViewById(R.id.textView001);

        String bookSe = "NEW";
        int book = spinner1.getSelectedItemPosition();
        int page = spinner2.getSelectedItemPosition();
        float fontSize = textView.getTextSize();

        try {
            String str = bookSe + ":" + book + ":" +  page + ":" +  fontSize;

            File file = new File(getFilesDir() + configFile);
            file.delete();

            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(str);
            bw.close();

            Toast.makeText(NewTestamentActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intentMain = new Intent(this, MainActivity.class);
        startActivity(intentMain);
    }
}
