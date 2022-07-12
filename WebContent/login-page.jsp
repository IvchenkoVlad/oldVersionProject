<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
<title>Login</title> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="css/style.css" rel="stylesheet">
</head>
<body>
<div id="container">
	<div id="header">
		<header>
			<form align="right" action="register-page.jsp">
				<p>Not a user? <input type="Submit" value="Register"/></p>
			</form>
		</header>
	</div>
	
	<div id="body">
		<form class="login-form" action="LoginServlet" method="POST">
			<p style="color: red;">${errorString}</p>
			<p>Username:<input type = "text" name="username" placeholder="username or email" required/></p>
			<p>Password:<input type = "password" name="password" placeholder="password" required/></p> 
			<input type="submit" value="Log in">
		</form>
	</div>
	
	
	<div id="footer">
		<h6 align="center">
			  Developed by: Vladyslav Ivchenko<br>
			  Contact information: <a href="mailto:ivchenko.vladyslav@icloud.com">
			 ivchenko.vladyslav@icloud.com<br></a>
			   
		</h6>
	</div>
</div>
</body>
</html>