package handlers;

import java.util.HashMap;

import handlers.commands.Command;
import handlers.commands.impl.StopCmd;
import utils.ConsoleDisplay;
import utils.ConsoleInput;

/**
* @author alexandre
* CommandHandler.java
*/
public class CommandHandler implements Runnable{
	private ConsoleInput cInput;
	
	private ConnectionsHandler connectionHandler;
	private HashMap<String, Command> commands;
	private boolean cont;
	
	
	public CommandHandler(ConnectionsHandler ch) {
		cInput = ConsoleInput.getCurrentInst();
		this.connectionHandler = ch;
		commands = new HashMap<String, Command>();
		initCommand();
		cont = true;
	}
	
	private void initCommand() {
		commands.put(StopCmd.getCmdTrigger().toLowerCase() , new StopCmd(connectionHandler, this));
	}

	private void handle() {
		try {
			String[] input = cInput.nextLine().split(" ");
			String cmd = input[0];
			commands.get(cmd.toLowerCase()).handle(input);
			
		} catch (NullPointerException e) {
			ConsoleDisplay.display_notice("Wrong input.");
		}
		
	}
	
	public void requestStop() {
		cont = false;
	}
	
	@Override
	public void run() {
		while (cont) {
			handle();
		}
	}
	
	
	
	
	
}
