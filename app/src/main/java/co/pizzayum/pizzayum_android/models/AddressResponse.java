package co.pizzayum.pizzayum_android.models;

public class AddressResponse{
	private String address;
	private String status;

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
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
			"AddressResponse{" + 
			"address = '" + address + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
