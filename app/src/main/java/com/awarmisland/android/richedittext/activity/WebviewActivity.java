package com.awarmisland.android.richedittext.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.awarmisland.android.richedittext.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * create by awarmisland
 */
public class WebviewActivity extends Activity {
    @BindView(R.id.webView)
    WebView webview;
    @BindView(R.id.btn_right)
    Button btn_right;
    @BindView(R.id.btn_back)
    Button btn_back;
    String html_content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        btn_right.setText("html转span");
        btn_back.setText("返回");
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(false);
        html_content = getIntent().getStringExtra("content");
        webview.loadDataWithBaseURL("",html_content,"text/html","UTF-8",null);
    }


    @OnClick(R.id.btn_back)
    protected void btn_back_onClick() {
        finish();
    }

    @OnClick(R.id.btn_right)
    protected void btn_right_onClick(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("html_content",html_content);
        startActivity(intent);
    }
}
