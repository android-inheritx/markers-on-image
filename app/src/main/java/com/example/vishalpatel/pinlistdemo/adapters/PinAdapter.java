package com.example.vishalpatel.pinlistdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vishalpatel.pinlistdemo.models.PinModel;
import com.example.vishalpatel.pinlistdemo.R;
import com.example.vishalpatel.pinlistdemo.utils.BaseRecyclerAdapter;
import com.example.vishalpatel.pinlistdemo.utils.Contasts.Constants;
import java.util.ArrayList;

import butterknife.BindView;

public class PinAdapter extends BaseRecyclerAdapter<PinAdapter.PinViewHolder, PinModel> {

    private ArrayList<PinModel> list;
    Context context;


    public PinAdapter(ArrayList<PinModel> list, Context context) {
        super(list);
        this.list = list;
        this.context = context;
    }

    class PinViewHolder extends BaseRecyclerAdapter.ViewHolder {


        @BindView(R.id.tvCaptionName)
        TextView tvCaptionName;
        @BindView(R.id.tvCordinates)
        TextView tvCordinates;
        @BindView(R.id.itemView)
        LinearLayout itemView;
        @BindView(R.id.itemPinView)
        ImageView itemPinView;

        PinViewHolder(View itemView) {
            super(itemView);
            clickableViews(itemView);

        }
    }

    @Override
    public PinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PinViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_caption, parent, false));
    }

    @Override
    public void onBindViewHolder(PinViewHolder holder, int position) {

        PinModel pinModel = list.get(position);
        holder.tvCaptionName.setText(pinModel.getCaptionName());
        holder.tvCordinates.setText(pinModel.getX_cordinate_pin() + "\n" + pinModel.getY_cordinate_pin());
        holder.itemPinView.setImageBitmap(Constants.StringToBitMap(pinModel.getPinImage()));

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}