package com.example.p048293.biblereader;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WordSearch2Activity extends AppCompatActivity implements TextView.OnEditorActionListener {
    InputMethodManager imm;

    private String backColor = "-16777216";      // 배경색
    private String fontColor = "-4342339";      // 글자색
    private float fontSize = 20;         // 글자크기
    private int scrollSpeed = 1;        // 스크롤 속도
    int scrollDist = 7139;      // 스크롤 전체길이 7139
    int scrollTime = 145000;    // 스크롤 시간  145000

    private EditText searchWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_search2);

        searchWord = (EditText)findViewById(R.id.editText);
        final Button button3 = (Button)findViewById(R.id.button3);
        final Button btnFontSize = (Button)findViewById(R.id.btnFontSize);

        final TableLayout tableLayout = (TableLayout)findViewById(R.id.tableLayout1);
        tableLayout.removeAllViewsInLayout();
        tableLayout.setStretchAllColumns(true);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        searchWord.setOnEditorActionListener(this); //mEditText와 onEditorActionListener를 연결

        // 화면 포커스를 강제로 맨 위로
        WindowManager.LayoutParams wp = getWindow().getAttributes();
        wp.gravity = Gravity.TOP; //Don't set BOTTOM !

        readConfig(tableLayout);

        // 검색어 editText
        searchWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchWord.setFocusable(true);
            }
        });

        // 포커스 Out 시 키보드 강제 해제
        searchWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if( !searchWord.isFocused() ) {
                    imm.hideSoftInputFromWindow(searchWord.getWindowToken(), 0);  // 키보드 숨기기
                }
            }
        });

        // 검색버튼
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(searchWord.getWindowToken(), 0);  // 키보드 숨기기
                readBook(tableLayout, searchWord);
            }
        });

        //폰트 사이즈 리스너
        btnFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(searchWord.getWindowToken(), 0);  // 키보드 숨기기

                if(fontSize > 80) {
                    fontSize = 30;
                } else {
                    fontSize = fontSize + 5;
                }

                readBook(tableLayout, searchWord);
            }
        });
    }


    //config 파일 읽어서 반영
    public void readConfig(TableLayout tableLayout) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // searchWord 찾기
    public void readBook(final TableLayout tableLayout, EditText searchWord) {
        tableLayout.removeAllViewsInLayout();

        String word = searchWord.getText().toString();
        int wordCnt = 0;    // 구절 개수

        TableRow row = new TableRow(this);    // 테이블 ROW 생성
        EditText text = new EditText(this);   // 데이터

        try {
            if(word == null || word.equals("") || word == "" || word.equals(null)) {
                searchWord.requestFocus();
                setTextStr(tableLayout, row, text, "검색어가 없습니다.", word);
            } else if(word.length() <= 1) {
                searchWord.requestFocus();
                setTextStr(tableLayout, row, text, "두 글자 이상 검색 가능합니다.", word);
            } else {
                String line = "";

                //구약
                InputStream is1 = getResources().openRawResource(R.raw.bible_old);
                BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
                while ((line = br1.readLine()) != null) {
                    if (line.indexOf(word) != -1) {
                        setTextStr(tableLayout, row, text, line, word);
                        wordCnt++;
                    }
                }
                br1.close();

                //신약
                InputStream is2 = getResources().openRawResource(R.raw.bible_new);
                BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
                while ((line = br2.readLine()) != null) {
                    if (line.indexOf(word) != -1) {
                        setTextStr(tableLayout, row, text, line, word);
                        wordCnt++;
                    }
                }
                br2.close();

                for(int i=0; i<5; i++) {
                    setTextStr(tableLayout, row, text, "", word);
                }

                Toast.makeText(this, wordCnt + "개의 구절이 검색되었습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView1);
        scrollView.setScrollY(0);
    }


    public void setTextStr(TableLayout tableLayout, TableRow row, EditText text, String textStr, String word) {
        row = new TableRow(this);
        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));

        text = new EditText(this);
        TableRow.LayoutParams textParam = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        textParam.setMargins(5, 5, 5, 5);
        textParam.weight = 1;
        text.setLayoutParams(textParam);

        // 속성 지정
        text.setTextSize(fontSize);                // 폰트사이즈
        //text.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
        text.setGravity(Gravity.LEFT);    // 폰트정렬
        text.setBackgroundDrawable(new ColorDrawable(Integer.parseInt(backColor)));
        text.setTextColor(Integer.parseInt(fontColor));

        text.setInputType(0);
        text.setSingleLine(false);
        text.setSelectAllOnFocus(true); // 클릭시 문장 전체 선택

        // word 에 해당되는 단어마다 색칠하기
        SpannableStringBuilder sb = new SpannableStringBuilder(textStr);
        String tempReadStr = textStr;

        int startIndex = tempReadStr.indexOf(word);
        int nowIndex = startIndex;
        while ( tempReadStr.contains(word) ) {
            sb.setSpan(new ForegroundColorSpan(Color.RED), nowIndex, nowIndex + word.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            tempReadStr = tempReadStr.substring(startIndex + word.length());
            startIndex = tempReadStr.indexOf(word);
            nowIndex = startIndex + (textStr.length() - tempReadStr.length());
        }

        text.setText(sb);

        // 클립보드에 복사
        final String copyStr = textStr;
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyText(copyStr);
            }
        });

        row.addView(text);

        tableLayout.addView(row);
    }



    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        TableLayout tableLayout = (TableLayout)findViewById(R.id.tableLayout1);

        //오버라이드한 onEditorAction() 메소드
        if(v.getId() == R.id.editText && actionId == EditorInfo.IME_ACTION_DONE){ // 뷰의 id를 식별, 키보드의 완료 키 입력 검출
            readBook(tableLayout, searchWord);
        }
        return false;
    }


    // 클립보드 복사
    void copyText(String copyStr) {
        if (copyStr.length() != 0) {
            // 클립데이터 생성(텍스트)
            ClipData clip = ClipData.newPlainText("text", copyStr);

            // 클립매니져 객체 생성하고 클립데이터를 setPrimaryClip 메서드를 이용하여 클립보드에 복사
            ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setPrimaryClip(clip);
            Toast.makeText(this, "복사되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // 클립보드 붙여넣기
    void pasteText(TextView pasteText) {
        ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);

        // 클리보드에 클립데이터가 있는지 체크
        if (cm.hasPrimaryClip() == false) {
            //Toast.makeText(this, "Clipboard Empty", 0).show();
            return;
        }

        // 클립보드에 텍스트 형태의 MIME Type이 있는지 체크
        if (cm.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) == false) {
            //Toast.makeText(this, "Clip is not text", 0).show();
            return;
        }

        ClipData clip = cm.getPrimaryClip(); // 클립데이터를 읽는다.
        ClipData.Item item = clip.getItemAt(0);

        pasteText.setText(item.getText());
    }
}


