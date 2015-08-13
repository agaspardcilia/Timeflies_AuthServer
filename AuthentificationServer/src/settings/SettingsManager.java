package settings;

public class SettingsManager {
	private final static boolean LOAD_DEAFAULT_SETTINGS = true;
	
	private static NetworkSettings networkSettings;
	
	public void initSettings() {
		if (LOAD_DEAFAULT_SETTINGS) {
			networkSettings = new NetworkSettings();
		} else {
			
		}
	}
	
}
