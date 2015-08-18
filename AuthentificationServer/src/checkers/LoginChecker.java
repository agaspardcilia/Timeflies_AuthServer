package checkers;

import java.util.UUID;

import exceptions.AuthentificationException;
import messages.LoginRequest;

public class LoginChecker {
	
	private static boolean isLoginCorrect(String login, String pwd) {
		return true;
	}
	
	public static UUID connect(LoginRequest request) throws AuthentificationException {
		if(isLoginCorrect(request.getLogin(), request.getPassWord())) {
			return UUID.randomUUID();
		} else {
			throw new AuthentificationException();
		}
	}
}
