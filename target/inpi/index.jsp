<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
	<h1>Welcome to INPI Web Application</h1>
    <h4>To start the application, please click on the button below to load INPI dataset ( Multi Xml Zip File )</h4>
 
	<form action="rest/file/upload" method="post" enctype="multipart/form-data">
	   <p>
			Select a file : <input type="file" name="file" size="45" accept=".zip"/>
	   </p>
	   <input type="submit" value="Upload It" />
	</form>
 
</body>
</html>