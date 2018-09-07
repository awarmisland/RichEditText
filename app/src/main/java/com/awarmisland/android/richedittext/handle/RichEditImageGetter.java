package com.awarmisland.android.richedittext.handle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.WindowManager;

import com.awarmisland.android.richedittext.R;
import com.awarmisland.android.richedittext.view.RichEditText;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.util.HashSet;

public class RichEditImageGetter implements Html.ImageGetter {
    private HashSet<Target> targets;
    private HashSet<GifDrawable> gifDrawables;
    private final Context mContext;
    private final RichEditText mTextView;
    public RichEditImageGetter(Context context, RichEditText textView) {
        this.mContext = context;
        this.mTextView = textView;
        targets = new HashSet<>();
        gifDrawables = new HashSet<>();
        mTextView.setTag(R.id.img_tag, this);
    }

    @Override
    public Drawable getDrawable(String url) {
        final UrlDrawable urlDrawable = new UrlDrawable();
        final RequestBuilder load;
        final Target target;
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher);
        if(isGif(url)){
            load = Glide.with(mContext).asGif().load(url);
            target = new GifTarget(urlDrawable);
        }else {
            load = Glide.with(mContext).asBitmap()
                    .load(url)
                    .apply(options);
            target = new BitmapTarget(urlDrawable);
        }
        targets.add(target);
        load.into(target);
        return urlDrawable;
    }

    class BitmapTarget extends SimpleTarget<Bitmap> {
        private final UrlDrawable urlDrawable;
        public BitmapTarget(UrlDrawable urlDrawable) {
            this.urlDrawable = urlDrawable;
        }

        @Override
        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
            Drawable drawable = new BitmapDrawable(mContext.getResources(), resource);
            int w = getScreenSize(mContext).x;
            int hh=drawable.getIntrinsicHeight();
            int ww=drawable.getIntrinsicWidth() ;
            int padding = Utils.dip2px(mContext,30);
            int high=hh*(w-padding)/ww;
            Rect rect = new Rect(0, 0,w-padding,high);
            drawable.setBounds(rect);
            urlDrawable.setBounds(rect);
            urlDrawable.setDrawable(drawable);
            mTextView.setText(mTextView.getText());
            mTextView.invalidate();
        }
    }
    class GifTarget extends SimpleTarget<GifDrawable> {
        private final UrlDrawable urlDrawable;
        private  GifTarget(UrlDrawable urlDrawable) {
            this.urlDrawable = urlDrawable;
        }

        @Override
        public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
            int w = getScreenSize(mContext).x;
            int hh=resource.getIntrinsicHeight();
            int ww=resource.getIntrinsicWidth() ;
            int high = hh * (w - 50)/ww;
            Rect rect = new Rect(20, 20,w-30,high);
            resource.setBounds(rect);
            urlDrawable.setBounds(rect);
            urlDrawable.setDrawable(resource);
            gifDrawables.add(resource);
            resource.setCallback(mTextView);
            resource.start();
//            resource.setLoopCount(GlideDrawable.LOOP_FOREVER);
            mTextView.setText(mTextView.getText());
            mTextView.invalidate();
        }
    }
    class UrlDrawable extends BitmapDrawable {
        private Drawable drawable;

        @SuppressWarnings("deprecation")
        public UrlDrawable() {
        }
        @Override
        public void draw(Canvas canvas) {
            if (drawable != null)
                drawable.draw(canvas);
        }
        public Drawable getDrawable() {
            return drawable;
        }
        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }
    }
    private static boolean isGif(String path) {
        int index = path.lastIndexOf('.');
        return index > 0 && "gif".toUpperCase().equals(path.substring(index + 1).toUpperCase());
    }
    /**
     * 获取屏幕尺寸
     *
     * @param context 上下文
     * @return 屏幕尺寸像素值，下标为0的值为宽，下标为1的值为高
     */
    public static Point getScreenSize(Context context) {

        // 获取屏幕宽高
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point screenSize = new Point();
        wm.getDefaultDisplay().getSize(screenSize);
        return screenSize;
    }
}
