package customer.webapp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
	
	public Set<String> getCities(){
		Set<String> cities = new HashSet<String>();
		for(Customer person: this.Data){
			cities.add(person.getCity());
		}
		return cities;
	}
	
	public Set<String> getStates(){
		Set<String> states = new HashSet<String>();
		for(Customer person: this.Data){
			states.add(person.getState());
		}
		return states;
	}
	

	public void setData(List<Customer> data) {
		this.Data = data;
	}

}
