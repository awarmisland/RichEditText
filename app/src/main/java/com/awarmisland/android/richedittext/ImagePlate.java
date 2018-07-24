package com.awarmisland.android.richedittext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

public class ImagePlate {
    private Context mContext;
    private RequestManager glideRequests;
    private RichEditText view;
    public ImagePlate(RichEditText view,Context context){
        this.view =view;
        this.mContext=context;
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
        int start = view.getSelectionStart();
        SpannableString ss = new SpannableString("\nimg\n");
        RichImageSpan myImgSpan = new RichImageSpan(mContext, pic, uri);
        ss.setSpan(myImgSpan, 1, img_str.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.getEditableText().insert(start, ss);// 设置ss要添加的位置
        view.requestLayout();
        view.requestFocus();
//        setClick(ss.getSpanStart(myImgSpan),ss.getSpanEnd(myImgSpan),img_str);
    }

    public static Bitmap zoomBitmapToFixWidth(Bitmap bitmap, int maxWidth) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int newHight = maxWidth * h / w;
        return zoomBitmap(bitmap, maxWidth, newHight);
    }

    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }
}
