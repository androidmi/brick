package com.example.df.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by df on 15-8-6.
 */
public class CustButton extends View {
    private static final String TAG = "CustButton";
    private int mBgColor = Color.BLACK;
    Paint p = new Paint();
    TextPaint textPaint = new TextPaint();

    public CustButton(Context context) {
        super(context);
    }

    public CustButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");
        p.setColor(mBgColor);
        p.setStrokeWidth(100f);
        canvas.drawLine(0, 100, 100, 100, p);
        int width = getWidth();
        int height = getHeight();
        Log.i(TAG, "width:"+width);
        Log.i(TAG, "height:"+height);

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40f);
        canvas.drawText("line for line", 0, 100, textPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        Log.i(TAG, "action:"+action);
        switch (action){
            case MotionEvent.ACTION_UP:
                mBgColor = Color.BLACK;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_MOVE:
                mBgColor = Color.parseColor("#CDCDCD");
                break;
            case MotionEvent.ACTION_DOWN:
                ObjectAnimator animX = ObjectAnimator.ofFloat(this, "x", 500f);
                ObjectAnimator animY = ObjectAnimator.ofFloat(this, "y", 100f);
                AnimatorSet animationSet = new AnimatorSet();
                animationSet.playTogether(animX, animY);
                animationSet.start();
                mBgColor = Color.parseColor("#CDCDCD");
                break;

        }
        invalidate();
        return super.onTouchEvent(event);
    }
}
