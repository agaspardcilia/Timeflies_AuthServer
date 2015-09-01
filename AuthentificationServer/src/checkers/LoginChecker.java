package checkers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import exceptions.AuthentificationException;
import messages.LoginRequest;
import utils.ConsoleDisplay;
import utils.DBMapper;

public class LoginChecker {
	private final static String QUERY = "SELECT * FROM utilisateur WHERE pseudo = ? AND mdp = ?;";
	
	private static boolean isLoginCorrect(String login, String pwd) {
		ResultSet res;
		try {
			res = DBMapper.executeQuery(QUERY, login, pwd);
			return res.next();
		} catch (SQLException e) {
			if (ConsoleDisplay.debug)
				e.printStackTrace();
		}
		
		return false;
	}
	
	public static UUID checkLogin(LoginRequest request) throws AuthentificationException {
		if(isLoginCorrect(request.getLogin(), request.getPassWord())) {
			return UUID.randomUUID();
		} else {
			throw new AuthentificationException();
		}
	}
}
