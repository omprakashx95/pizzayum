package co.pizzayum.pizzayum_android.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.activities.ItemDetails;
import co.pizzayum.pizzayum_android.models.PizzaDetailsModel;
import co.pizzayum.pizzayum_android.services.Pizza;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;

public class PizzaListAdapter extends RecyclerView.Adapter<PizzaListAdapter.MyViewHolder> {

    private Context mContext;
    private List<PizzaDetailsModel> pizzaDetailsModelList;
    private int counter_value = 0 ;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, about, price_tag;
        public ImageView thumbnail ;
        public Button btn_minus, btn_plus;
        public View card_view, btn_active;

        public ImageView plus, minus ;
        TextView counter ;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            about = view.findViewById(R.id.about);
            price_tag = view.findViewById(R.id.price_tag);
            thumbnail = view.findViewById(R.id.thumbnail);


            card_view = view.findViewById(R.id.card_view);
            btn_active = view.findViewById(R.id.btn);

            plus = view.findViewById(R.id.plus);
            minus = view.findViewById(R.id.minus);
            counter = view.findViewById(R.id.counter);
        }
    }

    public PizzaListAdapter(Context mContext, List<PizzaDetailsModel> pizzaDetailsModelList) {
        this.mContext = mContext;
        this.pizzaDetailsModelList = pizzaDetailsModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pizza_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PizzaDetailsModel pizzaDetailsModel = pizzaDetailsModelList.get(position);
        holder.title.setText(pizzaDetailsModel.getName());
        holder.about.setText(pizzaDetailsModel.getContent());

        String price_s;
        if (pizzaDetailsModel.getMedium().equals("0") || pizzaDetailsModel.getLarge().equals("0")) {
            price_s = mContext.getString(R.string.Rs) + pizzaDetailsModel.getRegular();
            visibilityController(holder.btn_active, true);
            holder.card_view.setClickable(false);
            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Clicked + ", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            price_s = "R : " + mContext.getString(R.string.Rs) + pizzaDetailsModel.getRegular() +
                    "     M : " + mContext.getString(R.string.Rs) + pizzaDetailsModel.getMedium() +
                    "     L : " + mContext.getString(R.string.Rs) + pizzaDetailsModel.getLarge();
            holder.btn_active.setVisibility(View.INVISIBLE);

            holder.card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PizzaConstants.PIZZA_ID = pizzaDetailsModel.getId();
                    PizzaConstants.PIZZA_CATEGORY = pizzaDetailsModel.getCategory();
                    PizzaConstants.PIZZA_NAME = pizzaDetailsModel.getName();
                    PizzaConstants.PIZZA_CONTENT = pizzaDetailsModel.getContent();
                    PizzaConstants.MEDIUM_PRICE = pizzaDetailsModel.getMedium();
                    PizzaConstants.LARGE_PRICE = pizzaDetailsModel.getLarge();
                    PizzaConstants.REGULAR_PRICE = pizzaDetailsModel.getRegular();
                    PizzaConstants.SELECTED_PIZZA_PRICE = pizzaDetailsModel.getRegular();
                    mContext.startActivity(new Intent(mContext, ItemDetails.class));
                }
            });
        }

        holder.price_tag.setText(price_s);
    }

    @Override
    public int getItemCount() {
        return pizzaDetailsModelList.size();
    }

    private  void visibilityController(View v, boolean flag) {
        if (flag) {
            v.setVisibility(View.VISIBLE);
        } else {
            v.setVisibility(View.INVISIBLE);
        }
    }
}
