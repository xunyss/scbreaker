package io.xunyss.scbreaker.handle.jna;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WTypes;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

/**
 *
 * @author XUNYSS
 */
public interface Kernel32X extends StdCallLibrary {
	
	/**
	 *
	 */
	Kernel32X INSTANCE = Native.load("kernel32", Kernel32X.class, W32APIOptions.DEFAULT_OPTIONS);
	
	
	// GetFinalPathNameByHandleA - deFlags
	int FILE_NAME_NORMALIZED	= 0x0;
	int FILE_NAME_OPENED		= 0x8;
	int VOLUME_NAME_DOS			= 0x0;
	int VOLUME_NAME_NT			= 0x2;
	int VOLUME_NAME_GUID		= 0x1;
	int VOLUME_NAME_NONE		= 0x4;
	
	/**
	 *
	 * @param hFile
	 * @param lpszFilePath
	 * @param cchFilePath
	 * @param dwFlags
	 * @return
	 */
	WinDef.DWORD GetFinalPathNameByHandleA(
			WinNT.HANDLE hFile,
			WTypes.LPSTR lpszFilePath,
			int /* WinDef.DWORD */ cchFilePath,
			int /* WinDef.DWORD */ dwFlags
	);
}
