package com.awarmisland.android.richedittext;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 样式设置模板
 */
public class FontSytlePanel extends LinearLayout {
    @BindView(R.id.btn_bold)
    Button btn_bold;
    @BindView(R.id.btn_italic)
    Button btn_italic;
    @BindView(R.id.btn_underline)
    Button btn_underline;
    @BindView(R.id.btn_streak)
    Button btn_streak;

    private Context mContext;
    private FontStyle fontStyle;
    private OnFontSelectListener onFontSelectListener;

    public FontSytlePanel(Context context) {
        super(context);
        initView(context);
    }

    public FontSytlePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FontSytlePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context mContext){
        this.mContext=mContext;
        LayoutInflater.from(mContext).inflate(R.layout.view_font_style_panel,this);
        ButterKnife.bind(this);
        initFontStyle(new FontStyle());
    }

    @OnClick(R.id.btn_bold)
    protected void btn_bold_click(View view){
        setFontStyle(view);
    }
    @OnClick(R.id.btn_italic)
    protected void btn_italic_click(View view){
        setFontStyle(view);
    }
    @OnClick(R.id.btn_underline)
    protected void btn_underline_click(View view){
        setFontStyle(view);
    }
    @OnClick(R.id.btn_streak)
    protected void btn_streak_click(View view){
        setFontStyle(view);
    }
    private void setFontStyle(View view){
        Button button = (Button) view;
        button.setTextColor(Color.BLACK);
        boolean flag= false;
        if(onFontSelectListener!=null){
            switch (view.getId()){
                case R.id.btn_bold:
                    fontStyle.isBold=!fontStyle.isBold;
                    flag=fontStyle.isBold;
                    onFontSelectListener.setBold(fontStyle.isBold);
                    break;
                case R.id.btn_italic:
                    fontStyle.isItalic=!fontStyle.isItalic;
                    flag=fontStyle.isItalic;
                    onFontSelectListener.setItalic(fontStyle.isItalic);
                    break;
                case R.id.btn_underline:
                    fontStyle.isUnderline=!fontStyle.isUnderline;
                    flag=fontStyle.isUnderline;
                    onFontSelectListener.setUnderline(fontStyle.isUnderline);
                    break;
                case R.id.btn_streak:
                    fontStyle.isStreak=!fontStyle.isStreak;
                    flag=fontStyle.isStreak;
                    onFontSelectListener.setStreak(fontStyle.isStreak);
                    break;
            }
            if(flag){
                button.setTextColor(Color.RED);
            }
        }
    }
    public void initFontStyle(FontStyle fontStyle){
        this.fontStyle = fontStyle;
        initDefaultStyle();
        if(fontStyle.isBold){
            btn_bold.setTextColor(Color.RED);
        }
        if(fontStyle.isItalic){
            btn_italic.setTextColor(Color.RED);
        }
        if(fontStyle.isUnderline){
            btn_underline.setTextColor(Color.RED);
        }
        if(fontStyle.isStreak){
            btn_streak.setTextColor(Color.RED);
        }
    }
    private void initDefaultStyle(){
        btn_bold.setTextColor(Color.BLACK);
        btn_italic.setTextColor(Color.BLACK);
        btn_underline.setTextColor(Color.BLACK);
        btn_streak.setTextColor(Color.BLACK);
    }

    public void setOnFontSelectListener(OnFontSelectListener onFontSelectListener) {
        this.onFontSelectListener = onFontSelectListener;
    }

    public interface OnFontSelectListener{
        void setBold(boolean isBold);
        void setItalic(boolean isItalic);
        void setUnderline(boolean isUnderline);
        void setStreak(boolean isStreak);
    }
}
