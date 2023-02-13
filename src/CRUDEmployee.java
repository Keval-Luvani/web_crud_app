import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web_crud_app.DatabaseConnection;

@WebServlet(urlPatterns = {"/employee/*"})
public class CRUDEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public CRUDEmployee() {
        super();
    }
    
    public static String getAction(HttpServletRequest request) {
    	String uri = request.getRequestURI();
	    String [] uri_list = uri.split("/");
	    String action = uri_list[uri_list.length-1];
	    return action;
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getAction(request);
		
		if(action.equals("update")){
			CRUDoperation.fetchData(request,response);
		}else if(action.equals("delete")){
			CRUDoperation.deleteEmployee(request,response);
		}else if(action.equals("create")){
			CRUDoperation.createEmployeePage(request,response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getAction(request);
		
		if(action.equals("create")) {
			CRUDoperation.createEmployee(request,response);
		}else if(action.equals("update")){
			CRUDoperation.updateEmployee(request,response);
		}
	}
}
