package io.xunyss.scbreaker;

import io.xunyss.scbreaker.handle.ScHandles;

/**
 *
 * @author XUNYSS
 */
public class ScBreaker {
	
	// TODO: (OS != Windows) 일 경우 exception 발생시킬 것!
	
	
	private static final String[] SC_PROCESSES = {"WINWORD.EXE", "EXCEL.EXE"};
	
	
	public void handle() {
		ScHandles scHandles = ScHandles.snapshot(SC_PROCESSES);
		System.out.println(scHandles);
	}
}
