package storage;

import java.net.InetAddress;
import java.util.Date;

/**
* @author alexandre
* UserInformation.java
*/
public class UserInformation {
	private String username;
	private Date lastRefresh;
	private InetAddress ip;
	
	public UserInformation(String username, Date lastRefresh, InetAddress ip) {
		this.username = username;
		this.lastRefresh = lastRefresh;
		this.ip = ip;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Date getLastRefresh() {
		return lastRefresh;
	}

	public InetAddress getIp() {
		return ip;
	}
	
	public void setRefreshToCurrentTime() {
		lastRefresh = new Date(System.currentTimeMillis());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!obj.getClass().equals(this.getClass()))
			return false;
		UserInformation crt = (UserInformation) obj;
		
		return username.equals(crt.username);
		
	}
	
	@Override
	public String toString() {
		return username + ", last refresh " + lastRefresh + " from " + ip;
	}
	
	
	
}
