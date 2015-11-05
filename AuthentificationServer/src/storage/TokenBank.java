package storage;

import java.net.InetAddress;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import utils.ConsoleDisplay;

/**
* @author alexandre
* TokenBank.java
*/
public class TokenBank {
	private final static long TOKEN_TIMEOUT = 1000; // TODO define a real value for that timeout.
	
	private static TokenBank currentInstance;
	
	private ConcurrentHashMap<UUID, UserInformation> tokens;
	
	
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
		
		tokens = new ConcurrentHashMap<UUID, UserInformation>();
		
	}
	
	//TODO check if it's still the same ip on the first case.
	public UUID addToken(String username, InetAddress ip) {
		UserInformation crt = new UserInformation(username, new Date(System.currentTimeMillis()), ip);
		UUID token;
		if (tokens.containsValue(crt)) {
			for (UUID k : tokens.keySet()) 
				if (tokens.get(k).equals(crt)) {
					//Bad InetAddress.
					if (!tokens.get(k).getIp().equals(ip)) {
						tokens.remove(k);
						token  = UUID.randomUUID();
						tokens.put(token, crt);
						return token;
					} else
						return k;
				}
			
			return null;
		} else {
			token = UUID.randomUUID();
			tokens.put(token, crt);
			return token;
		}
	}
	
	public void removeToken(UUID token) {
		tokens.remove(token);
	}
	
	public boolean refreshToken(UUID token) {
		if (!isTokenValid(token))
			return false;
		else {
			tokens.get(token).setRefreshToCurrentTime();
			return true;
		}
	}
	
	//TODO define new token validity condition.
	public boolean isTokenValid(UUID token) {
		try {
			return tokens.get(token).getLastRefresh().getTime() + TOKEN_TIMEOUT > System.currentTimeMillis();			
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public static TokenBank getCurrentInstance() {
		return currentInstance;
	}
	
	
	public ConcurrentHashMap<UUID, UserInformation> getTokens() {
		return tokens;
	}
}
