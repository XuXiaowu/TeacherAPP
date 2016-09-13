package com.lichen.teacher.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by xiaowu on 2016/9/8.
 */
public class TouchableImageView extends ImageView {

    private final Paint mBorderPaint = new Paint();
    private final Paint mBorderPaint2 = new Paint();

    public TouchableImageView(Context context) {
        super(context);

        mBorderPaint.setColor(0x33000000);
        mBorderPaint2.setColor(0x99274c8a);
    }

    public TouchableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBorderPaint.setColor(0x33000000);
        mBorderPaint2.setColor(0x99274c8a);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isPressed()) canvas.drawColor(0x33000000);
    }

    @Override
    protected void dispatchSetPressed(boolean pressed) {
        super.dispatchSetPressed(pressed);
        invalidate();
    }
}

