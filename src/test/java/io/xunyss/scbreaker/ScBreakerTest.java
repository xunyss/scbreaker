package io.xunyss.scbreaker;

import java.util.List;

import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinNT;
import org.junit.Test;

import io.xunyss.scbreaker.jna.FileHandles;
import io.xunyss.scbreaker.jna.Processes;

/**
 *
 * @author XUNYSS
 */
public class ScBreakerTest {

	@Test
	public void exec() throws Exception {
		List<Processes.Process> processes = Processes.list();
		for (Processes.Process process : processes) {
			System.out.println(process.getPid() + "\t" + process.getParentPid() + "\t" + process.getFilename());
		}
		
		processes = Processes.list(new Processes.ProcessFilter() {
			@Override
			public boolean accept(int pid, String filename) {
				return "java.exe".equals(filename);
			}
		});
		for (Processes.Process process : processes) {
			System.out.println(process.getPid() + "\t" + process.getParentPid() + "\t" + process.getFilename());
		}
	}
	
	@Test
	public void exec2() {
		List<String> handles = FileHandles.list();
		for (String handle : handles) {
			System.out.println(handle);
		}
	}
	
	@Test
	public void test() throws Exception {
		Platform.isWindows();
//		ScBreaker scBreaker = new ScBreaker();
//		scBreaker.handle();
//		Kernel32 kernel32 = (Kernel32) Native.loadLibrary(Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
		

		
		
	}
	
	@Test
	public void test2() {
		Kernel32 kernel32 = Kernel32.INSTANCE;
		final User32 user32 = User32.INSTANCE;
		
		final WinNT.HANDLE h = kernel32.OpenProcess(WinNT.PROCESS_DUP_HANDLE, false, 7136);
		
		
		
	}
}
