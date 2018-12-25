package co.pizzayum.pizzayum_android.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.models.PizzaDetailsModel;
import co.pizzayum.pizzayum_android.models.Topping;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;

public class ToppingSliderAdapter extends RecyclerView.Adapter<ToppingSliderAdapter.MyViewHolder> {

    private Context mContext;
    private List<PizzaDetailsModel> item_list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price;
        public ImageView pizza_pic;
        RelativeLayout container ;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.topping_label);
            pizza_pic = view.findViewById(R.id.topping_pic);
            price = view.findViewById(R.id.topping_price);
            container = view.findViewById(R.id.container);
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
        String topping_p = mContext.getString(R.string.Rs);
        switch (PizzaConstants.SELECTED_PIZZA_SIZE){
            case "Regular":
                topping_p += model.getRegular();
                break;
            case "Medium":
                topping_p += model.getMedium();
                break;
            case "Large":
                topping_p += model.getLarge();
                break;
            default:
                Log.e("CrustLog","Error in Crust Pricing");
        }

        holder.price.setText(topping_p);
        holder.name.setText(model.getTopping_name());
        holder.pizza_pic.setImageDrawable(mContext.getResources().getDrawable(Integer.parseInt(model.getUrl())));
        if (model.isTopping_button_flag()) {
            holder.name.setTextColor(Color.parseColor("#FF5733"));
            holder.container.setBackgroundResource(R.drawable.size_slider_item_bg_selected);
        } else {
            holder.name.setTextColor(Color.parseColor("#aaaaaa"));
            holder.container.setBackgroundResource(R.drawable.size_slider_item_bg);
        }
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }
}
