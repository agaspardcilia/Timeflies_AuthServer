package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import settings.DataBaseSettings;
import settings.SettingsManager;

/**
 * @author alexandre
 * DBMapper.java
 * Database connection.
 */
public class DBMapper {
	private static Connection database;
	
	
	public static  void init() throws Exception {
		
		ConsoleDisplay.display_startNotice("database connection");
		
		DataBaseSettings dbSettings = SettingsManager.getDataBaseSettings();
		
		try {
			database = DriverManager.getConnection("jdbc:postgresql://" + dbSettings.getAddress() + ":" + dbSettings.getPort() 
			+ "/" + dbSettings.getDbName(),	dbSettings.getUsername(), dbSettings.getPassword());
		} catch (Exception e) {
			ConsoleDisplay.display_startNoticeFail();
			throw e;
		}
		
		ConsoleDisplay.display_startNoticeSuccess();
	}
	
	public static Connection getDatabase(){
		return database;
	}
	
	public static ResultSet executeQuery(String query, String... param) throws SQLException {
		if (database == null) {
			ConsoleDisplay.display_errorNotice("ERROR : Can't execute any query : no connection to database.");
			return null;
		}
		
		try {
			PreparedStatement stat = database.prepareStatement(query);
			for (int i = 0; i < param.length; i++) 
				stat.setString(i, param[i]);
			
			return stat.executeQuery();
			
		} catch (SQLException e) {
			ConsoleDisplay.display_errorNotice("ERROR : Statement creation failed.");
			throw e;
		}
		
	}
}
