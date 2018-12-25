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
import co.pizzayum.pizzayum_android.models.PizzaOrderTableModel;
import co.pizzayum.pizzayum_android.utility.DatabaseHelper;

public class HistoryBilling extends RecyclerView.Adapter<HistoryBilling.MyViewHolder> {

    private Context mContext;
    private List<OrderDetailItem> pizzaDetailsModelList;
    private int counter_value = 0;
    DatabaseHelper db ;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, content, item_total, pizza_size;
        public ImageView thumbnail;
        public View card_view, btn_active;

        public ImageView plus, minus;
        TextView counter;

        LinearLayout customisation_container;
        TextView customisation_btn, extra_toppings, extra_crust;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            content = view.findViewById(R.id.content);
            pizza_size = view.findViewById(R.id.pizza_size);
            thumbnail = view.findViewById(R.id.thumbnail);

            customisation_container = view.findViewById(R.id.customisation_container);
            customisation_btn = view.findViewById(R.id.customisation_btn);
            extra_toppings = view.findViewById(R.id.extra_toppings);
            extra_crust = view.findViewById(R.id.extra_crust);

            card_view = view.findViewById(R.id.card_view);

            plus = view.findViewById(R.id.plus);
            minus = view.findViewById(R.id.minus);
            counter = view.findViewById(R.id.counter);

            item_total = view.findViewById(R.id.item_total);
        }
    }

    public HistoryBilling(Context mContext, List<OrderDetailItem> pizzaDetailsModelList) {
        this.mContext = mContext;
        this.pizzaDetailsModelList = pizzaDetailsModelList;
        db = new DatabaseHelper(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bill_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
       // final OrderDetailItem model = pizzaDetailsModelList.get(position);

    }

    @Override
    public int getItemCount() {
        return pizzaDetailsModelList.size();
    }


}
