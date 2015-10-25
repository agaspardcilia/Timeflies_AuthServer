package handlers.commands;
/**
* @author alexandre
* Command.java
*/
public abstract class Command {
	private final static String SPLIT_REGEX = " ";
	
	
	public abstract void handle(String[] args);
	
	public void handle(String rawArgs) {
		handle(splitArgs(rawArgs));
	}
	
	
	public String[] splitArgs(String rawArgs) {
		return rawArgs.split(SPLIT_REGEX);
	}
	
	public abstract String getCommandTrigger();
}
