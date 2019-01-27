package io.xunyss.scbreaker.jna;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

/**
 *
 */
public abstract class Processes {
	
	/**
	 *
	 * @param processFilter
	 * @return
	 */
	public static List<Process> list(ProcessFilter processFilter) {
		// process info list
		List<Process> processes = new ArrayList<>();
		
		Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();
		WinNT.HANDLE snapshot = Kernel32.INSTANCE.CreateToolhelp32Snapshot(
				Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));
		
		try {
			while (Kernel32.INSTANCE.Process32Next(snapshot, processEntry)) {
				// process info
				int pid = processEntry.th32ProcessID.intValue();
				int parentPid = processEntry.th32ParentProcessID.intValue();
				String filename = Native.toString(processEntry.szExeFile);
				
				// filter
				if (processFilter != null && !processFilter.accept(pid, filename)) {
					continue;
				}
				
				// set process info
				Process processInfo = new Process();
				processInfo.setPid(pid);
				processInfo.setParentPid(parentPid);
				processInfo.setFilename(filename);
				processes.add(processInfo);
			}
		}
		finally {
			Kernel32.INSTANCE.CloseHandle(snapshot);
		}
		
		return processes;
	}
	
	/**
	 *
	 * @return
	 */
	public static List<Process> list() {
		return list(null);
	}
	
	
	/**
	 *
	 */
	public static class Process {
		
		private int pid;
		private int parentPid;
		private String filename;
		
		private Process() {
		
		}
		
		public int getPid() {
			return pid;
		}
		
		private void setPid(int pid) {
			this.pid = pid;
		}
		
		public int getParentPid() {
			return parentPid;
		}
		
		private void setParentPid(int parentPid) {
			this.parentPid = parentPid;
		}
		
		public String getFilename() {
			return filename;
		}
		
		private void setFilename(String filename) {
			this.filename = filename;
		}
	}
	
	/**
	 *
	 */
	public interface ProcessFilter {
		
		/**
		 *
		 * @param pid
		 * @param filename
		 * @return
		 */
		boolean accept(int pid, String filename);
	}
}
