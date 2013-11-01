package customer.webapp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class Cities
 */

//The purpose of this class is to send a JSON response with a list of cities for the drop-down list data source in the kendo UI filter menu
@WebServlet("/customer.webapp/Cities")
public class Cities extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CustomerDB custRepo;
    private Gson _gson = new Gson();
    
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
    public Cities() {
    	super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub   
		
		try {
            
            CustomerDataSourceResult d = new CustomerDataSourceResult();
            d.setData(custRepo.customerList());
            response.setContentType("json");
            response.getWriter().print(_gson.toJson(d.getCities()));
            
		} catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response.sendError(500);
		}
         
         
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
