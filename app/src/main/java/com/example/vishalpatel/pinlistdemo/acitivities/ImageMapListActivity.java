package com.example.vishalpatel.pinlistdemo.acitivities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;

import com.example.vishalpatel.pinlistdemo.adapters.PinAdapter;
import com.example.vishalpatel.pinlistdemo.models.PinModel;
import com.example.vishalpatel.pinlistdemo.views.PinView;
import com.example.vishalpatel.pinlistdemo.R;
import com.example.vishalpatel.pinlistdemo.utils.Contasts.Constants;
import com.example.vishalpatel.pinlistdemo.views.subscaleviews.ImageSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageMapListActivity extends AppCompatActivity {


    @BindView(R.id.rvCaptionList)
    RecyclerView rvCaptionList;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;
    @BindView(R.id.pinView)
    PinView pinView;

    PinAdapter adapter;
    ArrayList<PinModel> markersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_markers_list);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Points");
        initPinView();
        setRecycleView();

        if (markersList != null && markersList.size() > 0) {
            for (int i = 0; i < markersList.size(); i++) {
                String xPoint = String.valueOf(markersList.get(i).x_cordinate_pin);
                String yPoint = String.valueOf(markersList.get(i).y_cordinate_pin);
                String captionName = String.valueOf(markersList.get(i).captionName);
                pinView.setPin(new PointF(Float.parseFloat(xPoint), Float.parseFloat(yPoint)), captionName, true);
            }
        } else {
            markersList = new ArrayList<>();
        }

    }

    private void setRecycleView() {
        // Get list of Markers from secure local storage.
        markersList = Hawk.get(Constants.MARKERS_LIST);
        // Initialization of Recycleview
        rvCaptionList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PinAdapter(markersList, this);
        rvCaptionList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initPinView() {
        // set map Image to View
        pinView.setImage(ImageSource.asset(Constants.MAP_IMAGE));
        pinView.setMinimumDpi(50);
        pinView.setMaxScale(2F);
    }

    // Floating Action Button(+) On Click
    @OnClick(R.id.fabAdd)
    void onAddButtonClick() {
        Intent intent = new Intent(ImageMapListActivity.this, FullImagePinActivity.class);
        startActivityForResult(intent, Constants.REQ_SET_PIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQ_SET_PIN && resultCode == RESULT_OK) {

            if (pinView != null) {

                String point_X = data.getStringExtra(Constants.POINT_X);
                String point_Y = data.getStringExtra(Constants.POINT_Y);
                String captionName = data.getStringExtra(Constants.CAPTIONNAME);
                pinView.setPin(new PointF(Float.parseFloat(point_X), Float.parseFloat(point_Y)), captionName, true);
                setRecycleView();

            }
        }

    }

}
