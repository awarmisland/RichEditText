package com.awarmisland.android.richedittext;

import android.content.Context;
import android.graphics.Color;
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
 * 字体大小设置面板
 */
public class FontSizeSelectView extends LinearLayout{

    @BindView(R.id.btn_font_size_small)
    Button btn_font_size_small;
    @BindView(R.id.btn_font_size_normal)
    Button btn_font_size_normal;
    @BindView(R.id.btn_font_size_big)
    Button btn_font_size_big;
    private OnFontSizeChangeListener onFontSizeChangeListener;
    private FontStyle fontStyle;
    public FontSizeSelectView(Context context) {
        super(context);
        initView(context);
    }

    public FontSizeSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FontSizeSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.view_font_size_select,this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_font_size_small)
    protected void btn_font_size_small_onClick(){
        if(onFontSizeChangeListener!=null){
            fontStyle.fontSize=FontStyle.SMALL;
            onFontSizeChangeListener.onFontSizeSelect(FontStyle.SMALL);
            initDefaultView(R.id.btn_font_size_small);
        }
    }
    @OnClick(R.id.btn_font_size_normal)
    protected void btn_font_size_normal_onClick(){
        if(onFontSizeChangeListener!=null){
            fontStyle.fontSize=FontStyle.NORMAL;
            onFontSizeChangeListener.onFontSizeSelect(FontStyle.NORMAL);
            initDefaultView(R.id.btn_font_size_normal);
        }
    }
    @OnClick(R.id.btn_font_size_big)
    protected void btn_font_size_big_onClick(){
        if(onFontSizeChangeListener!=null){
            fontStyle.fontSize=FontStyle.BIG;
            onFontSizeChangeListener.onFontSizeSelect(FontStyle.BIG);
            initDefaultView(R.id.btn_font_size_big);
        }
    }

    /**
     * 初始化view
     * @param fontStyle
     */
    public void initFontStyle(FontStyle fontStyle){
        this.fontStyle = fontStyle;
        int size = fontStyle.fontSize;
        long id=R.id.btn_font_size_normal;
        if(size==FontStyle.SMALL){
            id=R.id.btn_font_size_small;
        }else if(size==FontStyle.BIG){
            id=R.id.btn_font_size_big;
        }
        initDefaultView(id);
    }
    private void initDefaultView(long id){
        btn_font_size_small.setTextColor(Color.BLACK);
        btn_font_size_normal.setTextColor(Color.BLACK);
        btn_font_size_big.setTextColor(Color.BLACK);
        if(R.id.btn_font_size_small==id){
            btn_font_size_small.setTextColor(Color.RED);
        }else if(R.id.btn_font_size_normal==id){
            btn_font_size_normal.setTextColor(Color.RED);
        }else if(R.id.btn_font_size_big==id){
            btn_font_size_big.setTextColor(Color.RED);
        }
    }

    public void setOnFontSizeChangeListener(OnFontSizeChangeListener onFontSizeChangeListener) {
        this.onFontSizeChangeListener = onFontSizeChangeListener;
    }

    public void setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
    }

    public interface OnFontSizeChangeListener{
        void onFontSizeSelect(int size);
    }
}
