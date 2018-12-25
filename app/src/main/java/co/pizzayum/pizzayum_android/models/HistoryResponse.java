package co.pizzayum.pizzayum_android.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class HistoryResponse{

	@SerializedName("orderDetail")
	private List<OrderDetailItem> orderDetail;

	@SerializedName("user")
	private User user;

	public void setOrderDetail(List<OrderDetailItem> orderDetail){
		this.orderDetail = orderDetail;
	}

	public List<OrderDetailItem> getOrderDetail(){
		return orderDetail;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	@Override
 	public String toString(){
		return 
			"HistoryResponse{" + 
			"orderDetail = '" + orderDetail + '\'' + 
			",user = '" + user + '\'' + 
			"}";
		}
}