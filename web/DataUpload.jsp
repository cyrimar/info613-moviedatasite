<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Movie Data XML Upload</title>
</head>
<body>
<form action="DataUploadServlet" enctype="multipart/form-data" method="post">
<p>
Please specify a file, or a set of files:

<input name="datafile" size="50" type="file">
</p>
<div>
<input value="Send" type="submit">
</div>
</form>
</body>
</html>