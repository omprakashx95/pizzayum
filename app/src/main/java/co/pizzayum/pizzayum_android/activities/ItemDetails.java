package co.pizzayum.pizzayum_android.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.adapters.PizzaListAdapter;
import co.pizzayum.pizzayum_android.adapters.ToppingAdapter;
import co.pizzayum.pizzayum_android.models.Topping;

public class ItemDetails extends AppCompatActivity {
    List<Topping> toppingList;
    ToppingAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        toppingList = new ArrayList<>();
        adapter = new ToppingAdapter(this, toppingList);
        RecyclerView pizza_list = findViewById(R.id.crust_list);
        pizza_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        pizza_list.setItemAnimator(new DefaultItemAnimator());
        pizza_list.setAdapter(adapter);
        fillRecords();
    }

    void fillRecords() {
        Topping model = new Topping();
        model.setUrl(R.drawable.black_olive);
        model.setName("Black Olive");
        toppingList.add(model);

        model = new Topping();
        model.setUrl(R.drawable.onion);
        model.setName("Onion");
        toppingList.add(model);

        model = new Topping();
        model.setUrl(R.drawable.capsicum);
        model.setName("Capsicum");
        toppingList.add(model);

        model = new Topping();
        model.setUrl(R.drawable.paneer);
        model.setName("Paneer");
        toppingList.add(model);

        model = new Topping();
        model.setUrl(R.drawable.mashroom);
        model.setName("Mashroom");
        toppingList.add(model);

        model = new Topping();
        model.setUrl(R.drawable.golden_corn);
        model.setName("Golden Corn");
        toppingList.add(model);

        model = new Topping();
        model.setUrl(R.drawable.tomato);
        model.setName("Tomato");
        toppingList.add(model);

        model = new Topping();
        model.setUrl(R.drawable.red_paprika);
        model.setName("Red Paprika");
        toppingList.add(model);

        model = new Topping();
        model.setUrl(R.drawable.jalapeno);
        model.setName("Jalapeno");
        toppingList.add(model);

        adapter.notifyDataSetChanged();
    }
}
