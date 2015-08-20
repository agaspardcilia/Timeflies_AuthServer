package settings;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author alexandre
 * DatabaseSettings.java
 * This class contains login informations of the database.
 * The xml documents must be placed at the root of the program directory and be named "DBLogs.xml".
 * This documents must contains the following lines filled with correct informations :
 * 
 * <root>
 * 		<logs>
 * 			<username>[Your username here]</username>
 * 			<pwd>[Your password here]</pwd>
 * 		</logs>
 * 		<address>
 * 			<url>[Server's address]</url>
 * 			<port>[Server's port]</port>
 * 			<dbname>[Database name]</dbname>
 * 		</address>
 * </root>
 * 
 * 
 */
public class DataBaseSettings {
	private final static String LOGS = "logs";
	private final static String ADDRESS = "address";
	private final static String USERNAME = "username";
	private final static String PWD = "pwd";
	private final static String URL = "url";
	private final static String PORT = "port";
	private final static String DBNAME = "dbname";


	private String username;
	private String password;
	private String address;
	private int port;
	private String dbName;


	public DataBaseSettings(Element xmlRoot) {
		NodeList logs = xmlRoot.getElementsByTagName(LOGS).item(0).getChildNodes();
		NodeList address = xmlRoot.getElementsByTagName(ADDRESS).item(0).getChildNodes();

		for (int i = 0; i < logs.getLength(); i++) {
			if (logs.item(i).getNodeName().equals(USERNAME))
				username = logs.item(i).getTextContent();
			else if (logs.item(i).getNodeName().equals(PWD))
				password = logs.item(i).getTextContent();
		}
		
		for (int i = 0; i < address.getLength(); i++) {
			if (address.item(i).getNodeName().equals(URL))
				this.address = address.item(i).getTextContent();
			else if (address.item(i).getNodeName().equals(PORT))
				port = Integer.parseInt(address.item(i).getTextContent());
			else if (address.item(i).getNodeName().equals(DBNAME))
				dbName = address.item(i).getTextContent();
		}		
	}

	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}


	public String getAddress() {
		return address;
	}


	public int getPort() {
		return port;
	}

	public String getDbName() {
		return dbName;
	}


}
