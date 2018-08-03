package com.awarmisland.android.richedittext;

/**
 * span 样式记录
 */
public class SpanPart extends FontStyle {
    public int start;
    public int end;

    public SpanPart(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public SpanPart(FontStyle fontStyle) {
        this.isBold = fontStyle.isBold;
        this.isItalic = fontStyle.isItalic;
        this.isStreak = fontStyle.isStreak;
        this.isUnderline = fontStyle.isUnderline;
        this.fontSize = fontStyle.fontSize;
    }
}