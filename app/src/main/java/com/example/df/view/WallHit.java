package com.example.df.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.concurrent.TimeUnit;

/**
 * Created by df on 15-8-13.
 */
public class WallHit extends View implements Runnable {
    private static final String TAG = "WallHit";
    private Handler mH = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();
        }
    };

    private float mX;
    private float mY;
    private float brickX;
    private float brickY;
    float radius = 50;

    float brickWidth = 200;
    float brickHeight = 50;

    Paint p = new Paint();
    private boolean mRight = false;
    private boolean mMoving = false;

    Paint zhuanPaint = new Paint();

    public WallHit(Context context) {
        super(context);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        Log.i(TAG, "width:"+width);
        drawBall(canvas);

        int divider = width / 20;
        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = 0;
//        for (int j = 0; j < 10; j++) {
//            for (int i = 0; i < 20; i++) {
//                // draw 砖 view
//                Rect rect = new Rect();
//                left = i * divider;
//                rect.left = left + 1;
//                rect.right = left + divider;
//                top = j * 25;
//                rect.top = top + 1;
//                rect.bottom = top + 25;
//                zhuanPaint.setColor(Color.GRAY);
//                canvas.drawRect(rect, zhuanPaint);
//            }
//        }
        drawBrick(canvas);
    }

    private void drawBall(Canvas canvas) {
        p.setColor(Color.parseColor("#cdffcd"));
        int height = getHeight();
        if (mY == 0) {
            mY = height -  2* radius + 5;
        } else if (mY == height) {

        }
        int width = getWidth();
        if (mX == 0) {
            mX = width / 2;
        } else if (mX >= width) {
            mX = width;
        }
        canvas.drawCircle(mX, mY, radius, p);
    }

    private void drawBrick(Canvas canvas) {
        p.setColor(Color.parseColor("#ff00ff"));
        int height = getHeight();
        if (brickY == 0) {
            brickY = height -  brickHeight + 5;
        }
        int width = getWidth();
        if (brickX == 0) {
            brickX = (width - brickWidth)/2;
        }
        float left = brickX;
        float right = brickX + brickWidth;
        float top = brickY;
        float bottom = height;
        canvas.drawRect(left, top, right, bottom, p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        Log.i(TAG, "action:"+action);
        switch (action){
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mMoving = false;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_DOWN:
                mMoving = true;
                float pointX = event.getX();
                float pointY = event.getY();
                float width = getWidth();
                if (pointX >= width/2) {
                    // if touch on right move to right
                    mRight = true;
                } else {
                    mRight = false;
                }
                new Thread(this).start();
                break;

        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private double angle = 30.0;
    private float mMoveLen = 0;

    @Override
    public void run() {
        while (mMoving) {
            if (mMoving) {
                if (mRight) {
                    brickX += 0.01;
                } else {
                    brickX -= 0.01;
                }
            }
            mMoveLen += 0.01;
            mX = (float) (mMoveLen / Math.cos(angle));
            mY -= 0.01;
            mH.sendEmptyMessage(3);
        }
    }
}
