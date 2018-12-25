package co.pizzayum.pizzayum_android.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.models.PizzaDetailsModel;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;

public class SizeSliderAdapter extends RecyclerView.Adapter<SizeSliderAdapter.MyViewHolder> {

    private Context mContext;
    private List<PizzaDetailsModel> item_list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView size, price;
        RelativeLayout container ;
        public MyViewHolder(View view) {
            super(view);
            container = view.findViewById(R.id.container);
            price = view.findViewById(R.id.pizza_price);
            size = view.findViewById(R.id.pizza_size);
        }
    }

    public SizeSliderAdapter(Context mContext, List<PizzaDetailsModel> item_list) {
        this.mContext = mContext;
        this.item_list = item_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.size_slider_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        PizzaDetailsModel model = item_list.get(position);

        String price_s = mContext.getString(R.string.Rs) + model.getPizza_price();
        holder.price.setText(price_s);
        holder.size.setText(model.getName());
        if (PizzaConstants.SIZE_ENABLE_POSITION == position){
            holder.size.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.container.setBackgroundResource(R.drawable.size_slider_item_bg_selected);
        }else {
            holder.size.setTextColor(Color.parseColor("#aaaaaa"));
            holder.container.setBackgroundResource(R.drawable.size_slider_item_bg);
        }
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }
}
