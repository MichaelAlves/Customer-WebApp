package customer.webapp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Servlet implementation class ExcelFile
 */
@WebServlet("/customer.webapp/ExcelFile")
public class ExcelFile extends HttpServlet {
	private CustomerDB customers = null;
	private static final long serialVersionUID = 1L;
    
	public void init() throws ServletException{
		super.init();
		try{
			customers = new CustomerDB(this.getServletContext().getRealPath("customerdata/customers.db"));
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExcelFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=customers.xls");
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Customers");
			CustomerDataSourceResult data = new CustomerDataSourceResult();
			data.setData(customers.customerList());
			int rownum = 0;
			for(Customer c: data.getData()){
				HSSFRow row = sheet.createRow(rownum);
				if(rownum > 0){
					row.createCell(0).setCellValue((String) c.getFirstName());
					row.createCell(1).setCellValue((String) c.getLastName());
					row.createCell(2).setCellValue((String) c.getAddress());
					row.createCell(3).setCellValue((String) c.getCity());
					row.createCell(4).setCellValue((String) c.getState());
					row.createCell(5).setCellValue(c.getPhoneNumber());
				}
				else{
					row.createCell(0).setCellValue((String) "First name");
					row.createCell(1).setCellValue((String) "Last name");
					row.createCell(2).setCellValue((String) "Address");
					row.createCell(3).setCellValue((String) "City");
					row.createCell(4).setCellValue((String) "State");
					row.createCell(5).setCellValue((String) "Phone Number");
				}
				rownum++;
			}
			workbook.write(response.getOutputStream());
			response.flushBuffer();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
