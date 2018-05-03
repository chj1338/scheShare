package com.example.p048293.biblereader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView textView = (TextView)findViewById(R.id.textView);

        try {
            String line = "";
            String helpStr = "";

            InputStream is_help = getResources().openRawResource(R.raw.help);
            BufferedReader br_help = new BufferedReader(new InputStreamReader(is_help));

            while ((line = br_help.readLine()) != null) {
                helpStr += line + "\n";
            }
            br_help.close();

            helpStr += "\n\n\n";

            textView.setText(helpStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
