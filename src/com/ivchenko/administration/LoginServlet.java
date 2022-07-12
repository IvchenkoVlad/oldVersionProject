package com.ivchenko.administration;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UsersDAO usersDaoUtil;
	
	@Resource(name="jdbc/project_db")
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

	public LoginServlet() {
        super();
        
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			User user = usersDaoUtil.isUserExists(username, username);
			if(user == null) {
				String loginError = "Wrong username or password";
				request.setAttribute("errorString", loginError);
				RequestDispatcher dispatcher = request.getRequestDispatcher("login-page.jsp");
				dispatcher.forward(request, response);
			}
			else {
				PasswordHashing ph = new PasswordHashing();
				user = usersDaoUtil.checkPasswordHash(user);
				boolean isPasswordCorrect = ph.checkHash(password, user.getPassword(), user.getSalt());
				if(isPasswordCorrect) {
					HttpSession session = request.getSession(true);	 
					session.setAttribute("loggedUser", user);
			        session.setAttribute("currentSessionUser",username); 
					RequestDispatcher dispatcher = request.getRequestDispatcher("/delivery-page.jsp");
					dispatcher.forward(request, response);
				}
				else {
					String loginError = "Wrong username or password";
					request.setAttribute("errorString", loginError);
					RequestDispatcher dispatcher = request.getRequestDispatcher("login-page.jsp");
					dispatcher.forward(request, response);
				}
 			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
