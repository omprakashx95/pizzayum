package co.pizzayum.pizzayum_android.models;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class OrderResponse{

	@SerializedName("orderId")
	private int orderId;

	@SerializedName("tokenNo")
	private int tokenNo;

	@SerializedName("status")
	private String status;

	public void setOrderId(int orderId){
		this.orderId = orderId;
	}

	public int getOrderId(){
		return orderId;
	}

	public void setTokenNo(int tokenNo){
		this.tokenNo = tokenNo;
	}

	public int getTokenNo(){
		return tokenNo;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"OrderResponse{" + 
			"orderId = '" + orderId + '\'' + 
			",tokenNo = '" + tokenNo + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}