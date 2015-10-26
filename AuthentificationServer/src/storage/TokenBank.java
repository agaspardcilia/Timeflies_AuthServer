package storage;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import utils.ConsoleDisplay;

/**
* @author alexandre
* TokenBank.java
*/
public class TokenBank {
	private final static long TOKEN_TIMEOUT = 1000; // TODO define a real value for that timeout.
	
	private static TokenBank currentInstance;
	
	private HashMap<UUID, Date> tokens;
	
	
	public static void init() {
		ConsoleDisplay.display_startNotice("Token database");
		try {
			currentInstance = new TokenBank();
			ConsoleDisplay.display_startNoticeSuccess();
		} catch (Exception e) {
			ConsoleDisplay.display_startNoticeFail();
			ConsoleDisplay.printStack(e);
		}
		
	}
	
	public TokenBank() {
		
		tokens = new HashMap<UUID, Date>();
		
	}
	
	public boolean addToken(UUID token) {
		if (tokens.keySet().contains(token))
			return false;
		else {
			tokens.put(token, new Date(System.currentTimeMillis()));
			return true;
		}
	}
	
	public void removeToken(UUID token) {
		tokens.remove(token);
	}
	
	public boolean refreshToken(UUID token) {
		if (!isTokenValid(token))
			return false;
		else {
			tokens.put(token, new Date(System.currentTimeMillis()));
			return true;
		}
	}
	
	//TODO change the date operation to something recent.
	public boolean isTokenValid(UUID token) {
		try {
			return tokens.get(token).getTime() + TOKEN_TIMEOUT > System.currentTimeMillis();			
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public static TokenBank getCurrentInstance() {
		return currentInstance;
	}
	
	
	public HashMap<UUID, Date> getTokens() {
		return tokens;
	}
}
