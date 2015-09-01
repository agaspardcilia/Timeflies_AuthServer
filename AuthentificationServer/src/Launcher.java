import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
				password = sha1(new String(System.console().readPassword()));
			} else {
				password = sha1(sc.nextLine());
			}

			LoginRequest request = new LoginRequest(username, password);

			UUID token;
			try {
				token = LoginChecker.checkLogin(request);
				System.out.println("token : " + token);
			} catch (AuthentificationException e) {
				System.out.println("Authentification failed.");
			}

			sc.close();

		} else {
			Thread connectionHandlerThread = new Thread(connectionHandler);

		}



	}


	public static String sha1(String message){
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] hash = md.digest(message.getBytes("UTF-8"));

			//converting byte array to Hexadecimal String
			StringBuilder sb = new StringBuilder(2*hash.length);
			for(byte b : hash){
				sb.append(String.format("%02x", b&0xff));
			}

			digest = sb.toString();

		} catch (UnsupportedEncodingException ex) {
		} catch (NoSuchAlgorithmException ex) {
		}
		return digest;
	}




}
