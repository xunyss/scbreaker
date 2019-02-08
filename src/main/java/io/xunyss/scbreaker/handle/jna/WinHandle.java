package io.xunyss.scbreaker.handle.jna;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.WTypes;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;

/**
 *
 * @author XUNYSS
 */
public abstract class WinHandle {
	
	/**
	 *
	 * @return
	 */
	private static NtDllX.SYSTEM_HANDLE_INFORMATION getSystemHandleInformation() {
		final int incSize = 8_192 * 10;
		int size = incSize;
		
		while (true) {
			DisposableMemory buffer = new DisposableMemory(size);
			try {
				if (0 == NtDllX.INSTANCE.NtQuerySystemInformation(NtDllX.SystemHandleInformation, buffer, (int) buffer.size(), null)) {
					NtDllX.SYSTEM_HANDLE_INFORMATION systemHandleInformation = Structure.newInstance(NtDllX.SYSTEM_HANDLE_INFORMATION.class, buffer);
					systemHandleInformation.read();
					return systemHandleInformation;
				}
			}
			finally {
				buffer.dispose();
			}
			size += incSize;
		}
	}
	
	/**
	 *
	 * @return
	 */
	public static List<Object[]> list(int pid, String... pnames) {
		List<Object[]> handles = new ArrayList<>();
		
		NtDllX.SYSTEM_HANDLE_INFORMATION shi = getSystemHandleInformation();
		Memory oti_buf = new Memory(8_192);
		WTypes.LPSTR hpf_buf = new WTypes.LPSTR(new Memory(WinDef.MAX_PATH));
		
		String processName, objectTypeName, objectName, handlePath;
		int fileType;
		boolean ignore = true;
		for (int idx = 0; idx < shi.HandleCount; idx++) {
			NtDllX.SYSTEM_HANDLE sh = shi.Handles[idx];
			WinNT.HANDLE remoteHandle = new WinNT.HANDLE(new Pointer(sh.Handle));
			WinNT.HANDLE remoteProcess = Kernel32.INSTANCE.OpenProcess(
					Kernel32.PROCESS_DUP_HANDLE | Kernel32.PROCESS_QUERY_INFORMATION | Kernel32.PROCESS_VM_READ,
					false, sh.ProcessId);
			
			if (remoteProcess == null) {	// failed to open remote process
				continue;
			}
			else {
				//----------------------------------------------------------------------------------
				// filtering: pid
				//----------------------------------------------------------------------------------
				if (pid > 0 && sh.ProcessId != pid) {
					continue;
				}
				
				//----------------------------------------------------------------------------------
				// query process image name
				//----------------------------------------------------------------------------------
				try {
					processName = Kernel32Util.QueryFullProcessImageName(remoteProcess, 0);
				}
				catch (Win32Exception ex) {
					continue;
				}
				
				//----------------------------------------------------------------------------------
				// filtering: pname
				//----------------------------------------------------------------------------------
				if (pid == 0 && pnames.length > 0) {
					for (String pname : pnames) {
						if (processName.endsWith(pname)) {
							ignore = false;
							break;
						}
					}
					if (ignore) {
						continue;
					}
					ignore = true;
				}
				
				//----------------------------------------------------------------------------------
				// query target handle
				//----------------------------------------------------------------------------------
				WinNT.HANDLEByReference targetHandle = new WinNT.HANDLEByReference();
				if (0 != NtDllX.INSTANCE.NtDuplicateObject(remoteProcess, remoteHandle, Kernel32.INSTANCE.GetCurrentProcess(), targetHandle, 0, 0, 0)) {
					// failed to duplicate object handle
					continue;
				}
				
//				//----------------------------------------------------------------------------------
//				// query OBJECT_TYPE_INFORMATION
//				//----------------------------------------------------------------------------------
//				if (0 != NtDllX.INSTANCE.NtQueryObject(targetHandle.getValue(), NtDllX.ObjectTypeInformation, oti_buf, (int) oti_buf.size(), null)) {
//					// failed to query object information
//					continue;
//				}
//				NtDllX.OBJECT_TYPE_INFORMATION oti = Structure.newInstance(NtDllX.OBJECT_TYPE_INFORMATION.class, oti_buf);
//				oti.read();
//				objectTypeName = oti.Name.Buffer.getWideString(0);
				
//				if (/* "Directory".equals(objectTypeName) || */ "File".equals(objectTypeName)) {
					fileType = Kernel32.INSTANCE.GetFileType(targetHandle.getValue());
//					if (fileType == Kernel32.FILE_TYPE_PIPE) {
//						// calls to determine name for names pipes block,
//						// so skip these
//						continue;
//					}
					if (fileType != Kernel32.FILE_TYPE_DISK) {
						continue;
					}
				
//					//------------------------------------------------------------------------------
//					// query OBJECT_NAME_INFORMATION
//					//------------------------------------------------------------------------------
//					NtDllX.INSTANCE.NtQueryObject(targetHandle.getValue(), NtDllX.ObjectNameInformation, oti_buf, (int) oti_buf.size(), null);
//					NtDllX.OBJECT_NAME_INFORMATION oni = Structure.newInstance(NtDllX.OBJECT_NAME_INFORMATION.class, oti_buf);
//					oni.read();
//					objectName = oni.Name.Buffer != null ? oni.Name.Buffer.getWideString(0) : "NULL";
					
					//------------------------------------------------------------------------------
					// query handle file path
					//------------------------------------------------------------------------------
					Kernel32X.INSTANCE.GetFinalPathNameByHandleA(targetHandle.getValue(), hpf_buf, WinDef.MAX_PATH, Kernel32X.VOLUME_NAME_DOS);
					handlePath = hpf_buf.getPointer().getString(0, "MS949");
				
					//------------------------------------------------------------------------------
					// set handle info
					//------------------------------------------------------------------------------
					handles.add(new Object[] {
							sh.ProcessId,
							Pointer.nativeValue(targetHandle.getPointer()),
							processName,
							"File",			// fileType == Kernel32.FILE_TYPE_DISK
							handlePath
					});
//				}
				
				// close object handle
				Kernel32.INSTANCE.CloseHandle(targetHandle.getValue());
			}
			
			// close process handle
			Kernel32.INSTANCE.CloseHandle(remoteProcess);
		}
		
		return handles;
	}
	
	/**
	 *
	 * @param pnames
	 * @return
	 */
	public static List<Object[]> list(String... pnames) {
		return list(0, pnames);
	}
	
	/**
	 *
	 * @return
	 */
	public static List<Object[]> list() {
		return list(0);
	}
}
