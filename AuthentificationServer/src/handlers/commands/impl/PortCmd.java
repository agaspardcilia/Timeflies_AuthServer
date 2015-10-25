package handlers.commands.impl;

import handlers.commands.Command;
import settings.SettingsManager;
import utils.ConsoleDisplay;

/**
* @author alexandre
* PortCmd.java
*/
public class PortCmd extends Command {
	private final static String CMD_TRIGGER = "port";

	
	
	@Override
	public void handle(String[] args) {
		ConsoleDisplay.display_notice("Current port : " + SettingsManager.getNetworkSettings().getPort());
	}



	@Override
	public String getCommandTrigger() {
		return CMD_TRIGGER;
	}

}
