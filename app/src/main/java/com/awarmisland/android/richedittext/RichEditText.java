package com.awarmisland.android.richedittext;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.text.Spannable;
import android.text.style.CharacterStyle;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
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

        if(fontStyle.isBold){
            getEditableText().setSpan(new StyleSpan(Typeface.BOLD),style_start,style_start,INCLUD_INCLUD_MODE);
        }
        if(fontStyle.isItalic){
            getEditableText().setSpan(new StyleSpan(Typeface.ITALIC),style_start,style_start,INCLUD_INCLUD_MODE);
        }
        if(fontStyle.isUnderline){
            getEditableText().setSpan(new UnderlineSpan(),style_start,style_start,INCLUD_INCLUD_MODE);
        }
        if(onSelectChangeListener!=null){
            onSelectChangeListener.onSelect(start,start);
            onSelectChangeListener.onFontStyleChang(fontStyle);
        }
    }

    public void setBold(boolean isBold){
        setStyleSpan(isBold,Typeface.BOLD);
    }
    public void setItalic(boolean isItalic){ setStyleSpan(isItalic,Typeface.ITALIC); }
    public void setUnderline(boolean isUnderline){
        setUnderlineSpan(isUnderline);
    }
    public void setStreak(boolean isStreak){

    }

    /**
     * bold italic
     * @param isSet
     * @param type
     */
    private void setStyleSpan(boolean isSet,int type){
        int start = getSelectionStart();
        int end = getSelectionEnd();
        int mode = EXCLUD_INCLUD_MODE;
        StyleSpan[] styles = getEditableText().getSpans(start,end,StyleSpan.class);
        List<Part> parts = new ArrayList<>();
        for(StyleSpan styleSpan : styles){
            if (styleSpan.getStyle() == type) {
                parts.add(new Part(getEditableText().getSpanStart(styleSpan),getEditableText().getSpanEnd(styleSpan)));
                getEditableText().removeSpan(styleSpan);
            }
        }
        for(Part part : parts){
            if(part.start<start){
                if(start==end){mode=EXCLUD_MODE;}
                getEditableText().setSpan(new StyleSpan(type),part.start,start,mode);
            }
            if(part.end>end){
                getEditableText().setSpan(new StyleSpan(type),end,part.end,mode);
            }
        }
        if(isSet){
            if(start==end){
                mode=INCLUD_INCLUD_MODE;
            }
            getEditableText().setSpan(new StyleSpan(type),start,end,mode);
        }
    }
    private void setUnderlineSpan(boolean isSet){
        int start = getSelectionStart();
        int end = getSelectionEnd();
        int mode = EXCLUD_INCLUD_MODE;
        UnderlineSpan[] spans = getEditableText().getSpans(start,end,UnderlineSpan.class);
        List<Part> parts = new ArrayList<>();
        for(UnderlineSpan span:spans){
            parts.add(new Part(getEditableText().getSpanStart(span),getEditableText().getSpanEnd(span)));
            getEditableText().removeSpan(span);
        }
        for(Part part : parts){
            if(part.start<start){
                if(start==end){mode=EXCLUD_MODE;}
                getEditableText().setSpan(new UnderlineSpan(),part.start,start,mode);
            }
            if(part.end>end){
                getEditableText().setSpan(new UnderlineSpan(),end,part.end,mode);
            }
        }
        if(isSet){
            if(start==end){
                mode=INCLUD_INCLUD_MODE;
            }
            getEditableText().setSpan(new UnderlineSpan(),start,end,mode);
        }
    }
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
