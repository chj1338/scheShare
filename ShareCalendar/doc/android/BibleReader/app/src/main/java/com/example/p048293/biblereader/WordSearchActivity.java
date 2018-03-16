package com.example.p048293.biblereader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WordSearchActivity extends AppCompatActivity {
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_search);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        final TextView textView = (TextView)findViewById(R.id.textView001);
        final EditText editText = (EditText)findViewById(R.id.editText);
        final Button button3 = (Button)findViewById(R.id.button3);
        final Button btnFontSize = (Button)findViewById(R.id.btnFontSize);

        // 화면 포커스를 강제로 맨 위로
        WindowManager.LayoutParams wp = getWindow().getAttributes();
        wp.gravity = Gravity.TOP; //Don't set BOTTOM !

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
                editText.setFocusable(false);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);  // 키보드 숨기기
                readBook(textView, editText);
            }
        });

        //폰트 사이즈 리스너
        btnFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float fontSize = textView.getTextSize();

                if(fontSize > 50) {
                    fontSize = 30;
                } else {
                    fontSize = fontSize + 5;
                }

                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
            }
        });
    }


    // book index 조합으로 찾기
    public void readBook(TextView textView, EditText editText) {
        Toast.makeText(this, "검색시작", Toast.LENGTH_SHORT).show();
        String word = editText.getText().toString();

        try {
            if(word == null || word.equals("") || word == "" || word.equals(null)) {
                textView.setText("검색어가 없습니다.");
                editText.requestFocus();
            } else {
                String readStr = "";
                String line = "";

                //구약
                InputStream is1 = getResources().openRawResource(R.raw.bible_old);
                BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));

                //구약
                while ((line = br1.readLine()) != null) {
                    if (line.indexOf(word) != -1) {
                        readStr += line + "\n\n";
                    }
                }

                br1.close();

                //신약
                InputStream is2 = getResources().openRawResource(R.raw.bible_new);
                BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));

                //신약
                while ((line = br2.readLine()) != null) {
                    if (line.indexOf(word) != -1) {
                        readStr += line + "\n\n";
                    }
                }
                br2.close();

                textView.setText(readStr);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "파일을 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView3);
        scrollView.setScrollY(0);

        Toast.makeText(this, "검색 끝", Toast.LENGTH_SHORT).show();
    }
}
