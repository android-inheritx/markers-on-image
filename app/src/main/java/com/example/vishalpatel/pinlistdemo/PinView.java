package com.example.vishalpatel.pinlistdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;

import com.example.vishalpatel.pinlistdemo.utils.subscaleviews.Model;
import com.example.vishalpatel.pinlistdemo.utils.subscaleviews.SubsamplingScaleImageView;

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

    public boolean setPin(PointF sPin, String name) {
        if (pinNames.contains(name)) {
            return false;
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

//    @Override
//    public boolean onTouchEvent(@NonNull MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//
//                Log.i("Pinkal", "onTouchEvent: X: " + x + " Y: " + y + "\n\n");
//
//                for (int i = 0; i < canvasArray.size(); i++) {
//
//                    Log.i("Pinkal", "onTouchEvent: Click X: " + canvasArray.get(i).getX() + "Click Y: " + canvasArray.get(i).getY());
//
////                    if (x == canvasArray.get(i).getX() && y == canvasArray.get(i).getY()) {
////                        Log.i("Pinkal", "onTouchEvent: Click: " + canvasArray.get(i).getPoint() + "\n\n");
////                    }
//
//                    if (x > canvasArray.get(i).getX() && x < canvasArray.get(i).getX() + canvasArray.get(i).getBitmapW() && y > canvasArray.get(i).getY() && y < canvasArray.get(i).getY() + canvasArray.get(i).getBitmapH()) {
//
//                        Log.e("TOUCHED 1", "X: " + x + " Y: " + y);
//
//
//                    }
//
////                    if (x > canvasArray.get(i).getBitmapW() / 2 && x < canvasArray.get(i).getBitmapW() / 2 + 200 && y > canvasArray.get(i).getBitmapH() / 2 && y < canvasArray.get(i).getBitmapH() / 2 + 200) {
////                        Log.e("TOUCHED 2", "X: " + x + " Y: " + y);
////                        //Bitmap touched
////                    }
//                }
//                Log.i("Pinkal", "-----------------------------------------------------------------------------------------------");
//
////                return true;
//        }
//        return super.onTouchEvent(event);
//    }

    public ArrayList<Model> setOnPinClick(){
        return canvasArray;
    }

    interface OnPinClick{
        void pinClick();
    }

//    private final Paint paint = new Paint();
//    private final PointF vPin = new PointF();
//    private PointF sPin;
//    private Bitmap pinBitmap;
//
//    public PinView(Context context) {
//        this(context, null);
//    }
//
//    public PinView(Context context, AttributeSet attr) {
//        super(context, attr);
//        initialise();
//    }
//
//    public void setPin(PointF sPin) {
//        this.sPin = sPin;
//        initialise();
//        invalidate();
//    }
//
//    private void initialise() {
//        float density = getResources().getDisplayMetrics().densityDpi;
//        pinBitmap = BitmapFactory.decodeResource(this.getResources(), drawable.pushpin_blue);
//        float w = (density/420f) * pinBitmap.getWidth();
//        float h = (density/420f) * pinBitmap.getHeight();
//        pinBitmap = Bitmap.createScaledBitmap(pinBitmap, (int)w, (int)h, true);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        // Don't draw pinBitmap before image is ready so it doesn't move around during setup.
//        if (!isReady()) {
//            return;
//        }
//
//        paint.setAntiAlias(true);
//
//        if (sPin != null && pinBitmap != null) {
//            sourceToViewCoord(sPin, vPin);
//            float vX = vPin.x - (pinBitmap.getWidth()/2);
//            float vY = vPin.y - pinBitmap.getHeight();
//            canvas.drawBitmap(pinBitmap, vX, vY, paint);
//        }
//
//    }

}
