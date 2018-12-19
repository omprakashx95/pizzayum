package co.pizzayum.pizzayum_android.activities;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.adapters.CrustSliderAdapter;
import co.pizzayum.pizzayum_android.adapters.SizeSliderAdapter;
import co.pizzayum.pizzayum_android.adapters.ToppingSliderAdapter;
import co.pizzayum.pizzayum_android.models.PizzaDetailsModel;
import co.pizzayum.pizzayum_android.models.PizzaOrderTableModel;
import co.pizzayum.pizzayum_android.models.SelectedCatResponse;
import co.pizzayum.pizzayum_android.utility.CustomItemClickListener;
import co.pizzayum.pizzayum_android.utility.DatabaseHelper;
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
    TextView cart_btn_view;

    int item_counter = 1;
    int total_bill = 0;
    int crust_bill = 0;
    int topping_price = 0;
    int extra_cheese_price = 0;
    int topping_counter = 0;
    PizzaDetailsModel extra_cheese_data;
    List<Integer> topping_pics;

    DatabaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        topping_pics = new ArrayList<>();
        toppingPicsInitializer();

        size_model_list = new ArrayList<>();
        crust_model_list = new ArrayList<>();
        topping_model_list = new ArrayList<>();
        extra_cheese_data = new PizzaDetailsModel();

        db = new DatabaseHelper(this);

        contentInitializer();
        resetVariables();

        sizeSlider();
        sizeSliderData();

        crustSlider();
        crustSliderData();

        toppingSlider();
        toppingSliderData();

        extraCheeseData();

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
                extra_cheese_price = 0;

                setFinalView();
            }
        }));

        topping_slider_view.addOnItemTouchListener(new RecyclerTouchListener(this,
                topping_slider_view, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                PizzaDetailsModel m = topping_model_list.get(position);

                switch (PizzaConstants.SELECTED_PIZZA_SIZE) {
                    case "Regular":
                        PizzaConstants.PIZZA_TOPPING_PRICE = m.getRegular();
                        break;
                    case "Medium":
                        PizzaConstants.PIZZA_TOPPING_PRICE = m.getMedium();
                        break;
                    case "Large":
                        PizzaConstants.PIZZA_TOPPING_PRICE = m.getLarge();
                        break;
                }

                if (m.isTopping_button_flag()) {
                    topping_counter --;
                    m.setTopping_button_flag(false);
                    topping_model_list.set(position, m);
                    topping_adapter.notifyDataSetChanged();
                    topping_price = topping_price - Integer.parseInt(PizzaConstants.PIZZA_TOPPING_PRICE);
                    logGenerator("Topping False");
                } else {
                    topping_counter ++;
                    m.setTopping_button_flag(true);
                    topping_model_list.set(position, m);
                    topping_adapter.notifyDataSetChanged();
                    topping_price = topping_price + Integer.parseInt(PizzaConstants.PIZZA_TOPPING_PRICE);
                    logGenerator("Topping True");
                }

                PizzaConstants.PIZZA_TOPPING_CONTENT = "";
                for (PizzaDetailsModel t : topping_model_list) {
                    if (t.isTopping_button_flag()) {
                        PizzaConstants.PIZZA_TOPPING_CONTENT += t.getTopping_name();
                    }
                }

                if (topping_price > 0) {
                    PizzaConstants.PIZZA_TOPPING_ID = String.valueOf(m.getId());
                } else {
                    PizzaConstants.PIZZA_TOPPING_ID = null;
                }

                Log.e("Pizza Topping", "ToppingContent" + PizzaConstants.PIZZA_TOPPING_CONTENT);
                setFinalView();
            }
        }));

        crust_slider_view.addOnItemTouchListener(new RecyclerTouchListener(this,
                crust_slider_view, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                PizzaDetailsModel m = crust_model_list.get(position);

                switch (PizzaConstants.SELECTED_PIZZA_SIZE) {
                    case "Regular":
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
                    int i = 0;
                    for (PizzaDetailsModel model : crust_model_list) {
                        model.setCrust_button_flag(false);
                        crust_model_list.set(i, model);
                        i++;
                    }
                    crust_adapter.notifyDataSetChanged();
                    crust_bill = 0;
                    PizzaConstants.PIZZA_CRUST_ID = null;
                    PizzaConstants.PIZZA_CRUST_DETAILS = "0";
                    logGenerator("CRUST FALSE");

                } else {
                    int i = 0;
                    for (PizzaDetailsModel model : crust_model_list) {
                        model.setCrust_button_flag(false);
                        crust_model_list.set(i, model);
                        i++;
                    }
                    m.setCrust_button_flag(true);
                    crust_model_list.set(position, m);
                    crust_adapter.notifyDataSetChanged();
                    crust_bill = Integer.parseInt(PizzaConstants.PIZZA_CRUST_PRICE);
                    PizzaConstants.PIZZA_CRUST_ID = String.valueOf(m.getId());
                    PizzaConstants.PIZZA_CRUST_DETAILS = m.getName();
                    logGenerator("CRUST TRUE");
                }

                setFinalView();
            }
        }));
    }

    void resetVariables() {
        PizzaConstants.SIZE_ENABLE_POSITION = 0;
        PizzaConstants.SELECTED_PIZZA_SIZE = "Regular";
        PizzaConstants.PIZZA_QUANTITY = "1";
        PizzaConstants.PIZZA_CRUST_ID = null;
        PizzaConstants.PIZZA_CRUST_DETAILS = null;
        PizzaConstants.PIZZA_CRUST_PRICE = "0";

        PizzaConstants.PIZZA_TOPPING_ID = null;
        PizzaConstants.PIZZA_TOPPING_CONTENT = null;
        PizzaConstants.PIZZA_TOPPING_PRICE = "0";

        PizzaConstants.EXTRA_CHEESE_PRICE = "0";
        PizzaConstants.EXTRA_CHEESE_ID = null;
        PizzaConstants.BILL = "0";
    }

    void logGenerator(String Tag) {
        Log.e(Tag, "----------------------------------------------------------------------------");
        Log.e(Tag, "\n Pizza Quantity: " + item_counter);
        Log.e(Tag, "Crust Price: " + crust_bill);
        Log.e(Tag, "Topping Counter: " + topping_counter);
        Log.e(Tag, "Topping Price: " + topping_price);
        Log.e(Tag, "Cheese Price: " + extra_cheese_price);
        Log.e(Tag, "Selected Pizza Price: " + Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE));
        Log.e(Tag, "Total Bill: " + (item_counter * total_bill));
        Log.e(Tag, "Total + CRUST + Topping: " + (item_counter * (total_bill + crust_bill + topping_price + extra_cheese_price)));
    }

    void toppingPicsInitializer() {
        topping_pics.add(R.drawable.black_olive);
        topping_pics.add(R.drawable.onion);
        topping_pics.add(R.drawable.capsicum);
        topping_pics.add(R.drawable.paneer);
        topping_pics.add(R.drawable.mashroom);
        topping_pics.add(R.drawable.golden_corn);
        topping_pics.add(R.drawable.tomato);
        topping_pics.add(R.drawable.jalapeno);
        topping_pics.add(R.drawable.red_paprika);
    }

    void onCheckboxClicked(View view) {
        boolean checked = extra_cheese_view.isChecked();

        switch (PizzaConstants.SELECTED_PIZZA_SIZE) {
            case "Regular":
                PizzaConstants.EXTRA_CHEESE_PRICE = extra_cheese_data.getRegular();
                break;
            case "Medium":
                PizzaConstants.EXTRA_CHEESE_PRICE = extra_cheese_data.getMedium();
                break;
            case "Large":
                PizzaConstants.EXTRA_CHEESE_PRICE = extra_cheese_data.getLarge();
                break;
        }

        if (checked) {
            extra_cheese_price = Integer.parseInt(PizzaConstants.EXTRA_CHEESE_PRICE);
            PizzaConstants.EXTRA_CHEESE_ID = String.valueOf(extra_cheese_data.getId());
            PizzaConstants.EXTRA_CHEESE_PRICE = String.valueOf(extra_cheese_price);
        } else {
            extra_cheese_price = 0;
            PizzaConstants.EXTRA_CHEESE_ID = null;
            PizzaConstants.EXTRA_CHEESE_PRICE = "0";
        }

        logGenerator("CheckBox");
        setFinalView();
    }

    void plusButtonClicked() {
        item_counter++;
        counter_view.setText(String.valueOf(item_counter));
        total_bill = Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE);
        // bill_view.setText(total_bill);

        logGenerator("Plus");
        setFinalView();
    }

    void minusButtonClicked() {
        item_counter--;
        counter_view.setText(String.valueOf(item_counter));
        total_bill = Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE);
        // bill_view.setText(item_counter * Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE));

        logGenerator("Minus");
        setFinalView();
    }

    void initialiseCartData() {
        item_counter = 1;
        counter_view.setText(String.valueOf(item_counter));
        total_bill = Integer.parseInt(PizzaConstants.SELECTED_PIZZA_PRICE);
        crust_bill = 0;
        topping_price = 0;

        logGenerator("INITIALISATION");
        setFinalView();
    }

    void clickListenerInitialization() {
        plus_view.setOnClickListener(this);
        minus_view.setOnClickListener(this);
        cart_btn_view.setOnClickListener(this);
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
        cart_btn_view = findViewById(R.id.cart_btn);
    }

    void setFinalView() {
        PizzaConstants.PIZZA_QUANTITY = String.valueOf(item_counter);
        String temp = getString(R.string.Rs) + (item_counter * (total_bill + crust_bill + topping_price + extra_cheese_price));
        bill_view.setText(temp);
        temp = item_counter + " ITEM";
        quantity_view.setText(temp);

        PizzaConstants.BILL = String.valueOf(item_counter * (total_bill + crust_bill + topping_price
                + extra_cheese_price));
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
        model.setName("Regular");
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
        int i = 0;
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
                    model.setUrl(String.valueOf(topping_pics.get(i)));
                    topping_model_list.add(model);
                    i++;
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

        PizzaConstants.ID = 0 ;
        for (PizzaOrderTableModel pizza: db.getAllOrders()){
            PizzaConstants.ID = pizza.getId();
            Log.e("Count","Count Log: "+PizzaConstants.ID);
        }
        PizzaConstants.ID++ ;

        PizzaOrderTableModel model = new PizzaOrderTableModel(
                PizzaConstants.ID,
                PizzaConstants.PIZZA_ID,
                PizzaConstants.SELECTED_PIZZA_SIZE,
                PizzaConstants.PIZZA_QUANTITY,
                PizzaConstants.PIZZA_CATEGORY,
                PizzaConstants.PIZZA_NAME,
                PizzaConstants.PIZZA_CONTENT,
                String.valueOf(total_bill),

                PizzaConstants.PIZZA_TOPPING_ID,
                String.valueOf(topping_counter),
                PizzaConstants.PIZZA_TOPPING_CONTENT,
                String.valueOf(topping_price),

                PizzaConstants.EXTRA_CHEESE_ID,
                PizzaConstants.EXTRA_CHEESE_PRICE,

                PizzaConstants.PIZZA_CRUST_ID,
                PizzaConstants.PIZZA_CRUST_DETAILS,
                String.valueOf(crust_bill),

                PizzaConstants.BILL);
        long id = db.insertOrder(model);
        printAllOrders();
    }

    private void printAllOrders() {
        Log.e("Tag", "get values");
        List<PizzaOrderTableModel> modelList = db.getAllOrders();
        for (int i = 0; i < modelList.size(); i++) {
            PizzaOrderTableModel model = modelList.get(i);
            Log.e("TABLE RECORD ", "unique id : " + model.getId() + " P. ID: " + model.getProduct_id()
                    + " Size: " + model.getSize() + " Quantity: " + model.getProduct_quantity()
                    + " Cat: " + model.getProduct_cat() + " Name: " + model.getProduct_name()
                    + " Content: " + model.getProduct_content()
                    + " ToppingId: " + model.getTopping_id() + " Topping Details " + model.getTopping_details()
                    + " Extra Cheese Id: " + model.getExtra_cheese_id()
                    + " Extra Cheese Price: " + model.getExtra_cheese()
                    + " Crust Id: " + model.getCrust_id()
                    + " Crust Details: " + model.getCrust_details()
                    + " Bill " + model.getBill());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plus:
                plusButtonClicked();
                break;
            case R.id.minus:
                if (item_counter > 1) minusButtonClicked();
                break;
            case R.id.cart_btn:
                createOrder();
                Toast.makeText(this, "Record Added", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
