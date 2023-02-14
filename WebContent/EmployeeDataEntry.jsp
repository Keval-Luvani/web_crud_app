<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.util.Arrays"
    import="java.util.List"%>
 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Insert Data</title>
	</head>
	<body>
		<form action="<%= request.getContextPath() %>/employee/submit" method="post">
		    <input name="employee_id" type="hidden" value=${employee.getEmployeeId()}>
		    
			<label for="name">Name : </label>
			<input type="text" name="name" required value=${employee.getName()}>${age}<br> 
			
			<label for="age">Age : </label>
			<input type="number" name="age" required min=14 value=${employee.getAge()}><br>
			
			<label for="skills">Skills : </label>
		    <input type="checkbox"  name="skills" value="Java" ${skillList.contains("Java") ? 'checked' : ''}>Java
		    <input type="checkbox"  name="skills" value="Python" ${skillList.contains("Python") ? 'checked' : ''}>Python
		    <input type="checkbox"  name="skills" value="Node Js" ${skillList.contains("Node Js") ? 'checked' : ''}>Node Js	
		    <input type="checkbox"  name="skills" value="PHP" ${skillList.contains("PHP") ? 'checked' : ''}>PHP
		    <input type="checkbox"  name="skills" value="React" ${skillList.contains("React") ? 'checked' : ''}>React
		    <input type="checkbox"  name="skills" value="Angular" ${skillList.contains("Angular") ? 'checked' : ''}>Angular
		    <input type="checkbox"  name="skills" value="SQL" ${skillList.contains("SQL") ? 'checked' : ''}>SQL <br>
		    
		    <label for="salary">Salary : </label>
			<input type="number" name="salary" required step=0.01 value=${employee.getSalary()} ><br>
			
			<label for="date">Joining Date : </label>
			<input type="date" name="joining_date" required max=${todayDate} value=${employee.getJoiningDate()}> <br>
			
			<input type="submit" value="submit">
		</form>
	</body>
</html>