package com.example.vishalpatel.pinlistdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.vishalpatel.pinlistdemo.utils.BaseRecyclerAdapter;
import com.example.vishalpatel.pinlistdemo.utils.subscaleviews.ImageSource;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageMapListActivity extends Activity {


    @BindView(R.id.rvCaptionList)
    RecyclerView rvCaptionList;

    @BindView(R.id.toolBar)
    Toolbar toolBar;
@BindView(R.id.btnAdd)
Button btnAdd;

    @BindView(R.id.pinView)
    PinView pinView;

    public static int REQ_SET_PIN=1001;
    private PinAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_demo);
        ButterKnife.bind(this);

        toolBar.setTitle("Points");


        pinView.setImage(ImageSource.asset("map_image.jpg"));
        pinView.setMinimumDpi(50);
        pinView.setMaxScale(2F);

        rvCaptionList.setLayoutManager(new LinearLayoutManager(this));




        ArrayList<PinModel> pinList = Hawk.get("pointList");


        if (pinList !=null && pinList.size()>0){

            for (int i = 0; i < pinList.size(); i++) {
                String xPoint = String.valueOf(pinList.get(i).x_cordinate_pin);
                String yPoint = String.valueOf(pinList.get(i).y_cordinate_pin);
                String captionName = String.valueOf(pinList.get(i).captionName);
                pinView.setPin(new PointF(Float.parseFloat(xPoint),Float.parseFloat(yPoint)),captionName);
            }



        }else {
            pinList = new ArrayList<>();
        }


         adapter = (PinAdapter) new PinAdapter(pinList, this).setRecycleOnItemClickListener(mRecycleOnItemClickListener);
        rvCaptionList.setAdapter(adapter);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ImageMapListActivity.this, FullImagePinActivity.class);
                startActivityForResult(intent,REQ_SET_PIN);
            }
        });


    }

    BaseRecyclerAdapter.RecycleOnItemClickListener mRecycleOnItemClickListener = new BaseRecyclerAdapter.RecycleOnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

            if (view.getId() == R.id.itemView) {
                //Navigate to New Screen

            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SET_PIN){

            if(pinView != null){

                String point_X = data.getStringExtra("point_X");
                String point_Y = data.getStringExtra("point_Y");
                String  captionName = data.getStringExtra("captionName");

                pinView.setPin(new PointF(Float.parseFloat(point_X),Float.parseFloat(point_Y)),captionName);
                setResult(REQ_SET_PIN);
                adapter.notifyDataSetChanged();

            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(ImageMapListActivity.this, FullImagePinActivity.class);
                startActivityForResult(intent,REQ_SET_PIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
