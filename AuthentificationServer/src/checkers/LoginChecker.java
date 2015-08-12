package checkers;

import java.util.UUID;

import javax.naming.AuthenticationException;

import messages.LoginRequest;
import utils.DBMapper;

public class LoginChecker {
	private DBMapper database;
	
	public LoginChecker() {
		database = new DBMapper();
		database.init();
	}
	
	private boolean isLoginCorrect(String login, String pwd) {
		return true;
	}
	
	public UUID connect(LoginRequest request) throws AuthenticationException {
		if(isLoginCorrect(request.getLogin(), request.getPassWord())) {
			return UUID.randomUUID();
		} else {
			throw new AuthenticationException();
		}
	}
}
