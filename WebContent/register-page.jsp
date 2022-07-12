<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			<form action="login-page.jsp">
				<p><input type="Submit" value="Back to login"/></p>
			</form>
		</header>
	</div>
	
	<div id="body">
		<form class="register-form"align="center" action="RegisterServlet" method="POST">
			<p style="color: red;">${errorString}</p> 
				First Name <br>
				<input type = "text" name="firstname"required/> <br>
				Last Name <br>
				<input type = "text" name="lastname" required/> <br>
				Username <br>
				<input type = "text" name="username" required/> <br>
				Email <br>
				<input type = "text" name="email" required/> <br> 
				Password <br>
				<input type = "password" name="password" required/> <br>
				<input type="submit" value="Register">
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