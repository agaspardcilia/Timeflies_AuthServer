package messages;

import java.io.Serializable;
import java.util.UUID;

public class LoginAnswer implements Serializable {
	private static final long serialVersionUID = -5375108905288101051L;
	
	private boolean loginSuccessFull;
	private UUID token;
	 
	public LoginAnswer(boolean loginSuccessfull, UUID token) {
		this.loginSuccessFull = loginSuccessfull;
		this.token = token;
	}
	 
}
