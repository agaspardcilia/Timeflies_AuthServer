package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import settings.DataBaseSettings;
import settings.SettingsManager;

/**
 * @author alexandre
 * DBMapper.java
 * Database connection.
 */
public class DBMapper {
	private static Connection database;
	
	private final static String DATE_PATTERN = "HH:mm:ss dd/MM/YY";
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);
	
	
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
	
	public static ResultSet executeQuery(String query, Object... args) throws SQLException {
		if (database == null) {
			ConsoleDisplay.display_errorNotice("ERROR, Can't execute any query : no connection to database.");
			return null;
		}
		
		try {
			PreparedStatement stat = database.prepareStatement(query);
			
			for (int i = 0; i < args.length; i++) 
				stat.setObject(i+1, args[i]);
			
			return stat.executeQuery();
			
		} catch (SQLException e) {
			ConsoleDisplay.display_errorNotice("ERROR : Statement creation failed.");
			throw e;
		}
		
	}
	
	/**
	 * Return current time. This method is in DBMapper to be sure it's use with database interactions. 
	 * The used pattern is "HH:mm:ss dd/MM/YY".
	 */
	public static String getTime() {
		
		return DATE_FORMAT.format(new Date(System.currentTimeMillis()));
	}
	
	/**
	 * Parse a string to a date. Caution : this method is made to parse date from data base who follow the "HH:mm:ss dd/MM/YY" pattern.
	 * @param s
	 * 	String to parse.
	 * @return
	 * 	Date from parsed string.
	 */
	public static Date parseDate(String s) {
		
		try {
			return DATE_FORMAT.parse(s);
		} catch (ParseException e) {
			ConsoleDisplay.display_errorNotice("ERROR : Failed to parse time.");
			ConsoleDisplay.printStack(e);
			return null;
		}
	}
	
}
