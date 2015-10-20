package handlers;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

import settings.SettingsManager;
import utils.ConsoleDisplay;

public class ConnectionsHandler implements Runnable {
	private ServerSocket serverSocket;
	private LinkedList<AuthHandler> handlers;
	private boolean endRequest;
	
	public ConnectionsHandler() throws Exception {
		ConsoleDisplay.display_startNotice("connection handler");
		try {
			int port = SettingsManager.getNetworkSettings().getPort();
			serverSocket = new ServerSocket(port);
			
			handlers = new LinkedList<AuthHandler>();
			endRequest = false;
		} catch(Exception e) {
			ConsoleDisplay.display_startNoticeFail();
			throw e;
		}
		ConsoleDisplay.display_startNoticeSuccess();
	}
	

	@Override
	public void run() {
		Thread thread;
		AuthHandler crt;
			while (!endRequest) { 
				try {
					crt = new AuthHandler(serverSocket.accept());
					handlers.add(crt);
					
					thread = new Thread(crt);
					thread.start();
				} catch (IOException e) {
					ConsoleDisplay.printStack(e);
				}
			}
	}
	
	
	public void stopListen() {
		if (handlers.size() != 0)
			stopHandlers();
		
		endRequest = true;
	}
	
	private void stopHandlers() {
		AuthHandler crt;
		
		while((crt = handlers.pop()) != null) {
			crt.requestEnd();
		}
	}
}
