package co.pizzayum.pizzayum_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.models.HistorySortedModel;
import co.pizzayum.pizzayum_android.models.OrderDetailItem;
import co.pizzayum.pizzayum_android.utility.DatabaseHelper;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context mContext;
    private List<HistorySortedModel> pizzaDetailsModelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView items_name, order_date, bill;

        public MyViewHolder(View view) {
            super(view);
            items_name = view.findViewById(R.id.items_name);
            order_date = view.findViewById(R.id.order_date);
            bill = view.findViewById(R.id.bill);
        }
    }

    public HistoryAdapter(Context mContext, List<HistorySortedModel> pizzaDetailsModelList) {
        this.mContext = mContext;
        this.pizzaDetailsModelList = pizzaDetailsModelList;
    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final HistorySortedModel model = pizzaDetailsModelList.get(position);

        String item ="";
        List<OrderDetailItem> data = model.getHistory_slider_data();
        for (int i = 0 ; i< data.size(); i++){
            item += data.get(i).getQuantity()+ " x " + data.get(i).getName() ;
            if (i== data.size()-1){
                item += " ";
            }else
                item += ", ";

        }
        String bill = mContext.getString(R.string.Rs);
        String time = "";
        if (data.size()>0){
            bill += data.get(0).getOrderTotal();
            time = data.get(0).getCreatedAt();
        }

        holder.items_name.setText(item);
        holder.bill.setText(bill);
        holder.order_date.setText(time);
    }

    @Override
    public int getItemCount() {
        return pizzaDetailsModelList.size();
    }

}
