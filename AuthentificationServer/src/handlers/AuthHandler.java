package handlers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import checkers.LoginChecker;
import exceptions.AuthentificationException;
import messages.Message;
import messages.error.ErrorCodes;
import messages.error.ErrorMessage;
import messages.login.LoginAnswer;
import messages.login.LoginMessage;
import messages.login.LoginRequest;
import messages.refresh.RefreshMessage;
import messages.refresh.RefreshSessionAnswer;
import messages.refresh.RefreshSessionAnswer.Answer;
import messages.refresh.RefreshSessionRequest;
import messages.server_login.ServerLoginAnswer;
import messages.server_login.ServerLoginAnswer.AnswerType;
import messages.server_login.ServerLoginMessage;
import messages.server_login.ServerLoginRequest;
import storage.TokenBank;
import utils.ConsoleDisplay;


//XXX Not tested yet
public class AuthHandler implements Runnable {
	private final static int RETRY = 5;
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private TokenBank tokenBank;
	
	private boolean isAServer;

	public AuthHandler(Socket socket) throws IOException {
		this.socket = socket;
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		tokenBank = null;
		isAServer = false;
	}

	@Override
	public void run() {
		Message inMessage;

		//TODO finish run 


		try {
			inMessage = (Message) in.readObject();
			switch (inMessage.getMessageType()) { 
			case LOGIN:
				handleLogin((LoginMessage)inMessage);
				break;
			case SERVER_LOGIN :
				handleServerLogin((ServerLoginMessage)inMessage);
				break;
			default:
				ConsoleDisplay.display_errorNotice("Unkhown or missused message.");
				break;
			}

		} catch (ClassNotFoundException e) {
			ConsoleDisplay.display_errorNotice("Error while reading input stream.");
			ConsoleDisplay.printStack(e);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	private void handleLogin(LoginMessage m) {
		LoginRequest request = (LoginRequest)m;
		UUID token;

		//login/pwd check
		try {
			//SUCCESS
			token = LoginChecker.checkLogin(request);
			LoginChecker.addUUIDToDB(token, socket.getInetAddress().toString());
			out.writeObject(new LoginAnswer(LoginAnswer.AnswerType.SUCCESS, token));
		} catch (AuthentificationException e) {
			//FAIL
			
			if (!sendMessage(new LoginAnswer(LoginAnswer.AnswerType.FAIL, null))) {
				close();
				return;
			}
		} catch (IOException e) {
			//ERROR
			if(!sendMessage(new LoginAnswer(LoginAnswer.AnswerType.ERROR, null))) {
				close();
				return;
			}
				
		} finally {
			close();
		}


	}

	private void handleServerLogin(ServerLoginMessage m) {
		ServerLoginRequest request = (ServerLoginRequest)m;
		tokenBank = TokenBank.getCurrentInstance();
		
		if(LoginChecker.checkServerLogin(request)) {
			sendMessage(new ServerLoginAnswer(AnswerType.SUCCES));
			handleRefresh();
		} else {
			sendMessage(new ServerLoginAnswer(AnswerType.BAD_INFOS));
			close();
		}
		//TODO handle server login request w/ modification of database.
	}

	
	/**
	 * Handles refresh, will send an error message (code 10) if the client is  not registered as a server.
	 */
	private void handleRefresh() {
		//Check if the client can do that.
		if(!isAServer) {
			sendMessage(new ErrorMessage(ErrorCodes.NOT_A_SERVER, ErrorCodes.NOT_A_SERVER_txt));
			close();
		
		} else {
			boolean cont = true;
			RefreshMessage message;
			RefreshSessionRequest request;
			RefreshSessionAnswer answer;
			while (cont) {
				message = (RefreshMessage)readMessage();
				
				//Will stop communications.
				if(message.isEndOfCom()) {
					cont = false;
				} else {
					
					request = (RefreshSessionRequest) message;
					//Check token validity and refresh it.
					if(tokenBank.refreshToken(request.getToken())) {
						//The refresh is successful.
						answer = new RefreshSessionAnswer(Answer.SUCCESS);
					} else {
						//Failure.
						answer = new RefreshSessionAnswer(Answer.TIME_OUT);
					}
					
					sendMessage(answer);
				}
			}
			
			close();
		}
	}
	
	private boolean sendMessage(Message m) {
		for (int i = 0; i < RETRY; i++) {
			try {
				out.writeObject(m);
				return true;
			} catch (IOException e) {
				
			}
		}
		
		return false;
	}
	
	private Message readMessage() {
		try {
			return (Message)in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			return null;
		}
	}
	
	private void close() {
		try {
			socket.close();
		} catch (IOException e) {
		}
	}
	

	//	@Override
	//	public void run() {
	//		Message inMessage;
	//		
	//		LoginRequest request = null;
	//		UUID token;
	//		
	//		ConsoleDisplay.display_debug(socket.getInetAddress() + " is performing a connection attemp.");
	//		
	//		//init streams
	//		try {
	//			out = new ObjectOutputStream(socket.getOutputStream());
	//			in = new ObjectInputStream(socket.getInputStream());
	//		} catch (IOException e) {
	//			ConsoleDisplay.display_errorNotice(socket.getInetAddress().toString() + " : Failed to create streams.");
	//			return;
	//		}
	//
	//		try {
	//			inMessage = (Message) in.readObject();
	//		} catch (ClassNotFoundException | IOException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		//TODO finir la modif qui va permettre de gerer les refresh et les login.
	//		
	//		//get request
	//		try {
	//			request = (LoginRequest) in.readObject();
	//		} catch (ClassNotFoundException | IOException e) {
	//			ConsoleDisplay.display_errorNotice(socket.getInetAddress().toString() + " : Failed to read Object.");
	//		}
	//
	//		//login/pwd check
	//		try {
	//			//SUCCESS
	//			token = LoginChecker.checkLogin(request);
	//			LoginChecker.addUUIDToDB(token, socket.getInetAddress().toString());
	//			out.writeObject(new LoginAnswer(LoginAnswer.AnswerType.SUCCESS, token));
	//		} catch (AuthentificationException e) {
	//			//FAIL
	//			try {
	//				out.writeObject(new LoginAnswer(LoginAnswer.AnswerType.FAIL, null));
	//			} catch (IOException e1) {
	//				e1.printStackTrace();
	//			}
	//		} catch (IOException e) {
	//			//ERROR
	//			try {
	//				out.writeObject(new LoginAnswer(LoginAnswer.AnswerType.ERROR, null));
	//			} catch (IOException e1) {
	//				e1.printStackTrace();
	//			}
	//		}
	//		
	//		//Server mod.
	//		
	//		
	//		
	//		try {
	//			socket.close();
	//		} catch (IOException e) {
	//			ConsoleDisplay.display_errorNotice(socket.getInetAddress().toString() + " : Failed to close socket.");
	//		}
	//	}




}
