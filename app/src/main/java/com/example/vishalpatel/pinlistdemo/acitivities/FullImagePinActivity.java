package com.example.vishalpatel.pinlistdemo.acitivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.vishalpatel.pinlistdemo.models.PinModel;
import com.example.vishalpatel.pinlistdemo.utils.CommonUtils;
import com.example.vishalpatel.pinlistdemo.utils.Contasts.Constants;
import com.example.vishalpatel.pinlistdemo.views.PinView;
import com.example.vishalpatel.pinlistdemo.R;
import com.example.vishalpatel.pinlistdemo.views.subscaleviews.ImageSource;
import com.orhanobut.hawk.Hawk;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FullImagePinActivity extends AppCompatActivity {


    @BindView(R.id.pinFullView)
    PinView pinView;


    @BindView(R.id.etCaption)
    EditText etCaption;
    @BindView(R.id.btnSave)
    Button btnSave;

    PointF addedPoint;

    boolean isPinAdded = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marker);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Add Marker");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initPinView();

        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (pinView.isReady() && isPinAdded) {
                    pinView.removePin(addedPoint.x + "_" + addedPoint.y);
                    addedPoint = pinView.viewToSourceCoord(e.getX(), e.getY());
                    isPinAdded = true;
                    setPin(addedPoint);
                } else {
                    addedPoint = pinView.viewToSourceCoord(e.getX(), e.getY());
                    isPinAdded = true;
                    setPin(addedPoint);
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

    private void initPinView() {
        pinView.setImage(ImageSource.asset("map_image.jpg"));
        pinView.setMinimumDpi(50);
        pinView.setMaxScale(2F);
    }


    private void setPin(PointF center) {
        if (pinView.isReady()) {
            pinView.setPin(center, center.x + "_" + center.y,false);
        }
    }

    @OnClick(R.id.btnSave)
    void onSaveClick() {

        if (!etCaption.getText().toString().trim().isEmpty()) {
            if (isPinAdded) {
                List<PinModel> previousList = new ArrayList<>();
                previousList = Hawk.get(Constants.MARKERS_LIST);

                if (previousList != null && previousList.size()>0){
                    if (previousList.contains(addedPoint.x + "_" + addedPoint.y)) {
                        // If Previous pin is added on same location then we will remove that pin and user will set that pin again.
                      //  Toast.makeText(this, "This Location is already Added please Try another.", Toast.LENGTH_SHORT).show();

                        CommonUtils.AlertBox(this,"","This Location is already Added please Try another.");
                        pinView.removePin(addedPoint.x + "_" + addedPoint.y);
                    } else {
                        savePointList(previousList);
                    }
                }else {
                    previousList = new ArrayList<>();
                    savePointList(previousList);
                }

            } else {

                CommonUtils.AlertBox(this,"","Please add Pin into Map");


            }
        } else {
            CommonUtils.AlertBox(this,"","Please add caption");
        }


    }

    private void savePointList(List<PinModel> previousList) {

        PinModel pinModel = new PinModel();
        pinModel.setId(addedPoint.x + "_" + addedPoint.y);
        pinModel.setX_cordinate_pin(addedPoint.x);
        pinModel.setY_cordinate_pin(addedPoint.y);
        pinModel.setCaptionName(etCaption.getText().toString().trim());
        pinModel.setPinImage(CommonUtils.BitMapToString(getBitmapFromView(pinView)));
        previousList.add(pinModel);
        Hawk.put(Constants.MARKERS_LIST, previousList);

        Intent intent = getIntent();
        intent.putExtra(Constants.POINT_X, String.valueOf(addedPoint.x));
        intent.putExtra(Constants.POINT_Y,String.valueOf(addedPoint.y));
        intent.putExtra(Constants.CAPTIONNAME, etCaption.getText().toString().trim());

        setResult(RESULT_OK, intent);
        finish();
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
