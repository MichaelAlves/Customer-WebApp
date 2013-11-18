package customer.webapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Servlet implementation class PDF_File
 */
@WebServlet("/customer.webapp/PDF_File")
public class PDF_File extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CustomerDB customers = null;
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
    public PDF_File() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		response.addHeader("Content-Type", "application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=customers.pdf");
		
		PDDocument doc = new PDDocument();
	    PDPage page = new PDPage();
	    doc.addPage( page );
	 
	    PDPageContentStream contentStream = new PDPageContentStream(doc, page);
	 
	    
		String[][] content = new String[customers.customerList().size()][6];
		int i = 0;
		for(Customer c: customers.customerList()){
			content[i][0] = c.getFirstName();
			content[i][1] = c.getLastName();
			content[i][2] = c.getAddress();
			content[i][3] = c.getCity();
			content[i][4] = c.getState();
			content[i][5] = (String) c.getPhoneNumber();
			i++;
		}
		drawTable(page, contentStream, 700, 100, content);
		doc.save(output);
		response.getOutputStream().write(output.toByteArray());
		response.flushBuffer();
		contentStream.close();

		} catch (SQLException | COSVisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	public static void drawTable(PDPage page, PDPageContentStream contentStream, float y, float margin, String[][] content) throws IOException {
			final int rows = content.length;
			final int cols = content[0].length;
			final float rowHeight = 20f;
			final float tableWidth = page.findMediaBox().getWidth()-(2*margin);
			final float tableHeight = rowHeight * rows;
			final float colWidth = tableWidth/(float)cols;
			final float cellMargin=5f;

			//draw the rows
			float nexty = y ;
			for (int i = 0; i <= rows; i++) {
				contentStream.drawLine(margin,nexty,margin+tableWidth,nexty);
			nexty-= rowHeight;
			}

			//draw the columns
			float nextx = margin;
			for (int i = 0; i <= cols; i++) {
				contentStream.drawLine(nextx,y,nextx,y-tableHeight);
				nextx += colWidth;
			}

			//now add the text
			contentStream.setFont(PDType1Font.HELVETICA_BOLD,12);

			float textx = margin+cellMargin;
			float texty = y-15;
			for(int i = 0; i < content.length; i++){
				for(int j = 0 ; j < content[i].length; j++){
					String text = content[i][j];
					contentStream.beginText();
					contentStream.moveTextPositionByAmount(textx,texty);
					contentStream.drawString(text);
					contentStream.endText();
					textx += colWidth;
				}
				texty-=rowHeight;
				textx = margin+cellMargin;
			}
	}

}
