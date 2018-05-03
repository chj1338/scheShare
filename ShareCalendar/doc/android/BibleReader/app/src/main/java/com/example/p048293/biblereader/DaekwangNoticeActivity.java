package com.example.p048293.biblereader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

public class DaekwangNoticeActivity extends AppCompatActivity implements TextView.OnEditorActionListener  {
    InputMethodManager imm;
    private EditText txtURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daekwang_notice);

        txtURL = (EditText)findViewById(R.id.txtURL);
        WebView webView = (WebView)findViewById(R.id.webView);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        txtURL.setOnEditorActionListener(this); //mEditText와 onEditorActionListener를 연결
        imm.hideSoftInputFromWindow(txtURL.getWindowToken(), 0);  // 키보드 숨기기

        txtURL.clearFocus();
        goURL();

        webView.scrollTo(478, 557);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //오버라이드한 onEditorAction() 메소드
        if(v.getId() == R.id.txtURL && actionId == EditorInfo.IME_ACTION_DONE){ // 뷰의 id를 식별, 키보드의 완료 키 입력 검출
            goURL();
        }
        return false;
    }

    public void goURL(){
        TextView tvURL = (TextView)findViewById(R.id.txtURL);
        String url = tvURL.getText().toString();
        //Log.i("URL", "Opening URL with WebView :" + url);

        final long startTime = System.currentTimeMillis();
        WebView webView = (WebView)findViewById(R.id.webView);

        // 하드웨어 가속
        // 캐쉬 끄기
        //webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                long elapsedTime = System.currentTimeMillis()-startTime;
                TextView tvSec = (TextView) findViewById(R.id.tvSec);
                tvSec.setText(String.valueOf(elapsedTime));
            }
        });
        webView.loadUrl(url);

    }
}