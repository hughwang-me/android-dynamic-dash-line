package com.uwjx.line;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class DynamicDashLineView extends View {

    private int mWidth;
    private int mHeight;

    private Paint paint;
    private Path path;

    private Path path1;
    private Path path2;
    private Paint paint1;
    private Paint paint2;

    private int i = 0;

    public DynamicDashLineView(Context context) {
        super(context);
        init();
    }

    public DynamicDashLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DynamicDashLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20f);

        path = new Path();
        path1 = new Path();
        path2 = new Path();

        paint1 = new Paint();
        paint1.setColor(Color.RED);
        paint1.setStrokeWidth(2f);
        paint1.setStyle(Paint.Style.STROKE);

        paint2 = new Paint();
        paint2.setColor(Color.RED);
        paint2.setStrokeWidth(2f);
        paint2.setStyle(Paint.Style.STROKE);


        new Thread(){
            @Override
            public void run() {
                while (i <100000){
                    i++;
                    postInvalidate();

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        Log.w("uwjx" , "onSizeChanged -> " + mWidth);
        Log.w("uwjx" , "onSizeChanged -> " + mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//            canvas.restore();
//            canvas.drawLine( 0f , 100f , 300f , 100f , paint);

//        DashPathEffect dashPathEffect1 = new DashPathEffect(new float[]{60, 60}, 0);
//        paint.setPathEffect(dashPathEffect1);

            paint.setColor(Color.RED);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(20);
            paint.setAntiAlias(true);
            paint.setPathEffect(new DashPathEffect(new float[] {25, 15}, -i));

            path.reset();
            path.moveTo(0, mHeight/2.0f );
            path.lineTo(mWidth/2.0f, mHeight/2.0f );

        path1.reset();
        path1.moveTo(0, mHeight/2.0f -10 );
        path1.lineTo(mWidth/2.0f +10, mHeight/2.0f -10);

        path2.reset();
        path2.moveTo(0, mHeight/2.0f +10 );
        path2.lineTo(mWidth/2.0f -10, mHeight/2.0f +10);



        path.lineTo(   mWidth/2.0f , mHeight/3.0f *2);
//            path.quadTo(  mWidth/2.0f+1, mHeight/2.0f ,mWidth/2.0f , mHeight/3.0f *2);
//        path1.quadTo(  mWidth/2.0f+10+1, mHeight/2.0f ,mWidth/2.0f +10 , mHeight/3.0f *2);
//        path2.quadTo(  mWidth/2.0f-10-1, mHeight/2.0f ,mWidth/2.0f -10 , mHeight/3.0f *2);

        path1.lineTo(   mWidth/2.0f +10 , mHeight/3.0f *2);
        path2.lineTo(   mWidth/2.0f -10 , mHeight/3.0f *2);

//            path.quadTo(600 , 700 , 900 , 1200);

            canvas.drawPath(path, paint);
            canvas.drawPath(path1, paint1);
        canvas.drawPath(path2, paint1);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
    }
}
