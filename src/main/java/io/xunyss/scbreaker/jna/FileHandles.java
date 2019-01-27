package io.xunyss.scbreaker.jna;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.WinNT;

/**
 *
 */
public abstract class FileHandles {
	
	/**
	 *
	 * @return
	 */
	private static NtDllX.SYSTEM_HANDLE_INFORMATION getSystemHandleInformation() {
		final int initialSize = 1024;
		int size = initialSize;
		
		while (true) {
			DisposableMemory systemHandleInformationMemory = new DisposableMemory(size);
			try {
				int result = NtDllX.INSTANCE.NtQuerySystemInformation(NtDllX.SystemHandleInformation,
						systemHandleInformationMemory, (int) systemHandleInformationMemory.size(), null);
				if (result == 0) {
					NtDllX.SYSTEM_HANDLE_INFORMATION systemHandleInformation = Structure.newInstance(
							NtDllX.SYSTEM_HANDLE_INFORMATION.class, systemHandleInformationMemory);
					systemHandleInformation.read();
					return systemHandleInformation;
				}
			}
			finally {
				systemHandleInformationMemory.dispose();
			}
			
			size += initialSize;
		}
	}
	
	public static List<String> list() {
		List<String> handles = new ArrayList<>();
		
		NtDllX.SYSTEM_HANDLE_INFORMATION systemHandleInformation = getSystemHandleInformation();
		Memory objectTypeInformationMemory = new Memory(8192);
		String processName, objectTypeName, objectName;
		int result;
		for (int idx = 0; idx < systemHandleInformation.HandleCount; idx++) {
			NtDllX.SYSTEM_HANDLE systemHandle = systemHandleInformation.Handles[idx];
			
			WinNT.HANDLE remoteHandle = new WinNT.HANDLE(new Pointer(systemHandle.Handle));
			WinNT.HANDLE remoteProcess = Kernel32.INSTANCE.OpenProcess(
					Kernel32.PROCESS_DUP_HANDLE | Kernel32.PROCESS_QUERY_INFORMATION | Kernel32.PROCESS_VM_READ,
					false, systemHandle.ProcessId);
			if (remoteProcess == null) {	// failed to open remote process
				continue;
			}
			else {
				processName = Kernel32Util.QueryFullProcessImageName(remoteProcess, 0);
				
				WinNT.HANDLEByReference targetHandle = new WinNT.HANDLEByReference();
				result = NtDllX.INSTANCE.NtDuplicateObject(remoteProcess, remoteHandle,
						Kernel32.INSTANCE.GetCurrentProcess(), targetHandle, 0, 0, 0);
				if (result != 0) {	// failed to duplicate handle
					continue;
				}
				else {
					result = NtDllX.INSTANCE.NtQueryObject(targetHandle.getValue(), NtDllX.ObjectTypeInformation,
							objectTypeInformationMemory, (int) objectTypeInformationMemory.size(), null);
					if (result != 0) {	// failed to query information
						continue;
					}
					
					NtDllX.OBJECT_TYPE_INFORMATION objectTypeInformation = Structure.newInstance(
							NtDllX.OBJECT_TYPE_INFORMATION.class, objectTypeInformationMemory);
					objectTypeInformation.read();
					
					objectTypeName = objectTypeInformation.Name.Buffer.getWideString(0);
					
					if ("Directory".equals(objectTypeName) || "File".equals(objectTypeName)) {
						int fileType = Kernel32.INSTANCE.GetFileType(targetHandle.getValue());
						if (fileType == Kernel32.FILE_TYPE_PIPE) {
							// calls to determine name for names pipes block,
							// so skip these
							continue;
						}
						
						NtDllX.INSTANCE.NtQueryObject(targetHandle.getValue(), NtDllX.ObjectNameInformation,
								objectTypeInformationMemory, (int) objectTypeInformationMemory.size(), null);
						
						NtDllX.OBJECT_NAME_INFORMATION objectNameInformation = Structure.newInstance(
								NtDllX.OBJECT_NAME_INFORMATION.class, objectTypeInformationMemory);
						objectNameInformation.read();
						
						// QueryDosDevice
						objectName = objectNameInformation.Name.Buffer != null
								? objectNameInformation.Name.Buffer.getWideString(0) : "NULL";
						
						handles.add(String.format("%s: %s: %s",	processName, objectTypeName, objectName));
					}
				}
				// close object handle
				Kernel32.INSTANCE.CloseHandle(targetHandle.getValue());
			}
			// close process handle
			Kernel32.INSTANCE.CloseHandle(remoteProcess);
		}
		
		return handles;
	}
}
