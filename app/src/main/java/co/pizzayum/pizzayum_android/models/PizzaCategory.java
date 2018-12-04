package co.pizzayum.pizzayum_android.models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

public class PizzaCategory{

	@SerializedName("category")
	private List<String> category;

	public void setCategory(List<String> category){
		this.category = category;
	}

	public List<String> getCategory(){
		return category;
	}

	@Override
 	public String toString(){
		return 
			"PizzaCategory{" + 
			"category = '" + category + '\'' + 
			"}";
		}
}