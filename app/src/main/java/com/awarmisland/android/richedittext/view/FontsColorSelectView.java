package com.awarmisland.android.richedittext.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.awarmisland.android.richedittext.bean.FontStyle;
import com.awarmisland.android.richedittext.R;
import com.awarmisland.android.richedittext.handle.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 颜色选择
 */
public class FontsColorSelectView extends LinearLayout implements
        View.OnClickListener{
    private Context mContext;
    private FontStyle fontStyle;
    @BindView(R.id.ivw_black)
    ImageView ivw_black;
    @BindView(R.id.ivw_grey)
    ImageView ivw_grey;
    @BindView(R.id.ivw_red)
    ImageView ivw_red;
    @BindView(R.id.ivw_blue)
    ImageView ivw_blue;



    public FontsColorSelectView(Context context) {
        super(context);
        initView(context);
    }

    public FontsColorSelectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FontsColorSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
    private void initView(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_font_color_select,this);
        ButterKnife.bind(this,view);
        ivw_black.setOnClickListener(this);
        ivw_grey.setOnClickListener(this);
        ivw_red.setOnClickListener(this);
        ivw_blue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        long id = view.getId();
        int color = Color.parseColor(FontStyle.BLACK);
        if(id==R.id.ivw_black){
        }else if(id ==R.id.ivw_grey){
            color = Color.parseColor(FontStyle.GREY);
        }else if(id ==R.id.ivw_red){
            color = Color.parseColor(FontStyle.RED);
        } else if(id ==R.id.ivw_blue){
            color = Color.parseColor(FontStyle.BLUE);
        }
        initDefaultView(id);
        if(onColorSelectListener!=null) {
            onColorSelectListener.onColorSelect(color);
        }
    }
    public void initFontStyle(FontStyle fontStyle){
        this.fontStyle =fontStyle;
        int color = fontStyle.color;
        int id = R.id.ivw_black;
        if(fontStyle.color==0) {
            color = Color.parseColor(FontStyle.BLACK);
        }
        if(color==Color.parseColor(FontStyle.GREY)){
            id=R.id.ivw_grey;
        }else if(color==Color.parseColor(FontStyle.RED)){
            id=R.id.ivw_red;
        }else if(color==Color.parseColor(FontStyle.BLUE)){
            id=R.id.ivw_blue;
        }
        initDefaultView(id);
    }
    private void initDefaultView(long id){
        setViewHeight(ivw_black,34);
        setViewHeight(ivw_grey,34);
        setViewHeight(ivw_red,34);
        setViewHeight(ivw_blue,34);
        if(id==R.id.ivw_black){
            setViewHeight(ivw_black,42);
        }else if(id ==R.id.ivw_grey){
            setViewHeight(ivw_grey,42);
        }else if(id ==R.id.ivw_red){
            setViewHeight(ivw_red,42);
        } else if(id ==R.id.ivw_blue){
            setViewHeight(ivw_blue,42);
        }
    }
    private void setViewHeight(View view,int height){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height= Utils.dip2px(mContext,height);
        view.setLayoutParams(params);
    }
    private OnColorSelectListener onColorSelectListener;

    public void setOnColorSelectListener(OnColorSelectListener onColorSelectListener) {
        this.onColorSelectListener = onColorSelectListener;
    }

    public void setFontStyle(FontStyle fontStyle) {
        this.fontStyle = fontStyle;
    }

    public interface OnColorSelectListener{
        void onColorSelect(int color);
    }
}
