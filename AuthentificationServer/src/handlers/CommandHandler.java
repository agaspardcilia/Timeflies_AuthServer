package handlers;

import java.util.HashMap;

import handlers.commands.Command;
import handlers.commands.impl.PortCmd;
import handlers.commands.impl.StopCmd;
import handlers.commands.impl.TokenListCmd;
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
		StopCmd stop = new StopCmd(connectionHandler, this);
		PortCmd port = new PortCmd();
		TokenListCmd tok = new TokenListCmd();
		
		commands.put(stop.getCommandTrigger() , stop);
		commands.put(port.getCommandTrigger(), port);
		commands.put(tok.getCommandTrigger(), tok);
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
