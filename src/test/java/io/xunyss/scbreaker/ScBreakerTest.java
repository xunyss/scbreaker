package io.xunyss.scbreaker;

import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import org.junit.Test;

/**
 *
 * @author XUNYSS
 */
public class ScBreakerTest {

	@Test
	public void exec() {
	
	}
	
	@Test
	public void test() throws Exception {
		Platform.isWindows();
//		ScBreaker scBreaker = new ScBreaker();
//		scBreaker.handle();
//		Kernel32 kernel32 = (Kernel32) Native.loadLibrary(Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
		
		Kernel32 kernel32 = Kernel32.INSTANCE;
		User32 user32 = User32.INSTANCE;
		
		
		
		Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();
		WinNT.HANDLE snapshot = kernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));
		
		try {
			while (kernel32.Process32Next(snapshot, processEntry)) {
				System.out.println(processEntry.th32ProcessID + "\t" + Native.toString(processEntry.szExeFile));
				
				if (8308 == processEntry.th32ProcessID.intValue()) {
					
				
				
				
				}
			}
		}
		finally {
			kernel32.CloseHandle(snapshot);
		}
		
		
	}
}
