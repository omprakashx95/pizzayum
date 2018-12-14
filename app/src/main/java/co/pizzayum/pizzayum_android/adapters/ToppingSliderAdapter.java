package co.pizzayum.pizzayum_android.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.models.PizzaDetailsModel;
import co.pizzayum.pizzayum_android.models.Topping;

public class ToppingSliderAdapter extends RecyclerView.Adapter<ToppingSliderAdapter.MyViewHolder> {

    private Context mContext;
    private List<PizzaDetailsModel> item_list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView pizza_pic ;

        public MyViewHolder(View view) {
            super(view);
             name = view.findViewById(R.id.topping_label);
             pizza_pic = view.findViewById(R.id.topping_pic);
        }
    }

    public ToppingSliderAdapter(Context mContext, List<PizzaDetailsModel> item_list) {
        this.mContext = mContext;
        this.item_list = item_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topping_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
         PizzaDetailsModel model = item_list.get(position);
         holder.name.setText(model.getTopping_name());
        if (model.isTopping_button_flag()){
            holder.name.setTextColor(Color.parseColor("#FF5733"));
        }else {
            holder.name.setTextColor(Color.parseColor("#aaaaaa"));
        }
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }
}
