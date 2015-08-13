package settings;

public class NetworkSettings {
	private int port;
	private final static int DEFAULT_PORT = 42666;
	
	public NetworkSettings() {
		this.port = DEFAULT_PORT;
	}
	
	public NetworkSettings(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}
}
