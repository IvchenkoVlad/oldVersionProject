<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.omg.CORBA.Current"%>
<%@ page import="java.util.*,com.ivchenko.administration.*" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
  
<html>
<head>
	<title>Admin Page</title>
	<script type="text/javascript">
	    function checkAll(checkId){
	        var inputs = document.getElementsByTagName("input");
	        for (var i = 0; i < inputs.length; i++) { 
	            if (inputs[i].type == "checkbox" && inputs[i].id == checkId) { 
	                if(inputs[i].checked == true) {
	                    inputs[i].checked = false ;
	                    
	                } else if (inputs[i].checked == false ) {
	                    inputs[i].checked = true ;
	                }
	            }  
	        } 
	    }
	    function sum(){
	    	var sum = 0; var index = 0;
	        var inputs = document.getElementsByTagName("input");
	        var table = document.getElementById("table");
	        for (var i = 0; i < inputs.length; i++) {	        	
	        		if (inputs[i].type == "checkbox" && inputs[i].id == "chk"){
	        			 if(inputs[i].checked == true){
	        				 sum = sum + parseInt(table.rows[i-3].cells[4].innerHTML.substring(1));
	        				 index = index + 1;
	        			 }
	        		}        	
	        }
	        document.getElementById("val").value = "$ "+sum+".00";
	        document.getElementById("num").value = index;
	    }
	</script>
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
	
		
	<div id="body">
		<form align = "center" action="PackageControllerServlet">
		    Select a Customer:
		    <select name="customerID">
		        <c:forEach items="${listOfCustomers}" var="customer">
		            <option  value="${customer.customerID}">PMB# ${customer.pmbNumber}: ${customer.firstName} ${customer.lastName}</option>
		        </c:forEach>
		    </select>
		    <br/><br/>
		    <input type="hidden" value="LIST" name="command">
		    <input type="submit" value="List Parcels" />
		</form>
		
		<c:if test="${not empty parcelsList}">
			<form action="PackageControllerServlet" align="center">
				<!-- regular form style="width:46.5%; float:left" -->
				
				<table class="r_header" id="table" align="center" >
				<thead>
			 			<tr>
						    <th><input type="checkbox" id="chk_new"  onclick="checkAll('chk');" ></th>
						    <th>Tracking #</th> 
						    <th>Received</th>
						    <th>Status</th> 
						    <th>Storage Due</th>
					  	</tr>
				</thead>
				<tbody>
					  	<c:forEach var="i" begin = "0" end = "${parcelsList.size() - 1}">
				 		<tr>
		        				<td><input name="selected_${i}" value="${parcelsList.get(i).getParcelID()}" id="chk" type="checkbox"></td>
		        				<td>${parcelsList.get(i).getTrackingNumber()}</td>
			  				  	<td>${parcelsList.get(i).getReceivedDate()}</td>
			  				  	<td>${parcelsList.get(i).getStatus()}</td>
			  				  	<td>$${storageDueList.get(i)}.00</td> 
			  			</tr>
			  			</c:forEach>
			  	</tbody>
					</table>
					<br>
					<input type="hidden" name="count" value="${parcelsList.size()}">
					<input type="hidden" name="command" value="RELEASE">
					<input type="submit" value="Release">
			</form>
			<table class="summary_table" align="center">
						<thead>
							<tr>
								<th>Count</th>
								<th>Payment</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type="text" name="sum" id="num" value="0" readonly></td>
								<td><input type="text" name="sum" id="val" value="0" readonly></td>
							</tr>
						</tbody>
			</table>
		</c:if>
		
		
		<c:if test="${empty parcelsList}">
		<h1 align="center">No packages to pick up!</h1>
		</c:if>
	</div>


	<script>window.onclick = sum;</script>
	
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