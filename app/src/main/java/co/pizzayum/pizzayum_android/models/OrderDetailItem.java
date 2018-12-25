package co.pizzayum.pizzayum_android.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class OrderDetailItem{

	@SerializedName("extra_cheese")
	private String extraCheese;

	@SerializedName("topping_detail")
	private String toppingDetail;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("medium")
	private String medium;

	@SerializedName("content")
	private String content;

	@SerializedName("topping_id")
	private Object toppingId;

	@SerializedName("delivery_agent")
	private String deliveryAgent;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("topping_qty")
	private String toppingQty;

	@SerializedName("price")
	private String price;

	@SerializedName("veg")
	private String veg;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	@SerializedName("regular")
	private String regular;

	@SerializedName("crust_id")
	private String crustId;

	@SerializedName("item")
	private String item;

	@SerializedName("payment_mode")
	private String paymentMode;

	@SerializedName("quantity")
	private String quantity;

	@SerializedName("large")
	private String large;

	@SerializedName("url")
	private String url;

	@SerializedName("token_no")
	private String tokenNo;

	@SerializedName("size")
	private String size;

	@SerializedName("sub_total")
	private String subTotal;

	@SerializedName("name")
	private String name;

	@SerializedName("order_total")
	private String orderTotal;

	@SerializedName("category")
	private String category;

	@SerializedName("order_id")
	private String orderId;

	public void setExtraCheese(String extraCheese){
		this.extraCheese = extraCheese;
	}

	public String getExtraCheese(){
		return extraCheese;
	}

	public void setToppingDetail(String toppingDetail){
		this.toppingDetail = toppingDetail;
	}

	public String getToppingDetail(){
		return toppingDetail;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setMedium(String medium){
		this.medium = medium;
	}

	public String getMedium(){
		return medium;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setToppingId(Object toppingId){
		this.toppingId = toppingId;
	}

	public Object getToppingId(){
		return toppingId;
	}

	public void setDeliveryAgent(String deliveryAgent){
		this.deliveryAgent = deliveryAgent;
	}

	public String getDeliveryAgent(){
		return deliveryAgent;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public void setToppingQty(String toppingQty){
		this.toppingQty = toppingQty;
	}

	public String getToppingQty(){
		return toppingQty;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setVeg(String veg){
		this.veg = veg;
	}

	public String getVeg(){
		return veg;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setRegular(String regular){
		this.regular = regular;
	}

	public String getRegular(){
		return regular;
	}

	public void setCrustId(String crustId){
		this.crustId = crustId;
	}

	public String getCrustId(){
		return crustId;
	}

	public void setItem(String item){
		this.item = item;
	}

	public String getItem(){
		return item;
	}

	public void setPaymentMode(String paymentMode){
		this.paymentMode = paymentMode;
	}

	public String getPaymentMode(){
		return paymentMode;
	}

	public void setQuantity(String quantity){
		this.quantity = quantity;
	}

	public String getQuantity(){
		return quantity;
	}

	public void setLarge(String large){
		this.large = large;
	}

	public String getLarge(){
		return large;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUrl(){
		return url;
	}

	public void setTokenNo(String tokenNo){
		this.tokenNo = tokenNo;
	}

	public String getTokenNo(){
		return tokenNo;
	}

	public void setSize(String size){
		this.size = size;
	}

	public String getSize(){
		return size;
	}

	public void setSubTotal(String subTotal){
		this.subTotal = subTotal;
	}

	public String getSubTotal(){
		return subTotal;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setOrderTotal(String orderTotal){
		this.orderTotal = orderTotal;
	}

	public String getOrderTotal(){
		return orderTotal;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	@Override
 	public String toString(){
		return 
			"OrderDetailItem{" + 
			"extra_cheese = '" + extraCheese + '\'' + 
			",topping_detail = '" + toppingDetail + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",medium = '" + medium + '\'' + 
			",content = '" + content + '\'' + 
			",topping_id = '" + toppingId + '\'' + 
			",delivery_agent = '" + deliveryAgent + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",topping_qty = '" + toppingQty + '\'' + 
			",price = '" + price + '\'' + 
			",veg = '" + veg + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",regular = '" + regular + '\'' + 
			",crust_id = '" + crustId + '\'' + 
			",item = '" + item + '\'' + 
			",payment_mode = '" + paymentMode + '\'' + 
			",quantity = '" + quantity + '\'' + 
			",large = '" + large + '\'' + 
			",url = '" + url + '\'' + 
			",token_no = '" + tokenNo + '\'' + 
			",size = '" + size + '\'' + 
			",sub_total = '" + subTotal + '\'' + 
			",name = '" + name + '\'' + 
			",order_total = '" + orderTotal + '\'' + 
			",category = '" + category + '\'' + 
			",order_id = '" + orderId + '\'' + 
			"}";
		}
}