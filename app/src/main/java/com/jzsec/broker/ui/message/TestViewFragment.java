package com.jzsec.broker.ui.message;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.jzsec.broker.R;
import com.jzsec.broker.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zhaopan on 2017/9/12.
 */

public class TestViewFragment extends BaseFragment {


    public static SupportFragment newInstance() {
        Bundle args = new Bundle();
        SupportFragment fragment = new TestViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return new MyView2(getContext());
    }

    public static class MyView3 extends View{
        private Paint mPaint;
        private Canvas mBufferCanvas;
        private Bitmap mBufferBitmap;

        public MyView3(Context context){
            super(context);
            init();
        }
        public MyView3(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init(){
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.GREEN);
            setBackgroundColor(Color.WHITE);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();
            switch(action){
                case MotionEvent.ACTION_DOWN:
                    if(null == mBufferBitmap){
                        mBufferBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                        mBufferCanvas = new Canvas(mBufferBitmap);
                    }
                    mBufferCanvas.drawCircle(event.getX(), event.getY(), 50, mPaint);
                    break;
                case MotionEvent.ACTION_UP:
                    invalidate();
                    break;
            }
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (mBufferBitmap == null) {
                return;
            }
            canvas.drawBitmap(mBufferBitmap,0,0,null);
        }
    }

    /**
     * 每次点击的时候在点击处画一个圆。我们先不使用双缓冲来实现：http://www.jianshu.com/p/efc0bebfd22e
     */
    public static class MyView2 extends View {

        private Paint mPaint;
        private List<Point> mPoints;

        public MyView2(Context context) {
            super(context);
            init();
        }

        public MyView2(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.GREEN);
            setBackgroundColor(Color.WHITE);
            mPoints = new ArrayList<>();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mPoints.add(new Point((int) event.getX(), (int) event.getY()));
                    break;
                case MotionEvent.ACTION_UP:
                    invalidate();
                    break;
            }
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (Point p : mPoints) {
                canvas.drawCircle(p.x, p.y, 50, mPaint);
            }
        }
    }

    // 自定义 view,需要实现 一个显式的构造函数，重写 onDraw 方法，一切的操作都在该方法上进行
    public static class MyView extends View {

        public MyView(Context context) {
            super(context);
        }

        /**
         * 要画图形，最起码要有三个对象：
         * 1.颜色对象 Color
         * 2.画笔对象 Paint
         * 3.画布对象 Canvas
         */

        @Override
        public void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub

            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            //设置字体大小
            paint.setTextSize(100);

            //让画出的图形是空心的
            paint.setStyle(Paint.Style.STROKE);
            //设置画出的线的 粗细程度
            paint.setStrokeWidth(5);
            //画出一根线
            canvas.drawLine(0, 0, 200, 200, paint);

            //画矩形
            canvas.drawRect(200, 500, 300, 300, paint);

            //画圆
            canvas.drawCircle(200, 200, 100, paint);
            //画出字符串 drawText(String text, float x, float y, Paint paint)
            // y 是 基准线 ，不是 字符串的 底部
            canvas.drawText("apple", 60, 60, paint);
            canvas.drawLine(0, 60, 500, 60, paint);

            //绘制图片
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher), 150, 150, paint);

            super.onDraw(canvas);
        }

    }

}
