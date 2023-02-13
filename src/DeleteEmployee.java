

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web_crud_app.DatabaseConnection;


@WebServlet("/DeleteEmployee")
public class DeleteEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteEmployee() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int employeeId = Integer.parseInt(request.getParameter("employeeid"));
		
		try {
	        Connection connenction = DatabaseConnection.initializeDatabase();
	        String query = "delete from employee where employee_id=?";
	        
	        PreparedStatement prepareStatement = connenction.prepareStatement(query);
	        prepareStatement.setInt(1, employeeId);
	        prepareStatement.executeUpdate();
	        
	        request.getRequestDispatcher("ViewEmployee.jsp").forward(request, response);    
	    }catch(SQLException e){
	    	e.printStackTrace();
	    }catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
