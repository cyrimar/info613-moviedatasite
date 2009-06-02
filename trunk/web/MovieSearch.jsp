<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <script type="text/javascript">
function clearFields() 
{
if (confirm("Are you sure you want to clear form?")) 
document.search.reset();
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Movie Search</title>
</head>
<body>
<table bgcolor="#D8D8E8" align="center">
<tr><th><h1>INFO 613 Movie Data Website<h1></th></tr>
</table>
<hr/>
<p><a href="Index.html">Home Page</a></p>
<form name="search" action="reporting" method="POST" style="height: 136px">
         <center><h2>Search Movies</h2></center> 
         <center><input type="radio" name="radios" value="director" checked>Director</center>
          	<center><center style="width: 50 px"><input type="radio" name="radios" value="actor">Actor/Actress</center>
             </center>  
         <center> <b>First Name:</b> <input type="text" name="firstName" size="20"> </center> 
  <center> <b>Last Name: </b><input type="text" name="lastName" size="20"></center><br>
  <center><input type = "submit" value="Submit" > <input type = "button" value="Clear" onClick="clearFields()"></center>
    </form>
</body>
</html>