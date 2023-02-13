package web_crud_app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
	    public  static Connection initializeDatabase() throws SQLException, ClassNotFoundException
	    {
	        String databaseDriver = "com.mysql.jdbc.Driver";
	        String databaseURL = "jdbc:mysql:// localhost:3306/";
	        String databaseName = "employee";
	        String databaseUsername = "root";
	        String databasePassword = "root";
	  
	        Class.forName(databaseDriver);
	        Connection connection = DriverManager.getConnection(databaseURL + databaseName, databaseUsername, databasePassword);
	        return connection;
	    }
}
