package co.pizzayum.pizzayum_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.models.OrderDetailItem;
import co.pizzayum.pizzayum_android.utility.DatabaseHelper;

public class HistoryBilling extends RecyclerView.Adapter<HistoryBilling.MyViewHolder> {

    private Context mContext;
    private List<OrderDetailItem> pizzaDetailsModelList;
    private int counter_value = 0;
    DatabaseHelper db;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, thin_crust, topping, item_bill;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            thin_crust = view.findViewById(R.id.thin_crust);
            topping = view.findViewById(R.id.toppings);
            item_bill = view.findViewById(R.id.item_bill);
        }
    }

    public HistoryBilling(Context mContext, List<OrderDetailItem> pizzaDetailsModelList) {
        this.mContext = mContext;
        this.pizzaDetailsModelList = pizzaDetailsModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bill_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final OrderDetailItem model = pizzaDetailsModelList.get(position);
        String t = model.getQuantity()+ " x " +model.getName() + " ["+model.getSize()+"]";
        holder.title.setText(t);
        holder.item_bill.setText(mContext.getString(R.string.Rs) + model.getSubTotal());
        if (model.getCrustId() == null){
            holder.thin_crust.setVisibility(View.GONE);
        }else{
            holder.thin_crust.setText(model.getCrustId());
        }

        if (model.getToppingDetail() == null){
            holder.topping.setVisibility(View.GONE);
        }else{
            holder.topping.setText(model.getToppingDetail());
        }

    }

    @Override
    public int getItemCount() {
        return pizzaDetailsModelList.size();
    }

}
