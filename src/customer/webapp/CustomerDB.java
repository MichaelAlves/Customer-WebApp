package customer.webapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDB {
	private Connection conn = null;
	
	public CustomerDB(String dbpath) throws SQLException{
		try{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbpath); //establish connection with customer database
		}
		catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
			
		}
	}
	
	public List<Customer> customerList() throws SQLException{ //returns a list of customer objects by querying customer database
		List<Customer> customers = new ArrayList<Customer>();
		PreparedStatement stmt = null;
		ResultSet results = null;
		try {
			String query = "SELECT * from customer"; //customer table fields: id, first_name, last_name, address, city, state, phone
			stmt = conn.prepareStatement(query);
			results = stmt.executeQuery();
			//creates new customer object for every row in the customer table
			while(results.next()){
				Customer p = new Customer();
				p.setID(results.getInt("id"));
				p.setFirstName(results.getString("first_name"));
				p.setLastName(results.getString("last_name"));
				p.setAddress(results.getString("address"));
				p.setCity(results.getString("city"));
				p.setState(results.getString("state"));
				p.setPhoneNumber(results.getString("phone"));
				customers.add(p);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			//close all connections
			stmt.close();
			results.close();
		}
		return customers;
		
	}
	
	public Customer updateCustomer(Customer person) throws SQLException{ //updates customer object
		PreparedStatement stat = null;
		
		try{
			
		//query to update customer DB
		String updatePerson = "UPDATE customer SET first_name = ?, last_name = ?, address = ?, city = ?, state = ?, phone = ?, " +
							  "WHERE id = ?";
		stat = conn.prepareStatement(updatePerson);
		//update parameters of sql row with customer attributes
		stat.setString(1, person.getFirstName());
		stat.setString(2, person.getLastName());
		stat.setString(3, person.getAddress());
		stat.setString(4, person.getCity());
		stat.setString(5, person.getState());
		stat.setString(6, person.getPhoneNumber());
		stat.setInt(7, person.getID());
		stat.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			stat.close();
		}	
		return person;
	}
	
	public void deleteCustomer(Customer person) throws SQLException{ //remove customer from customerDB
		PreparedStatement stmnt = null;
		
		try{
			String sql = "DELETE FROM customer WHERE id = ?";
            
            // prepare the statement for execution
            stmnt = conn.prepareStatement(sql);
            
            // set the id of the customer to delete
            stmnt.setInt(1, person.getID());
            
            // execute the delete
            stmnt.executeUpdate();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			stmnt.close();
		}
		
	}
	
	public int getRowCount() throws SQLException {//returns row count for data-schema total
		ResultSet rs = null;
		PreparedStatement stmt = null;
		int count = 0;
		try{
			stmt = conn.prepareStatement("SELECT * from customer");
			rs = stmt.executeQuery();
			while(rs.next()){
				count++;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			rs.close();
			stmt.close();
		}
		return count;
	}
	
	public int createCustomer(Customer to_create) throws Exception{
		int cust_id = 0;
		PreparedStatement stmt = null;
		ResultSet results = null;
		
		try{
			String insertNewCustomer = "INSERT INTO customer(first_name, last_name, address, city, state, phone)" +
									   " VALUES(?, ?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(insertNewCustomer, Statement.RETURN_GENERATED_KEYS);
			//map customer object to new sql row
			stmt.setString(1, to_create.getFirstName());
			stmt.setString(2, to_create.getLastName());
			stmt.setString(3, to_create.getAddress());
			stmt.setString(4, to_create.getCity());
			stmt.setString(5, to_create.getState());
			stmt.setString(6, to_create.getPhoneNumber());
			
			
			int rows = stmt.executeUpdate();
		    
			//if no rows produced in the update then row insertion failed
		    if(rows == 0){
		    	throw new SQLException("Error: Can't insert customer");
		    }
			//get primary key from result set
		    results = stmt.getGeneratedKeys();
			if(results!=null&&results.next()){
				cust_id = results.getInt(1);
			}
			else{
				throw new SQLException("Error: No generated auto-key for this customer");
			}
			
		}
		finally{
			stmt.close();
			results.close();
		}
		return cust_id;
	}

}
