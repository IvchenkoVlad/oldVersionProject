package com.ivchenko.administration;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


//@WebServlet("/DisplayCustomersServlet")
@WebServlet(value="/CustomerControllerServlet")

public class CustomerControllerServlet extends HttpServlet {   
	private static final long serialVersionUID = 1L;
	
	private CustomerDAO customerDao;
	
	@Resource(name="jdbc/project_db")
	private DataSource dataSource;
	
    @Override
	public void init() throws ServletException {
    	super.init();
    
		try {
			customerDao = new CustomerDAO(dataSource);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public CustomerControllerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String theCommand = request.getParameter("command");
			
			if(theCommand == null) {
				theCommand = "LIST";
			}
			
			switch (theCommand) {
			case "LIST":
				listCustomers(request, response);
				break;
			case "LOAD":
				loadCustomer(request, response);
				break;
			case "UPDATE":
				updateCustomer(request, response);
				break;
			case "ADD":
				addCustomer(request, response);
				break;
			case "DELETE":
				deleteCustomer(request, response);
				break;
			default:
				listCustomers(request, response);
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}	
	}

	private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String customerId = request.getParameter("customerId");
		
		customerDao.deleteCustomer(customerId);
		
		listCustomers(request, response);
		
		
	}

	private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		int pmbNumber = Integer.parseInt(request.getParameter("pmb"));
		String phone = request.getParameter("phone"); 
		String email = request.getParameter("email");
		int customerFK = getLoggedUserId(request, response);
		
		Customer customer = new Customer(firstName, lastName, pmbNumber, phone, email, customerFK);
		
		if(customerDao.isPMBAvailable(pmbNumber, -100, customerFK)) {
			customerDao.addCustomer(customer);
			listCustomers(request, response);
		}
		else {
			request.setAttribute("errorString", "PMB # '"+pmbNumber+"' is already in use.");
			request.setAttribute("theCustomer", customer);
			RequestDispatcher rDispatcher = request.getRequestDispatcher("add-customer.jsp");
			rDispatcher.forward(request, response);
		}
		
	}

	private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		
		//read data from the form
		int customerID = Integer.parseInt(request.getParameter("customerId"));
		int currentPmbNumber = Integer.parseInt(request.getParameter("customerPmb"));
		
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		int updatedPmbNumber = Integer.parseInt(request.getParameter("pmb"));
		String phone = request.getParameter("phone"); 
		String email = request.getParameter("email");
		int userFK = getLoggedUserId(request, response);
		
		Customer theCustomer = new Customer(customerID, firstName, lastName, updatedPmbNumber, phone, email, userFK);
			
		if(customerDao.isPMBAvailable(updatedPmbNumber, currentPmbNumber, userFK)) {
			//updating customer info into DB
			customerDao.updateCustomer(theCustomer);
			
			//redirect to admin-page.jsp
			listCustomers(request, response);
		}
		else {
			request.setAttribute("errorString", "PMB # '"+updatedPmbNumber+"' is already in use.");
			request.setAttribute("theCustomer", theCustomer);
			RequestDispatcher rDispatcher = request.getRequestDispatcher("update-customer.jsp");
			rDispatcher.forward(request, response);
		}
	}

	private void loadCustomer(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		
		//read customer id from the admin-page form
		String theCustomerId = request.getParameter("customerId");
		
		//get customer from database util 'customerDAO'
		Customer theCustomer = customerDao.getCustomer(theCustomerId);
		
		//place students in the request attribute
		request.setAttribute("theCustomer", theCustomer);
		 
		//send data to add-customer.jsp
		RequestDispatcher rDispatcher = request.getRequestDispatcher("update-customer.jsp");
		rDispatcher.forward(request, response);
		     
	}

	private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		
		//getting current sesion user
		User currentUser = getLoggedUser(request, response);
		
		//retrieve all the customers related to current user
		List<Customer> customersList = customerDao.getCustomers(currentUser);
		
		//place attribute customersList into request
		request.setAttribute("customerList", customersList);
		
		//redirect to admin-page.jsp  
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("admin-page.jsp");
		requestDispatcher.forward(request, response);
		
	}
	
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
	
	public boolean isValid(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

}
