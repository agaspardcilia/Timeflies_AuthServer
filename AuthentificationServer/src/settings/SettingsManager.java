package settings;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import utils.ConsoleDisplay;

public class SettingsManager {
	private final static boolean LOAD_DEAFAULT_SETTINGS = true;

	private final static String DBSETTINGS_FNAME = "DBLogs.xml";
	
	private static NetworkSettings networkSettings;
	private static DataBaseSettings dbSettings;

	private static String path;
	
	public static void initSettings() throws Exception {
		ConsoleDisplay.display_startNotice("settings manager");
		try {
			definePath();
			
			loadDBSettings();
			
			if (LOAD_DEAFAULT_SETTINGS) {
				networkSettings = new NetworkSettings();
			} else {
				
				
			}
			
			
		} catch (Exception e) {
			ConsoleDisplay.display_startNoticeFail();
			throw e;
		}

		ConsoleDisplay.display_startNoticeSuccess();
	}

	public static NetworkSettings getNetworkSettings() {
		return networkSettings;
	}

	public static DataBaseSettings getDataBaseSettings() {
		return dbSettings;
	}
	
	public static String getPath() {
		return path;
	}
	
	private static void definePath() {
		path = System.getProperty("java.class.path").split(":")[0];
		int i;
		for (i = path.length()-1; i >= 0; i--) {
			if(path.charAt(i) == '/')
				break;
		}
		
		path = path.substring(0, i+1);
	}
	
	private static void loadDBSettings() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		File f = new File(path + DBSETTINGS_FNAME);
		
		
		Document dbSettings = builder.parse(f);
		Element root = dbSettings.getDocumentElement();
		
			SettingsManager.dbSettings = new DataBaseSettings(root);

	}
	
}
