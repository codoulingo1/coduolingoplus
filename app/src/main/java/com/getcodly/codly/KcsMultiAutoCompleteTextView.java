package com.getcodly.codly;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;
import java.util.List;

public class KcsMultiAutoCompleteTextView implements MultiAutoCompleteTextView.Tokenizer {
    private final char WHITE_SPACE = ' ';
    List<String> c;
    private char TOKEN_TERMINATING_CHAR;
    private String TOKEN_TERMINATING_STRING;

    public KcsMultiAutoCompleteTextView(char tokenTerminatingChar) {
        c = new ArrayList<String>();
        c.add(".");
        c.add(" ");
        c.add("=");
        c.add(">");
        c.add("<");
        c.add("\n");
        c.add("+");
        c.add("-");
        c.add("*");
        c.add("(");
        c.add(")");
        TOKEN_TERMINATING_CHAR = tokenTerminatingChar;
        if (TOKEN_TERMINATING_CHAR == WHITE_SPACE) {
            TOKEN_TERMINATING_STRING = "";
        } else {
            TOKEN_TERMINATING_STRING = String.valueOf(TOKEN_TERMINATING_CHAR) + String.valueOf(WHITE_SPACE);
        }
    }

    @Override
    public int findTokenStart(CharSequence text, int cursor) {
        int i = cursor;

        while (i > 0 && !c.contains(String.valueOf(text.charAt(i - 1)))) {
            i--;
        }
        while (i < cursor && c.contains(String.valueOf(text.charAt(i)))) {
            i++;
        }

        return i;
    }

    @Override
    public int findTokenEnd(CharSequence text, int cursor) {
        int i = cursor;
        int len = text.length();

        while (i < len) {
            if (c.contains(String.valueOf(text.charAt(i)))) {
                return i;
            } else {
                i++;
            }
        }

        return len;
    }

    @Override
    public CharSequence terminateToken(CharSequence text) {
        int i = text.length();

        while (i > 0 && c.contains(String.valueOf(text.charAt(i - 1)))) {
            i--;
        }

        if (i > 0 && c.contains(String.valueOf(text.charAt(i - 1)))) {
            return text;
        } else {
            if (text instanceof Spanned) {
                SpannableString sp = new SpannableString(text + TOKEN_TERMINATING_STRING);
                TextUtils.copySpansFrom((Spanned) text, 0, text.length(),
                        Object.class, sp, 0);
                return sp;
            } else {
                return text + TOKEN_TERMINATING_STRING;
            }
        }
    }
}