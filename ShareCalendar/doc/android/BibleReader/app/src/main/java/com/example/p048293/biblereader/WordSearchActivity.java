package com.example.p048293.biblereader;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WordSearchActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    InputMethodManager imm;

    private String backColor = "-16777216";      // 배경색
    private String fontColor = "-4342339";      // 글자색
    private float fontSize = 20;         // 글자크기
    private int scrollSpeed = 1;        // 스크롤 속도
    int scrollDist = 7139;      // 스크롤 전체길이 7139
    int scrollTime = 145000;    // 스크롤 시간  145000

    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_search);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);   // 화면꺼짐 방지

        textView = (TextView)findViewById(R.id.textView001);
        editText = (EditText)findViewById(R.id.editText);
        final Button button3 = (Button)findViewById(R.id.button3);
        final Button btnFontSize = (Button)findViewById(R.id.btnFontSize);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        editText.setOnEditorActionListener(this); //mEditText와 onEditorActionListener를 연결

        // 화면 포커스를 강제로 맨 위로
        WindowManager.LayoutParams wp = getWindow().getAttributes();
        wp.gravity = Gravity.TOP; //Don't set BOTTOM !

        readConfig(textView);

        // 검색어 editText
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setFocusable(true);
            }
        });

        // 검색버튼
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("검색중.....");
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);  // 키보드 숨기기
                readBook(textView, editText);
            }
        });

        //폰트 사이즈 리스너
        btnFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);  // 키보드 숨기기
                float fontSize = textView.getTextSize();

                if(fontSize > 80) {
                    fontSize = 30;
                } else {
                    fontSize = fontSize + 5;
                }

                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
            }
        });
    }


    //config 파일 읽어서 반영
    public void readConfig(TextView textView) {
        try {
            String line = null;

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
    public void readBook(TextView textView, EditText editText) {
        String word = editText.getText().toString();
        int wordCnt = 0;    // 구절 개수

        try {
            if(word == null || word.equals("") || word == "" || word.equals(null)) {
                textView.setText("검색어가 없습니다.");
                editText.requestFocus();
            } else if(word.length() <= 1) {
                textView.setText("두글자 이상 검색 가능합니다.");
                editText.requestFocus();
            } else {
                String readStr = "";
                String line = "";

                //구약
                InputStream is1 = getResources().openRawResource(R.raw.bible_old);
                BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
                while ((line = br1.readLine()) != null) {
                    if (line.indexOf(word) != -1) {
                        readStr += line + "\n\n";
                        wordCnt++;
                    }
                }
                br1.close();

                //신약
                InputStream is2 = getResources().openRawResource(R.raw.bible_new);
                BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));

                while ((line = br2.readLine()) != null) {
                    if (line.indexOf(word) != -1) {
                        readStr += line + "\n\n";
                        wordCnt++;
                    }
                }
                br2.close();
                readStr += "\n\n\n\n\n";

                // word 에 해당되는 단어마다 색칠하기
                SpannableStringBuilder sb = new SpannableStringBuilder(readStr);
                String tempReadStr = readStr;

                int startIndex = tempReadStr.indexOf(word);
                int nowIndex = startIndex;
                while ( tempReadStr.contains(word) ) {
                    sb.setSpan(new ForegroundColorSpan(Color.RED), nowIndex, nowIndex + word.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

                    tempReadStr = tempReadStr.substring(startIndex + word.length());
                    startIndex = tempReadStr.indexOf(word);
                    nowIndex = startIndex + (readStr.length() - tempReadStr.length());
                }

                textView.setText(sb);

                Toast.makeText(this, wordCnt + "개의 구절이 검색되었습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView3);
        scrollView.setScrollY(0);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //오버라이드한 onEditorAction() 메소드
        if(v.getId() == R.id.editText && actionId == EditorInfo.IME_ACTION_DONE){ // 뷰의 id를 식별, 키보드의 완료 키 입력 검출
            readBook(textView, editText);
        }
        return false;
    }
}


