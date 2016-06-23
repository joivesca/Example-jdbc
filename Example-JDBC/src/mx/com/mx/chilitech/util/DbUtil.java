package mx.com.mx.chilitech.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbUtil {
	private static Connection dbConnection = null;
	
	public static Connection getConnection() {
		
		if (dbConnection != null){
			return dbConnection;
		} else {
			try {
				InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("db.properties");
				Properties properties = new Properties();
				
				if (properties != null) {
					properties.load(inputStream);
					
					Class.forName(properties.getProperty("dbDriver")).newInstance();
					dbConnection = DriverManager.getConnection(properties.getProperty("connectionUrl"), 
							properties.getProperty("userName"), properties.getProperty("password"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dbConnection;
	}
}
