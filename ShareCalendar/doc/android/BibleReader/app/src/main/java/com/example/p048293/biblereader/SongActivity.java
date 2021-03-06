package com.example.p048293.biblereader;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SongActivity extends AppCompatActivity {
    InputMethodManager imm = null;

    private String backColor = "-16777216";      // 배경색
    private String fontColor = "-4342339";      // 글자색
    private float fontSize = 20;         // 글자크기
    private int scrollSpeed = 1;        // 스크롤 속도
    int scrollDist = 7139;      // 스크롤 전체길이 7139
    int scrollTime = 145000;    // 스크롤 시간  145000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        // 화면꺼짐 방지
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // 액션바 타이틀 관련
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_title_bar);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        final TextView textView = (TextView)findViewById(R.id.textView001);
        final Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);    // 장.제목 select
        final Button btnFontSize = (Button)findViewById(R.id.btnFontSize);
        final Button btnSearch = (Button)findViewById(R.id.btnSearch);
        final EditText editText1 = (EditText)findViewById(R.id.editText1);
        final EditText editText2 = (EditText)findViewById(R.id.editText2);

        // 책 combo item 셋팅
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.song_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item_left);
        spinner1.setAdapter(adapter);

        readConfig(textView);

        // spinner1 리스너
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 찬송가 리스트 item 으로 검색
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imm.hideSoftInputFromWindow(editText1.getWindowToken(), 0);  // 키보드 숨기기

                if(spinner1.getSelectedItemPosition() != 0) {
                    editText1.setText("");
                    editText2.setText("");
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
                imm.hideSoftInputFromWindow(editText1.getWindowToken(), 0);  // 키보드 숨기기
                searchSong(v, spinner1, editText1, editText2);
            }
        });

        //폰트 사이즈 리스너
        btnFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(editText1.getWindowToken(), 0);  // 키보드 숨기기
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
                    readStr += line + "\n";
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

            if(paramPage != "" && paramPage != null && !paramPage.equals("") && !paramPage.equals(null)) {   // 번호가 있으면 번호 우선으로 찾고
                editText2.setText("");

                for (int i = 0; i < adapter.getCount(); i++) {
                    String tempTitle = adapter.getItem(i).toString();

                    if (tempTitle.indexOf(paramPage + ".") != -1) {
                        spinner1.setSelection(i);
                        i = adapter.getCount(); // 첫번째 것만 찾고 종료
                    }
                }
            } else if(paramWord != "" && paramWord != null && !paramWord.equals("") && !paramWord.equals(null)) {  // 번호가 없으면 검색어로 찾는다
                for (int i = 0; i < adapter.getCount(); i++) {
                    String tempTitle = String.valueOf(adapter.getItem(i));
                    if (tempTitle.indexOf(paramWord) != -1) {
                        spinner1.setSelection(i);
                        i = adapter.getCount(); // 첫번째 것만 찾고 종료
                    }
                }
            } else {
                Toast.makeText(SongActivity.this, "번호나 제목 한가지로 검색이 가능합니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView3);
        scrollView.setScrollY(0);
    }

    // 설정메뉴로 이동
    public void btnGoSettingMenu(View v) {
        Intent intent006 = new Intent(this, SettingMenuActivity.class);
        startActivity(intent006);
    }
}
