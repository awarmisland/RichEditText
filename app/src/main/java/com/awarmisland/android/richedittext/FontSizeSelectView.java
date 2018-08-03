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
    private OnSizeSelectListener onSizeSelectListener;

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
        btn_font_size_small.setTextColor(Color.BLACK);
        btn_font_size_normal.setTextColor(Color.RED);
        btn_font_size_big.setTextColor(Color.BLACK);
    }

    @OnClick({R.id.btn_font_size_small,R.id.btn_font_size_normal,R.id.btn_font_size_big})
    protected void btn_on_click(View view){
        int id = view.getId();
        btn_font_size_small.setTextColor(Color.BLACK);
        btn_font_size_normal.setTextColor(Color.BLACK);
        btn_font_size_big.setTextColor(Color.BLACK);
        int size=FontStyle.NORMAL;
        switch (id){
            case R.id.btn_font_size_small:
                btn_font_size_small.setTextColor(Color.RED);
                size=FontStyle.SMALL;
                break;
            case R.id.btn_font_size_normal:
                btn_font_size_normal.setTextColor(Color.RED);
                size=FontStyle.NORMAL;
                break;
            case R.id.btn_font_size_big:
                btn_font_size_big.setTextColor(Color.RED);
                size=FontStyle.BIG;
                break;
        }
        if(onSizeSelectListener!=null){
            onSizeSelectListener.onSizeSelect(size);
        }
    }

    /**
     * 初始化view
     * @param size
     */
    public void setFontSizeStatus(int size){
        if(size==0){size=FontStyle.NORMAL;}
        btn_font_size_small.setTextColor(Color.BLACK);
        btn_font_size_normal.setTextColor(Color.BLACK);
        btn_font_size_big.setTextColor(Color.BLACK);
        switch (size){
            case FontStyle.SMALL:
                btn_font_size_small.setTextColor(Color.RED);
                break;
            case FontStyle.NORMAL:
                btn_font_size_normal.setTextColor(Color.RED);
                break;
            case FontStyle.BIG:
                btn_font_size_big.setTextColor(Color.RED);
                break;
        }
    }
    public void setOnSizeSelectListener(OnSizeSelectListener onSizeSelectListener) {
        this.onSizeSelectListener = onSizeSelectListener;
    }

    public interface OnSizeSelectListener{
        void onSizeSelect(int size);
    }
}
