package utils;

/**
 * @author alexandre
 * LibChecker.java
 * Check dependencies.
 */
public class LibChecker {

	public static void check() throws Exception {
		ConsoleDisplay.display_startNotice("library checker");
		try {
			Class.forName("org.postgresql.Driver");			
		} catch (Exception e) {
			ConsoleDisplay.display_startNoticeFail();
			throw e;
		}
		ConsoleDisplay.display_startNoticeSuccess();
	}
	
}
