package io.xunyss.scbreaker.handle.jna;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;

/**
 *
 * @author XUNYSS
 */
public interface WinBaseX {
	
	/**
	 * FILE_INFO_BY_HANDLE_CLASS
	 * 
	 * @see com.sun.jna.platform.win32.Kernel32#GetFileInformationByHandleEx
	 * @see com.sun.jna.platform.win32.WinBase
	 */
	@Structure.FieldOrder({"FileNameLength", "FileName"})
	class FILE_NAME_INFO extends Structure {
		
		public static class ByReference extends FILE_NAME_INFO implements Structure.ByReference {
			public ByReference() {
			}
			
			public ByReference(Pointer memory) {
				super(memory);
			}
		}
		
		public int FileNameLength;
		public char[] FileName = new char[WinDef.MAX_PATH];
		
		public static int sizeOf() {
			return Native.getNativeSize(FILE_NAME_INFO.class, null);
		}
		
		public FILE_NAME_INFO() {
			super();
		}
		
		public FILE_NAME_INFO(Pointer memory) {
			super(memory);
			read();
		}
	}
}
