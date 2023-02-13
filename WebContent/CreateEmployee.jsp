<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.time.LocalDate"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Create Employee</title>
	</head>
	<body>
	<%!
	 public String getDate(){
		LocalDate date = LocalDate.now();
        return date.toString(); 
	 }
	%>
		<form action="CreateEmployee" method="post">
			<label for="name">Name : </label>
			<input type="text" name="name" required><br>
			
			<label for="age">Age : </label>
			<input type="number" name="age" required><br>
			
			<label for="skills">Skills : </label>
		    <input type="checkbox"  name="skills" value="Java">Java
		    <input type="checkbox"  name="skills" value="Python">Python
		    <input type="checkbox"  name="skills" value="Node Js">Node Js
		    <input type="checkbox"  name="skills" value="PHP">PHP
		    <input type="checkbox"  name="skills" value="React">React
		    <input type="checkbox"  name="skills" value="Angular">Angular
		    <input type="checkbox"  name="skills" value="SQL">SQL<br>
			
			<label for="salary">Salary : </label>
			<input type="number" name="salary" required step=0.01 ><br>
			
			<label for="date">Joining_Date : </label>
			<input type="date" name="joining_date" max=<%=getDate()%> required><br>
			
			<input type="submit" value="Submit">
		</form>
	</body>
</html>