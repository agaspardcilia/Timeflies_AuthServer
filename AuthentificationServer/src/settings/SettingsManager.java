package settings;

import utils.ConsoleDisplay;

public class SettingsManager {
	private final static boolean LOAD_DEAFAULT_SETTINGS = true;

	private static NetworkSettings networkSettings;


	public static void initSettings() {
		ConsoleDisplay.display_startNotice("settings manager");

		try {
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

}
