package com.awarmisland.android.richedittext;

public class Part extends FontStyle {
    public int start;
    public int end;

    public Part(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public Part(FontStyle fontStyle) {
        this.isBold = fontStyle.isBold;
        this.isItalic = fontStyle.isItalic;
        this.isStreak = fontStyle.isStreak;
        this.isUnderline = fontStyle.isUnderline;
        this.fontSize = fontStyle.fontSize;
    }
}