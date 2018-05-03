package com.example.p048293.biblereader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;

public class CommonActivity extends AppCompatActivity {
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
    }
*/

    public static String toDate() {
        String toDateStr = "";

        int nowYear = 0;    // 올해
        int nowMon = 0;     // 당월
        int nowDate = 0;    // 오늘
        int nowDay = 0;     // 오늘 요일

        // 오늘에 날짜를 세팅 해준다.
        Date date = new Date();
        nowYear = date.getYear() + 1900;
        nowMon = date.getMonth() + 1;
        nowDate = date.getDate();
        nowDay = date.getDay();    // 요일

        toDateStr += nowYear;

        if(nowMon < 10) {
            toDateStr += "0" + nowMon;
        } else {
            toDateStr += nowMon;
        }

        if(nowDate < 10) {
            toDateStr += "0" + nowDate;
        } else {
            toDateStr += nowDate;
        }

        return toDateStr;
    }


    // 읽기기록 저장
    public void saveReadHist(String bookSe, int bookIndex, int page) {

        ArrayAdapter adapter = null;
        if(bookSe.equals("NEW")) {
            adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.bible_new_name_kor_ac, android.R.layout.simple_spinner_item);
        } else {
            adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.bible_old_name_kor_ac, android.R.layout.simple_spinner_item);
        }
        String book = (String)adapter.getItem(bookIndex);
        String strHist = bookSe + ":" + book + ":" +  page;

        try {
            // readHist List 파일 읽기
            File fileList = new File(getFilesDir() + "/readHist" + bookSe + "List.txt");

            // 몇 차수 까지 존재하는지 확인 (한번 읽으면 1차, 두번 읽으면 2차)
            int chaSu = 1;      // 몇차에 저장해야 하는지 확인용
            int chaSuCnt = 0;   // 전체 몇차까지 있는지 확인용

            String line = null;
            BufferedReader br = new BufferedReader(new FileReader(fileList));
            while((line=br.readLine()) != null) {
                chaSuCnt++;
            }
            br.close();

            // 현재의 strHist 내용이 몇차까지 기록되었는지 확인
            for(int i=1; i<=chaSuCnt; i++) {
                line = null;
                BufferedReader br2 = new BufferedReader(new FileReader(getFilesDir() + "/readHist" + bookSe + "_" + i + ".txt"));
                while((line=br2.readLine()) != null) {
                    if( line.indexOf(strHist) != -1 ) {
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

            Toast.makeText(this, chaSu + "차 완독 저장했습니다.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 완독버튼 차수 변경
    public int readChasuCheck(String bookSe, int bookIndex, int page) {
        ArrayAdapter adapter = null;
        if(bookSe.equals("NEW")) {
            adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.bible_new_name_kor_ac, android.R.layout.simple_spinner_item);
        } else {
            adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.bible_old_name_kor_ac, android.R.layout.simple_spinner_item);
        }
        String book = (String)adapter.getItem(bookIndex);

        String strHist = bookSe + ":" + book + ":" +  page;

        // 몇 차수 까지 존재하는지 확인 (한번 읽으면 1차, 두번 읽으면 2차)
        int chaSu = 1;  // 몇차에 저장해야 하는지 확인용
        int chaSuCnt = 0;   // 전체 몇차까지 있는지 확인용

        try {
            File fileList = new File(getFilesDir() + "/readHist" + bookSe + "List.txt");
            if( !fileList.exists() ) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(fileList, true));
                bw.write("");
                bw.close();
            }

            String line = null;
            BufferedReader br = new BufferedReader(new FileReader(fileList));
            while((line=br.readLine())!=null) {
                chaSuCnt++;
            }
            br.close();

            // 현재의 strHist 내용이 몇차까지 기록되었는지 확인
            for(int i=1; i<=chaSuCnt; i++) {
                line = "";
                BufferedReader br2 = new BufferedReader(new FileReader(getFilesDir() + "/readHist" + bookSe + "_" + i + ".txt"));
                while((line=br2.readLine())!=null) {
                    if(line.indexOf(strHist) != -1 ) {
                        chaSu++;
                        break;
                    }
                }
                br2.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return chaSu;
    }
}
