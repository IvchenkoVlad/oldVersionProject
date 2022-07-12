<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.omg.CORBA.Current"%>
<%@ page import="java.util.*,com.ivchenko.administration.*" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html>
<head>
	<title>Main Page</title>
	<link href="css/style.css" rel="stylesheet">
</head>

 <body>
 
 <div id="container">
	<div id="header">
				<form align="right" action="LogOutServlet" method="post"> 
 						<input  type="submit" value="Log out[${currentSessionUser}]"/>	 				
 				</form> 
	</div>

		<div id="nav-bar">
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
				<li><a href="">History</a></li>
			</ul>
		</div>

	<br>
	<div>
		<c:if test="${not empty submitDone}">
			<script>alert("You have successfully accepted all the packages.");</script>
		</c:if>
		<c:if test="${empty listOfUnaccepted}">
			<form action="PackageControllerServlet" align="center">
				<!-- regular form -->
				
				<table class="fixed_header" align="center" >
				<thead>
			 			<tr>
						    <th>Tracking #</th>
						    <th>PMB #</th> 
					  	</tr>
				</thead>
				<tbody>
					  	<c:forEach var="i" begin = "1" end = "25">
				 		<tr>
		        				<td><input name="tracking_${i}" type="text"></td>
			  				  	<td><input name="pmb_${i}" type="text"></td>
			  			</tr>
			  			</c:forEach>
			  	</tbody>
					</table>
					<input type="hidden" name="command" value="ACCEPT">
					<input type="hidden" name="count" value="25">					
					<input type="submit" value="Accept Packages">
					
			</form>
		</c:if>
		<c:if test="${not empty listOfUnaccepted}">
			<form action="PackageControllerServlet" align="center">
				<p style="color: red;">${errorString}</p>
				<table class="fixed_header" id="custtable" align="center" border='1'>
				<thead>
			 			<tr>
						    <th>Tracking #</th>
						    <th>PMB #</th> 
					  	</tr>
				</thead>
				<tbody>
					  	<c:forEach var="i" begin = "0" end = "${listOfUnaccepted.size() - 1}">
				 		<tr>
		        				<td><input name="tracking_${i+1}" value="${listOfUnaccepted.get(i).getTrackingNumber()}" type="text"></td>
			  				  	<td><input name="pmb_${i+1}" value="${listOfUnaccepted.get(i).getStatus()}" type="text"></td>
			  				  	
			  				  	<!--  add one more peace that can accept the SHIP FROM data + also update in database -->
			  			</tr>
			  			</c:forEach>
			  	</tbody>
					</table>
					<input type="hidden" name="command" value="ACCEPT">
					<input type="hidden" name="count" value="${listOfUnaccepted.size()}">
					<input type="submit" value="Accept Packages">
			</form>
		</c:if>
	</div>
	<!-- <div id="footer">
		<h6 align="center">
			  Developed by: Vladyslav Ivchenko<br>
			  Contact information: <a href="mailto:ivchenko.vladyslav@icloud.com">
			 ivchenko.vladyslav@icloud.com<br></a>
			   
		</h6>
	</div>-->
</div>

</body>
</html>