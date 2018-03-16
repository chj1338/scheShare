package com.example.p048293.biblereader;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OldTestamentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_testament);

        final TextView textView = (TextView)findViewById(R.id.textView001);
        final Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);    // 책 select
        final Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);    // 장 select
        final Button btnFontSize = (Button)findViewById(R.id.btnFontSize);
        final Button btnPrePage = (Button)findViewById(R.id.btnPrePage);
        final Button btnNextPage = (Button)findViewById(R.id.btnNextPage);

        // 책 combo item 셋팅
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.bible_old_name_kor, android.R.layout.simple_spinner_item);
        spinner1.setAdapter(adapter);

        // 장 combo item 셋팅
        setLastPage(spinner1, spinner2);

        // spinner1 리스너
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 장 combo item 셋팅
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);

                setLastPage(spinner1, spinner2);
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

                if(fontSize > 50) {
                    fontSize = 30;
                } else {
                    fontSize = fontSize + 5;
                }

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
                    Toast.makeText(OldTestamentActivity.this, "맨 처음 입니다.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(OldTestamentActivity.this, "마지막 입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // 장 combo item 셋팅
    public void setLastPage(Spinner spinner1, Spinner spinner2) {
        // last page item
        ArrayAdapter adapter_page = ArrayAdapter.createFromResource (this, R.array.bible_old_page, android.R.layout.simple_spinner_item);

        int sp1Position = spinner1.getSelectedItemPosition();
        int lastPage = Integer.parseInt((String)adapter_page.getItem(sp1Position));

        ArrayList<String> item2 = new ArrayList<>();
        for(int i=1; i<=lastPage; i++) {
            item2.add(i+"");
        }

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
    }


    // book index 조합으로 찾기
    public void readBook(View v, Spinner spinner1, Spinner spinner2, TextView textView) {
        // last page item
        ArrayAdapter adapter_ac_name = ArrayAdapter.createFromResource (this, R.array.bible_old_name_kor_ac, android.R.layout.simple_spinner_item);
        int sp1Position = spinner1.getSelectedItemPosition();

        String book = (String)adapter_ac_name.getItem(sp1Position);
        String index = (String)spinner2.getSelectedItem();

        try {
            //raw 폴더 읽기
            InputStream is = getResources().openRawResource(R.raw.bible_old);
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

            textView.setText(readStr);
            br2.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            textView.setText("파일을 찾을 수 없습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView3);
        scrollView.setScrollY(0);
    }
}
