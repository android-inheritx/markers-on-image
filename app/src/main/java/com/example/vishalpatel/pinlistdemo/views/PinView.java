package com.example.vishalpatel.pinlistdemo.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.example.vishalpatel.pinlistdemo.R;
import com.example.vishalpatel.pinlistdemo.views.subscaleviews.Model;
import com.example.vishalpatel.pinlistdemo.views.subscaleviews.SubsamplingScaleImageView;

import java.util.ArrayList;


public class PinView extends SubsamplingScaleImageView {

    private ArrayList<PointF> sPin = new ArrayList<>();
    private ArrayList<String> pinNames = new ArrayList<>();
    private Bitmap pinBitmap;

    public PinView(Context context) {
        this(context, null);
    }

    public PinView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    public boolean setPin(PointF sPin, String name, boolean isShowOrAdd) {
        if (pinNames.contains(name)) {
            return false;
        } else {

            // isShowOrAdd is used only for If we want to add only one marker /only shows markers on Image

            if (isShowOrAdd) {
                this.sPin.add(sPin);
                pinNames.add(name);
                initialise();
                invalidate();
                return true;
            } else {
                if (pinNames.size() < 1) {
                    this.sPin.add(sPin);
                    pinNames.add(name);
                    initialise();
                    invalidate();
                    return true;
                } else {
                    return false;
                }
            }


        }
    }

    public PointF getPin(String name) {

        return sPin.get(pinNames.indexOf(name));
    }

    public boolean removePin(String name) {
        if (pinNames.contains(name)) {
            sPin.remove(pinNames.indexOf(name));
            pinNames.remove(name);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> getPinNames() {
        return pinNames;
    }

    private void initialise() {
        float density = getResources().getDisplayMetrics().densityDpi;
        pinBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.pushpin_blue);
        float w = (density / 420f) * pinBitmap.getWidth();
        float h = (density / 420f) * pinBitmap.getHeight();
        pinBitmap = Bitmap.createScaledBitmap(pinBitmap, (int) w, (int) h, true);
    }

    ArrayList<Model> canvasArray = new ArrayList();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw pinBitmap before image is ready so it doesn't move around during setup.
        if (!isReady()) {
            return;
        }
        canvasArray.clear();

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        for (PointF point : sPin) {
            if (point != null && pinBitmap != null) {

                PointF vPin = sourceToViewCoord(point);
                float vX = vPin.x - (pinBitmap.getWidth() / 2);
                float vY = vPin.y - pinBitmap.getHeight();
                canvas.drawBitmap(pinBitmap, vX, vY, paint);
                Model model = new Model();
                model.setX(vX);
                model.setY(vY);
                model.setBitmapH(pinBitmap.getHeight());
                model.setBitmapW(pinBitmap.getWidth());
                model.setPoint(point);
                canvasArray.add(model);
            }
        }
    }





    public ArrayList<Model> setOnPinClick() {
        return canvasArray;
    }

    interface OnPinClick {
        void pinClick();
    }


}
