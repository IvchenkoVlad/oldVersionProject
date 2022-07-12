package com.ivchenko.administration;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

public class CustomerDAO {

	private DataSource dataSource;

	public CustomerDAO(DataSource thedataSource) {
		dataSource = thedataSource;
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		// TODO Auto-generated method stub
		try {
			if (myRs != null) {
				myRs.close();
			}
			if (myStmt != null) {
				myStmt.close();
			}
			if (myConn != null) {
				myConn.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isPMBAvailable(int updatedPmb, int currentPmb, int userFK) throws SQLException {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		if(updatedPmb == currentPmb) {
			return true;
		}

		try {
			myConn = dataSource.getConnection();
			String sql = "SELECT * FROM customer WHERE customer.pmbNumber=? AND customer.user_fk=?;"; // and user FK
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, updatedPmb);
			myStmt.setInt(2, userFK);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {		
				return false;
			}
		} finally {
			close(myConn, myStmt, myRs);
		}
		return true;
	}

	public Customer getCustomer(String theCustomerId) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		int id = Integer.parseInt(theCustomerId);
		try {
			myConn = dataSource.getConnection();
			String sql = "SELECT * FROM customer WHERE customer.customer_id=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, theCustomerId);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {		
				String firstName = myRs.getString("firstname");
				String lastName = myRs.getString("lastname");
				int pmbNumber = myRs.getInt("pmbNumber");
				String phone = myRs.getString("phone");
				String email = myRs.getString("email");
				int userFK = myRs.getInt("user_fk");

				Customer customer = new Customer(id, firstName, lastName, pmbNumber, phone, email, userFK);
				return customer;
			}
		} finally {
			close(myConn, myStmt, myRs);
		}
		return null;
	}

	public void updateCustomer(Customer theCustomer) throws SQLException {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		
		try {
			myConn = dataSource.getConnection();
			  
			String sql = "UPDATE project_db.customer "
					+ "SET firstname=?, lastname=?, phone=?,email=?,pmbNumber=? "
					+ "WHERE customer_id=? and user_fk =?;";
			
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, theCustomer.getFirstName());
			myStmt.setString(2, theCustomer.getLastName());
			myStmt.setString(3, theCustomer.getPhone());
			myStmt.setString(4, theCustomer.getEmail());
			myStmt.setInt(5, theCustomer.getPmbNumber());
			myStmt.setInt(6, theCustomer.getCustomerID());
			myStmt.setInt(7, theCustomer.getUserFK());
			 
			myStmt.execute();
			
		}
		finally {
			close(myConn, myStmt, null);
		}
		
	}



	public List<Customer> getCustomers(User currentUser) throws SQLException {

		int user_fk = currentUser.getId();
		List<Customer> listOfCustomers = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = dataSource.getConnection();
			String sql = "SELECT * FROM customer WHERE customer.user_fk=? ORDER BY customer.pmbNumber";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, user_fk);
			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				int id = myRs.getInt("customer_id");
				String firstName = myRs.getString("firstname");
				String lastName = myRs.getString("lastname");
				int pmbNumber = myRs.getInt("pmbNumber");
				String phone = myRs.getString("phone");
				String email = myRs.getString("email");

				Customer customer = new Customer(id, firstName, lastName, pmbNumber, phone, email, user_fk);
				listOfCustomers.add(customer);
			}
		} finally {
			close(myConn, myStmt, myRs);
		}
		return listOfCustomers;
	}



	public void addCustomer(Customer customer) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		
		try {
			myConn = dataSource.getConnection();
			  
			String sql = "INSERT INTO project_db.customer "
					+ "(firstname, lastname, pmbNumber, phone, email, user_fk) VALUES "
					+ "(?, ?, ?, ?, ?, ?);";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, customer.getFirstName());
			myStmt.setString(2, customer.getLastName());
			myStmt.setInt(3, customer.getPmbNumber());
			myStmt.setString(4, customer.getPhone());
			myStmt.setString(5, customer.getEmail());
			myStmt.setInt(6, customer.getUserFK());
			 
			myStmt.execute();
			
		}
		finally {
			close(myConn, myStmt, null);
		}
		
	}



	public void deleteCustomer(String customerId) throws SQLException {
		int custID = Integer.parseInt(customerId);
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		myConn = dataSource.getConnection();
		
		String sql = "DELETE FROM project_db.customer WHERE customer_id=?;";
		
		myStmt = myConn.prepareStatement(sql);
		myStmt.setInt(1, custID);
		
		myStmt.execute();
		
		
	}

	public int getCustomerByPmb(String pmbNumber, int userFK) throws SQLException {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = dataSource.getConnection();
			String sql = "SELECT * FROM customer WHERE customer.pmbNumber=? AND customer.user_fk=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, Integer.parseInt(pmbNumber));
			myStmt.setInt(2, userFK);
			myRs = myStmt.executeQuery();
			if (myRs.next()) {		
				int id = myRs.getInt("customer_id");
				return id;
			}
		} finally {
			close(myConn, myStmt, myRs);
		}
		return -1;
	}

	
}

