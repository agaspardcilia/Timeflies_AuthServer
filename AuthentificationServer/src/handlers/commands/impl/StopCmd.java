package handlers.commands.impl;

import handlers.CommandHandler;
import handlers.ConnectionsHandler;
import handlers.commands.Command;
import utils.ConsoleDisplay;
import utils.ThreadManager;

/**
* @author alexandre
* StopCmd.java
*/
public class StopCmd extends Command {
	private final static String CMD_TRIGGER = "stop";
	
	private ConnectionsHandler cHandler;
	private CommandHandler cmdHandler;
	
	public StopCmd(ConnectionsHandler handler, CommandHandler commandHandler) {
		this.cHandler = handler;
		this.cmdHandler = commandHandler;
	}
	
	@Override
	public void handle(String[] args) {
		ConsoleDisplay.display_notice("Stopping...");
		
		cHandler.stopListen();
		cmdHandler.requestStop();
		ThreadManager.getCurrentInstance().killThreads();
	}

	public String getCommandTrigger() {
		return CMD_TRIGGER;
	}
	


}
