package customer.webapp;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet("/customer.webapp/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDB custRepo = null;
	private Gson g = new Gson();
	
	public void init() throws ServletException{
		super.init();
	    try {
			custRepo = new CustomerDB(this.getServletContext().getRealPath("customerdata/customers.db"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		try{
			//data source we will be using in our application
			CustomerDataSourceResult d = new CustomerDataSourceResult();
			d.setData(custRepo.customerList());
			d.setTotal(custRepo.getRowCount());
			
			response.setContentType("json");
			
			//print result in JSON format to response
			
			response.getWriter().write(g.toJson(d));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	  }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			
			
			int cust_id = request.getParameter("id") == "" ? 0 : Integer.parseInt(request.getParameter("id"));
			Customer cust = parseNewCustomer(request, cust_id);
			
			//get CRUD request
			String sqlOperation = request.getQueryString().trim();
			
			if (sqlOperation.equals("update")) {
                // add the customer id and update the customer
                custRepo.updateCustomer(cust);
			}
			if (sqlOperation.equals("create")) {
                //create the customer
                cust.setID(custRepo.createCustomer(cust));
                response.getWriter().write(g.toJson(cust));
			}
        	if (sqlOperation.equals("delete")) {
                // delete the customer
                custRepo.deleteCustomer(cust);
        	}
		}
		catch(SQLException e){
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	/**
	 * creates a new customer object with client's grid input
	 * @param request
	 * @return customer
	 */
	private Customer parseNewCustomer(HttpServletRequest request, int cust_id){
		//parse new customer from request
		Customer cust = new Customer();
		cust.setID(cust_id);
		cust.setFirstName(request.getParameter("first_name"));
		cust.setLastName(request.getParameter("last_name"));
		cust.setAddress(request.getParameter("address"));
		cust.setCity(request.getParameter("city"));
		cust.setState(request.getParameter("state"));
		cust.setPhoneNumber(request.getParameter("phone"));
		return cust;
	}
	

}
