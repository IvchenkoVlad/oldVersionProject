<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.ivchenko.administration.*" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration</title>
<link href="css/style.css" rel="stylesheet">
</head>
<body>
<div id="container">
	<div id="header">
		<header align="right">
			<form action="CustomerControllerServlet">
				<p><input type="Submit" value="Back to admin page"/></p>
			</form>
		</header>
	</div>
	
	<div id="body">
		 <form class="register-form" action="CustomerControllerServlet" align="center" method="GET">
			<input type="hidden" name="command" value = "ADD">
				<p style="color: red;">${errorString}</p>
				First Name <br>
				<input type = "text" name="firstname"  required/> <br>
				Last Name <br>
				<input type = "text" name="lastname"  required/> <br>
				PMB# <br>
				<input type = "text" name="pmb" required/> <br>
				Phone <br>
				<input type = "text" name="phone"  required/> <br> 
				Email <br>
				<input type = "text" name="email" required/> <br>
				<input type="submit" value="Save">
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