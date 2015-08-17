import handlers.ConnectionsHandler;
import settings.SettingsManager;
import utils.ConsoleDisplay;

public class Launcher {

	public static void main(String[] args) {
		ConsoleDisplay.display_splash();
		
		ConnectionsHandler connectionHandler = null;
		
		
		try {
			SettingsManager.initSettings();
			connectionHandler = new ConnectionsHandler();
			
		} catch (Exception e) {
			ConsoleDisplay.display_errorNotice("Failed to initialize. Stopping the program.");
			e.printStackTrace();
			return;
		}
		
		Thread connectionHandlerThread = new Thread(connectionHandler);
		
		
	}
	
	
}
