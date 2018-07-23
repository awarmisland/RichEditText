package com.awarmisland.android.richedittext;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

public class ImagePlate {

    private RequestManager glideRequests;
    private View view;
    public ImagePlate(View view,Context context){
        this.view =view;
        glideRequests = Glide.with(context);
    }

    /**
     * 图片加载
     * @param path
     */
    public void image(String path) {
        final Uri uri = Uri.parse(path);
        final int maxWidth = view.getMeasuredWidth() -view. getPaddingLeft() - view.getPaddingRight();
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
        glideRequests.asBitmap()
                .load(new File(path))
                .apply(options)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        Bitmap bitmap = zoomBitmapToFixWidth(resource, maxWidth);
                        image(uri, bitmap);
                    }
                });
    }

    public void image(Uri uri, Bitmap pic) {
        String img_str="img";
        int start = getSelectionStart();
        SpannableString ss = new SpannableString("\nimg\n");
        MyImgSpan myImgSpan = new MyImgSpan(getContext(), pic, uri);
        ss.setSpan(myImgSpan, 1, img_str.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        getEditableText().insert(start, ss);// 设置ss要添加的位置
        requestLayout();
        requestFocus();
//        setClick(ss.getSpanStart(myImgSpan),ss.getSpanEnd(myImgSpan),img_str);
    }
}
