package handler.args.handlers;

import handler.args.GenericArgsHandler;
import utils.ConsoleDisplay;

/**
* @author alexandre
* DebugModeHandler.java
*/
public class DebugModeHandler extends GenericArgsHandler{
	
	public DebugModeHandler(String[] args) {
		super(args, "-d");
	}

	@Override
	protected void handleArgs() {
		ConsoleDisplay.debug = true;
	}


	
	
}
