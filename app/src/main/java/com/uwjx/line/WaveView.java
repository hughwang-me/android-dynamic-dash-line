package com.uwjx.line;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


public class WaveView extends View {

    //组件的宽和高
    private float mViewWidth;
    private float mViewHeight;

    //波浪的宽和高(波浪的高预取0.4倍组件宽度,波浪的宽预取4倍组件的宽度)
    private float mWaveHeight;
    private float mWaveWidth;

    //波浪的轨迹和画笔
    private Path wavePath;
    private Paint wavePaint;

    private float mLevelHeight;//水位线高度,改变这个值即可以模拟出波浪上下动的效果(这个是外界控制的)
    private float xOffset;//波浪在水平线上的平移距离
    private float yOffset;//波浪在y轴上的平移距离

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
        mWaveHeight = (float) (0.4 * mViewWidth);
        mWaveWidth = 4 * mViewWidth;
        mLevelHeight = mViewHeight - 2 * mWaveHeight;
        init();
        changeWavePathAnimator();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(xOffset,0);
        canvas.drawPath(wavePath,wavePaint);
    }

    private void init(){

        wavePaint = new Paint();
        wavePaint.setAntiAlias(true);
        wavePaint.setColor(Color.BLUE);
        wavePaint.setStyle(Paint.Style.FILL);

        wavePath = new Path();
        wavePath.moveTo( -(4 * mViewWidth) ,mLevelHeight );//第一个点在view左侧看不到的地方,方便后续动画将其平移过来
        wavePath.quadTo( -(3 * mViewWidth) , mLevelHeight - 2 * mWaveHeight , -(2 * mViewWidth) , mLevelHeight);//绘制第一段贝塞尔曲线,不可见
        wavePath.quadTo( - mViewWidth , mLevelHeight + 2 * mWaveHeight , 0 , mLevelHeight);//绘制第二段贝塞尔曲线,不可见
        wavePath.quadTo(mViewWidth , mLevelHeight - 2 * mWaveHeight , 2 * mViewWidth , mLevelHeight);//绘制第三段贝塞尔曲线,可见
        wavePath.lineTo(2 * mViewWidth , mViewHeight);
        wavePath.lineTo(- (4 * mViewWidth) ,mViewHeight);
        wavePath.close();

    }

    private void changeWavePathAnimator(){
        ValueAnimator animator = ValueAnimator.ofFloat(0,1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Object rateObj = valueAnimator.getAnimatedValue();
                float rate = Float.parseFloat(rateObj.toString());
                xOffset = rate * mViewWidth * 4;
                invalidate();
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(1000);
        animator.setRepeatCount(Integer.MAX_VALUE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();
    }
}
