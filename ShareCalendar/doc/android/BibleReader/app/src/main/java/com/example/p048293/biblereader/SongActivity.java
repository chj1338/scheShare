package com.example.p048293.biblereader;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        final TextView textView = (TextView)findViewById(R.id.textView001);
        final Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);    // 장.제목 select
        final Button btnFontSize = (Button)findViewById(R.id.btnFontSize);
        final Button btnSearch = (Button)findViewById(R.id.btnSearch);
        final EditText editText1 = (EditText)findViewById(R.id.editText1);
        final EditText editText2 = (EditText)findViewById(R.id.editText2);

        // 책 combo item 셋팅
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.song_list, android.R.layout.simple_spinner_item);
        spinner1.setAdapter(adapter);

        // spinner1 리스너
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 찬송가 리스트 item 으로 검색
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);

                if(spinner1.getSelectedItemPosition() != 0) {
                    searchSongByIndex(view, spinner1, textView);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //검색버튼 리스너
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSong(v, spinner1, editText1, editText2);
            }
        });

        //폰트 사이즈 리스너
        btnFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


    // 찬송가 select로 찾기
    public void searchSongByIndex(View v, Spinner spinner1, TextView textView) {
        String song_title = "";
        song_title = spinner1.getSelectedItem().toString().trim().replaceAll(" ", "");
//        Toast.makeText(SongActivity.this, song_title, Toast.LENGTH_SHORT).show();

        try {
            //raw 폴더 읽기
            InputStream is = getResources().openRawResource(R.raw.new_song);
            BufferedReader br2 = new BufferedReader(new InputStreamReader(is));

            String readStr ="";
            String line = "";

            int checkPoint = 0;

            while((line=br2.readLine())!=null) {
                String tempTitle = line.trim().replaceAll(" ", "");

                if(tempTitle.indexOf(song_title) != -1) {
                    readStr += line + "\n\n";
                    checkPoint = 1;
                } else if(line.indexOf(".") != -1 && checkPoint == 1) {
                    checkPoint = 0;
                    break;
                } else if (checkPoint == 1) {
                    readStr += line + "\n\n";
                }
            }
            br2.close();

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


    // 찬송가 번호, 제목 검색으로 찾기
    // 찾은 제목의 position을 spinner1에 반영
    public void searchSong(View v, Spinner spinner1, EditText editText1, EditText editText2) {
        try {
            ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.song_list, android.R.layout.simple_spinner_item);

            String paramPage = editText1.getText().toString();
            String paramWord = editText2.getText().toString();

            Log.e("paramPage", paramPage);
            Log.e("paramWord", paramWord);

            if(paramPage != "" && paramPage != null && !paramPage.equals("") && !paramPage.equals(null)) {   // 번호가 있으면 번호 우선으로 찾고
                Log.e("번호찾기", adapter.getCount()+"");
                for (int i = 0; i < adapter.getCount(); i++) {
                    String tempTitle = adapter.getItem(i).toString();

                    if (tempTitle.equals(paramPage+".") || tempTitle == paramPage+".") {
                        spinner1.setSelection(i);
                        i = adapter.getCount(); // 첫번째 것만 찾고 종료
                    }
                }
            } else {  // 번호가 없으면 검색어로 찾는다
                Log.e("se", "제목찾기");
                for (int i = 0; i < adapter.getCount(); i++) {
                    String tempTitle = String.valueOf(adapter.getItem(i));
                    if (tempTitle.indexOf(paramWord) != -1) {
                        spinner1.setSelection(i - 1);
                        i = adapter.getCount(); // 첫번째 것만 찾고 종료
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView3);
        scrollView.setScrollY(0);
    }
}
