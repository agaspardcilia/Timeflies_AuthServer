package handlers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import checkers.LoginChecker;
import exceptions.AuthentificationException;
import messages.LoginAnswer;
import messages.LoginRequest;
import utils.ConsoleDisplay;


//XXX Not tested yet
public class AuthHandler implements Runnable {
	private Socket socket;

	public AuthHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		ObjectInputStream in;
		ObjectOutputStream out;
		LoginRequest request = null;
		UUID token;
		
		//init streams
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			ConsoleDisplay.display_errorNotice(socket.getInetAddress().toString() + " : Failed to create streams.");
			return;
		}

		//get request
		try {
			request = (LoginRequest) in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			ConsoleDisplay.display_errorNotice(socket.getInetAddress().toString() + " : Failed to read Object.");
		}

		//login/pwd check
		try {
			token = LoginChecker.connect(request);
			out.writeObject(new LoginAnswer(LoginAnswer.AnswerType.SUCCESS, token));
		} catch (AuthentificationException e) {
			try {
				out.writeObject(new LoginAnswer(LoginAnswer.AnswerType.FAIL, null));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
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

}
