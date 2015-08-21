package handler.args;


import handler.args.handlers.DebugModeHandler;
import handler.args.handlers.TestModeHandler;
import utils.ConsoleDisplay;

/**
* @author alexandre
* ArgsHandler.java
*/
public class ArgsHandler {
	
	public static void init(String[] args) {
		ConsoleDisplay.display_startNotice("arguments handler");
		
		new DebugModeHandler(args);
		new TestModeHandler(args);
		
		ConsoleDisplay.display_startNoticeSuccess();
	}
}
