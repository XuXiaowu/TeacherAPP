package com.lichen.teacher.view;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.lichen.teacher.util.ConvertUtils;

import java.util.TimeZone;

/**
 * Created by xiaowu on 2016/9/9.
 */
public class ClockView extends View {

    private static final String TAG = "ClockView";

    private final static int mDefaultPadding = 20; // 默认Padding值

    private int mDefaultSize;


    private int mWidth; // view宽度
    private int mHeight; // view高度
    private int mRadius; //半径
    private int mArcDistance; // 距离圆环的值

    private final static float mStartAngle = 165f; //  圆环起始角度
    private final static float mEndAngle = 210f;  // 圆环结束角度

    private Paint mSideRingPaint; //外层圆环画笔
    private Paint mInnerRingPaint; //内层圆环画笔
    private Paint mBigCalibrationPaint; //大刻度画笔
    private Paint mSmallCalibrationPaint; //大刻度画笔
    private Paint mCalibrationTextPaint; //刻度文字画笔
    private Paint mSecondHandPaint; //秒针画笔
    private Paint mMinutePaint; //分针画笔
    private Paint mHourPaint; //时针画笔
    private Paint mCenterCirclePaint; //中心圆画笔

    private RectF mSideRingRect; //外层圆环矩形
    private RectF mInnerRingRect; //内层圆环矩形

    private Time mCalendar;
    private int mHour;
    private int mMinute;
    private int mSecond;

    private String mCalibrationStr[] = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    public ClockView(Context context) {

        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
        initPaint();
        initRect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveMeasure(widthMeasureSpec, mDefaultSize),
                resolveMeasure(heightMeasureSpec, mDefaultSize));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
        mRadius = mWidth / 2;

        mSideRingRect.left = mDefaultPadding;
        mSideRingRect.right = mWidth - mSideRingRect.left;
        mSideRingRect.top = mDefaultPadding;
        mSideRingRect.bottom = mHeight - mSideRingRect.top;

        mInnerRingRect.left = mSideRingRect.left + mDefaultPadding + mArcDistance;
        mInnerRingRect.right = mWidth - mInnerRingRect.left;
        mInnerRingRect.top = mSideRingRect.top + mDefaultPadding + mArcDistance;
        mInnerRingRect.bottom = mHeight - mInnerRingRect.top;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawSideRing(canvas);
        drawInnerRing(canvas);
        drawBigCalibration(canvas);
        drawSmallCalibration(canvas);
        drawCalibrationText(canvas);
        drawSecondHand(canvas);
        drawMinuteHand(canvas);
        drawHourHand(canvas);
        drawCenterCircle(canvas);
    }

    /**
     * 根据传入的值进行测量
     *
     * @param measureSpec
     * @param defaultSize
     */
    public int resolveMeasure(int measureSpec, int defaultSize) {

        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {

            case MeasureSpec.UNSPECIFIED:
                result = defaultSize;
                break;

            case MeasureSpec.AT_MOST:
                //设置warp_content时设置默认值
                result = Math.min(specSize, defaultSize);
                break;
            case MeasureSpec.EXACTLY:
                //设置math_parent 和设置了固定宽高值
                break;

            default:
                result = defaultSize;
        }

        return result;
    }

    private void init() {
        mDefaultSize = ConvertUtils.dp2px(getContext(), 250);
        mArcDistance = ConvertUtils.dp2px(getContext(), 10);
        new MyThread().start();
        mCalendar = new Time();
    }

    private void initPaint() {
        //外圆画笔
        mSideRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSideRingPaint.setStrokeWidth(8);
        mSideRingPaint.setColor(Color.WHITE);
        mSideRingPaint.setStyle(Paint.Style.STROKE);
        mSideRingPaint.setAlpha(130);

        //内圆画笔
        mInnerRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerRingPaint.setStrokeWidth(30);
        mInnerRingPaint.setColor(Color.WHITE);
        mInnerRingPaint.setStyle(Paint.Style.STROKE);
        mInnerRingPaint.setAlpha(80);

        //圆环大刻度画笔
        mBigCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBigCalibrationPaint.setStrokeWidth(2);
        mBigCalibrationPaint.setStyle(Paint.Style.STROKE);
        mBigCalibrationPaint.setColor(Color.WHITE);
        mBigCalibrationPaint.setAlpha(130);

        //圆环小刻度画笔
        mSmallCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSmallCalibrationPaint.setStrokeWidth(1);
        mSmallCalibrationPaint.setStyle(Paint.Style.STROKE);
        mSmallCalibrationPaint.setColor(Color.WHITE);
        mSmallCalibrationPaint.setAlpha(100);

        //中心画笔
        mCenterCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterCirclePaint.setStrokeWidth(10);
        mCenterCirclePaint.setStyle(Paint.Style.STROKE);
        mCenterCirclePaint.setColor(Color.WHITE);
        mCenterCirclePaint.setAlpha(130);

        //圆环刻度文本画笔
        mCalibrationTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCalibrationTextPaint.setTextSize(20);
        mCalibrationTextPaint.setColor(Color.WHITE);

        //秒针画笔
        mSecondHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondHandPaint.setStrokeWidth(2);
        mSecondHandPaint.setStyle(Paint.Style.STROKE);
        mSecondHandPaint.setColor(Color.WHITE);
        mSecondHandPaint.setAlpha(130);

        //分针画笔
        mMinutePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMinutePaint.setStrokeWidth(4);
        mMinutePaint.setStyle(Paint.Style.STROKE);
        mMinutePaint.setColor(Color.WHITE);
        mMinutePaint.setAlpha(130);

        //时针画笔
        mHourPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHourPaint.setStrokeWidth(8);
        mHourPaint.setStyle(Paint.Style.STROKE);
        mHourPaint.setColor(Color.WHITE);
        mHourPaint.setAlpha(130);
    }

    private void initRect() {
        mSideRingRect = new RectF();
        mInnerRingRect = new RectF();
    }

    private void drawSideRing(Canvas canvas) {
        canvas.drawArc(mSideRingRect, 0, 360, false, mSideRingPaint);
    }

    private void drawInnerRing(Canvas canvas) {
        canvas.drawArc(mInnerRingRect, 0, 360, false, mInnerRingPaint);
    }

    /**
     * 绘制大刻度
     *
     * @param canvas
     */
    private void drawBigCalibration(Canvas canvas) {
        int starY = (int) (mInnerRingRect.left - mInnerRingPaint.getStrokeWidth() / 2);
        int stopY = (int) (starY + mInnerRingPaint.getStrokeWidth());
        int rotateAngle = 360 / 12;
        for (int i = 0; i < 12; i++) {
            canvas.drawLine(mRadius, starY, mRadius, stopY, mBigCalibrationPaint);
            canvas.rotate(rotateAngle, mRadius, mRadius);
        }
        canvas.restore();
    }

    /**
     * 绘制小刻度
     *
     * @param canvas
     */
    private void drawSmallCalibration(Canvas canvas) {
        int starY = (int) (mInnerRingRect.left - mInnerRingPaint.getStrokeWidth() / 2);
        int stopY = (int) (starY + mInnerRingPaint.getStrokeWidth());
        int rotateAngle = 360 / 60;
        for (int i = 0; i < 60; i++) {
            canvas.drawLine(mRadius, starY, mRadius, stopY, mSmallCalibrationPaint);
            canvas.rotate(rotateAngle, mRadius, mRadius);
        }
        canvas.restore();
    }

    private void drawCalibrationText(Canvas canvas) {
        //旋转画布
        canvas.save();
        canvas.rotate(30, mRadius, mRadius);

        int rotateAngle = 360 / 12;
        int y = (int) (mInnerRingRect.left + mInnerRingPaint.getStrokeWidth());
        for (int i = 0; i < 12; i++) {
            float textLen = mCalibrationTextPaint.measureText(mCalibrationStr[i]); // 测量文本的长度
            canvas.drawText(mCalibrationStr[i], mRadius - textLen / 2, y + 10, mCalibrationTextPaint);
            canvas.rotate(rotateAngle, mRadius, mRadius);
        }
        canvas.restore();

    }


    /**
     * 绘制秒针
     *
     * @param canvas
     */
    private void drawSecondHand(Canvas canvas) {
        canvas.save();
        int starY = (int) (mInnerRingRect.left + mInnerRingPaint.getStrokeWidth());
        float degrees = mSecond * 6;
        canvas.rotate(degrees, mRadius, mRadius);
        canvas.drawLine(mRadius, starY, mRadius, mRadius + 60, mSecondHandPaint);
        canvas.restore();
    }

    /**
     * 绘制分针
     *
     * @param canvas
     */
    private void drawMinuteHand(Canvas canvas) {
        canvas.save();
        int starY = (int) (mInnerRingRect.left + mInnerRingPaint.getStrokeWidth()) + 30;
        float degrees = mMinute * 10;
        canvas.rotate(degrees, mRadius, mRadius);
        canvas.drawLine(mRadius, starY, mRadius, mRadius + 60, mMinutePaint);
        canvas.restore();
    }

    /**
     * 绘制时针
     *
     * @param canvas
     */
    private void drawHourHand(Canvas canvas) {
        canvas.save();
        int starY = (int) (mInnerRingRect.left + mInnerRingPaint.getStrokeWidth()) + 60;
        float degrees = mHour * 30;
        canvas.rotate(degrees, mRadius, mRadius);
        canvas.drawLine(mRadius, starY, mRadius, mRadius + 60, mHourPaint);
        canvas.restore();
    }

    private void drawCenterCircle(Canvas canvas) {
        canvas.save();
        canvas.drawCircle(mRadius, mRadius, 5, mCenterCirclePaint);
        canvas.restore();
    }

    private void getTime() {
        mCalendar.setToNow();

        int hour = mCalendar.hour;
        int minute = mCalendar.minute;
        int second = mCalendar.second;
        /*这里我们为什么不直接把minute设置给mMinutes，而是要加上
            second /60.0f呢，这个值不是应该一直为0吗？
            这里又涉及到Calendar的 一个知识点，
            也就是它可以是Linient模式，
            此模式下，second和minute是可能超过60和24的，具体这里就不展开了，
            如果不是很清楚，建议看看Google的官方文档中讲Calendar的部分*/
        mHour = hour;
        mMinute = minute;
        mSecond = second;
    }

    public void startAnim() {

    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                mHandler.sendEmptyMessage(1);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class AnimThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                mHandler.sendEmptyMessage(2);
                try {
                    sleep(17);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getTime();
                    invalidate();
                    break;
                case 2:
                    break;
            }
            super.handleMessage(msg);
        }
    };


}