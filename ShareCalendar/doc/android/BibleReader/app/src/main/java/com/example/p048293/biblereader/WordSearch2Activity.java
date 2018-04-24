package com.example.p048293.biblereader;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WordSearch2Activity extends AppCompatActivity implements TextView.OnEditorActionListener {
    InputMethodManager imm;
    private TextView textView;
    private EditText editText;
    ProgressDialog dialog;
    Thread mThread;

    private Handler confirmHandler = new Handler() {
        public void handleMessage(Message msg) {
            dialog.dismiss();

            try {
                mThread.interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_search);

        textView = (TextView)findViewById(R.id.textView001);
        editText = (EditText)findViewById(R.id.editText);
        final Button button3 = (Button)findViewById(R.id.button3);
        final Button btnFontSize = (Button)findViewById(R.id.btnFontSize);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        editText.setOnEditorActionListener(this); //mEditText와 onEditorActionListener를 연결

        // 화면 포커스를 강제로 맨 위로
        WindowManager.LayoutParams wp = getWindow().getAttributes();
        wp.gravity = Gravity.TOP; //Don't set BOTTOM !

        // 검색어 editText
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    mThread.interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                editText.setFocusable(true);
            }
        });

        // 검색버튼
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);  // 키보드 숨기기
                //readBook(textView, editText);

                dialog = new ProgressDialog(WordSearch2Activity.this );
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("검색중입니다.....");
                dialog.show();

                mThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            readBook(textView, editText);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                mThread.start();
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


    // book index 조합으로 찾기
    public void readBook(final TextView textView, EditText editText) {
        String word = editText.getText().toString();
        int wordCnt = 0;    // 구절 개수

        try {
            if(word == null || word.equals("") || word == "" || word.equals(null)) {
                textView.setText("검색어가 없습니다.");
                editText.requestFocus();
                confirmHandler.sendEmptyMessage(0);
            } else if(word.length() <= 1) {
                textView.setText("두글자 이상 검색 가능합니다.");
                editText.requestFocus();
                confirmHandler.sendEmptyMessage(0);
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
                final SpannableStringBuilder sb = new SpannableStringBuilder(readStr);
                String tempReadStr = readStr;

                int startIndex = tempReadStr.indexOf(word);
                int nowIndex = startIndex;
                while ( tempReadStr.contains(word) ) {
                    sb.setSpan(new ForegroundColorSpan(Color.RED), nowIndex, nowIndex + word.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

                    tempReadStr = tempReadStr.substring(startIndex + word.length());
                    startIndex = tempReadStr.indexOf(word);
                    nowIndex = startIndex + (readStr.length() - tempReadStr.length());
                }

                // runOnUiThread : 스레드 내에서 UI 사용
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(sb);
                        confirmHandler.sendEmptyMessage(0);
                    }
                });
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