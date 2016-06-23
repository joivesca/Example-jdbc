package mx.com.mx.chilitech.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import mx.com.mx.chilitech.util.DbUtil;

public class StudentRepository {

	private Connection dbConnection;
	
	public StudentRepository(){
		dbConnection = DbUtil.getConnection();
	}
	
	public void create(String userName, String password, String firstName, String lastName, String dateOfBirth, String email) {
		try {
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO STUDENT( userName, password, firstName, ");
			query.append("					   lastName, dateOfBirth, email ) VALUES ");
			query.append("			VALUES( ?, ?, ?, ");
			query.append("					?, ?, ? ) ");
			
			PreparedStatement preparedStatement = dbConnection.prepareStatement(query.toString());
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, firstName);
			preparedStatement.setString(4, lastName);
			preparedStatement.setDate(5, new java.sql.Date(new SimpleDateFormat("MM/dd/yyyy").
					parse(dateOfBirth.substring(0,10)).getTime()));
			preparedStatement.setString(6, email);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public boolean findByUserName(String userName) {
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT COUNT(*) FROM STUDENT ");
			query.append("WHERE userName = ? ");
			PreparedStatement prepStatement = dbConnection.prepareStatement(query.toString());
	        prepStatement.setString(1, userName);   
          	
	        ResultSet result = prepStatement.executeQuery();
	        if (result != null) {   
	        	while (result.next()) {
	        		if (result.getInt(1) == 1) {
	        			return true;
	        		}               
	        	}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	  
	public boolean findByLogin(String userName, String password) {
		try {
			StringBuilder query = new StringBuilder();
			query.append("SELECT password FROM STUDENT ");
			query.append("WHERE userName = ? ");
			PreparedStatement prepStatement = dbConnection.prepareStatement(query.toString());
			prepStatement.setString(1, userName);           
          
			ResultSet result = prepStatement.executeQuery();
			if (result != null) {
				while (result.next()) {
					if (result.getString(1).equals(password)) {
						return true;
					}
				}               
			}           
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
