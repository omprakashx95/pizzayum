package co.pizzayum.pizzayum_android.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class InvoiceModel{

	@SerializedName("invoiceDetail")
	private List<InvoiceDetailItem> invoiceDetail;

	@SerializedName("orderDetail")
	private List<OrderDetailItem> orderDetail;

	@SerializedName("user")
	private User user;

	@SerializedName("status")
	private String status;

	public void setInvoiceDetail(List<InvoiceDetailItem> invoiceDetail){
		this.invoiceDetail = invoiceDetail;
	}

	public List<InvoiceDetailItem> getInvoiceDetail(){
		return invoiceDetail;
	}

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

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"InvoiceModel{" + 
			"invoiceDetail = '" + invoiceDetail + '\'' + 
			",orderDetail = '" + orderDetail + '\'' + 
			",user = '" + user + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}