package com.ivchenko.administration;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Servlet implementation class PackageControllerServlet
 */
@WebServlet("/PackageControllerServlet")
public class ParcelControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ParcelDAO parcelDao;
	private CustomerDAO customerDao;

	@Resource(name = "jdbc/project_db")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			parcelDao = new ParcelDAO(dataSource);
			customerDao = new CustomerDAO(dataSource);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ParcelControllerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String theCommand = request.getParameter("command");

			if (theCommand == null) {
				theCommand = "ACCEPT";
			}

			switch (theCommand) {
			case "ACCEPT":
				acceptParcels(request, response);
				break;
			case "LOAD":
				loadUsers(request, response);
				break;
			case "LIST":
				listParcels(request, response);
				break; 
			case "RELEASE":
				releaseParcels(request, response);
				break;
			default:
				break;
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void releaseParcels(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
		int index = Integer.parseInt(request.getParameter("count"));
		for(int i = 0; i < index; i++) {
			if(request.getParameter("selected_"+i) != null) {
				int parcelID = Integer.parseInt(request.getParameter("selected_"+i));
				parcelDao.releaseParcels(parcelID);
			}
		}
		loadUsers(request, response);
		
	}

	private void listParcels(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int userFK = getLoggedUserId(request, response);
		int customerFK = Integer.parseInt(request.getParameter("customerID"));
		List<Parcel> listOfParcel = parcelDao.listParcels(userFK, customerFK);
		request.setAttribute("parcelsList", listOfParcel); 
		List<Integer> storageDue = new  ArrayList<Integer>();
		for (Parcel parcel : listOfParcel) {
			int storage = getStorageDue(parcel.getReceivedDate());
			storageDue.add(storage);
		}
		request.setAttribute("storageDueList", storageDue);
		loadUsers(request, response);
	}

	private int getStorageDue(Date receivedDate) {
		java.util.Date date = new java.util.Date(); //today
		int difference =  (int) ((date.getTime()-receivedDate.getTime())/86400000) - 3;
		if(difference< 0){
		    difference = 0;
		}
		return difference;
	}

	private void loadUsers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
 
		User user = getLoggedUser(request, response);
		List<Customer> listOfCustomers = customerDao.getCustomers(user);
		request.setAttribute("listOfCustomers", listOfCustomers);
		RequestDispatcher rDispatcher = request.getRequestDispatcher("release-page.jsp");
		rDispatcher.forward(request, response);
		
	}

	private void acceptParcels(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException, ParseException, ServletException {

		List<Parcel> listOfUnaccepted = new ArrayList<Parcel>();
		List<Parcel> listOfAccepted = new ArrayList<Parcel>();
		int count = 0;
		if(request.getParameter("count") != null || request.getParameter("count") != "") {
			count = Integer.parseInt(request.getParameter("count"));
		}  
		else {
			count = 25;
		}
		for (int i = 1; i <= count; i++) {
			String trackingNumber = request.getParameter("tracking_" + i);
			String pmbNumber = request.getParameter("pmb_" + i);
			int userFK = getLoggedUserId(request, response);
			
			if (!trackingNumber.equals("") && !pmbNumber.equals("")) {
				if (!customerDao.isPMBAvailable(Integer.parseInt(pmbNumber), -100, userFK)) {
					int customerID = customerDao.getCustomerByPmb(pmbNumber, userFK);
					if (customerID != -1) {
						Parcel accParcel = new Parcel(trackingNumber, pmbNumber);
						listOfAccepted.add(accParcel);
						 //parcelDao.acceptParcel(trackingNumber, customerID, userFK);
					}
				} else {
					Parcel tempParcel = new Parcel(trackingNumber, "INCORRECT MAILBOX");
					listOfUnaccepted.add(tempParcel);
				}
			}
		}
		
		if(!listOfUnaccepted.isEmpty()) {
			listOfUnaccepted.addAll(listOfAccepted);
			request.setAttribute("listOfUnaccepted", listOfUnaccepted);
			request.setAttribute("errorString", "The mailboxes for selected package(s) wasn's accepted.");
			RequestDispatcher rDispatcher = request.getRequestDispatcher("delivery-page.jsp");
			rDispatcher.forward(request, response);
		}
		else if(listOfUnaccepted.isEmpty() && !listOfAccepted.isEmpty()) {
			for(int i = 0; i < listOfAccepted.size(); i++) {
					String trackingNumber = listOfAccepted.get(i).getTrackingNumber();
					String pmb = listOfAccepted.get(i).getStatus();
					int userFK = getLoggedUserId(request, response);
					int customerID = customerDao.getCustomerByPmb(pmb, userFK);
					parcelDao.acceptParcel(trackingNumber, customerID, userFK);
					
					
				}
			request.setAttribute("parcelList", listOfAccepted);
			RequestDispatcher rDispatcher2 = request.getRequestDispatcher("/PdfGeneratorServlet");
			rDispatcher2.forward(request, response);
		}
		
		
//		RequestDispatcher rDispatcher2 = request.getRequestDispatcher("/PdfGeneratorServlet");
//		rDispatcher2.forward(request, response);
//		if(!listOfAccepted.isEmpty()) {
//			request.setAttribute("parcelList", listOfAccepted);
//			RequestDispatcher rDispatcher2 = request.getRequestDispatcher("/PdfGeneratorServlet");
//			rDispatcher2.forward(request, response);
//		}
//		if (!listOfUnaccepted.isEmpty()) {
//			request.setAttribute("listOfUnaccepted", listOfUnaccepted);
//			request.setAttribute("errorString", "The mailboxes for selected package(s) wasn's accepted.");
//			RequestDispatcher rDispatcher = request.getRequestDispatcher("delivery-page.jsp");
//			rDispatcher.forward(request, response);
//		} else {
//			request.setAttribute("submitDone", "DONE");
//			RequestDispatcher rDispatcher = request.getRequestDispatcher("delivery-page.jsp");
//			rDispatcher.forward(request, response);
//		}
		
		
	}
	
//	private void resubmitParcels(HttpServletRequest request, HttpServletResponse response)
//			throws IOException, SQLException, ParseException, ServletException {
//
//		List<Parcel> listOfUnaccepted = new ArrayList<Parcel>();
//		int index = Integer.parseInt(request.getParameter("count"));
//
//		for (int i = 0; i <= index-1; i++) {
//			String trackingNumber = request.getParameter("tracking_" + i);
//			String pmbNumber = request.getParameter("pmb_" + i);
//			int userFK = getLoggedUserId(request, response);
//
//			if (!trackingNumber.equals("") && !pmbNumber.equals("")) {
//				if (!customerDao.isPMBAvailable(Integer.parseInt(pmbNumber), -100, userFK)) {
//					int customerID = customerDao.getCustomerByPmb(pmbNumber, userFK);
//					if (customerID != -1) {
//						parcelDao.acceptParcel(trackingNumber, customerID, userFK);
//					}
//				} else {
//					Parcel tempParcel = new Parcel(trackingNumber, Integer.parseInt(pmbNumber));
//					listOfUnaccepted.add(tempParcel);
//				}
//			}
//		}
//		//processRequest(request, response);
//		if (!listOfUnaccepted.isEmpty()) {
//			request.setAttribute("listOfUnaccepted", listOfUnaccepted);
//			request.setAttribute("errorString", "The mailboxes for selected package(s) wasn's accepted.");
//			RequestDispatcher rDispatcher = request.getRequestDispatcher("delivery-page.jsp");
//			rDispatcher.forward(request, response);
//		} else {
//			RequestDispatcher rDispatcher = request.getRequestDispatcher("delivery-page.jsp");
//			rDispatcher.forward(request, response);
//		}
//
//	}

	private int getLoggedUserId(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("loggedUser");
		return loggedUser.getId();

	}

	private User getLoggedUser(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("loggedUser");
		return loggedUser;
	}
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Set content type to application / pdf
        //browser will open the document only if this is set
        response.setContentType("application/pdf");
        //Get the output stream for writing PDF object        
        OutputStream out=response.getOutputStream();
        try {
            Document document = new Document();
            Font mailBoxFont = new Font(FontFamily.TIMES_ROMAN, 200.0f, Font.BOLD, BaseColor.BLACK);
			Font generalFont = new Font(FontFamily.TIMES_ROMAN, 30.0f, Font.BOLD, BaseColor.BLACK);
            /* Basic PDF Creation inside servlet */
            PdfWriter.getInstance(document, out);
            @SuppressWarnings("unchecked")
			List<Parcel> parcels = (List<Parcel>) request.getAttribute("parcelList");
            document.open();
            document.newPage();
            for (int i = 0; i < parcels.size(); i++) {
				Paragraph lineOne = new Paragraph("Parcel details:", generalFont);
				Paragraph lineTwo = new Paragraph("#" + "test", mailBoxFont);
				Paragraph lineThree = new Paragraph("Tracking:", generalFont);
				Paragraph lineFour = new Paragraph("test" + "\nDate received:\n" + new java.util.Date(), generalFont);

				lineOne.setAlignment(Element.ALIGN_CENTER);
				lineTwo.setAlignment(Element.ALIGN_CENTER);
				lineThree.setAlignment(Element.ALIGN_CENTER);
				lineFour.setAlignment(Element.ALIGN_CENTER);

				document.add(lineOne);
				document.add(lineTwo);
				document.add(lineThree);
				document.add(lineFour);
				document.newPage();
			}
            document.close();
        }
                catch (DocumentException exc){
                throw new IOException(exc.getMessage());
                }
        finally {            
            out.close();
        }
    }
}
