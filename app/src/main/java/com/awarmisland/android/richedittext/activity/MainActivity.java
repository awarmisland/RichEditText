package com.awarmisland.android.richedittext.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.awarmisland.android.richedittext.FontStyle;
import com.awarmisland.android.richedittext.FontStylePanel;
import com.awarmisland.android.richedittext.R;
import com.awarmisland.android.richedittext.RichEditText;
import com.awarmisland.android.richedittext.Utils;
import com.awarmisland.android.richedittext.handle.HtmlParser;
import com.awarmisland.android.richedittext.handle.RichEditImageGetter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements FontStylePanel.OnFontPanelListener
,RichEditText.OnSelectChangeListener {
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
            Spanned spanned = Html.fromHtml(html_content,new RichEditImageGetter(this,richEditText),null);
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            Uri originalUri = data.getData(); // 获得图片的uri
            String path = Utils.getRealPathFromUri(this,originalUri);
            richEditText.setImg(path);
        }
    }

    @OnClick(R.id.btn_right)
    protected void btn_right_onClick(){
        String content = HtmlParser.toHtml(richEditText.getEditableText());
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
}
