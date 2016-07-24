package com.crystal.canvasdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xpchi on 2016/7/23.
 */
public class CanvasView2 extends View {

    public CanvasView2(Context context) {
        super(context);
    }

    public CanvasView2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        Path cwPath = new Path();
        RectF cwRect = new RectF(50, 50, 250, 200);
        cwPath.addRect(cwRect, Path.Direction.CW); //指定顺时针画路径
        canvas.drawPath(cwPath, paint); //呈现路径

        Path ccwPath = new Path();
        RectF ccwRect = new RectF(50, 250, 250, 450);
        ccwPath.addRect(ccwRect, Path.Direction.CCW);
        canvas.drawPath(ccwPath, paint);

        paint.setColor(Color.DKGRAY);
        paint.setTextSize(30);
        canvas.drawTextOnPath("大爷不想说话", cwPath, 80, -10, paint); //文字顺时针环绕
        canvas.drawTextOnPath("去你大爷的，你个傻逼", ccwPath, 0, 10, paint); //文字逆时针环绕


    }
}
