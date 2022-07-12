package com.ivchenko.administration;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsersDAO usersDaoUtil;
	@Resource(name = "jdbc/project_db")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub   
		super.init();

		try {
			usersDaoUtil = new UsersDAO(dataSource);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegisterServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		try {   
			User isExist = usersDaoUtil.isUserExists(username, email);
			boolean validEmail = isValidEmail(email);
			if (isExist != null) {
				String loginError = "User with same username/e-mail already exists!";
				request.setAttribute("errorString", loginError);
				RequestDispatcher dispatcher = request.getRequestDispatcher("register-page.jsp");
				dispatcher.forward(request, response);
			} 
			else if(!validEmail){
				String loginError = "Email was not formated correctly!"; 
				request.setAttribute("errorString", loginError);
				RequestDispatcher dispatcher = request.getRequestDispatcher("register-page.jsp");
				dispatcher.forward(request, response);
			}
			else {
				PasswordHashing pHashing = new PasswordHashing();
				User user = pHashing.hashPassword(password);
				
				usersDaoUtil.registerUser(username, user.getPassword(), user.getSalt(), email, firstName, lastName);
				RequestDispatcher dispatcher = request.getRequestDispatcher("login-page.jsp");
				dispatcher.forward(request, response);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

}
