package co.pizzayum.pizzayum_android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.models.Album;

public class PizzaListAdapter extends RecyclerView.Adapter<PizzaListAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;
    private int counter_value = 0 ;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, about, price_tag, counter;
        public ImageView thumbnail, btn_minus, btn_plus;
        public View card_view, btn_active;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            about = view.findViewById(R.id.about);
            price_tag = view.findViewById(R.id.price_tag);
            thumbnail = view.findViewById(R.id.thumbnail);

            btn_minus = view.findViewById(R.id.minus);
            btn_plus = view.findViewById(R.id.plus);
            card_view = view.findViewById(R.id.card_view);
            btn_active = view.findViewById(R.id.btn_container);
            counter = view.findViewById(R.id.counter);
        }
    }

    public PizzaListAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pizza_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.about.setText(album.getContent());

        String price_s;
        if (album.getMedium().equals("0") || album.getLarge().equals("0")) {
            price_s = mContext.getString(R.string.Rs) + album.getRegular();

            visibilityController(holder.btn_active, true);
            visibilityController(holder.btn_minus, false);
            visibilityController(holder.btn_plus, false);
            counter_value = 1 ;
            holder.counter.setText("Add");
            holder.btn_active.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.btn_active.setClickable(false);
                    holder.counter.setText(String.valueOf(counter_value));
                    visibilityController(holder.btn_plus, true);
                    visibilityController(holder.btn_minus, true);
                }
            });

            holder.btn_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.counter.setText(String.valueOf(counter_value++));
                }
            });

            holder.btn_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.counter.setText(String.valueOf(counter_value--));
                }
            });


        } else {
            price_s = "R : " + mContext.getString(R.string.Rs) + album.getRegular() +
                    "     M : " + mContext.getString(R.string.Rs) + album.getMedium() +
                    "     L : " + mContext.getString(R.string.Rs) + album.getLarge();

            holder.btn_active.setVisibility(View.INVISIBLE);
        }

        holder.price_tag.setText(price_s);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }


    private  void visibilityController(View v, boolean flag) {
        if (flag) {
            v.setVisibility(View.VISIBLE);
        } else {
            v.setVisibility(View.INVISIBLE);
        }
    }
}