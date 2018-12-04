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
import co.pizzayum.pizzayum_android.models.Topping;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;

public class ToppingAdapter extends RecyclerView.Adapter<ToppingAdapter.MyViewHolder> {

    private Context mContext;
    private List<Topping> item_list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView pizza_pic ;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.topping_label);
            pizza_pic = view.findViewById(R.id.topping_pic);
        }
    }

    public ToppingAdapter(Context mContext, List<Topping> item_list) {
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
        Topping tab_name = item_list.get(position);
        holder.name.setText(tab_name.getName());
        holder.pizza_pic.setImageDrawable(mContext.getResources().getDrawable(tab_name.getUrl()));
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }
}
