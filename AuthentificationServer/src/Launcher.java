import java.util.Scanner;

import handler.args.ArgsHandler;
import handler.args.handlers.TestModeHandler;
import handlers.ConnectionsHandler;
import settings.SettingsManager;
import utils.ConsoleDisplay;
import utils.DBMapper;
import utils.LibChecker;

public class Launcher {
	
	
	public static void main(String[] args) {
		
		
		ConsoleDisplay.display_splash();
		
		ConnectionsHandler connectionHandler = null;
		try {
			ArgsHandler.init(args);
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
		
		
		if (TestModeHandler.isTestMode()) {
			ConsoleDisplay.display_notice("Test mode enable.");
			
			String username;
			String password;
			char[] tmp;
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Username : ");
			username = sc.nextLine();
			
			System.out.print("Password : ");
//			tmp = System.console().readPassword();
			
		} else {
			Thread connectionHandlerThread = new Thread(connectionHandler);
			
		}
		
		
		
	}
	
	
}
