package com.example.chenweiming.mypanelapplication;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edwardlu on 2018/2/21.
 */

public class BounceBallsLoading extends View {

    public final ArrayList<ShapeHolder> mBalls = new ArrayList<ShapeHolder>();
    public final List<Animator> mAnimList = new ArrayList<>();
    private AnimatorSet mAnimatorSet;

    private int mCircleMax;
    private int mCircleMin;
    private int mColor;
    private int mAnimDuration;
    private int mAnimDelay;
    private int mBallNumber = 3;
    private int mBallGap;

    public BounceBallsLoading(Context context) {
        this(context, null);
    }

    public BounceBallsLoading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceBallsLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.BounceBallsLoading, 0, 0);
            mCircleMax = a.getDimensionPixelSize(R.styleable.BounceBallsLoading_circleMax, dip2px(context, 15));
            mCircleMin = a.getDimensionPixelSize(R.styleable.BounceBallsLoading_circleMin, dip2px(context, 0));
            mColor = a.getColor(R.styleable.BounceBallsLoading_color, getResources().getColor(R.color.loading_dot_color));
            mAnimDuration = a.getInt(R.styleable.BounceBallsLoading_animDurationMs, 500);
            mBallNumber = a.getInt(R.styleable.BounceBallsLoading_ballNumber, 3);
            mBallGap = a.getDimensionPixelSize(R.styleable.BounceBallsLoading_ballGap, dip2px(context, 5));
            mAnimDelay = a.getInt(R.styleable.BounceBallsLoading_animDelayMs, 150);
        } finally {
            a.recycle();
        }
    }

    private void initBalls() {
        for (int i = 0; i < mBallNumber; i++) {
            addBall(i * (mCircleMax + mBallGap) + (mCircleMax - mCircleMin)/2, (mCircleMax - mCircleMin)/2);
            invalidate();
        }
    }

    private void addBall(float x, float y) {
        OvalShape circle = new OvalShape();
        circle.resize(mCircleMin, mCircleMin);
        ShapeDrawable drawable = new ShapeDrawable(circle);
        ShapeHolder shapeHolder = new ShapeHolder(drawable);
        shapeHolder.setX(x);
        shapeHolder.setY(y);
        Paint paint = drawable.getPaint(); //new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(mColor);
        paint.setStyle(Paint.Style.FILL);
        shapeHolder.setPaint(drawable.getPaint());
        mBalls.add(shapeHolder);
    }

    public void start() {
        if (getVisibility() == VISIBLE) {
            return;
        }
        initBalls();
        setVisibility(VISIBLE);
        if (mAnimatorSet == null) {
            mAnimatorSet = new AnimatorSet();
        }
        for (int i = 0; i < mBalls.size(); i++) {
            Animator anim = createAnimation(mBalls.get(i));
            anim.setStartDelay(mAnimDelay * i);
            mAnimList.add(anim);
        }
        mAnimatorSet.playTogether(mAnimList);
        mAnimatorSet.start();
    }

    public void cancel() {
        for (Animator anim : mAnimList) {
            anim.removeAllListeners();
        }
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
            mAnimatorSet = null;
        }
        setVisibility(INVISIBLE);
        mBalls.clear();
        mAnimList.clear();
    }

    private ObjectAnimator createAnimation(ShapeHolder ball) {
        PropertyValuesHolder pvhW = PropertyValuesHolder.ofFloat("width", ball.getWidth(), mCircleMax);
        PropertyValuesHolder pvhH = PropertyValuesHolder.ofFloat("height", ball.getHeight(), mCircleMax);
        PropertyValuesHolder pvTX = PropertyValuesHolder.ofFloat("x", ball.getX(), ball.getX() - (mCircleMax - mCircleMin)/2);
        PropertyValuesHolder pvTY = PropertyValuesHolder.ofFloat("y", ball.getY(), ball.getY() - (mCircleMax - mCircleMin)/2);
        ObjectAnimator animation = ObjectAnimator
                .ofPropertyValuesHolder(ball, pvhW, pvhH, pvTX, pvTY)
                .setDuration(mAnimDuration);
        animation.setStartDelay(mAnimDelay);
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.setRepeatMode(ValueAnimator.REVERSE);

        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });

        return animation;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Get the width measurement
        int size = View.resolveSize(getDesiredSquareSize(), widthMeasureSpec);
        //MUST call this to store the measurements
        setMeasuredDimension(size, mCircleMax);
    }

    private int getDesiredSquareSize() {
        // To ensure the widget layout has enough space for expandable circle
        return (mCircleMax * 3) + (mBallGap * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ShapeHolder holder: mBalls) {

            canvas.translate(holder.getX(), holder.getY());
            holder.getShape().draw(canvas);
            canvas.translate(-holder.getX(), -holder.getY());
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
