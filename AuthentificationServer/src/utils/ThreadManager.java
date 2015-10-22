package utils;

import java.util.ArrayList;

/**
* @author alexandre
* ThreadManager.java
*/
public class ThreadManager {
	private static ThreadManager currentInstance;
	
	private ArrayList<Thread> activeThreads;
	
	
	public ThreadManager() {
		activeThreads = new ArrayList<Thread>();
	}
	
	public static void init() {
		ConsoleDisplay.display_startNotice("Thread manager");
		try {
			currentInstance = new ThreadManager();
			ConsoleDisplay.display_startNoticeSuccess();
		} catch (Exception e) {
			ConsoleDisplay.display_startNoticeFail();
			throw e;
		}
		
	}
	
	public static ThreadManager getCurrentInstance() {
		return currentInstance;
	}
	
	public void addThread(Thread t) {
		activeThreads.add(t);
	}
	
	public void killThreads() {
		for (Thread thread : activeThreads) 
			if(thread.isAlive())
				thread.interrupt();
	}
}
