package co.pizzayum.pizzayum_android.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class InvoiceDetail{

	@SerializedName("payment_mode")
	private String paymentMode;

	@SerializedName("delivery_agent")
	private String deliveryAgent;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("order_total")
	private String orderTotal;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("email")
	private String email;

	public void setPaymentMode(String paymentMode){
		this.paymentMode = paymentMode;
	}

	public String getPaymentMode(){
		return paymentMode;
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

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setOrderTotal(String orderTotal){
		this.orderTotal = orderTotal;
	}

	public String getOrderTotal(){
		return orderTotal;
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

	@Override
 	public String toString(){
		return 
			"InvoiceDetail{" + 
			"payment_mode = '" + paymentMode + '\'' + 
			",delivery_agent = '" + deliveryAgent + '\'' + 
			",updated_at = '" + updatedAt + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",order_total = '" + orderTotal + '\'' + 
			",order_id = '" + orderId + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}