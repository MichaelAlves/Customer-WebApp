package customer.webapp;

import java.util.ArrayList;
import java.util.List;
//Data source object to pass to parse remote service response
public class CustomerDataSourceResult {
	private int Total;
	private List<Customer> Data;
	
	public int getTotal(){
		return Total;
	}
	
	public void setTotal(int total){
		this.Total = total;
	}

	public List<Customer> getData() {
		return Data;
	}
	
	public List<String> getCities(){
		List<String> cities = new ArrayList<String>();
		for(Customer person: this.Data){
			cities.add(person.getCity());
		}
		return cities;
	}
	
	public List<String> getStates(){
		List<String> states = new ArrayList<String>();
		for(Customer person: this.Data){
			states.add(person.getState());
		}
		return states;
	}
	

	public void setData(List<Customer> data) {
		this.Data = data;
	}

	

}
