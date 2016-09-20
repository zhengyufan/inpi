<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Patent List By Inventor</title>
</head>
<body>
	<h2>Search Patent List By Inventor</h2>
	<form method="POST" action="rest/inventor">
		<label>Given Name : </label>
		<input name="given-name"/>
		<label>Family Name : </label>
		<input name="family-name"/>
		<button type="submit">Search</button>
	</form>
</body>
</html>