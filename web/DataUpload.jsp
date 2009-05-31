<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Movie Data XML Upload</title>
</head>
<body>
<p>
IMPORTANT INSTRUCTIONS: There are two ways to upload files to the database. You can either upload a single
.xml file or a .zip file containing .xml files. The upload includes validation against a DTD. Therefore, if
your .xml files reference the movie.dtd it needs to be a reference to a publicly accesible location on the
web (http://www.somesite.com/movies.dtd) or to a location within the uploaded zip file (movies.dtd is include
in zip upload).</p>
<p>
 In other words, if you upload a single .xml file the upload will fail if it does not reference
a publicly accessible DTD, since you could not have uploaded the DTD along with it. The recommended practice
is to upload a .zip file with all .xml files and the movies.dtd in the root. You can include a directory
structure in the .zip, but it will make your DTD references more prone to error.
</p>
<form action="dataupload" enctype="multipart/form-data" method="post">
<p>
Please specify a single .xml or .zip file:

<input name="datafile" size="50" type="file">
</p>
<div>
<input value="Send" type="submit">
</div>
</form>
</body>
</html>