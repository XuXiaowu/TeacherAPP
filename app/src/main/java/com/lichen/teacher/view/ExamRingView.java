package com.lichen.teacher.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.lichen.teacher.util.ConvertUtils;

/**
 * Created by xiaowu on 2016/9/9.
 */
public class ExamRingView extends View {

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
    private Paint mSmallCalibrationPaint; //小刻度画笔
    private Paint mCalibrationTextPaint; //刻度文字画笔
    private Paint mHandPaint; //指针画笔
    private Paint mScoreTextPaint;    //分数文本画笔

    private RectF mSideRingRect; //外层圆环矩形
    private RectF mInnerRingRect; //内层圆环矩形

    private String mCalibrationStr[] = new String[]{"0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100"};
    private float mCurrentDegrees = -105;
    private int mCurrentScore;

    public ExamRingView(Context context) {

        this(context, null);
    }

    public ExamRingView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public ExamRingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
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
        drawSmallCalibration(canvas);
        drawCalibrationText(canvas);
        drawHand(canvas);
        drawScoreText(canvas);
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

        //圆环小刻度画笔
        mSmallCalibrationPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSmallCalibrationPaint.setStrokeWidth(1);
        mSmallCalibrationPaint.setStyle(Paint.Style.STROKE);
        mSmallCalibrationPaint.setColor(Color.WHITE);
        mSmallCalibrationPaint.setAlpha(130);

        //圆环刻度文本画笔
        mCalibrationTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCalibrationTextPaint.setTextSize(20);
        mCalibrationTextPaint.setColor(Color.WHITE);

        //指针画笔
        mHandPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHandPaint.setStrokeWidth(2);
        mHandPaint.setStyle(Paint.Style.STROKE);
        mHandPaint.setColor(Color.WHITE);
        mHandPaint.setAlpha(130);

        //分数文本画笔
        mScoreTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScoreTextPaint.setTextSize(70);
        mScoreTextPaint.setColor(Color.WHITE);
    }

    private void initRect() {
        mSideRingRect = new RectF();
        mInnerRingRect = new RectF();
    }

    private void drawSideRing(Canvas canvas) {
        canvas.drawArc(mSideRingRect, mStartAngle, mEndAngle, false, mSideRingPaint);
    }

    private void drawInnerRing(Canvas canvas) {
        canvas.drawArc(mInnerRingRect, mStartAngle, mEndAngle, false, mInnerRingPaint);
    }

    /**
     * 绘制内层小刻度
     *
     * @param canvas
     */
    private void drawSmallCalibration(Canvas canvas) {
        //旋转画布
        canvas.save();
        canvas.rotate(-105, mRadius, mRadius);
        int starY = (int) (mInnerRingRect.left - mInnerRingPaint.getStrokeWidth() / 2);
        int stopY = (int) (starY + mInnerRingPaint.getStrokeWidth());
        int rotateAngle = 210 / 30;
        for (int i = 0; i < 30; i++) {
            canvas.drawLine(mRadius, starY, mRadius, stopY, mSmallCalibrationPaint);
            canvas.rotate(rotateAngle, mRadius, mRadius);
        }
        canvas.restore();
    }

    private void drawCalibrationText(Canvas canvas) {
        //旋转画布
        canvas.save();
        canvas.rotate(-105, mRadius, mRadius);

        int rotateAngle = 210 / 10;
        int y = (int) (mInnerRingRect.left + mInnerRingPaint.getStrokeWidth());
        for (int i = 0; i < 10; i++) {
            float textLen = mCalibrationTextPaint.measureText(mCalibrationStr[i]); // 测量文本的长度
            canvas.drawText(mCalibrationStr[i], mRadius - textLen / 2, y + 10, mCalibrationTextPaint);
            canvas.rotate(rotateAngle, mRadius, mRadius);
        }
        canvas.restore();
    }


    /**
     * 绘制内层小刻度
     *
     * @param canvas
     */
    private void drawHand(Canvas canvas) {
        //旋转画布
        canvas.save();
        canvas.rotate(mCurrentDegrees, mRadius, mRadius);
        int starY = (int) (mInnerRingRect.left);
        canvas.drawLine(mRadius, starY, mRadius, mRadius, mHandPaint);
        canvas.restore();

//        canvas.rotate(0, mRadius, mRadius);
//        canvas.drawLine(mRadius, starY, mRadius, mRadius, mHandPaint);
    }

    private void drawScoreText(Canvas canvas) {
        float textLen = mCalibrationTextPaint.measureText(String.valueOf(mCurrentScore)); // 测量文本的长度
        canvas.drawText(String.valueOf(mCurrentScore), mRadius, mRadius + 90, mScoreTextPaint);
    }

    public void startAnim() {
        ValueAnimator mAngleAnim = ValueAnimator.ofFloat(-105, 0);
        mAngleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mAngleAnim.setDuration(3000);
        mAngleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                mCurrentDegrees = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mAngleAnim.start();

        ValueAnimator mNumAnim = ValueAnimator.ofInt(0, 50);
        mNumAnim.setDuration(3000);
        mNumAnim.setInterpolator(new LinearInterpolator());
        mNumAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {

                mCurrentScore = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mNumAnim.start();
    }

}