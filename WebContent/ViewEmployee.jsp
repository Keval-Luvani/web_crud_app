<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*" 
    import="web_crud_app.DatabaseConnection"%>

<html>
   	<head>
       <title>Employees</title>
    </head>
    <body>
       <h1>Employee Data</h1>
       <%
	       int id = 0;
	       Connection connection = DatabaseConnection.initializeDatabase();
	       String query = "select * from Employee";
	       PreparedStatement preparedStatement = connection.prepareStatement(query);
	       ResultSet resultset = null;
	       
	       try{
	    	  resultset = preparedStatement.executeQuery();
	       }catch(Exception ex){
	    	  ex.printStackTrace();
	       }
       %>
      <table BORDER="1">
	      <tr>
		      <th>ID</th>
		      <th>Name</th>
		      <th>Age</th>
		      <th>Salary</th>
		      <th>Skills</th>
		      <th>Joining_Date</th>
		      <th>Action</th>
	      </tr>
	      <% if(resultset!=null){ 
	      		while(resultset.next()){ %>
	      <tr>
		       <td> <%= ++id %></td>
		       <td> <%= resultset.getString(2) %></td>
		       <td> <%= resultset.getInt(3) %></td>
		       <td> <%= resultset.getFloat(4) %></td>
		       <td> <%= resultset.getString(5) %></td>
		       <td> <%= resultset.getString(6) %></td>
		       <td>
		        <a href="/web_crud_app/employee/update?employeeid=<%= resultset.getInt(1)%>">Update</a>
		        <a href="/web_crud_app/employee/delete?employeeid=<%= resultset.getInt(1)%>">Delete</a>
		       </td>
	   	  </tr>	
      	  <% }} %>
      </table>
      <a href="/web_crud_app/employee/create">Create Employee</a>
     </body>
</html>