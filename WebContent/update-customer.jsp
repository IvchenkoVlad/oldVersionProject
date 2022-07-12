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
			<input type="hidden" name="command" value = "UPDATE">
			<input type="hidden" name="customerId" value = "${theCustomer.customerID}">
			<input type="hidden" name="customerPmb" value = "${theCustomer.pmbNumber}">
				<p style="color: red;">${errorString}</p>
				First Name <br>
				<input type = "text" name="firstname" value='${theCustomer.firstName}' required/> <br>
				Last Name <br>
				<input type = "text" name="lastname" value="${theCustomer.lastName}" required/> <br>
				PMB# <br>
				<input type = "text" name="pmb" value="${theCustomer.pmbNumber}" required/> <br>
				Phone <br>
				<input type = "text" name="phone" value="${theCustomer.phone}" required/> <br> 
				Email <br>
				<input type = "text" name="email" value="${theCustomer.email}" required/> <br>
				<input type="submit" value="Update">
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