package co.pizzayum.pizzayum_android.models;

public class PizzaDetailsModel {
    private int id;
    private String name;
    private String url;
    private String regular;
    private String medium;
    private String large;
    private String content;
    private String category;
    private String veg;
    private String created_at;
    private String updated_at;

    private String topping_name;
    private boolean topping_button_flag ;

    private String pizza_price ;

    private boolean crust_button_flag ;

    public PizzaDetailsModel(int id, String name, String url, String regular, String medium, String large,
                             String content, String category, String veg, String created_at, String updated_at) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.regular = regular;
        this.medium = medium;
        this.large = large;
        this.content = content;
        this.category = category;
        this.veg = veg;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }


    public boolean isCrust_button_flag() {
        return crust_button_flag;
    }

    public void setCrust_button_flag(boolean crust_button_flag) {
        this.crust_button_flag = crust_button_flag;
    }

    public boolean isTopping_button_flag() {
        return topping_button_flag;
    }

    public void setTopping_button_flag(boolean topping_button_flag) {
        this.topping_button_flag = topping_button_flag;
    }

    public PizzaDetailsModel(){}

    public String getPizza_price() {
        return pizza_price;
    }

    public void setPizza_price(String pizza_price) {
        this.pizza_price = pizza_price;
    }

    public String getTopping_name() {
        return topping_name;
    }

    public void setTopping_name(String topping_name) {
        this.topping_name = topping_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getVeg() {
        return veg;
    }

    public void setVeg(String veg) {
        this.veg = veg;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
