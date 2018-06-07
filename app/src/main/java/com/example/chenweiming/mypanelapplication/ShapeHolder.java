package com.example.chenweiming.mypanelapplication;

import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.Keep;


/**
 * Created by edwardlu on 2018/2/21.
 */

public class ShapeHolder {
    @Keep
    private float x = 0, y = 0;
    private ShapeDrawable shape;
    private int color;
    private RadialGradient gradient;
    private float alpha = 1f;
    private Paint paint;

    public void setPaint(Paint value) {
        paint = value;
    }
    public Paint getPaint() {
        return paint;
    }

    @Keep
    public void setX(float value) {
        x = value;
    }

    @Keep
    public float getX() {
        return x;
    }

    @Keep
    public void setY(float value) {
        y = value;
    }

    @Keep
    public float getY() {
        return y;
    }
    public void setShape(ShapeDrawable value) {
        shape = value;
    }
    public ShapeDrawable getShape() {
        return shape;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int value) {
        shape.getPaint().setColor(value);
        color = value;
    }
    public void setGradient(RadialGradient value) {
        gradient = value;
    }
    public RadialGradient getGradient() {
        return gradient;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        shape.setAlpha((int)((alpha * 255f) + .5f));
    }

    @Keep
    public float getWidth() {
        return shape.getShape().getWidth();
    }

    @Keep
    public void setWidth(float width) {
        Shape s = shape.getShape();
        s.resize(width, s.getHeight());
    }

    @Keep
    public float getHeight() {
        return shape.getShape().getHeight();
    }

    @Keep
    public void setHeight(float height) {
        Shape s = shape.getShape();
        s.resize(s.getWidth(), height);
    }

    public ShapeHolder(ShapeDrawable s) {
        shape = s;
    }
}
