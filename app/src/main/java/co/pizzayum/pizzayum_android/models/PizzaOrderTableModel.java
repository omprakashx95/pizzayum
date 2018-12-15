package co.pizzayum.pizzayum_android.models;

public class PizzaOrderTableModel {

    public static final String TABLE_NAME = "order_table";

    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_SIZE = "size";
    public static final String PRODUCT_QUANTITY = "product_quantity";
    public static final String PRODUCT_CAT = "product_cat";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_CONTENT = "product_content";

    public static final String TOPPING_ID = "topping_id";
    public static final String TOPPING_DETAILS = "topping_details";

    public static final String CRUST_ID = "crust_id";
    public static final String CRUST_DETAILS = "crust_details";

    public static final String EXTRA_CHEESE = "extra_cheese";
    public static final String EXTRA_CHEESE_ID = "extra_cheese_id";
    public static final String BILL = "bill";

    private int product_id;
    private String size;
    private String product_quantity;
    private String product_cat;
    private String product_name;
    private String product_content;

    private String topping_id;
    private String topping_details;

    private String extra_cheese_id;
    private String extra_cheese;

    private String crust_id;
    private String crust_details;

    private String bill ;


    //  create table sql query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + PRODUCT_ID + " INTEGER PRIMARY KEY ,"
                    + PRODUCT_QUANTITY + " TEXT , "
                    + PRODUCT_SIZE + " TEXT ,"
                    + PRODUCT_NAME + " TEXT ,"
                    + PRODUCT_CAT + " TEXT ,"
                    + PRODUCT_CONTENT + " TEXT ,"
                    + CRUST_ID + " TEXT ,"
                    + CRUST_DETAILS + " TEXT ,"
                    + TOPPING_ID + " TEXT ,"
                    + TOPPING_DETAILS + " TEXT ,"
                    + EXTRA_CHEESE + " TEXT ,"
                    + EXTRA_CHEESE_ID + " TEXT ,"
                    + BILL + " TEXT "+
                    ")";

    public PizzaOrderTableModel(int product_id, String size, String product_quantity, String product_cat, String product_name, String product_content, String topping_id, String topping_details, String extra_cheese_id, String extra_cheese, String crust_id, String crust_details, String bill) {
        this.product_id = product_id;
        this.size = size;
        this.product_quantity = product_quantity;
        this.product_cat = product_cat;
        this.product_name = product_name;
        this.product_content = product_content;
        this.topping_id = topping_id;
        this.topping_details = topping_details;
        this.extra_cheese_id = extra_cheese_id;
        this.extra_cheese = extra_cheese;
        this.crust_id = crust_id;
        this.crust_details = crust_details;
        this.bill = bill;
    }

    public PizzaOrderTableModel(){}

    public String getExtra_cheese_id() {
        return extra_cheese_id;
    }

    public void setExtra_cheese_id(String extra_cheese_id) {
        this.extra_cheese_id = extra_cheese_id;
    }

    public String getProduct_cat() {
        return product_cat;
    }

    public void setProduct_cat(String product_cat) {
        this.product_cat = product_cat;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_content() {
        return product_content;
    }

    public void setProduct_content(String product_content) {
        this.product_content = product_content;
    }

    public String getCrust_details() {
        return crust_details;
    }

    public void setCrust_details(String crust_details) {
        this.crust_details = crust_details;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTopping_id() {
        return topping_id;
    }

    public void setTopping_id(String topping_id) {
        this.topping_id = topping_id;
    }

    public String getTopping_details() {
        return topping_details;
    }

    public void setTopping_details(String topping_details) {
        this.topping_details = topping_details;
    }

    public String getExtra_cheese() {
        return extra_cheese;
    }

    public void setExtra_cheese(String extra_cheese) {
        this.extra_cheese = extra_cheese;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getCrust_id() {
        return crust_id;
    }

    public void setCrust_id(String crust_id) {
        this.crust_id = crust_id;
    }
}
