package com.example.fang.viewself;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Fkz on 2017/4/20.
 */

public class MyView extends View {

    private Paint mPaint;
    private static final String TAG = "MyView";
    private Path mPath;
    private Bitmap bitmap;
    private int left=0;
    private int top=0;
    private int wif=1080;
    private int hi=1920;
    private Bitmap cachembitmap;
    private Canvas cachemcanvas;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);


        iniPath();

        startThread();



        bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.zhang);
        cachembitmap=Bitmap.createBitmap(wif,hi,Bitmap.Config.ARGB_8888);
        cachemcanvas=new Canvas();
        cachemcanvas.setBitmap(cachembitmap);

    }

    private void startThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(10);
                        left++;
                        top++;
                        cachemcanvas.drawColor(Color.WHITE);
                        cachemcanvas.drawBitmap(bitmap,left,top,mPaint);
                        postInvalidate();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void iniPath() {
        mPaint=new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath=new Path();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float dx=event.getX();
                float dy=event.getY();
                mPath.moveTo(dx,dy);
                break;
            case MotionEvent.ACTION_MOVE:
                float mx=event.getX();
                float my=event.getY();
                mPath.lineTo(mx,my);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                float ux=event.getX();
                float uy=event.getY();
                mPath.lineTo(ux,uy);
                invalidate();
                break;

        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawBitmap(bitmap,left,top,mPaint);
        canvas.drawBitmap(cachembitmap,left,top,mPaint);
        canvas.drawPath(mPath,mPaint);
        Log.i(TAG, "onDraw: ");
        super.onDraw(canvas);
    }
}
