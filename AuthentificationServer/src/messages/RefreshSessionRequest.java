package messages;

import java.io.Serializable;
import java.util.UUID;

/**
* @author alexandre
* RefreshSessionRequest.java
*/
public class RefreshSessionRequest implements Message, Serializable {
	private static final long serialVersionUID = -3290257706078934919L;

	private UUID token;
	
	public RefreshSessionRequest(UUID token) {
		this.token = token;
	}
	
	public UUID getToken() {
		return token;
	}
	
}
