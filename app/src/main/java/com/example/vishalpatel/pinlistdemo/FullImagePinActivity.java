package com.example.vishalpatel.pinlistdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.vishalpatel.pinlistdemo.utils.subscaleviews.ImageSource;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FullImagePinActivity extends Activity {


    @BindView(R.id.pinFullView)
    PinView pinView;

    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.etCaption)
    EditText etCaption;
    @BindView(R.id.btnSave)
    Button btnSave;

    PointF addedPoint;

    boolean isPinAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        ButterKnife.bind(this);

        pinView.setImage(ImageSource.asset("map_image.jpg"));
        pinView.setMinimumDpi(50);
        pinView.setMaxScale(2F);
        toolBar.setTitle("Plot Map");


        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (pinView.isReady()) {
                    addedPoint = pinView.viewToSourceCoord(e.getX(), e.getY());
                    isPinAdded = true;
                    setPin(addedPoint);
                } else {
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {

                return true;
            }
        });
        pinView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
    }


    private void setPin(PointF center) {
        if (pinView.isReady()) {
            pinView.setPin(center, center.x + "_" + center.y);
        }
    }

    @OnClick(R.id.btnSave)
    void onSaveClick(View view) {


        if (!etCaption.getText().toString().trim().isEmpty()) {
            if (isPinAdded) {
                List<PinModel> previousList = new ArrayList<>();

                previousList = Hawk.get("pointList");


                if (previousList != null && previousList.size()>0){
                    if (previousList.contains(addedPoint.x + "_" + addedPoint.y)) {
                        Toast.makeText(this, "This Location is already Added please Try another.", Toast.LENGTH_SHORT).show();
                        pinView.removePin(addedPoint.x + "_" + addedPoint.y);
                    } else {


                        savePointList(previousList);
                    }
                }else {
                    previousList = new ArrayList<>();
                    savePointList(previousList);
                }


            } else {
                Toast.makeText(this, "Please add Pin into Map", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please add caption", Toast.LENGTH_SHORT).show();
        }


    }

    private void savePointList(List<PinModel> previousList) {
        PinModel pinModel = new PinModel();
        pinModel.setId(addedPoint.x + "_" + addedPoint.y);
        pinModel.setX_cordinate_pin(addedPoint.x);
        pinModel.setY_cordinate_pin(addedPoint.y);
        pinModel.setCaptionName(etCaption.getText().toString().trim());
        previousList.add(pinModel);
        Hawk.put("pointList", previousList);

        Intent intent = getIntent();
        intent.putExtra("point_X", String.valueOf(addedPoint.x));
        intent.putExtra("point_Y",String.valueOf(addedPoint.y));
        intent.putExtra("captionName", etCaption.getText().toString().trim());

        setResult(RESULT_OK, intent);
        finish();
    }
}
