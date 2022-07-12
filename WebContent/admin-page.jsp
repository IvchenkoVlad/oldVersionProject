<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.omg.CORBA.Current"%>
<%@ page import="java.util.*,com.ivchenko.administration.*" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html>
<head>
	<title>Admin Page</title>
	<link href="css/style.css" rel="stylesheet">
</head>

 <body>
 
 <div id="container">
	<div id="header">
				<form align="right" action="LogOutServlet" method="post"> 
 						<input  type="submit" value="Log out [${currentSessionUser}]">
 				</form>
	</div>
	

		<div>
				<!-- admin page link -->
				<c:url var="adminLink" value="CustomerControllerServlet">
		 			<c:param name="command" value="LIST"/>
		 		</c:url>
		 		<!-- release page link -->
				<c:url var="releaseLink" value="PackageControllerServlet">
		 			<c:param name="command" value="LOAD"/>
		 		</c:url>
		 		
			<ul>
				<li><a href="delivery-page.jsp">Delivery</a></li>
				<li><a href="${releaseLink}">Pick Up</a></li>
				<li><a href="${adminLink}">Administration</a></li>
				<li><a href="history-page.jsp">History</a></li>
			</ul>
		</div>
		
		<p align="center"><input type="button" value="Add Customer"
		onclick="window.location.href='add-customer.jsp'; return false;"/></p>
		
	<div id="body">
		<c:if test="${not empty currentSessionUser}">
	 		<table id="custtable" align="center" border='1'>
	 			<tr>
				    <th>PMB #</th>
				    <th>First Name</th> 
				    <th>Last Name</th>
				    <th>Phone</th>
				    <th>Email</th>
				    <th>Edit</th>
				    <th>Delete</th>
			  	</tr>
		 		<c:forEach items="${customerList}" var="customer">
		 		
		 		<!-- set up update the link for each customer -->
		 		<c:url var="updLink" value="CustomerControllerServlet">
		 			<c:param name="command" value="LOAD"/>
		 			<c:param name="customerId" value="${customer.customerID}"/>
		 		</c:url>
		 		<!-- set up delete the link for each customer -->
		 		<c:url var="deleteLink" value="CustomerControllerServlet">
		 			<c:param name="command" value="DELETE"/>
		 			<c:param name="customerId" value="${customer.customerID}"/>
		 		</c:url>
		 		
		 		<tr>
	  				  <td>${customer.pmbNumber}</td>
	  				  <td>${customer.firstName}</td>
	  				  <td>${customer.lastName}</td>
	  				  <td>${customer.phone}</td>
	  				  <td>${customer.email}</td>
	  				  <td><a href="${updLink}">Update</a></td>
	  				  <td><a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this customer?')))return false">Delete</a></td>  
	  			</tr>
				</c:forEach>
			</table>
	 	</c:if>
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