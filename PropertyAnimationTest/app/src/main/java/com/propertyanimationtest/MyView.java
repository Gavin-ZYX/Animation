package com.propertyanimationtest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Gavin on 2016/9/20.
 * 自定义动画的View
 * （暂无法使用）
 */
public class MyView extends View {

    private static final String TAG = "MyView";
    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private int mWidth, mHeigth;
    private ValueAnimator valueAnimator;
    private float t;


    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(25);
        mPaint.setColor(Color.BLUE);
        //设置画笔为圆笔
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //坑锯齿
        mPaint.setAntiAlias(true);

        mPath = new Path();
        RectF rect = new RectF(-150, -150, 150, 150);
        mPath.addArc(rect, -90, 359.9f);
        mPathMeasure = new PathMeasure(mPath, false);

        valueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(3000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                t = (float) animation.getAnimatedValue();
                invalidate();
                //Log.i(TAG, t + "");
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2, mHeigth/2);
        Path dst = new Path();
        mPathMeasure.getSegment(mPathMeasure.getLength()*t, mPathMeasure.getLength()*t, dst, true);
        canvas.drawPath(dst,mPaint);
    }
}
