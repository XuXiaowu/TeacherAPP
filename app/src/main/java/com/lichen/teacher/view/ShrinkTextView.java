package com.lichen.teacher.view;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.lichen.teacher.models.LeaveMessageResult;

import java.util.List;

/**
 * Created by xiaowu on 2016/9/28.
 */
public class ShrinkTextView extends TextView {

    private static final String TAG = "InterceptTextView";

    private static final int MAX_LINE = 3;

    private String mText = "";

    private List<LeaveMessageResult> mDataList;
    private int mPosition;

    public ShrinkTextView(Context context) {
        super(context);
    }

    public ShrinkTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShrinkTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取某行显示文字的个数
     */
    public int getChatCountOfLine(int line) {
        return getLayout().getLineEnd(line);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mDataList.get(mPosition).isMeasure) return;
        String allText = getText().toString();
        int showTextCount = 0;
        int lineCount = getLineCount();
        if (lineCount > MAX_LINE) {
            showTextCount = getChatCountOfLine(2);
            setTextContent(allText, showTextCount, mOpenClickableSpan, false);
            mDataList.get(mPosition).isShrink = true;
        } else {
            setText(allText);
            setLines(lineCount);
            mDataList.get(mPosition).isShrink = false;
        }
        mDataList.get(mPosition).lineCount = lineCount;
        mDataList.get(mPosition).showTextCount = showTextCount;
        mDataList.get(mPosition).isMeasure = true;
    }

    private ClickableSpan mOpenClickableSpan = new NoUnderLineClickSpan() {
        @Override
        public void onClick(View widget) {
            setTextContent(mText, mText.length(), mCloseClickableSpan, true);
            setLines(mDataList.get(mPosition).lineCount);
            mDataList.get(mPosition).isOpen = true;
        }
    };

    private ClickableSpan mCloseClickableSpan = new NoUnderLineClickSpan() {
        @Override
        public void onClick(View widget) {
            int showCount = mDataList.get(mPosition).showTextCount;
            setTextContent(mText, showCount, mOpenClickableSpan, false);
            setLines(MAX_LINE);
            mDataList.get(mPosition).isOpen = false;
        }
    };

    public void setContent(List<LeaveMessageResult> dataList, int position) {
        mDataList = dataList;
        mPosition = position;
        mText = dataList.get(position).content;
        setText(mText);
        if (mDataList.get(mPosition).isMeasure) {
            if (mDataList.get(position).isShrink) {
                if (mDataList.get(mPosition).isOpen) {
                    setTextContent(mText, mText.length(), mOpenClickableSpan, true);
                    setLines(mDataList.get(mPosition).lineCount);
                } else {
                    int showCount = mDataList.get(mPosition).showTextCount;
                    setTextContent(mText, showCount, mOpenClickableSpan, false);
                    setLines(MAX_LINE);
                }
            } else {
//                setText(mText);
                setLines(mDataList.get(position).lineCount);
            }
        }
    }

    private void setTextContent(String allText, int showCount, ClickableSpan clickableSpan, boolean isOpen) {
        String endText;
        int endTextLength;
        if (isOpen) endText = " ...收起";
        else endText = " ...查看全部";
        endTextLength = endText.length();
        String showText = allText.substring(0, showCount - endTextLength) + endText;
        SpannableString ss = new SpannableString(showText);
        ss.setSpan(clickableSpan, showText.length() - endTextLength, showText.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        setMovementMethod(LinkMovementMethod.getInstance());
        setText(ss);
    }

    private class NoUnderLineClickSpan extends ClickableSpan {

        public NoUnderLineClickSpan() {
            super();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(Color.parseColor("#3688fd"));
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {

        }
    }
}
