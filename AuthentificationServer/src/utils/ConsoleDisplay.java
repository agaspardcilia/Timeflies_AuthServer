package utils;

/**
 * @author alexandre
 * ConsoleDisplay.java
 * If you need to display something, use this.
 */
public class ConsoleDisplay {
	private final static String PROGRAM_NAME = "Timeflies authentification server";
	private final static String VERSION = "0.0.3";
	private final static String AUTHOR = "A.Gaspard Cilia";
	
	public static boolean debug = false;
	
	public static void display_splash() {
		System.out.println();
		System.out.println(PROGRAM_NAME + " v" + VERSION + ".");
		System.out.println("By " + AUTHOR + " 2015, all rights reserved.");
		System.out.println();
	}	
	
	public static void display_errorNotice(String notice) {
		System.err.println(notice);
	}
	
	public static void display_notice(String notice) {
		System.out.println(notice);
	}
	
	public static void display_startNotice(String elementName) {
		System.out.print("Starting " + elementName + "...\t\t\t\t");
	}
	
	public static void display_startNoticeSuccess() {
		System.out.println("[OK]");
	}
	
	public static void display_startNoticeFail() {
		System.out.println("[FAIL]");
	}
	
	public static void printStack(Exception e) {
		if (debug)
			e.printStackTrace();
	}
	
}
