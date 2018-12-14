package co.pizzayum.pizzayum_android.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;

public class TabListAdapter extends RecyclerView.Adapter<TabListAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> item_list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item;

        public MyViewHolder(View view) {
            super(view);
            item = view.findViewById(R.id.tab_item);
        }
    }


    public TabListAdapter(Context mContext, List<String> item_list) {
        this.mContext = mContext;
        this.item_list = item_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tab_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String tab_name = item_list.get(position);
        holder.item.setText(tab_name);
        if (PizzaConstants.ENABLE_POSITION == position){
            holder.item.setTextColor(Color.parseColor("#fc2e2a"));
        }else {
            holder.item.setTextColor(Color.parseColor("#666666"));
        }
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }
}
