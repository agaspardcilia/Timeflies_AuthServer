package checkers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import exceptions.AuthentificationException;
import messages.LoginRequest;
import utils.DBMapper;

public class LoginChecker {
	
	
	private static boolean isLoginCorrect(String login, String pwd) {
		ResultSet res;
		try {
			res = DBMapper.executeQuery("SELECT * FROM utilisateur WHERE pseudo = " + login + " ;");
			System.out.println("fetch size : " + res.getFetchSize());
			return res.getFetchSize() != 1;
		} catch (SQLException e) {}
		
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
