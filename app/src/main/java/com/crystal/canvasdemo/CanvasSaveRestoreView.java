package com.crystal.canvasdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xpchi on 2016/7/23.
 */
public class CanvasSaveRestoreView extends View{

    public CanvasSaveRestoreView(Context context) {
        super(context);
    }

    public CanvasSaveRestoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasSaveRestoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*canvas.drawColor(Color.RED);
        //保存的画布大小为全屏幕大小
        canvas.save();

        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);
        //保存画布大小为Rect(100, 100, 800, 800)
        canvas.save();

        canvas.clipRect(new Rect(200, 200, 700, 700));
        canvas.drawColor(Color.BLUE);
        //保存画布大小为Rect(200, 200, 700, 700)
        canvas.save();

        canvas.clipRect(new Rect(300, 300, 600, 600));
        canvas.drawColor(Color.BLACK);
        //保存画布大小为Rect(300, 300, 600, 600)
        canvas.save();

        canvas.clipRect(new Rect(400, 400, 500, 500));
        canvas.drawColor(Color.WHITE);

        //将栈顶的画布状态取出来，作为当前画布，并画成黄色背景
        canvas.restore();
        canvas.drawColor(Color.YELLOW);*/

        /*Paint paint = new Paint();
        paint.setTextSize(40);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);

        String text = "你好";
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        canvas.save();
        canvas.drawText(text, (getWidth() - rect.width()) / 2, getHeight() / 2 + rect.height() / 2, paint); //居中呈现
        canvas.restore();*/

        canvas.drawColor(Color.RED);

        //保存当前画布大小即整屏
        canvas.save();

        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);

        //恢复整屏画布
        canvas.restore();
    }
}
