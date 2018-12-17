package co.pizzayum.pizzayum_android.utility;

import co.pizzayum.pizzayum_android.models.SelectedCatResponse;

public class PizzaConstants {

    public static String BASE_URL = "http://www.pizzayum.co/api";
    public static String CATEGORY = "category";
    public static String CATEGORY_NAME;
    public static int ENABLE_POSITION = 0;
    public static int SIZE_ENABLE_POSITION = 0;

    // Data Array
    public static SelectedCatResponse[] getResult;

    // About Pizza Order
    public static int ID;
    public static int PIZZA_ID;
    public static String PIZZA_QUANTITY;
    public static String PIZZA_NAME;
    public static String PIZZA_CONTENT;
    public static String PIZZA_CATEGORY;
    public static String REGULAR_PRICE = "0";
    public static String MEDIUM_PRICE;
    public static String LARGE_PRICE;
    public static String SELECTED_PIZZA_PRICE = REGULAR_PRICE;
    public static String SELECTED_PIZZA_SIZE = "Reguler";
    public static String PIZZA_BILL = "";


    public static String PIZZA_CRUST_ID;
    public static String PIZZA_CRUST_DETAILS;
    public static String PIZZA_CRUST_PRICE = "0";

    public static String PIZZA_TOPPING_ID = "0";
    public static String PIZZA_TOPPING_CONTENT ;
    public static String PIZZA_TOPPING_PRICE = "0";

    public static String EXTRA_CHEESE_PRICE = "0";
    public static String EXTRA_CHEESE_ID = "0";
    public static String BILL;
}
