package com.example.vishalpatel.pinlistdemo.views.subscaleviews;

import android.graphics.PointF;

public class Model {
    float x;
    float y;
    int bitmapH;

    public int getBitmapH() {
        return bitmapH;
    }

    public void setBitmapH(int bitmapH) {
        this.bitmapH = bitmapH;
    }

    public int getBitmapW() {
        return bitmapW;
    }

    public void setBitmapW(int bitmapW) {
        this.bitmapW = bitmapW;
    }

    int bitmapW;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public PointF getPoint() {
        return point;
    }

    public void setPoint(PointF point) {
        this.point = point;
    }

    PointF point;
}
