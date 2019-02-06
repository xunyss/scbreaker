package io.xunyss.scbreaker.handle.jna;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

/**
 *
 * @author XUNYSS
 */
public abstract class WinProcess {
	
	/**
	 *
	 * @return
	 */
	public static List<Object[]> list() {
		// process list
		List<Object[]> processes = new ArrayList<>();
		
		Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();
		WinNT.HANDLE snapshot = Kernel32.INSTANCE.CreateToolhelp32Snapshot(
				Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));
		
		try {
			while (Kernel32.INSTANCE.Process32Next(snapshot, processEntry)) {
				// set process info
				processes.add(new Object[] {
						processEntry.th32ProcessID.intValue(),			// process id
						processEntry.th32ParentProcessID.intValue(),	// parent process id
						Native.toString(processEntry.szExeFile)			// process filename
				});
			}
		}
		finally {
			Kernel32.INSTANCE.CloseHandle(snapshot);
		}
		
		return processes;
	}
}
