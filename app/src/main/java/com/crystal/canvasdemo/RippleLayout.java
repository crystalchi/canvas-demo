package com.crystal.canvasdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * 水纹效果
 * Created by xpchi on 2016/7/30.
 */
public class RippleLayout extends LinearLayout {

    private Paint mPaint = new Paint(); //画笔
    private View mTouchTarget; //被触摸的view
    //点击处的坐标
    private int mCenterX;
    private int mCenterY;
    //半径
    private int mMaxRevealRadius/* = 24*/;
    private int mRevealRaidus; //显示的半径
    private final int INVALIDATE_DURATION = 40; //重绘延迟时间
    private int[] mLocationInScreen = new int[2]; //保存整个手机屏幕的绝对坐标
    private boolean mIsPressed; //按压标识
    private boolean isCancelDraw = false; //是否结束重绘
    //目标view宽高
    private int mTargetWidth;
    private int mTargetHeight;
    private int mMinValue;
    private int mAvgRadiusValue;

    public RippleLayout(Context context) {
        super(context);
        init();
    }

    public RippleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RippleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.getLocationOnScreen(mLocationInScreen);
    }


    /**
     * touch事件分发事件
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.getLocationOnScreen(mLocationInScreen);
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //返回当前被触摸的view
                View touchTarget = getTouchTarget(this, x, y);
                if(touchTarget != null && touchTarget.isClickable() && touchTarget.isEnabled()){
                    mTouchTarget = touchTarget;
                    mCenterX = (int) ev.getX();
                    mCenterY = (int) ev.getY();
                    mRevealRaidus = 0;
                    mIsPressed = true;
                    isCancelDraw = false;
                    mTargetWidth = mTouchTarget.getMeasuredWidth();
                    mTargetHeight = mTouchTarget.getMeasuredHeight();
                    mMinValue = Math.min(mTargetWidth, mTargetHeight);
                    mAvgRadiusValue = mMinValue / 4;
                    int[] location = new int[2];
                    mTouchTarget.getLocationOnScreen(location);
                    if(mTargetWidth > mTargetHeight){
                        int left = location[0] - mLocationInScreen[0];
                        int distanceForCenterX = mCenterX - left;
                        mMaxRevealRadius = Math.max(distanceForCenterX, mTargetWidth - distanceForCenterX);
                    }else{
                        int top = location[1] - mLocationInScreen[1];
                        int distanceForCenterY = mCenterY - top;
                        mMaxRevealRadius = Math.max(distanceForCenterY, mTargetHeight - distanceForCenterY);
                    }

                    postInvalidateDelayed(INVALIDATE_DURATION);
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsPressed = false;
                postInvalidateDelayed(INVALIDATE_DURATION);

                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas); //绘制子控件

        if(isCancelDraw || mTouchTarget == null){
            return;
        }
        //获取点击view的范围left、top、right、bottom
        this.getLocationOnScreen(mLocationInScreen); //在绘制中再次更新最新的绝对坐标(弹出键盘，屏幕绝对坐标是否会变可测试)
        int[] location = new int[2]; //保存点击的目标view在屏幕中的绝对坐标
        mTouchTarget.getLocationOnScreen(location); //赋值坐标给location数组
        //目标view的范围
        int left = location[0] - mLocationInScreen[0];
        int top = location[1] - mLocationInScreen[1];
        int right = left + mTouchTarget.getMeasuredWidth();
        int bottom = top + mTouchTarget.getMeasuredHeight();

        if(mRevealRaidus <= mMinValue / 2){
            mRevealRaidus += mAvgRadiusValue;
        }else{
            mRevealRaidus += mAvgRadiusValue * 2;
        }
        /*mRevealRaidus += 3; //每次半径递增值为3*/
        canvas.save();
        /*Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE); //只画边框
        canvas.drawRect(left, top, right, bottom, paint);*/
        canvas.clipRect(left, top, right, bottom); //裁剪区域(点击的view)，主要让点击效果只能在裁剪区域内看到，以外看不到。
        canvas.drawCircle(mCenterX, mCenterY, mRevealRaidus, mPaint);
        canvas.restore();

        if(mRevealRaidus <= mMaxRevealRadius){
            //考虑性能的原因，这里对指定的区域进行绘制，其他区域不需要绘画，只需要对点击的区域绘制。
            postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
        }else{
            if(!mIsPressed){ //按压结束（即点击结束），此时应该结束重绘
                isCancelDraw = true; //结束重绘
                postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
            }
        }

    }

    /**
     * 初始化
     */
    private void init(){
        setWillNotDraw(false); //允许执行onDraw()
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
    }


    /**
     * 获取当前在屏幕中所点击位置坐标所属的目标view
     * @param view //当前整个父布局
     * @param x //点击位置的横坐标
     * @param y //点击位置的纵坐标
     * @return //返回点击坐标处的目标view
     */
    private View getTouchTarget(View view, int x, int y){
        View target = null;
        ArrayList<View> allTouchableView = view.getTouchables(); //获取该view中所有可触的子view
        for(View touchableView : allTouchableView){
            if(isTouchPointInView(touchableView, x, y)){ //当前touchableView就是当前触摸点坐标位置的view
                target = touchableView; //保存当前触摸的view
                break; //跳出循环
            }
        }
        return target;
    }

    /**
     * 手指所点击屏幕的坐标在当前view坐标范围内
     * @param view 当前所点击的view
     * @param x //点击位置的横坐标
     * @param y //点击位置的纵坐标
     * @return true：在view的坐标范围内，false：不在view的坐标范围内
     */
    private boolean isTouchPointInView(View view, int x, int y){
        int[] location = new int[2];
        view.getLocationOnScreen(location); //赋值view在当前屏幕中的绝对坐标给location数组
        int viewLeft = location[0]; //view的left
        int viewTop = location[1]; //view的top
        int viewRight = viewLeft + view.getMeasuredWidth(); //view的right
        int viewBottom = viewTop + view.getMeasuredHeight(); //view的top
        if(view.isClickable() && x >= viewLeft
                && x <= viewRight && y >= viewTop && y <= viewBottom){ //手指所点击的坐标是在此范围内
            return true;
        }
        return false;
    }


}
