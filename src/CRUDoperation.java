import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web_crud_app.DatabaseConnection;

public class CRUDoperation {
	public static void createEmployeePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LocalDate date = LocalDate.now();
        String todayDate= date.toString(); 
        request.setAttribute("todayDate", todayDate);
		request.setAttribute("action","create");
		
		request.getRequestDispatcher("/EmployeeDataEntry.jsp").forward(request, response);
	}
	
    public static void fetchData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int employeeId = Integer.parseInt(request.getParameter("employeeid"));
   	 
		try {
	        Connection connection = DatabaseConnection.initializeDatabase();
	        String query = "select * from employee where employee_id=?";
	        
	        PreparedStatement prepareStatement = connection.prepareStatement(query);
	        prepareStatement.setInt(1,employeeId);
	        Employee employee = new Employee();
	        ResultSet resultSet = prepareStatement.executeQuery();
	        while(resultSet.next()) {
	        	employee.setEmployeeId(resultSet.getInt(1));
	        	employee.setName(resultSet.getString(2));
	        	employee.setAge(resultSet.getInt(3));
	        	employee.setSalary(resultSet.getFloat(4));
	        	employee.setSkills(resultSet.getString(5));
	        	employee.setJoiningDate(resultSet.getString(6));       
	        }
	        
	        LocalDate date = LocalDate.now();
	        String todayDate= date.toString(); 
	        List<String> skillList = Arrays.asList(employee.getSkills().split(","));
	        request.setAttribute("employee", employee);
	        request.setAttribute("skillList", skillList);
	        request.setAttribute("todayDate", todayDate);
	        request.setAttribute("action","update");
	        request.getRequestDispatcher("/EmployeeDataEntry.jsp").forward(request, response);
		 }catch(SQLException e){
	    	e.printStackTrace();
	     }catch (ClassNotFoundException e) {
			e.printStackTrace();
		 }
	}
    
	public static void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int employeeId = Integer.parseInt(request.getParameter("employeeid"));
		try {
	        Connection connenction = DatabaseConnection.initializeDatabase();
	        String query = "delete from employee where employee_id=?";
	        
	        PreparedStatement prepareStatement = connenction.prepareStatement(query);
	        prepareStatement.setInt(1, employeeId);
	        prepareStatement.executeUpdate();

	        request.getRequestDispatcher("/ViewEmployee.jsp").forward(request, response);
	    }catch(SQLException e){
	    	e.printStackTrace();
	    }catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
    public static void createEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String name = request.getParameter("name");
		int age =  Integer.parseInt(request.getParameter("age"));
		String[] skill_list; 
		String skills;
		if(request.getParameterValues("skills")!=null) { 
			skill_list = request.getParameterValues("skills");
			skills = String.join(",", skill_list);
		}else {
			skills = "";
		}
		float salary = Float.parseFloat(request.getParameter("salary"));
		String date = request.getParameter("joining_date");
		 
        try {
	        Connection connenction = DatabaseConnection.initializeDatabase();
	        String query = "insert into employee (name,age,salary,skills,joining_date) values(?,?,?,?,?) ";
	        
	        PreparedStatement preparedStatement = connenction.prepareStatement(query);
	        preparedStatement.setString(1, name);
	        preparedStatement.setInt(2,age);
	        preparedStatement.setFloat(3, salary);
	        preparedStatement.setString(4, skills);
	        preparedStatement.setString(5, date);	
	        preparedStatement.executeUpdate();

	        request.getRequestDispatcher("/ViewEmployee.jsp").forward(request, response);
        }catch(SQLException e){
        	e.printStackTrace();
        }catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
    
	public static void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("employee_id"));
		String name = request.getParameter("name");
		int age =  Integer.parseInt(request.getParameter("age"));
		String[] skill_list; 
		String skills;
		if(request.getParameterValues("skills")!=null) { 
			skill_list = request.getParameterValues("skills");
			skills = String.join(",", skill_list);
		}else {
			skills = "";
		}
		float salary = Float.parseFloat(request.getParameter("salary"));
		String date = request.getParameter("joining_date");
	  
        try {
	        Connection connenction = DatabaseConnection.initializeDatabase();
	        String query = "update employee set name=?,age=?,salary=?,skills=?,joining_date=? where employee_id = ?";
	        
	        PreparedStatement prepareStatement = connenction.prepareStatement(query);
	        prepareStatement.setString(1, name);
	        prepareStatement.setInt(2,age);
	        prepareStatement.setFloat(3, salary);
	        prepareStatement.setString(4, skills);
	        prepareStatement.setString(5, date);
	        prepareStatement.setInt(6,id);
	        prepareStatement.executeUpdate();

	        request.getRequestDispatcher("/ViewEmployee.jsp").forward(request, response);
        }catch(SQLException e){
        	e.printStackTrace();
        }catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	
}
