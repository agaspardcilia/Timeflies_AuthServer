import java.io.Console;
import java.util.Scanner;
import java.util.UUID;

import checkers.LoginChecker;
import exceptions.AuthentificationException;
import handler.args.ArgsHandler;
import handler.args.handlers.TestModeHandler;
import handlers.ConnectionsHandler;
import messages.LoginRequest;
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
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Username : ");
			username = sc.nextLine();
			
			System.out.print("Password : ");
			//Only works with a shell terminal.
			if (System.console() != null) {
				password = new String(System.console().readPassword());
			} else {
				password = sc.nextLine();
			}
			
			LoginRequest request = new LoginRequest(username, password);
			try {
				UUID token = LoginChecker.checkLogin(request);
				System.out.println("token : " + token);
			} catch (AuthentificationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} else {
			Thread connectionHandlerThread = new Thread(connectionHandler);
			
		}
		
		
		
	}
	
	
}
