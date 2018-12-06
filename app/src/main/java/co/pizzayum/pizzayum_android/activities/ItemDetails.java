package co.pizzayum.pizzayum_android.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.adapters.PizzaListAdapter;
import co.pizzayum.pizzayum_android.adapters.ToppingAdapter;
import co.pizzayum.pizzayum_android.models.PizzaOrderTableModel;
import co.pizzayum.pizzayum_android.models.Topping;
import co.pizzayum.pizzayum_android.utility.DatabaseHelper;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;

public class ItemDetails extends AppCompatActivity implements View.OnClickListener  {
    List<Topping> toppingList;
    ToppingAdapter adapter;

    TextView count ;
    int counter = 0 ;
    ImageView plus_button, minus_button ;

    RecyclerView pizza_list ;

    TextView size_regular, size_medium, size_large;
    TextView crust_thin, crust_burst, extra_cheese ;

    PizzaOrderTableModel order_table_model ;

    private DatabaseHelper db;
    int bill = 0 ;
    private List<PizzaOrderTableModel> orderList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        db = new DatabaseHelper(this);

        order_table_model = new PizzaOrderTableModel();

        // orderList.addAll(db.getAllOrders());

        // adding a topping slider
        toppingSlider();

        // create a dummy data for topping slider
        fillRecords();

        // extra views Initializer
        extraViewInitializer();

        // setting click listener on views
        plus_button.setOnClickListener(this);
        minus_button.setOnClickListener(this);

        size_large.setOnClickListener(this);
        size_medium.setOnClickListener(this);
        size_regular.setOnClickListener(this);

        crust_thin.setOnClickListener(this);
        crust_burst.setOnClickListener(this);

        extra_cheese.setOnClickListener(this);
    }

    private void createOrder(){
        PizzaOrderTableModel model = new PizzaOrderTableModel(113,"Medium","2",
                "123","tomato,potato","30","300","123");

        long id = db.insertOrder(model);

        printAllOrders();
    }

    private void printAllOrders(){
        List<PizzaOrderTableModel> modelList =  db.getAllOrders();
        for(int i = 0 ; i< modelList.size(); i++ ){
            PizzaOrderTableModel model = modelList.get(i);
            Log.e("TABLE RECORD ","RECORD : "+i+" ID: "+model.getProduct_id()
                    + " Size: "+model.getSize()+ " Quantity: "+model.getProduct_quantity()
                    + " ToppingId: "+model.getTopping_id()+" Topping Details "+model.getTopping_details()
                    + " Extra Cheese: "+model.getExtra_cheese()+ " Bill "+ model.getBill()
                    + " Crust Id "+model.getCrust_id());
        }
    }

    /**
     * This is the method which is used here to create a horizontal slider to select extra topping
     * in the order like Black Olive, Onion etc
     */
    void toppingSlider(){
        toppingList = new ArrayList<>();
        adapter = new ToppingAdapter(this, toppingList);
        pizza_list = findViewById(R.id.crust_list);
        pizza_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        pizza_list.setItemAnimator(new DefaultItemAnimator());
        pizza_list.setAdapter(adapter);
    }

    /**
     * These are counter views to select the quantity of the items
     */
    void extraViewInitializer(){
        count = findViewById(R.id.counter);
        plus_button = findViewById(R.id.plus);
        minus_button = findViewById(R.id.minus);

        crust_burst = findViewById(R.id.crust_burst);
        crust_thin = findViewById(R.id.crust_thin);
        size_regular = findViewById(R.id.size_regular);
        size_medium = findViewById(R.id.size_medium);
        size_large = findViewById(R.id.size_large);
        extra_cheese = findViewById(R.id.extra_cheese);
    }

    /**
     * Create some topping records
     */
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.plus :
                counter ++ ;
                count.setText(String.valueOf(counter));
                break;
            case R.id.minus :
                counter--;
                count.setText(String.valueOf(counter));
                break;

            case R.id.size_regular:
                regularClicked();
                break;
            case R.id.size_medium:
                mediumClicked();
                break;
            case R.id.size_large:
                largeClicked();
                break;
            case R.id.crust_burst:
                break;
            case R.id.crust_thin:
                break;
            case R.id.extra_cheese:
                extraCheese();
                break;
        }
    }

    void regularClicked(){
        PizzaConstants.PIZZA_PRICE = PizzaConstants.REGULAR_PRICE;
        PizzaConstants.PIZZA_SIZE_NAME = "REGULAR" ;
        bill = bill + Integer.parseInt(PizzaConstants.PIZZA_PRICE);
        order_table_model.setBill(String.valueOf(bill));
        size_regular.setTextColor(Color.parseColor("#FF5733"));
        size_regular.setTextColor(Color.parseColor("#FFFFFF"));
        size_regular.setTextColor(Color.parseColor("#FFFFFF"));
    }

    void mediumClicked(){
        PizzaConstants.PIZZA_PRICE = PizzaConstants.MEDIUM_PRICE;
        PizzaConstants.PIZZA_SIZE_NAME = "MEDIUM" ;
        bill = bill + Integer.parseInt(PizzaConstants.PIZZA_PRICE);
        order_table_model.setBill(String.valueOf(bill));
        size_regular.setTextColor(Color.parseColor("#FFFFFF"));
        size_regular.setTextColor(Color.parseColor("#FF5733"));
        size_regular.setTextColor(Color.parseColor("#FFFFFF"));
    }

    void largeClicked(){
        PizzaConstants.PIZZA_PRICE = PizzaConstants.LARGE_PRICE;
        PizzaConstants.PIZZA_SIZE_NAME = "LARGE" ;
        bill = bill + Integer.parseInt(PizzaConstants.PIZZA_PRICE);
        order_table_model.setBill(String.valueOf(bill));
        size_regular.setTextColor(Color.parseColor("#FFFFFF"));
        size_regular.setTextColor(Color.parseColor("#FFFFFF"));
        size_regular.setTextColor(Color.parseColor("#FF5733"));
    }

    void extraCheese(){
        PizzaConstants.EXTRA_CHEESE = "30";
    }

    void crustBurst(){
        PizzaConstants.CRUST_ID = "123";
        PizzaConstants.CRUST_TOTAL_BILL = 30;

        order_table_model.setCrust_id(PizzaConstants.CRUST_ID);
    }

    void crustThin(){
        PizzaConstants.CRUST_ID = "134";
        PizzaConstants.CRUST_TOTAL_BILL = 30;
        order_table_model.setCrust_id(PizzaConstants.CRUST_ID);
    }

    void plus(){

    }
}
