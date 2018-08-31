package com.awarmisland.android.richedittext;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 样式设置模板
 */
public class FontSytlePanel extends LinearLayout
implements FontStyleSelectView.OnFontStyleSelectListener,FontSizeSelectView.OnSizeSelectListener
,FontsColorSelectView.OnColorSelectListener{
    @BindView(R.id.fontStyleSelectView)
    FontStyleSelectView fontStyleSelectView;
    //字体大小
    @BindView(R.id.fontSizeSelectView)
    FontSizeSelectView fontSizeSelectView;
    //字体颜色
    @BindView(R.id.fontColorSelectView)
    FontsColorSelectView fontColorSelectView;

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
        fontStyleSelectView.setFontStyle(fontStyle);
        fontStyleSelectView.setOnFontStyleSelectListener(this);
        fontSizeSelectView.setOnSizeSelectListener(this);
        fontColorSelectView.setFontStyle(fontStyle);
        fontColorSelectView.setOnColorSelectListener(this);
        initFontStyle(new FontStyle());
    }
    //图片选择
    @OnClick(R.id.btn_img)
    protected void btn_img(View view){ onFontSelectListener.insertImg(); }

    @Override
    public void setBold(boolean isBold) {
        onFontSelectListener.setBold(isBold);
    }
    @Override
    public void setItalic(boolean isItalic) {
        onFontSelectListener.setItalic(isItalic);
    }
    @Override
    public void setUnderline(boolean isUnderline) {
        onFontSelectListener.setUnderline(isUnderline);
    }
    @Override
    public void setStreak(boolean isStreak) {
        onFontSelectListener.setStreak(isStreak);
    }
    //字体大小选择
    @Override
    public void onSizeSelect(int size) {
        onFontSelectListener.setFontSize(size);
    }
    //颜色选择
    @Override
    public void onColorSelect(int color) {
        onFontSelectListener.setFontColor(color);
    }

    public void initFontStyle(FontStyle fontStyle){
        this.fontStyle = fontStyle;
        fontStyleSelectView.initFontStyle(fontStyle);
        fontSizeSelectView.setFontSizeStatus(fontStyle.fontSize);
        fontColorSelectView.initFontStyle(fontStyle);
    }


    public void setOnFontSelectListener(OnFontSelectListener onFontSelectListener) {
        this.onFontSelectListener = onFontSelectListener;
    }

    public interface OnFontSelectListener{
        void setBold(boolean isBold);
        void setItalic(boolean isItalic);
        void setUnderline(boolean isUnderline);
        void setStreak(boolean isStreak);
        void insertImg();
        void setFontSize(int size);
        void setFontColor(int color);
    }
}
