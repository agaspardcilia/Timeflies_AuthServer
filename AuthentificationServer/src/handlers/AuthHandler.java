package handlers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import checkers.LoginChecker;
import exceptions.AuthentificationException;
import messages.LoginAnswer;
import messages.LoginRequest;
import messages.Message;
import utils.ConsoleDisplay;


//XXX Not tested yet
public class AuthHandler implements Runnable {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public AuthHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		Message inMessage;
		
		
		LoginRequest request = null;
		UUID token;
		
		ConsoleDisplay.display_debug(socket.getInetAddress() + " is performing a connection attemp.");
		
		//init streams
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			ConsoleDisplay.display_errorNotice(socket.getInetAddress().toString() + " : Failed to create streams.");
			return;
		}

		try {
			inMessage = (Message) in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//TODO finir la modif qui va permettre de gerer les refresh et les login.
		
		//get request
		try {
			request = (LoginRequest) in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			ConsoleDisplay.display_errorNotice(socket.getInetAddress().toString() + " : Failed to read Object.");
		}

		//login/pwd check
		try {
			//SUCCESS
			token = LoginChecker.checkLogin(request);
			LoginChecker.addUUIDToDB(token, socket.getInetAddress().toString());
			out.writeObject(new LoginAnswer(LoginAnswer.AnswerType.SUCCESS, token));
		} catch (AuthentificationException e) {
			//FAIL
			try {
				out.writeObject(new LoginAnswer(LoginAnswer.AnswerType.FAIL, null));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			//ERROR
			try {
				out.writeObject(new LoginAnswer(LoginAnswer.AnswerType.ERROR, null));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			ConsoleDisplay.display_errorNotice(socket.getInetAddress().toString() + " : Failed to close socket.");
		}
	}
	
	private void handleLogin() {
		
	}
	
	private void handleRefresh() {
		
	}

}
