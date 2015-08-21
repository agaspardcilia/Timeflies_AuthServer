package handler.args;
/**
* @author alexandre
* GenericArgsHandler.java
*/
public abstract class GenericArgsHandler {
	private String param;
	protected String[] args;
	
	public GenericArgsHandler(String[] args, String param) {
		this.param = param;
		this.args = args;
		
		if (isCalled()) {
			handleArgs();
		}
	}
	
	private boolean isCalled() {
		for (int i = 0; i < args.length; i++) 
			if (args[i].equals(param)) 
				return true;
		
		return false;
		
	}
	
	protected abstract void handleArgs();
}
