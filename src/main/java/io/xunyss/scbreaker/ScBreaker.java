package io.xunyss.scbreaker;

import io.xunyss.commons.exec.ProcessExecutor;
import io.xunyss.commons.exec.support.StringOutputHandler;

/**
 *
 * @author XUNYSS
 */
public class ScBreaker {
	
	// TODO: (OS != Windows) 일 경우 exception 발생시킬 것!
	
	
	static final String HANDLE_EXE = "C:\\Portable Programs\\SysinternalsSuite\\handle.exe";
	
	
	public void handle() {
		System.out.println("handle");
		
		
		StringOutputHandler stringOutputHandler = new StringOutputHandler();
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(stringOutputHandler);
		
		try {
			processExecutor.execute(HANDLE_EXE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(stringOutputHandler.getOutputString());
	}
}
