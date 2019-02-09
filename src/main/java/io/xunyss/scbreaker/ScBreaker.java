package io.xunyss.scbreaker;

import io.xunyss.scbreaker.handle.ScHandles;

/**
 *
 * @author XUNYSS
 */
public class ScBreaker {
	
	// TODO: (OS != Windows) 일 경우 exception 발생시킬 것!
	
	
	public void availableList() {
		ScHandles scHandles = ScHandles.snapshot();
		System.out.println(scHandles);
	}
	
	
	public static void main(String[] args) {
		String prompt = "scbreaker> ";
		System.out.print(prompt);
		String str = System.console().readLine();
		if ("scan".equalsIgnoreCase(str)) {
			ScHandles scHandles = ScHandles.snapshot();
			System.out.println(scHandles);
		}
	}
}
