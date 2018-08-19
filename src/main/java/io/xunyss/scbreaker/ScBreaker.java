package io.xunyss.scbreaker;

import io.xunyss.commons.exec.ProcessExecutor;
import io.xunyss.commons.exec.support.ToStringStreamHandler;

/**
 *
 * @author XUNYSS
 */
public class ScBreaker {
	
	// TODO: (OS != Windows) 일 경우 exception 발생시킬 것!
	
	
	static final String HANDLE_EXE = "C:\\Portable Programs\\SysinternalsSuite\\handle.exe";
	
	
	public void handle() {
		System.out.println("handle");
		
		
		ToStringStreamHandler toStringStreamHandler = new ToStringStreamHandler();
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(toStringStreamHandler);
		
		try {
			processExecutor.execute(HANDLE_EXE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(toStringStreamHandler.getOutputString());
	}
}
