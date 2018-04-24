package com.example.p048293.biblereader;

import android.animation.ObjectAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class DispalySettingActivity extends AppCompatActivity {
    String configFile = "/configDisp.txt";

    private String backColor = "-16777216";      // 배경색
    private String fontColor = "-4342339";      // 글자색
    private float fontSize = 20;         // 글자크기
    private int scrollSpeed = 1;        // 스크롤 속도
    int scrollDist = 7139;      // 스크롤 전체길이 7139
    int scrollTime = 145000;    // 스크롤 시간  145000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispaly_setting);

        final TextView textView001 = (TextView)findViewById(R.id.textView001);

        // 배경색 버튼
        final Button back01 = (Button)findViewById(R.id.back01);
        final Button back02 = (Button)findViewById(R.id.back02);
        final Button back03 = (Button)findViewById(R.id.back03);
        final Button back04 = (Button)findViewById(R.id.back04);
        final Button back05 = (Button)findViewById(R.id.back05);
        final Button back06 = (Button)findViewById(R.id.back06);

        // 글자색 버튼
        final Button font01 = (Button)findViewById(R.id.font01);
        final Button font02 = (Button)findViewById(R.id.font02);
        final Button font03 = (Button)findViewById(R.id.font03);
        final Button font04 = (Button)findViewById(R.id.font04);
        final Button font05 = (Button)findViewById(R.id.font05);
        final Button font06 = (Button)findViewById(R.id.font06);

        // 글자크기 버튼
        final Button btnFontSizeUp = (Button)findViewById(R.id.btnFontSizeUp);
        final Button btnFontSizeDown = (Button)findViewById(R.id.btnFontSizeDown);
        final TextView fontText = (TextView)findViewById(R.id.fontText);

        // 자동스크롤 버튼
        final Button btnScrollStart = (Button)findViewById(R.id.btnScrollStart);
        final Button btnScrollUp = (Button)findViewById(R.id.btnScrollUp);
        final Button btnScrollDown = (Button)findViewById(R.id.btnScrollDown);
        final Button btnScrollStop = (Button)findViewById(R.id.btnScrollStop);
        final TextView scrollText = (TextView)findViewById(R.id.scrollText);

        // 설정 저장
        final Button btnSaveConfig = (Button)findViewById(R.id.btnSaveConfig);

        final ScrollView scrollView3 = (ScrollView)findViewById(R.id.scrollView3);
        final ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollView3, "scrollY", 0);

        // 현재 폰트사이즈 출력 (PX -> DP로 변환)
        float density = getResources().getDisplayMetrics().density;
        int fontPx = (int)(textView001.getTextSize() / density);
        fontText.setText(fontPx + "");

        // (DP -> PX로 변환)
        //Resources r = getResources();
        //int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Integer.parseInt(fontText.getText().toString()), r.getDisplayMetrics());

        readConfig(textView001, fontText, scrollText);

        readBook(textView001);

        // ========== 이벤트 시작 =============== //

        back01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable colorDrawable = (ColorDrawable) back01.getBackground();
                int colorCode = colorDrawable.getColor();
                textView001.setBackgroundDrawable(colorDrawable);
                backColor = colorCode + "";
            }
        });

        back02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable colorDrawable = (ColorDrawable) back02.getBackground();
                int colorCode = colorDrawable.getColor();
                textView001.setBackgroundDrawable(colorDrawable);
                backColor = colorCode + "";
            }
        });

        back03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable colorDrawable = (ColorDrawable) back03.getBackground();
                int colorCode = colorDrawable.getColor();
                textView001.setBackgroundDrawable(colorDrawable);
                backColor = colorCode + "";
            }
        });

        back04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable colorDrawable = (ColorDrawable) back04.getBackground();
                int colorCode = colorDrawable.getColor();
                textView001.setBackgroundDrawable(colorDrawable);
                backColor = colorCode + "";
            }
        });

        back05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable colorDrawable = (ColorDrawable) back05.getBackground();
                int colorCode = colorDrawable.getColor();
                textView001.setBackgroundDrawable(colorDrawable);
                backColor = colorCode + "";
            }
        });

        back06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable colorDrawable = (ColorDrawable) back06.getBackground();
                int colorCode = colorDrawable.getColor();
                textView001.setBackgroundDrawable(colorDrawable);
                backColor = colorCode + "";
            }
        });

        font01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable colorDrawable = (ColorDrawable) font01.getBackground();
                int colorCode = colorDrawable.getColor();
                textView001.setTextColor(colorCode);
                fontColor = colorCode + "";
            }
        });

        font02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable colorDrawable = (ColorDrawable) font02.getBackground();
                int colorCode = colorDrawable.getColor();
                textView001.setTextColor(colorCode);
                fontColor = colorCode + "";
            }
        });

        font03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable colorDrawable = (ColorDrawable) font03.getBackground();
                int colorCode = colorDrawable.getColor();
                textView001.setTextColor(colorCode);
                fontColor = colorCode + "";
            }
        });

        font04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable colorDrawable = (ColorDrawable) font04.getBackground();
                int colorCode = colorDrawable.getColor();
                textView001.setTextColor(colorCode);
                fontColor = colorCode + "";
            }
        });

        font05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable colorDrawable = (ColorDrawable) font05.getBackground();
                int colorCode = colorDrawable.getColor();
                textView001.setTextColor(colorCode);
                fontColor = colorCode + "";
            }
        });

        font06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDrawable colorDrawable = (ColorDrawable) font06.getBackground();
                int colorCode = colorDrawable.getColor();
                textView001.setTextColor(colorCode);
                fontColor = colorCode + "";
            }
        });

        btnFontSizeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 폰트사이즈 출력 (PX -> DP로 변환)
                float density = getResources().getDisplayMetrics().density;
                int fontDp = (int)(textView001.getTextSize() / density);

                fontSize = fontDp + 5;

                if(fontSize > 100) {
                    fontSize = 100;
                    Toast.makeText(DispalySettingActivity.this, "최대 크기입니다.", Toast.LENGTH_SHORT).show();
                }

                fontText.setText(fontSize + "");
                //textView001.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                textView001.setTextSize(fontSize);
            }
        });

        btnFontSizeUp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // 현재 폰트사이즈 출력 (PX -> DP로 변환)
                float density = getResources().getDisplayMetrics().density;
                int fontDp = (int)(textView001.getTextSize() / density);

                fontSize = fontDp + 5;

                if(fontSize > 100) {
                    fontSize = 100;
                    Toast.makeText(DispalySettingActivity.this, "최대 크기입니다.", Toast.LENGTH_SHORT).show();
                    return true;
                }

                fontText.setText(fontSize + "");
                //textView001.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                textView001.setTextSize(fontSize);

                return false;   // false : 다음 이벤트 계속진행, true : 이벤트 중지
            }
        });

        btnFontSizeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 폰트사이즈 출력 (PX -> DP로 변환)
                float density = getResources().getDisplayMetrics().density;
                int fontDp = (int)(textView001.getTextSize() / density);

                fontSize = fontDp - 5;

                if(fontSize < 20) {
                    fontSize = 20;
                    Toast.makeText(DispalySettingActivity.this, "최소 크기입니다.", Toast.LENGTH_SHORT).show();
                }

                fontText.setText(fontSize + "");
                //textView001.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                textView001.setTextSize(fontSize);
            }
        });

        btnFontSizeDown.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // 현재 폰트사이즈 출력 (PX -> DP로 변환)
                float density = getResources().getDisplayMetrics().density;
                int fontDp = (int)(textView001.getTextSize() / density);

                fontSize = fontDp - 5;

                if(fontSize < 20) {
                    fontSize = 20;
                    Toast.makeText(DispalySettingActivity.this, "최소 크기입니다.", Toast.LENGTH_SHORT).show();
                    return true;
                }

                fontText.setText(fontSize + "");
                //textView001.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                textView001.setTextSize(fontSize);

                return false;   // false : 다음 이벤트 계속진행, true : 이벤트 중지
            }
        });

        // 자동 스크롤 시작
        btnScrollStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 현재 내용 전체길이
                scrollDist = textView001.getMeasuredHeight();
                scrollTime = 7139 / textView001.getMeasuredHeight() * 145000;   // 최초 기준값 - 길이:3719, 시간:145000
                //Toast.makeText(DispalySettingActivity.this, textView001.getMeasuredHeight() + " : " + textView001.getBottom() + " : " + textView001.getHeight(), Toast.LENGTH_LONG).show();

                scrollText.setText(scrollSpeed + "");

                objectAnimator.setIntValues(scrollDist); // 전체수행길이
                objectAnimator.setDuration(scrollTime * scrollSpeed);    // 전체수행시간
                objectAnimator.setInterpolator(new LinearInterpolator());   // 일정속도로
                objectAnimator.start();
            }
        });

        btnScrollUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objectAnimator.cancel();

                scrollDist = scrollDist - textView001.getBottom();

                scrollSpeed--;
                if(scrollSpeed == 0 ) {
                    scrollSpeed = 1;
                    Toast.makeText(DispalySettingActivity.this, "최고 속도입니다.", Toast.LENGTH_SHORT).show();
                }

                objectAnimator.setIntValues(scrollDist); // 전체수행길이
                objectAnimator.setDuration(scrollTime);    // 전체수행시간

                btnScrollStart.callOnClick();
            }
        });

        btnScrollDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objectAnimator.cancel();

                scrollDist = scrollDist - textView001.getBottom();
                scrollSpeed++;
                if(scrollSpeed > 5 ) {
                    scrollSpeed = 5;
                    Toast.makeText(DispalySettingActivity.this, "최저 속도입니다.", Toast.LENGTH_SHORT).show();
                }

                objectAnimator.setIntValues(scrollDist); // 전체수행길이
                objectAnimator.setDuration(scrollTime);    // 전체수행시간

                btnScrollStart.callOnClick();
            }
        });

        // 자동 스크롤 중지
        btnScrollStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectAnimator.cancel();
            }
        });

        // 내용 저장
        btnSaveConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectAnimator.cancel();

                try {
                    File file = new File(getFilesDir() + configFile);

                    if(file.exists()) {
                        file.delete();
                    }

                    BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
                    bw.write("backColor" + ":" + backColor + "\n");
                    bw.write("fontColor" + ":" + fontColor + "\n");
                    bw.write("fontSize" + ":" + fontSize + "\n");
                    bw.write("scrollSpeed" + ":" + scrollSpeed + "\n");

                    bw.close();

                    Toast.makeText(DispalySettingActivity.this, "화면 설정을 저장하였습니다.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //config 파일 읽어서 반영
    public void readConfig(TextView textView, TextView fontText, TextView scrollText) {
        File file = new File(getFilesDir() + configFile);

        if( !file.exists()) {
            return;
        }

        try {
            String line = "";
            String tempStr = "";

            BufferedReader br = new BufferedReader(new FileReader(file));
            while((line=br.readLine())!=null) {
                //Toast.makeText(OldTestamentActivity.this, "2 : " + line, Toast.LENGTH_SHORT).show();

                tempStr += line + "\n";

                String dispConfig[] = line.split(":");

                if(dispConfig.length == 2 && dispConfig[0].equals("backColor") && dispConfig[1] != null) {
                    backColor = dispConfig[1];
                } else if(dispConfig.length == 2 && dispConfig[0].equals("fontColor") && dispConfig[1] != null) {
                    fontColor = dispConfig[1];
                } else if(dispConfig.length == 2 && dispConfig[0].equals("fontSize") && dispConfig[1] != null) {
                    fontSize = Float.parseFloat(dispConfig[1]);
                    //textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontSize);
                    fontText.setText(fontSize + "");
                } else if(dispConfig.length == 2 && dispConfig[0].equals("scrollSpeed") && dispConfig[1] != null) {
                    scrollSpeed = Integer.parseInt(dispConfig[1]);
                }

                textView.setBackgroundDrawable(new ColorDrawable(Integer.parseInt(backColor)));
                textView.setTextColor(Integer.parseInt(fontColor));
                textView.setTextSize(fontSize);
                scrollText.setText(scrollSpeed + "");
            }
            br.close();

            textView.setText(tempStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 성경찾기
    public void readBook(TextView textView) {
        // last page item
        ArrayAdapter adapter_ac_name = ArrayAdapter.createFromResource (this, R.array.bible_old_name_kor_ac, android.R.layout.simple_spinner_item);

        String book = "창";
        String index = "1";

        try {
            //raw 폴더 읽기
            InputStream is = getResources().openRawResource(R.raw.bible_old);
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

}
