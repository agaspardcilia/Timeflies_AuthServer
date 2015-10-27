package handlers.commands.impl;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import handlers.commands.Command;
import storage.TokenBank;
import utils.ConsoleDisplay;
import utils.ConsoleInput;

/**
* @author alexandre
* TokenListCmd.java
*/
public class TokenListCmd extends Command {
	private final static String CMD_TRIGGER = "tokenlist";
	
	
	@Override
	public void handle(String[] args) {
		ConcurrentHashMap<UUID, Date> tokens = TokenBank.getCurrentInstance().getTokens();
		
		ConsoleDisplay.display_notice(tokens.size() + " tokens in bank.");
		ConsoleDisplay.display_notice("Display all ? (y/n)");
		
		if (ConsoleInput.getCurrentInst().nextLine().toLowerCase().charAt(0) == 'y') 
			for (UUID token : tokens.keySet()) 
				ConsoleDisplay.display_notice("->" + token + ", " + tokens.get(token));
			
		ConsoleDisplay.display_notice("");
		
	}

	@Override
	public String getCommandTrigger() {
		return CMD_TRIGGER;
	}
	
}
