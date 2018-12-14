package co.pizzayum.pizzayum_android.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.models.PizzaDetailsModel;
import co.pizzayum.pizzayum_android.utility.CToL;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;

public class CrustSliderAdapter extends RecyclerView.Adapter<CrustSliderAdapter.MyViewHolder> {

    private Context mContext;
    private List<PizzaDetailsModel> item_list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item;

        public MyViewHolder(View view) {
            super(view);
            item = view.findViewById(R.id.crust_name);
        }
    }

    public CrustSliderAdapter(Context mContext, List<PizzaDetailsModel> item_list) {
        this.mContext = mContext;
        this.item_list = item_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crust_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        PizzaDetailsModel model = item_list.get(position);
        holder.item.setText(model.getName());
        if (model.isCrust_button_flag()){
            holder.item.setTextColor(Color.parseColor("#FF5733"));
        }else {
            holder.item.setTextColor(Color.parseColor("#aaaaaa"));
        }
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }
}
