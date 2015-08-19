import handlers.ConnectionsHandler;
import settings.SettingsManager;
import utils.ConsoleDisplay;
import utils.DBMapper;
import utils.LibChecker;

public class Launcher {
	
	
	
	public static void main(String[] args) {
		
		try {
			if (args[0].equals("-d"))
				ConsoleDisplay.debug = true;			
		} catch (ArrayIndexOutOfBoundsException e) {}
		
		ConsoleDisplay.display_splash();
		
		
		ConnectionsHandler connectionHandler = null;
		try {
			LibChecker.check();
			SettingsManager.initSettings();
			connectionHandler = new ConnectionsHandler();
			DBMapper.init();
		} catch (Exception e) {
			ConsoleDisplay.display_errorNotice("Failed to initialize. Stopping the program.");
			if (ConsoleDisplay.debug)
				e.printStackTrace();
			return;
		}
		
		Thread connectionHandlerThread = new Thread(connectionHandler);
		
		
	}
	
	
}
