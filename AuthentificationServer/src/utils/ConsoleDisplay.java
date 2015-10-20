package utils;

/**
 * @author alexandre
 * ConsoleDisplay.java
 * If you need to display something, use this.
 */
public class ConsoleDisplay {
	private final static String PROGRAM_NAME = "Timeflies authentification server";
	private final static String VERSION = "0.0.8";
	private final static String AUTHOR = "A.Gaspard Cilia";
	
	public static boolean debug = false;
	
	/**
	 * Displays program's "splash screen".
	 */
	public static void display_splash() {
		System.out.println();
		System.out.println(PROGRAM_NAME + " v" + VERSION + ".");
		System.out.println("By " + AUTHOR + " 2015, all rights reserved.");
		System.out.println();
	}	
	
	/**
	 * Displays an error notice.
	 * @param notice
	 * 	Text to display.
	 */
	public static void display_errorNotice(String notice) {
		System.err.println(notice);
	}
	
	/**
	 * Displays something.
	 * @param notice
	 * 	Text to display.
	 */
	public static void display_notice(String notice) {
		System.out.println(notice);
	}
	
	/**
	 * Advise user that something is starting. You shouldn't display anything after that unless you used
	 * display_startNoticeSuccess() or display_startNoticeFail(). 
	 * @param elementName
	 */
	public static void display_startNotice(String elementName) {
		System.out.print("Starting " + elementName + "...\t\t\t\t");
	}

	public static void display_startNoticeSuccess() {
		System.out.println("[OK]");
	}
	
	public static void display_startNoticeFail() {
		System.out.println("[FAIL]");
	}
	
	/**
	 * print a stacktrace.
	 * @param e
	 * 	Exception that you need to display the stacktrace.
	 */
	public static void printStack(Exception e) {
		if (debug)
			e.printStackTrace();
	}
	
	/**
	 * Display s if the debug is true.
	 */
	public static void display_debug(String s) {
		if (debug)
			System.out.println("Debug : " + s);
	}
	
}
