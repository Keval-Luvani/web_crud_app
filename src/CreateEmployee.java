import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web_crud_app.DatabaseConnection;

@WebServlet("/CreateEmployee")
public class CreateEmployee extends HttpServlet  {
	private static final long serialVersionUID = 1L;
       
	public CreateEmployee() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{		
		
		String name = request.getParameter("name");
		int age =  Integer.parseInt(request.getParameter("age"));
		String[] skill_list = request.getParameterValues("skills");
		String skills = String.join(",", skill_list);
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
	        
	        request.getRequestDispatcher("ViewEmployee.jsp").forward(request, response);;
        }catch(SQLException e){
        	e.printStackTrace();
        }catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
}
