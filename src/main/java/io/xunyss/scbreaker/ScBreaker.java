package io.xunyss.scbreaker;

import io.xunyss.commons.exec.ProcessExecutor;
import io.xunyss.commons.exec.support.StringOutputHandler;

/**
 *
 * @author XUNYSS
 */
public class ScBreaker {
	
	static final String HANDLE = "C:\\Portable Programs\\SysinternalsSuite\\handle.exe";
	
	public void handle() {
		System.out.println("handle");
		
		
		StringOutputHandler stringOutputHandler = new StringOutputHandler();
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(stringOutputHandler);
		
		try {
			processExecutor.execute(HANDLE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(stringOutputHandler.getOutputString());
	}
}
