package utils;


import java.sql.Connection;
import java.sql.DriverManager;

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
			database = DriverManager.getConnection("jdbc:postgresql://" + dbSettings.getAddress() + ":" + dbSettings.getPort(),
					dbSettings.getUsername(), dbSettings.getPassword());
		} catch (Exception e) {
			ConsoleDisplay.display_startNoticeFail();
			throw e;
		}
		
		ConsoleDisplay.display_startNoticeSuccess();
	}
	
	public static Connection getDatabase(){
		return database;
	}
}
