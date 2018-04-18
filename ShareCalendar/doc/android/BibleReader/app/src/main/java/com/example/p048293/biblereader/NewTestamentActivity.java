package com.example.p048293.biblereader;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
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
    String configFile = "configNew.txt";

    int nowPage = 0;    // spinner2 변경용

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

        // 책 combo item 셋팅
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.bible_new_name_kor, android.R.layout.simple_spinner_item);
        spinner1.setAdapter(adapter);

        // 장 combo item 셋팅
        setLastPage(spinner1, spinner2);

        //config 파일을 읽어서 이전 정보 반영
        readConfig(spinner1, spinner2, textView);

        // spinner1 리스너
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 장 combo item 셋팅
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);

                setLastPage(spinner1, spinner2);
                nowPage = 0;
                readBook(view, spinner1, spinner2, textView);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // spinner2 리스너
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(NewTestamentActivity.this, "현재 : " + spinner2.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
                readBook(view, spinner1, spinner2, textView);
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
                int position = spinner2.getSelectedItemPosition();

                if(position > 0) {
                    position--;
                    spinner2.setSelection(position);
                    readBook(v, spinner1, spinner2, textView);
                } else {
                    Toast.makeText(NewTestamentActivity.this, "맨 처음 입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //다음 페이지
        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = spinner2.getSelectedItemPosition();
                int lastPosition = spinner2.getCount() - 1;

                if(position < lastPosition) {
                    position++;
                    spinner2.setSelection(position);
                    readBook(v, spinner1, spinner2, textView);
                } else {
                    Toast.makeText(NewTestamentActivity.this, "마지막 입니다.", Toast.LENGTH_SHORT).show();
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
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
                String rowData[] = line.split(" ");
                String colData[] = rowData[0].split(":");

                if(colData[0].equals(book + index)) {
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

            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, Float.parseFloat(newConfig2[3]));
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
