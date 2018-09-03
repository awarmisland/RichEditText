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
 * ljz
 * 字样式板
 */
public class FontStylePanel extends LinearLayout {
    private Context mContext;
    private OnFontPanelListener onFontPanelListener;

    @BindView(R.id.btn_img)
    Button btn_img;
    //字体 加粗 斜杠 删除线 下划线 设置
    @BindView(R.id.fontStyleSelectView)
    FontStyleSelectView cusView_fontStyle;
    //字体 大小
    @BindView(R.id.fontSizeSelectView)
    FontSizeSelectView cusView_fontSize;
    //字体颜色
    @BindView(R.id.fontColorSelectView)
    FontsColorSelectView cusView_fontColor;

    private FontStyle fontStyle;
    public FontStylePanel(Context context) {
        super(context);
        initView(context);
    }

    public FontStylePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FontStylePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_font_style_panel,this);
        ButterKnife.bind(this);
        fontStyle = new FontStyle();
        //字体样式
        cusView_fontStyle.setOnFontStyleSelectListener(onFontStyleSelectListener);
        cusView_fontStyle.setFontStyle(fontStyle);
        //字体大小
        cusView_fontSize.setOnFontSizeChangeListener(onFontSizeChangeListener);
        cusView_fontSize.setFontStyle(fontStyle);
        //字体颜色
        cusView_fontColor.setOnColorSelectListener(onColorSelectListener);
        cusView_fontColor.setFontStyle(fontStyle);
    }

    //拍照
    @OnClick(R.id.btn_img)
    protected void btn_img_onClick(){
        if(onFontPanelListener!=null) {
            onFontPanelListener.insertImg();
        }
    }

    /**
     * 字体样式 设置
     */
    private FontStyleSelectView.OnFontStyleSelectListener onFontStyleSelectListener = new FontStyleSelectView.OnFontStyleSelectListener() {
        @Override
        public void setBold(boolean isBold) {
            if(onFontPanelListener!=null) {
                onFontPanelListener.setBold(isBold);
            }
        }
        @Override
        public void setItalic(boolean isItalic) {
            if(onFontPanelListener!=null) {
                onFontPanelListener.setItalic(isItalic);
            }
        }
        @Override
        public void setUnderline(boolean isUnderline) {
            if(onFontPanelListener!=null) {
                onFontPanelListener.setUnderline(isUnderline);
            }
        }
        @Override
        public void setStreak(boolean isStreak) {
            if(onFontPanelListener!=null) {
                onFontPanelListener.setStreak(isStreak);
            }
        }
    };

    /**
     * 字体 大小
     */
    private FontSizeSelectView.OnFontSizeChangeListener onFontSizeChangeListener = new FontSizeSelectView.OnFontSizeChangeListener() {
        @Override
        public void onFontSizeSelect(int size) {
            if(onFontPanelListener!=null){
                onFontPanelListener.setFontSize(size);
            }
        }
    };
    /**
     * 字体 颜色
     */
    private FontsColorSelectView.OnColorSelectListener onColorSelectListener = new FontsColorSelectView.OnColorSelectListener() {
        @Override
        public void onColorSelect(int color) {
            if(onFontPanelListener!=null){
                onFontPanelListener.setFontColor(color);
            }
        }
    };

    public interface OnFontPanelListener{
        void setBold(boolean isBold);
        void setItalic(boolean isItalic);
        void setUnderline(boolean isUnderline);
        void setStreak(boolean isStreak);
        void insertImg();
        void setFontSize(int size);
        void setFontColor(int color);
    }


    public void initFontStyle(FontStyle fontStyle){
        this.fontStyle =fontStyle;
        cusView_fontStyle.initFontStyle(fontStyle);
        cusView_fontSize.initFontStyle(fontStyle);
        cusView_fontColor.initFontStyle(fontStyle);
    }

    public void setOnFontPanelListener(OnFontPanelListener onFontPanelListener) {
        this.onFontPanelListener = onFontPanelListener;
    }
}
