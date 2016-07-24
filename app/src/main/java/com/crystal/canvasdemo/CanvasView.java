package com.crystal.canvasdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xpchi on 2016/7/22.
 */
public class CanvasView extends View {

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED); //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE); //描边
        paint.setStrokeWidth(5); //描边宽度

        Path path = new Path();
        path.moveTo(10, 10); //起点
        path.lineTo(10, 100); //第一条直线终点
        path.lineTo(300, 100); //第二条直线的终点
        path.close(); //闭环

        canvas.drawPath(path, paint); //在画板上呈现图形
    }
}
