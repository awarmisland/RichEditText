package com.awarmisland.android.richedittext;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.CharacterStyle;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * RichEditText 富文本
 */
public class RichEditText extends AppCompatEditText implements View.OnClickListener{
    private Context mContext;
    public static final int EXCLUD_MODE= Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
    public static final int EXCLUD_INCLUD_MODE= Spannable.SPAN_EXCLUSIVE_INCLUSIVE;
    public static final int INCLUD_INCLUD_MODE= Spannable.SPAN_INCLUSIVE_INCLUSIVE;
    private OnSelectChangeListener onSelectChangeListener;
    public RichEditText(Context context) {
        super(context);
        initView(context);
    }

    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context){
        mContext = context;
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int style_start =getSelectionStart();
        int start = getSelectionStart()-1;
        if(start<0){
            start=0;
        }
        FontStyle fontStyle = getFontStyle(start,start);
        setBold(fontStyle.isBold);
        setItalic(fontStyle.isItalic);
        setUnderline(fontStyle.isUnderline);
        setStreak(fontStyle.isStreak);
        setFontSize(fontStyle.fontSize);
        if(onSelectChangeListener!=null){
            onSelectChangeListener.onSelect(start,start);
            onSelectChangeListener.onFontStyleChang(fontStyle);
        }
    }

    /**
     * public setting
     */
    public void setBold(boolean isBold){ setStyleSpan(isBold,Typeface.BOLD); }
    public void setItalic(boolean isItalic){ setStyleSpan(isItalic,Typeface.ITALIC); }
    public void setUnderline(boolean isUnderline){ setUnderlineSpan(isUnderline); }
    public void setStreak(boolean isStreak){ setStreakSpan(isStreak); }
    public void setFontSize(int size){ setFontSizeSpan(size); }
    public void setImg(String path){
        if(!TextUtils.isEmpty(path)) {
            ImagePlate plate = new ImagePlate(this, mContext);
            plate.image(path);
        }
    }
    /**
     * bold italic
     * @param isSet
     * @param type
     */
    private void setStyleSpan(boolean isSet,int type){
        FontStyle fontStyle = new FontStyle();
        if(type==Typeface.BOLD){
            fontStyle.isBold=true;
        }else if(type==Typeface.ITALIC){
            fontStyle.isItalic=true;
        }
        setSpan(fontStyle,isSet,StyleSpan.class);
    }

    /**
     * underline
     * @param isSet
     */
    private void setUnderlineSpan(boolean isSet){
        FontStyle fontStyle = new FontStyle();
        fontStyle.isUnderline=true;
        setSpan(fontStyle,isSet,UnderlineSpan.class);
    }

    /**
     * Strikethrough
     * @param isSet
     */
    private void setStreakSpan(boolean isSet){
        FontStyle fontStyle = new FontStyle();
        fontStyle.isStreak=true;
        setSpan(fontStyle,isSet,StrikethroughSpan.class);
    }

    /**
     * 设置 字体大小
     * @param size
     */
    private void setFontSizeSpan(int size){
        if(size==0){
            size = FontSizeSelectView.NORMAL;
        }
        FontStyle fontStyle = new FontStyle();
        fontStyle.fontSize =size;
        setSpan(fontStyle,true, AbsoluteSizeSpan.class);
    }
    /**
     * 通用set Span
     * @param fontStyle
     * @param isSet
     * @param tClass
     * @param <T>
     */
    private <T> void setSpan(FontStyle fontStyle,boolean isSet,Class<T> tClass){
        Log.d("setSpan","");
        int start = getSelectionStart();
        int end = getSelectionEnd();
        int mode = EXCLUD_INCLUD_MODE;
        T[] spans = getEditableText().getSpans(start,end,tClass);
        //获取
        List<SpanPart> spanStyles = getOldFontSytles(spans,fontStyle);
        for(SpanPart spanStyle : spanStyles){
            if(spanStyle.start<start){
                if(start==end){mode=EXCLUD_MODE;}
                getEditableText().setSpan(getInitSpan(spanStyle), spanStyle.start,start,mode);
            }
            if(spanStyle.end>end){
                getEditableText().setSpan(getInitSpan(spanStyle),end, spanStyle.end,mode);
            }
        }
        if(isSet){
            if(start==end){
                mode=INCLUD_INCLUD_MODE;
            }
            getEditableText().setSpan(getInitSpan(fontStyle),start,end,mode);
        }
    }

    /**
     *  获取当前 选中 spans
     * @param spans
     * @param fontStyle
     * @param <T>
     * @return
     */
    private <T> List<SpanPart> getOldFontSytles(T[] spans, FontStyle fontStyle){
        List<SpanPart> spanStyles = new ArrayList<>();
        for(T span:spans){
            boolean isRemove=false;
            if(span instanceof StyleSpan){//特殊处理 styleSpan
                int style_type = ((StyleSpan) span).getStyle();
                if((fontStyle.isBold&& style_type==Typeface.BOLD)
                        || (fontStyle.isItalic&&style_type==Typeface.ITALIC)){
                    isRemove=true;
                }
            }else{
                isRemove=true;
            }
            if(isRemove) {
                SpanPart spanStyle = new SpanPart(fontStyle);
                spanStyle.start = getEditableText().getSpanStart(span);
                spanStyle.end = getEditableText().getSpanEnd(span);
                if(span instanceof AbsoluteSizeSpan){
                    spanStyle.fontSize = ((AbsoluteSizeSpan) span).getSize();
                }
                spanStyles.add(spanStyle);
                getEditableText().removeSpan(span);
            }
        }
        return spanStyles;
    }
    /**
     * 返回 初始化 span
     * @param fontStyle
     * @return
     */
    private CharacterStyle getInitSpan(FontStyle fontStyle){
        if(fontStyle.isBold){
            return new StyleSpan(Typeface.BOLD);
        }else if(fontStyle.isItalic){
            return new StyleSpan(Typeface.ITALIC);
        }else if(fontStyle.isUnderline){
            return new UnderlineSpan();
        }else if(fontStyle.isStreak){
            return new StrikethroughSpan();
        }else if(fontStyle.fontSize>0){
            return new AbsoluteSizeSpan(fontStyle.fontSize,true);
        }
        return  null;
    }

    /**
     * 获取某位置的  样式
     * @param start
     * @param end
     * @return
     */
    private FontStyle getFontStyle(int start,int end){
        FontStyle fontStyle = new FontStyle();
        CharacterStyle[] characterStyles = getEditableText().getSpans(start,end,CharacterStyle.class);
        for(CharacterStyle style : characterStyles){
            if(style instanceof StyleSpan){
                int type = ((StyleSpan) style).getStyle();
                if(type==Typeface.BOLD){
                    fontStyle.isBold=true;
                }else if(type==Typeface.ITALIC){
                    fontStyle.isItalic=true;
                }
            }else if(style instanceof UnderlineSpan){
                fontStyle.isUnderline=true;
            }else if(style instanceof StrikethroughSpan){
                fontStyle.isStreak=true;
            }else if(style instanceof AbsoluteSizeSpan){
                fontStyle.fontSize = ((AbsoluteSizeSpan) style).getSize();
            }
        }
        return fontStyle;
    }
    public void setOnSelectChangeListener(OnSelectChangeListener onSelectChangeListener) {
        this.onSelectChangeListener = onSelectChangeListener;
    }

    public interface OnSelectChangeListener{
        void onFontStyleChang(FontStyle fontStyle);
        void onSelect(int start,int end);
    }

}
