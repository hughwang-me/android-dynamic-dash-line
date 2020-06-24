package com.uwjx.line;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.Nullable;

public class DynamicDashLineView2 extends SurfaceView implements SurfaceHolder.Callback {

    private int mWidth;
    private int mHeight;

    private Paint paint;
    private Path path;

    private SurfaceHolder holder;

    private int i = 0;

    public DynamicDashLineView2(Context context) {
        super(context);
        init();
    }

    public DynamicDashLineView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DynamicDashLineView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(20f);

        path = new Path();

        holder = this.getHolder();
        holder.addCallback(this);
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

        for (int i = 0; i <100 ; i++) {
//            canvas.restore();
            canvas.drawLine( 0f , 100f , 300f , 100f , paint);

//        DashPathEffect dashPathEffect1 = new DashPathEffect(new float[]{60, 60}, 0);
//        paint.setPathEffect(dashPathEffect1);

            paint.setColor(Color.RED);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(30);
            paint.setPathEffect(new DashPathEffect(new float[] {15, 5}, i));

            path.reset();
            path.moveTo(0, mHeight/2.0f  );
            path.lineTo(mWidth, mHeight/2.0f  );
            canvas.drawPath(path, paint);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        new Thread(new LineThread()).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    class LineThread implements Runnable {
        @Override
        public void run() {
//            while (i <1000){
//                i++;
//                invalidate();
//            }
        }
    }
}
