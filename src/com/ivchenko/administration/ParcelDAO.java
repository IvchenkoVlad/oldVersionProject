package com.ivchenko.administration;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class ParcelDAO {
	
	private DataSource dataSource;

	public ParcelDAO(DataSource thedataSource) {
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

	public void acceptParcel(String trackingNumber, int customerFK, int userFK) throws SQLException, ParseException {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		java.util.Date myDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date( myDate.getTime() );
		
		try {
			myConn = dataSource.getConnection();
			  
			String sql = "INSERT INTO project_db.parcel "
					+ "(trackingN, received, released, status, sender, customer_fk, user_fk) VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?);";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, trackingNumber);
			myStmt.setDate(2, sqlDate);
			myStmt.setDate(3, null);
			myStmt.setString(4, "awaiting for pick up"); 
			myStmt.setString(5, null);
			myStmt.setInt(6, customerFK);
			myStmt.setInt(7, userFK);
			 
			myStmt.execute();
			
		}
		finally {
			close(myConn, myStmt, null);
		}
		
	}

	public List<Parcel> listParcels(int userFK, int customerFK) throws SQLException {
		
		List<Parcel> listOfParcels = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = dataSource.getConnection();
			String sql = "SELECT * FROM parcel WHERE parcel.user_fk=? and parcel.customer_fk=? "
					+ "and parcel.status=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, userFK);
			myStmt.setInt(2, customerFK);
			myStmt.setString(3, "awaiting for pick up");
			myRs = myStmt.executeQuery();
			while (myRs.next()) {
				int id = myRs.getInt("parcel_id");
				String trackingNumber = myRs.getString("trackingN");
				Date received = myRs.getDate("received");
				Date released = myRs.getDate("released");
				String status = myRs.getString("status");
				String sender = myRs.getString("sender");
				int customerID = myRs.getInt("customer_fk");
				int userID = myRs.getInt("user_fk");
				
				

				Parcel customer = new Parcel(id, trackingNumber, received, released, status, sender, customerID, userID);
				listOfParcels.add(customer);
			}
			return listOfParcels;
		} finally {
			close(myConn, myStmt, myRs);
		}
		
	}

	public void releaseParcels(int parcelID) throws SQLException {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		java.util.Date myDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date( myDate.getTime() );
		
		
		try {
			myConn = dataSource.getConnection();
			  
			String sql = "UPDATE project_db.parcel "
					+ "SET released=?, status=? "
					+ "WHERE parcel_id=?;";
			
			myStmt = myConn.prepareStatement(sql);
			myStmt.setDate(1, sqlDate);
			myStmt.setString(2, "picked up");
			myStmt.setInt(3, parcelID);

			 
			myStmt.execute();
			
		}
		finally {
			close(myConn, myStmt, null);
		}
		
	}

	
}
