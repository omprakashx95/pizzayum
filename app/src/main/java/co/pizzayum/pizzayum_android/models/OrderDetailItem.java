package co.pizzayum.pizzayum_android.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class OrderDetailItem{

	@SerializedName("item")
	private String item;

	@SerializedName("quantity")
	private String quantity;

	@SerializedName("extra_cheese")
	private String extraCheese;

	@SerializedName("topping_detail")
	private String toppingDetail;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("topping_id")
	private String toppingId;

	@SerializedName("token_no")
	private String tokenNo;

	@SerializedName("size")
	private String size;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("topping_qty")
	private String toppingQty;

	@SerializedName("price")
	private String price;

	@SerializedName("sub_total")
	private String subTotal;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("email")
	private String email;

	@SerializedName("crust_id")
	private String crustId;

	public void setItem(String item){
		this.item = item;
	}

	public String getItem(){
		return item;
	}

	public void setQuantity(String quantity){
		this.quantity = quantity;
	}

	public String getQuantity(){
		return quantity;
	}

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

	public void setToppingId(String toppingId){
		this.toppingId = toppingId;
	}

	public String getToppingId(){
		return toppingId;
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

	public void setSubTotal(String subTotal){
		this.subTotal = subTotal;
	}

	public String getSubTotal(){
		return subTotal;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setCrustId(String crustId){
		this.crustId = crustId;
	}

	public String getCrustId(){
		return crustId;
	}

	@Override
 	public String toString(){
		return 
			"OrderDetailItem{" + 
			"item = '" + item + '\'' + 
			",quantity = '" + quantity + '\'' + 
			",extra_cheese = '" + extraCheese + '\'' + 
			",topping_detail = '" + toppingDetail + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",topping_id = '" + toppingId + '\'' + 
			",token_no = '" + tokenNo + '\'' + 
			",size = '" + size + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",topping_qty = '" + toppingQty + '\'' + 
			",price = '" + price + '\'' + 
			",sub_total = '" + subTotal + '\'' + 
			",order_id = '" + orderId + '\'' + 
			",email = '" + email + '\'' + 
			",crust_id = '" + crustId + '\'' + 
			"}";
		}
}