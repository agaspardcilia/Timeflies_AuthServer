package utils;

import java.util.Scanner;

/**
* @author alexandre
* ConsoleInput.java
*/
public class ConsoleInput {
	private static ConsoleInput currentInst;
	
	private Scanner sc;
	
	public static void init() {
		ConsoleDisplay.display_startNotice("Console input");
		try {
			currentInst = new ConsoleInput();
			ConsoleDisplay.display_startNoticeSuccess();
		} catch (Exception e) {
			ConsoleDisplay.display_startNoticeFail();
			throw e;
		}
		
		
	}
	
	public ConsoleInput() {
		sc = new Scanner(System.in);
	}
	
	public String nextLine() {
		System.out.print(">");
		return sc.nextLine();
	}
	
	public static ConsoleInput getCurrentInst() {
		return currentInst;
	}
}
