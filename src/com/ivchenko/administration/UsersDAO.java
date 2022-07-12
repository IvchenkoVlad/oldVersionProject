package com.ivchenko.administration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

public class UsersDAO {
	
	private DataSource dataSource;
	
	public UsersDAO(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public User isUserExists(String username, String email) throws SQLException {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.createStatement();
			String sql = "SELECT * FROM users WHERE users.username='"+ username +"' OR users.email='"+email+"'";
			myRs = myStmt.executeQuery(sql);
			if(myRs.next()) {
				int id =myRs.getInt("user_id");
				User newUser = new User(id);
				return newUser;
			}
			return null;
			
		} finally {
			close(myConn, myStmt, myRs);
		}
	
	}
	
	public User loginUser(String promtedUnOrEm, String promtedPassword) throws Exception{
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
			  
			String sql = "SELECT * FROM users WHERE "
					+ "(username = '" + promtedUnOrEm + "' OR email='"+promtedUnOrEm+"')"
							+ " AND password = '" +promtedPassword +"';";
			
			myStmt = myConn.createStatement();
			 
			myRs = myStmt.executeQuery(sql);
			
			if (myRs.next()) {
				int id =myRs.getInt("user_id");
				String username = myRs.getString("username");
				String password = myRs.getString("password");
				String email = myRs.getString("email");
				User logedUser = new User(id, username, password, email);
				return logedUser;
			}
				return null;
		}
		finally {
			close(myConn, myStmt, myRs);
		}
		
	
	}

	public User checkPasswordHash(User user) throws SQLException {
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
			myStmt = myConn.createStatement();
			String sql = "SELECT * FROM users WHERE users.user_id='"+ user.getId()+ "';";
			myRs = myStmt.executeQuery(sql);
			if(myRs.next()) {
				String username = myRs.getString("username");
				String password = myRs.getString("password");
				String salt = myRs.getString("salt");
				String email = myRs.getString("email");
				//String firstName = myRs.getString("firstname");
				//String lastName = myRs.getString("lastname");
				user.setUsername(username);
				user.setPassword(password);
				user.setSalt(salt);
				user.setEmail(email);
				return user;
			}
			return null;
			
		} finally {
			close(myConn, myStmt, myRs);
		}
		
		
	}
	
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		// TODO Auto-generated method stub
		try {
			if(myRs != null) {
				myRs.close();
			}
			if(myStmt != null) {
				myStmt.close();
			}
			if(myConn != null) {
				myConn.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void registerUser(String promtedUserName, String promtedPassword, String salt, String promtedEmail,
			String promtedFn, String promtedLn) throws Exception{
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
			  
			String sql = "INSERT INTO `project_db`.`users` "
					+ "(`username`, `password`, `salt`, `email`, `firstname`,`lastname`) VALUES "
					+ "('"+ promtedUserName+"', '"+ promtedPassword+"', '"+ salt +"', '"+ promtedEmail+"', '"+promtedFn+"', '"+promtedLn+"');";
			
			myStmt = myConn.createStatement();
			 
			myStmt.executeUpdate(sql);
			
		}
		finally {
			close(myConn, myStmt, myRs);
		}
	}
}
