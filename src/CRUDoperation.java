import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web_crud_app.DatabaseConnection;

public class CRUDoperation {
	public static List<String[]> skillmap = new ArrayList<String[]>();
	
	public static void createEmployeePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setAttribute("todayDate", LocalDate.now().toString());
		request.getRequestDispatcher("/EmployeeDataEntry.jsp").forward(request, response);
	}
	
	private static ResultSet getEmployees() throws ClassNotFoundException, SQLException {
		 Connection connection = DatabaseConnection.initializeDatabase();
	     String query = "select * from employee";
	     PreparedStatement preparedStatement = connection.prepareStatement(query);
	     ResultSet resultSet = preparedStatement.executeQuery();
	     return resultSet;
	}
	
	public static void viewEmployee(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, ServletException, IOException {
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			ResultSet resultset =  getEmployees();
			while(resultset.next()) {
				Employee employee = new Employee();
				employee.setEmployeeId(resultset.getInt(1));
				employee.setName(resultset.getString(2));
				employee.setAge(resultset.getInt(3));
				employee.setSalary(resultset.getFloat(4));
				employee.setJoiningDate(resultset.getString(5));
				employee.setSkillList(getSkills(resultset.getInt(1)));
				employeeList.add(employee);
			}
			request.setAttribute("employeeList", employeeList);
			request.getRequestDispatcher("/ViewEmployee.jsp").forward(request, response);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	public static void fetchData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int employeeId = Integer.parseInt(request.getParameter("employeeid"));
   	 
		try {
	        Connection connection = DatabaseConnection.initializeDatabase();
	        String query = "select * from employee where employee_id=?";
	        
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1,employeeId);
	        Employee employee = new Employee();
	        Skills skills = new Skills();
	        ResultSet resultSet = preparedStatement.executeQuery();
	        while(resultSet.next()) {
	        	employee.setEmployeeId(resultSet.getInt(1));
	        	employee.setName(resultSet.getString(2));
	        	employee.setAge(resultSet.getInt(3));
	        	employee.setSalary(resultSet.getFloat(4));
	        	employee.setJoiningDate(resultSet.getString(5));       
	        }
	        skills.setId(employeeId);
	        skills.setSkillList(getSkills(employeeId));
	        request.setAttribute("skillList", skills);
	        request.setAttribute("employee", employee);
	        request.setAttribute("todayDate", LocalDate.now().toString());
	        
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
	        
	        response.sendRedirect("/web_crud_app/");
	    }catch(SQLException e){
	    	e.printStackTrace();
	    }catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
    public static void createEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String name = request.getParameter("name");
		int age =  Integer.parseInt(request.getParameter("age"));
		String[] skill_list = request.getParameterValues("skills");
		float salary = Float.parseFloat(request.getParameter("salary"));
		String date = request.getParameter("joining_date");
		 
        try {
	        Connection connenction = DatabaseConnection.initializeDatabase();
	        String query = "insert into employee (name,age,salary,joining_date) values(?,?,?,?)";
	        
	        PreparedStatement preparedStatement = connenction.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
	        preparedStatement.setString(1, name);
	        preparedStatement.setInt(2,age);
	        preparedStatement.setFloat(3, salary);
	        preparedStatement.setString(4, date);
	        preparedStatement.execute();
	        ResultSet resultsetid = preparedStatement.getGeneratedKeys();
	        
	        int id=0;
	        while(resultsetid.next()) {
	        	id = resultsetid.getInt(1);
	        }
	        
			if(request.getParameterValues("skills")!=null) { 
				for(String skill:skill_list) {
					addSkills(id,skill);
			    }
			}
			response.sendRedirect("/web_crud_app/");
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
		String[] skill_list = request.getParameterValues("skills");
		float salary = Float.parseFloat(request.getParameter("salary"));
		String date = request.getParameter("joining_date");
	  
        try {
	        Connection connenction = DatabaseConnection.initializeDatabase();
	        String query = "update employee set name=?,age=?,salary=?,joining_date=? where employee_id = ?";
	        
	        PreparedStatement prepareStatement = connenction.prepareStatement(query);
	        prepareStatement.setString(1, name);
	        prepareStatement.setInt(2,age);
	        prepareStatement.setFloat(3, salary);
	        prepareStatement.setString(4, date);
	        prepareStatement.setInt(5,id);
	        prepareStatement.executeUpdate();
	        updateSkills(id,skill_list);
	        response.sendRedirect("/web_crud_app/");
        }catch(SQLException e){
        	e.printStackTrace();
        }catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteSkills(int employeeid, String skill) throws ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnection.initializeDatabase();
        String query = "delete from skills where employee_id=? AND skill=?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,employeeid);
        preparedStatement.setString(2,skill);
        try{
	    	  preparedStatement.executeUpdate();
	    }catch(Exception ex){
	    	  ex.printStackTrace();
	    }
	}
	
	public static void addSkills(int employeeid, String skill) throws ClassNotFoundException, SQLException {
		Connection connection = DatabaseConnection.initializeDatabase();
        String query = "insert into skills (employee_id,skill) values(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,employeeid);
        preparedStatement.setString(2,skill);
        try{
	    	  preparedStatement.executeUpdate();
	    }catch(Exception ex){
	    	  ex.printStackTrace();
	    }
	}
	
	public static void updateSkills(int id,String [] skill_list) throws ClassNotFoundException, SQLException {
		List<String> updatedSkillList = Arrays.asList(skill_list);
		List<String> databaseSkillList = getSkills(id);
		for(String skill : updatedSkillList) {
			if(!databaseSkillList.contains(skill)){
				addSkills(id,skill);
			}
		}
		for(String skill : databaseSkillList) {
			if(!updatedSkillList.contains(skill)){
				deleteSkills(id,skill);
			}
		}
	}
	
	public static List<String> getSkills(int id) throws SQLException, ClassNotFoundException {
		Connection connection = DatabaseConnection.initializeDatabase();
        String query = "select skill from skills where employee_id=?";
        List<String> skillList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1,id);
        try{
	    	  ResultSet resultSet = preparedStatement.executeQuery();
	    	  while(resultSet.next()){
	    		  skillList.add(resultSet.getString(1));
	    	  }
	    }catch(Exception ex){
	    	  ex.printStackTrace();
	    }
   		return skillList;
	}
}
