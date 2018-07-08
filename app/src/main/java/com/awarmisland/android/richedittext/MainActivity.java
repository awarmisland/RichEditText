package com.awarmisland.android.richedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FontSytlePanel.OnFontSelectListener
,RichEditText.OnSelectChangeListener{
    @BindView(R.id.richEditText)
    RichEditText richEditText;
    @BindView(R.id.fontStylePanel)
    FontSytlePanel fontSytlePanel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fontSytlePanel.setOnFontSelectListener(this);
        richEditText.setOnSelectChangeListener(this);
    }



    @Override
    public void setBold(boolean isBold) {
        richEditText.setBold(isBold);
    }

    @Override
    public void setItalic(boolean isItalic) {
        richEditText.setItalic(isItalic);
    }

    @Override
    public void setUnderline(boolean isUnderline) {
        richEditText.setUnderline(isUnderline);
    }

    @Override
    public void setStreak(boolean isStreak) {
        richEditText.setStreak(isStreak);
    }

    /**
     * 样式改变
     * @param fontStyle
     */
    @Override
    public void onFontStyleChang(FontStyle fontStyle) {
        fontSytlePanel.initFontStyle(fontStyle);
    }

    /**
     * 光标选中监听
     * @param start
     * @param end
     */
    @Override
    public void onSelect(int start, int end) {

    }
}
