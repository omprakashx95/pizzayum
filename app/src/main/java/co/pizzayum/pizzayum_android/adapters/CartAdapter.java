package co.pizzayum.pizzayum_android.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.activities.ItemDetails;
import co.pizzayum.pizzayum_android.models.PizzaDetailsModel;
import co.pizzayum.pizzayum_android.models.PizzaOrderTableModel;
import co.pizzayum.pizzayum_android.utility.DatabaseHelper;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context mContext;
    private List<PizzaOrderTableModel> pizzaDetailsModelList;
    private int counter_value = 0;
    OnDataChangeListener mOnDataChangeListener;
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

    public CartAdapter(Context mContext, List<PizzaOrderTableModel> pizzaDetailsModelList) {
        this.mContext = mContext;
        this.pizzaDetailsModelList = pizzaDetailsModelList;
        db = new DatabaseHelper(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PizzaOrderTableModel model = pizzaDetailsModelList.get(position);
        holder.title.setText(model.getProduct_name());
        holder.content.setText(model.getProduct_content());
        holder.pizza_size.setText(model.getSize());
        holder.extra_toppings.setText(model.getTopping_details());
        holder.extra_crust.setText(model.getCrust_details());
        holder.counter.setText(model.getProduct_quantity());
        holder.item_total.setText(mContext.getString(R.string.Rs) + model.getBill());

        if (model.getTopping_id() == null && model.getCrust_id() == null) {
            holder.customisation_container.setVisibility(View.GONE);
        } else if (model.getTopping_id() == null) {
            holder.extra_toppings.setVisibility(View.GONE);
        } else if (model.getCrust_id() == null) {
            holder.extra_crust.setVisibility(View.GONE);
        }

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(model.getProduct_quantity());
                count++;

                String updated_bill = String.valueOf(count * (
                        Integer.parseInt(model.getPizza_bill()) + Integer.parseInt(model.getCrust_bill())
                                + Integer.parseInt(model.getTopping_bill()) + Integer.parseInt(model.getExtra_cheese())));

                model.setProduct_quantity(String.valueOf(count));
                model.setBill(updated_bill);
                pizzaDetailsModelList.set(position, model);
                db.updateOrder(model);

                PizzaOrderTableModel m = pizzaDetailsModelList.get(position);
                holder.counter.setText(m.getProduct_quantity());
                holder.item_total.setText(mContext.getString(R.string.Rs) + m.getBill());

                if (mOnDataChangeListener != null) {
                    mOnDataChangeListener.onDataChanged(pizzaDetailsModelList.size());
                }
            }
        });

        holder.minus.setTag(position);
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(model.getProduct_quantity());

                if (count > 1) {
                    count--;
                    String updated_bill = String.valueOf(count * (
                            Integer.parseInt(model.getPizza_bill()) + Integer.parseInt(model.getCrust_bill())
                                    + Integer.parseInt(model.getTopping_bill()) + Integer.parseInt(model.getExtra_cheese())));
                    model.setProduct_quantity(String.valueOf(count));
                    model.setBill(updated_bill);
                    pizzaDetailsModelList.set(position, model);
                    db.updateOrder(model);
                    PizzaOrderTableModel m = pizzaDetailsModelList.get(position);
                    holder.counter.setText(m.getProduct_quantity());
                    holder.item_total.setText(mContext.getString(R.string.Rs) + m.getBill());

                    if (mOnDataChangeListener != null) {
                        mOnDataChangeListener.onDataChanged(pizzaDetailsModelList.size());
                    }
                } else {

                    db.deleteOrder(model);
                    pizzaDetailsModelList.remove(position);
                    notifyDataSetChanged();
                    if (mOnDataChangeListener != null) {
                        mOnDataChangeListener.onDataChanged(pizzaDetailsModelList.size());
                    }
                }
            }
        });

        holder.customisation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mContext.startActivity(new Intent(mContext, ItemDetails.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pizzaDetailsModelList.size();
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        mOnDataChangeListener = onDataChangeListener;
    }

    public interface OnDataChangeListener {
        public void onDataChanged(int size);
    }
}
