package com.awarmisland.android.richedittext.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.awarmisland.android.richedittext.bean.FontStyle;
import com.awarmisland.android.richedittext.view.FontStylePanel;
import com.awarmisland.android.richedittext.R;
import com.awarmisland.android.richedittext.view.RichEditText;
import com.awarmisland.android.richedittext.handle.Utils;
import com.awarmisland.android.richedittext.handle.CustomHtml;
import com.awarmisland.android.richedittext.handle.RichEditImageGetter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * create by awarmisland
 */
public class MainActivity extends AppCompatActivity implements FontStylePanel.OnFontPanelListener
,RichEditText.OnSelectChangeListener {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @BindView(R.id.richEditText)
    RichEditText richEditText;
    @BindView(R.id.fontStylePanel)
    FontStylePanel fontStylePanel;
    @BindView(R.id.btn_back)
    Button btn_back;
    @BindView(R.id.btn_right)
    Button btn_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btn_back.setVisibility(View.GONE);
        btn_right.setText("生成html");
        fontStylePanel.setOnFontPanelListener(this);
        richEditText.setOnSelectChangeListener(this);
        initRichTextCon();
    }

    private void initRichTextCon(){
        String html_content = getIntent().getStringExtra("html_content");
        if(!TextUtils.isEmpty(html_content)){
            Log.d("richText","html转span:"+html_content);
            Spanned spanned = CustomHtml.fromHtml(html_content,CustomHtml.FROM_HTML_MODE_LEGACY,new RichEditImageGetter(this,richEditText),null);
            richEditText.setText(spanned);
        }
    }

    @Override
    public void setBold(boolean isBold) {
        richEditText.setBold(isBold);
    }

    @Override
    public void setItalic(boolean isItalic) {
        richEditText.setItalic(isItalic);
    }

    @Override
    public void setUnderline(boolean isUnderline) {
        richEditText.setUnderline(isUnderline);
    }

    @Override
    public void setStreak(boolean isStreak) { richEditText.setStreak(isStreak); }

    @Override
    public void insertImg() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                pickPicture();
            }
        } else {
            pickPicture();
        }
    }
    private void pickPicture(){
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        startActivityForResult(getAlbum, 0);
    }
    //字体大小
    @Override
    public void setFontSize(int size) {
        richEditText.setFontSize(size);
    }
    //颜色设置
    @Override
    public void setFontColor(int color) {
        richEditText.setFontColor(color);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            Uri originalUri = data.getData(); // 获得图片的uri
            String path = Utils.getRealPathFromUri(this,originalUri);
            richEditText.setImg(path);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.btn_right)
    protected void btn_right_onClick(){
        String content = CustomHtml.toHtml(richEditText.getEditableText(),CustomHtml.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE);
        Log.d("richText","span转html:"+content);
        Intent intent = new Intent(this,WebviewActivity.class);
        intent.putExtra("content",content);
        startActivity(intent);
    }
    /**
     * 样式改变
     * @param fontStyle
     */
    @Override
    public void onFontStyleChang(FontStyle fontStyle) {
        fontStylePanel.initFontStyle(fontStyle);
    }

    /**
     * 光标选中监听
     * @param start
     * @param end
     */
    @Override
    public void onSelect(int start, int end) {
    }

    private void checkPermission() {

    }
}
