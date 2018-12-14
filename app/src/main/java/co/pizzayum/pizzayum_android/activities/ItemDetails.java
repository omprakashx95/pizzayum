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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.adapters.CrustSliderAdapter;
import co.pizzayum.pizzayum_android.adapters.SizeSliderAdapter;
import co.pizzayum.pizzayum_android.adapters.ToppingSliderAdapter;
import co.pizzayum.pizzayum_android.models.PizzaDetailsModel;
import co.pizzayum.pizzayum_android.models.SelectedCatResponse;
import co.pizzayum.pizzayum_android.utility.CustomItemClickListener;
import co.pizzayum.pizzayum_android.utility.PizzaConstants;
import co.pizzayum.pizzayum_android.utility.RecyclerTouchListener;

public class ItemDetails extends AppCompatActivity implements View.OnClickListener {

    //  private DatabaseHelper db;
    List<PizzaDetailsModel> crust_model_list;
    List<PizzaDetailsModel> size_model_list;
    List<PizzaDetailsModel> topping_model_list;

    // Adapters
    SizeSliderAdapter size_adapter;
    ToppingSliderAdapter topping_adapter;
    CrustSliderAdapter crust_adapter;

    // Recycler Views
    RecyclerView size_slider_view;
    RecyclerView topping_slider_view;
    RecyclerView crust_slider_view;
    ImageView minus_view, plus_view;
    TextView pizza_cat_view, pizza_content_view, pizza_name_view;
    RelativeLayout cart_footer_view;
    TextView bill_view, quantity_view, counter_view;
    CheckBox extra_cheese_view;

    int item_counter = 1;
    int total_bill = 0;
    int crust_bill = 0;
    int topping_price = 0 ;
    int extra_cheese_price = 0 ;
    PizzaDetailsModel extra_cheese_data ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        size_model_list = new ArrayList<>();
        crust_model_list = new ArrayList<>();
        topping_model_list = new ArrayList<>();
        extra_cheese_data = new PizzaDetailsModel() ;

        // db = new DatabaseHelper(this);
        // order_table_model = new PizzaOrderTableModel();
        // adding a topping slider
        // toppingSlider();
        // add a horizontal size slider
        // create a dummy data for topping slider
        // fillRecords();
        // orderList.addAll(db.getAllOrders());
        // extra views Initializer
        // extraViewInitializer();

        contentInitializer();

        sizeSlider();
        sizeSliderData();

        crustSlider();
        crustSliderData();

        toppingSlider();
        toppingSliderData();

        extraCheeseData() ;

        clickListenerInitialization();

        initialiseCartData();

        size_slider_view.addOnItemTouchListener(new RecyclerTouchListener(this,
                size_slider_view, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                PizzaDetailsModel model = size_model_list.get(position);
                PizzaConstants.SELECTED_PIZZA_SIZE = model.getName();
                PizzaConstants.SELECTED_PIZZA_PRICE = model.getPizza_price();
                initialiseCartData();
                PizzaConstants.SIZE_ENABLE_POSITION = position;
                size_adapter.notifyDataSetChanged();

                crustSlider();
                crustSliderData();

                toppingSlider();
                toppingSliderData();

                extra_cheese_view.setChecked(false);
                extra_cheese_price = 0 ;

                setFinalView();
            }
        }));

        topping_slider_view.addOnItemTouchListener(new RecyclerTouchListener(this,
                topping_slider_view, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                PizzaDetailsModel m = topping_model_list.get(position);

                switch (PizzaConstants.SELECTED_PIZZA_SIZE) {
                    case "Reguler":
                        PizzaConstants.PIZZA_TOPPING_PRICE = m.getRegular();
                        break;
                    case "Medium":
                        PizzaConstants.PIZZA_TOPPING_PRICE = m.getMedium();
                        break;
                    case "Large":
                        PizzaConstants.PIZZA_TOPPING_PRICE = m.getLarge();
                        break;
                }

                Log.e("TOPPING Price","Topping Price: " + PizzaConstants.PIZZA_TOPPING_PRICE);

                if (m.isTopping_button_flag()) {
                    m.setTopping_button_flag(false);
                    topping_model_list.set(position, m);
                    topping_adapter.notifyDataSetChanged();

                    topping_price = topping_price - Integer.parseInt(PizzaConstants.PIZZA_TOPPING_PRICE);
                    Log.e("TOPPING FALSE","\n Pizza Quantity: " + item_counter);
                    Log.e("TOPPING FALSE","Crust Price: " + crust_bill);
                    Log.e("TOPPING FALSE","Topping Price: " + topping_price);
                    Log.e("TOPPING FALSE","Cheese Price: " + extra_cheese_price);
                    Log.e("TOPPING FALSE","Selected Pizza Price: " + Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE));
                    Log.e("TOPPING FALSE","Total Bill: "+ (item_counter * total_bill));
                    Log.e("TOPPING FALSE","Total + CRUST + Topping: "+ (item_counter *  (total_bill + crust_bill + topping_price + extra_cheese_price)));
                } else {
                    m.setTopping_button_flag(true);
                    topping_model_list.set(position, m);
                    topping_adapter.notifyDataSetChanged();

                    topping_price = topping_price + Integer.parseInt(PizzaConstants.PIZZA_TOPPING_PRICE);
                    Log.e("TOPPING TRUE","\n Pizza Quantity: " + item_counter);
                    Log.e("TOPPING TRUE","Crust Price: " + crust_bill);
                    Log.e("TOPPING TRUE","Topping Price: " + topping_price);
                    Log.e("TOPPING TRUE","Cheese Price: " + extra_cheese_price);
                    Log.e("TOPPING TRUE","Selected Pizza Price: " + Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE));
                    Log.e("TOPPING TRUE","Total Bill: "+ (item_counter * total_bill));
                    Log.e("TOPPING TRUE","Total + CRUST + Topping: "+ (item_counter *  (total_bill + crust_bill + topping_price + extra_cheese_price)));
                }
                setFinalView();
            }
        }));

        crust_slider_view.addOnItemTouchListener(new RecyclerTouchListener(this,
                crust_slider_view, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                PizzaDetailsModel m = crust_model_list.get(position);

                switch (PizzaConstants.SELECTED_PIZZA_SIZE) {
                    case "Reguler":
                        PizzaConstants.PIZZA_CRUST_PRICE = m.getRegular();
                        break;
                    case "Medium":
                        PizzaConstants.PIZZA_CRUST_PRICE = m.getMedium();
                        break;
                    case "Large":
                        PizzaConstants.PIZZA_CRUST_PRICE = m.getLarge();
                        break;
                }

                if (m.isCrust_button_flag()) {
                    m.setCrust_button_flag(false);
                    crust_model_list.set(position, m);
                    crust_adapter.notifyDataSetChanged();

                    crust_bill = crust_bill - Integer.parseInt(PizzaConstants.PIZZA_CRUST_PRICE);
                    Log.e("CRUST FALSE","\n Pizza Quantity: " + item_counter);
                    Log.e("CRUST FALSE","Crust Price: " + crust_bill);
                    Log.e("CRUST FALSE","Topping Price: " + topping_price);
                    Log.e("CRUST FALSE","Cheese Price: " + extra_cheese_price);
                    Log.e("CRUST FALSE","Selected Pizza Price: " + Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE));
                    Log.e("CRUST FALSE","Total Bill: "+ (item_counter * total_bill));
                    Log.e("CRUST FALSE","Total + CRUST + Topping: "+ (item_counter *  (total_bill + crust_bill + topping_price + extra_cheese_price)));

                } else {
                    m.setCrust_button_flag(true);
                    crust_model_list.set(position, m);
                    crust_adapter.notifyDataSetChanged();

                    crust_bill = crust_bill + Integer.parseInt(PizzaConstants.PIZZA_CRUST_PRICE);

                    Log.e("CRUST TRUE","\n Pizza Quantity: " + item_counter);
                    Log.e("CRUST TRUE","Crust Price: " + crust_bill);
                    Log.e("CRUST TRUE","Topping Price: " + topping_price);
                    Log.e("CRUST TRUE","Cheese Price: " + extra_cheese_price);
                    Log.e("CRUST TRUE","Selected Pizza Price: " + Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE));
                    Log.e("CRUST TRUE","Total Bill: "+ (item_counter * total_bill));
                    Log.e("CRUST TRUE","Total + CRUST + Topping: "+ (item_counter *  (total_bill + crust_bill + topping_price + extra_cheese_price)));
                }
                setFinalView();
            }

        }));
    }

    void onCheckboxClicked(View view){
        boolean checked = extra_cheese_view.isChecked();

        switch (PizzaConstants.SELECTED_PIZZA_SIZE) {
            case "Reguler":
                PizzaConstants.EXTRA_CHEESE_PRICE = extra_cheese_data.getRegular();
                break;
            case "Medium":
                PizzaConstants.EXTRA_CHEESE_PRICE = extra_cheese_data.getMedium();
                break;
            case "Large":
                PizzaConstants.EXTRA_CHEESE_PRICE = extra_cheese_data.getLarge();
                break;
        }

        if (checked){
            extra_cheese_price = Integer.parseInt(PizzaConstants.EXTRA_CHEESE_PRICE);
        }else {
            extra_cheese_price = 0;
        }

        Log.e("CheckBox","\n Pizza Quantity: " + item_counter);
        Log.e("CheckBox","Crust Price: " + crust_bill);
        Log.e("CheckBox","Topping Price: " + topping_price);
        Log.e("CheckBox","Cheese Price: " + extra_cheese_price);
        Log.e("CheckBox","Selected Pizza Price: " + Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE));
        Log.e("CheckBox","Total Bill: "+ (item_counter * total_bill));
        Log.e("CheckBox","Total + CRUST + Topping: "+ (item_counter *  (total_bill + crust_bill + topping_price + extra_cheese_price)));

        setFinalView();
    }

    void plusButtonClicked() {
        item_counter ++ ;
        counter_view.setText(String.valueOf(item_counter));
        total_bill = Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE);
        //bill_view.setText(total_bill);

        Log.e("PLUS","\n Pizza Quantity: " + item_counter);
        Log.e("PLUS","Crust Price: " + crust_bill);
        Log.e("PLUS","Topping Price: " + topping_price);
        Log.e("PLUS","Cheese Price: " + extra_cheese_price);
        Log.e("PLUS","Selected Pizza Price: " + Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE));
        Log.e("PLUS","Total Bill: "+ (item_counter * total_bill));
        Log.e("PLUS","Total + CRUST + Topping: "+ (item_counter *  (total_bill + crust_bill +topping_price + extra_cheese_price)));
        setFinalView();
    }

    void minusButtonClicked() {
        item_counter -- ;
        counter_view.setText(String.valueOf(item_counter));
        total_bill = Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE);
        //bill_view.setText(item_counter * Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE));

        Log.e("MINUS","\n Pizza Quantity: " + item_counter);
        Log.e("MINUS","Crust Price: " + crust_bill);
        Log.e("MINUS","Topping Price: " + topping_price);
        Log.e("MINUS","Cheese Price: " + extra_cheese_price);
        Log.e("MINUS","Selected Pizza Price: " + Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE));
        Log.e("MINUS","Total Bill: "+ (item_counter * total_bill));
        Log.e("MINUS","Total + CRUST + Topping: "+ (item_counter *  (total_bill + crust_bill + topping_price + extra_cheese_price)));
        setFinalView();
    }

    void initialiseCartData() {
        item_counter = 1;
        counter_view.setText(String.valueOf(item_counter));
        total_bill = Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE) ;
        crust_bill = 0 ;
        topping_price = 0 ;

        Log.e("INITIALISATION","\n Pizza Quantity: " + item_counter);
        Log.e("INITIALISATION","Crust Price: " + crust_bill);
        Log.e("INITIALISATION","Topping Price: " + topping_price);
        Log.e("INITIALISATION","Cheese Price: " + extra_cheese_price);
        Log.e("INITIALISATION","Selected Pizza Price: " + Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE));
        Log.e("INITIALISATION","Total Bill: "+ (item_counter * total_bill));
        Log.e("INITIALISATION","Total + CRUST + Topping: "+ (item_counter *  (total_bill + crust_bill + topping_price + extra_cheese_price)));
        setFinalView();
    }

    void clickListenerInitialization() {
        plus_view.setOnClickListener(this);
        minus_view.setOnClickListener(this);
    }

    void contentInitializer() {
        pizza_name_view = findViewById(R.id.selected_pizza);
        pizza_cat_view = findViewById(R.id.pizza_cat);
        pizza_content_view = findViewById(R.id.lbl);

        pizza_name_view.setText(PizzaConstants.PIZZA_NAME);
        pizza_cat_view.setText(PizzaConstants.PIZZA_CATEGORY);
        pizza_content_view.setText(PizzaConstants.PIZZA_CONTENT);

        minus_view = findViewById(R.id.minus);
        plus_view = findViewById(R.id.plus);
        counter_view = findViewById(R.id.counter);

        cart_footer_view = findViewById(R.id.cart_footer);

        bill_view = findViewById(R.id.bill);
        quantity_view = findViewById(R.id.quantity);

        extra_cheese_view = findViewById(R.id.checkbox);
    }

    void setFinalView(){
        String temp = getString(R.string.Rs) +  (item_counter *  (total_bill + crust_bill + topping_price + extra_cheese_price));
        bill_view.setText(temp);
        temp = item_counter + " ITEM";
        quantity_view.setText(temp);
    }

    private void sizeSlider() {
        size_model_list.clear();
        size_slider_view = findViewById(R.id.size_slider);
        size_slider_view.setHasFixedSize(false);
        size_slider_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        size_adapter = new SizeSliderAdapter(this, size_model_list);
        size_slider_view.setItemAnimator(new DefaultItemAnimator());
        size_slider_view.setAdapter(size_adapter);
    }

    private void sizeSliderData() {
        PizzaDetailsModel model = new PizzaDetailsModel();
        model.setName("Reguler");
        model.setPizza_price(PizzaConstants.REGULAR_PRICE);
        size_model_list.add(model);

        model = new PizzaDetailsModel();
        model.setName("Medium");
        model.setPizza_price(PizzaConstants.MEDIUM_PRICE);
        size_model_list.add(model);

        model = new PizzaDetailsModel();
        model.setName("Large");
        model.setPizza_price(PizzaConstants.LARGE_PRICE);
        size_model_list.add(model);
        size_adapter.notifyDataSetChanged();
    }

    /**
     * This is the method which is used here to create a horizontal slider to select extra topping
     * in the order like Black Olive, Onion etc
     */
    void crustSlider() {
        crust_model_list.clear();
        crust_slider_view = findViewById(R.id.crust_slider);
        crust_slider_view.setHasFixedSize(false);
        crust_slider_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        crust_adapter = new CrustSliderAdapter(this, crust_model_list);
        crust_slider_view.setItemAnimator(new DefaultItemAnimator());
        crust_slider_view.setAdapter(crust_adapter);
    }

    private void crustSliderData() {
        crust_model_list.clear();
        for (SelectedCatResponse a : PizzaConstants.getResult) {
            if (a.getCategory().equals("Crust")) {
                PizzaDetailsModel model = new PizzaDetailsModel();
                model.setId(a.getId());
                model.setCategory(a.getCategory());
                model.setContent(a.getContent());
                model.setName(a.getName());
                model.setRegular(a.getRegular());
                model.setMedium(a.getMedium());
                model.setLarge(a.getLarge());
                model.setCrust_button_flag(false);
                crust_model_list.add(model);
            }
        }
        crust_adapter.notifyDataSetChanged();
    }


    /**
     * This is the method which is used here to create a horizontal slider to select extra topping
     * in the order like Black Olive, Onion etc
     */
    void toppingSlider() {
        topping_model_list.clear();
        topping_slider_view = findViewById(R.id.topping_slider);
        topping_slider_view.setHasFixedSize(false);
        topping_slider_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        topping_adapter = new ToppingSliderAdapter(this, topping_model_list);
        topping_slider_view.setItemAnimator(new DefaultItemAnimator());
        topping_slider_view.setAdapter(topping_adapter);
    }

    private void toppingSliderData() {
        for (SelectedCatResponse a : PizzaConstants.getResult) {
            if (a.getCategory().equals("Extra Topping") && a.getName().equals("Vegetarian")) {
                for (String s : a.getContent().split(",")) {
                    PizzaDetailsModel model = new PizzaDetailsModel();
                    model.setId(a.getId());
                    model.setTopping_name(s);
                    model.setRegular(a.getRegular());
                    model.setMedium(a.getMedium());
                    model.setLarge(a.getLarge());
                    model.setTopping_button_flag(false);
                    topping_model_list.add(model);
                }
            }
        }
        topping_adapter.notifyDataSetChanged();
    }

    private void extraCheeseData() {
        for (SelectedCatResponse a : PizzaConstants.getResult) {
            if (a.getCategory().equals("Extra Topping") && a.getName().equals("Extra Cheese")) {
                extra_cheese_data.setId(a.getId());
                extra_cheese_data.setRegular(a.getRegular());
                extra_cheese_data.setMedium(a.getMedium());
                extra_cheese_data.setLarge(a.getLarge());
            }
        }
    }

    private void createOrder() {
//        PizzaOrderTableModel model = new PizzaOrderTableModel(113,"Medium","2",
//                "123","tomato,potato","30","300","123");
//
//        long id = db.insertOrder(model);
//        printAllOrders();
    }

    private void printAllOrders() {
//        List<PizzaOrderTableModel> modelList =  db.getAllOrders();
//        for(int i = 0 ; i< modelList.size(); i++ ){
//            PizzaOrderTableModel model = modelList.get(i);
//            Log.e("TABLE RECORD ","RECORD : "+i+" ID: "+model.getProduct_id()
//                    + " Size: "+model.getSize()+ " Quantity: "+model.getProduct_quantity()
//                    + " ToppingId: "+model.getTopping_id()+" Topping Details "+model.getTopping_details()
//                    + " Extra Cheese: "+model.getExtra_cheese()+ " Bill "+ model.getBill()
//                    + " Crust Id "+model.getCrust_id());
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plus:
                plusButtonClicked();
                break;
            case R.id.minus:
                if (item_counter>1) minusButtonClicked();
                break;
        }
    }
}
