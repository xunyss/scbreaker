package io.xunyss.scbreaker.jna;

import java.util.List;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.NtDll;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.W32APIOptions;

/**
 *
 */
interface NtDllX extends NtDll {
	
	int SystemHandleInformation = 16;
	int ObjectBasicInformation = 0;
	int ObjectNameInformation = 1;
	int ObjectTypeInformation = 2;
	
	/**
	 *
	 */
	NtDllX INSTANCE = Native.load("NtDll", NtDllX.class, W32APIOptions.DEFAULT_OPTIONS);
	
	
	/**
	 *
	 */
	class SYSTEM_HANDLE_INFORMATION extends Structure {
		
		public static final List<String> FIELDS = createFieldsOrder(
				"HandleCount", "Handles");
		
		public int HandleCount = 0;
		public SYSTEM_HANDLE[] Handles = new SYSTEM_HANDLE[1];
		
		@Override
		public void write() {
			HandleCount = Handles.length;
		}
		
		@Override
		public void read() {
			int handleCount = (Integer) readField("HandleCount");
			Handles = new SYSTEM_HANDLE[handleCount];
			super.read();
		}
		
		@Override
		protected List<String> getFieldOrder() {
			return FIELDS;
		}
	}
	
	/**
	 *
	 */
	class SYSTEM_HANDLE extends Structure {
		
		public static final List<String> FIELDS = createFieldsOrder(
				"ProcessId", "ObjectTypeNumber", "Flags", "Handle", "Object", "GrantedAccess");
		
		public int ProcessId;
		public byte ObjectTypeNumber;
		public byte Flags;
		public short Handle;
		public WinDef.PVOID Object;
		public int GrantedAccess;
		
		@Override
		protected List<String> getFieldOrder() {
			return FIELDS;
		}
	}
	
	/**
	 *
	 */
	class OBJECT_TYPE_INFORMATION extends Structure {
		
		public static final List<String> FIELDS = createFieldsOrder(
				"Name", "TotalNumberOfObjects", "TotalNumberOfHandles", "TotalPagedPoolUsage", "TotalNonPagedPoolUsage",
				"TotalNamePoolUsage", "TotalHandleTableUsage", "HighWaterNumberOfObjects", "HighWaterNumberOfHandles",
				"HighWaterPagedPoolUsage", "HighWaterNonPagedPoolUsage", "HighWaterNamePoolUsage",
				"HighWaterHandleTableUsage", "InvalidAttributes", "GenericMapping", "ValidAccess", "SecurityRequired",
				"MaintainHandleCount", "MaintainTypeList", "PoolType", "PagedPoolUsage", "NonPagedPoolUsage"
		);
		
		public UNICODE_STRING Name;
		public int TotalNumberOfObjects;
		public int TotalNumberOfHandles;
		public int TotalPagedPoolUsage;
		public int TotalNonPagedPoolUsage;
		public int TotalNamePoolUsage;
		public int TotalHandleTableUsage;
		public int HighWaterNumberOfObjects;
		public int HighWaterNumberOfHandles;
		public int HighWaterPagedPoolUsage;
		public int HighWaterNonPagedPoolUsage;
		public int HighWaterNamePoolUsage;
		public int HighWaterHandleTableUsage;
		public int InvalidAttributes;
		public GENERIC_MAPPING GenericMapping;
		public int ValidAccess;
		public short SecurityRequired;
		public short MaintainHandleCount;
		public short MaintainTypeList;
		public int PoolType;
		public int PagedPoolUsage;
		public int NonPagedPoolUsage;
		
		@Override
		protected List<String> getFieldOrder() {
			return FIELDS;
		}
	}
	
	/**
	 *
	 */
	class OBJECT_NAME_INFORMATION extends Structure {
		
		public static final List<String> FIELDS = createFieldsOrder(
				"Name");
		
		public UNICODE_STRING Name;
		
		@Override
		protected List<String> getFieldOrder() {
			return FIELDS;
		}
	}
	
	/**
	 *
	 */
	class GENERIC_MAPPING extends Structure {
		
		public static final List<String> FIELDS = createFieldsOrder(
				"GenericRead", "GenericWrite", "GenericExecute", "GenericAll");
		
		public int GenericRead;
		public int GenericWrite;
		public int GenericExecute;
		public int GenericAll;
		
		@Override
		protected List<String> getFieldOrder() {
			return FIELDS;
		}
	}
	
	/**
	 *
	 */
	class UNICODE_STRING extends Structure {
		
		public static final List<String> FIELDS = createFieldsOrder(
				"Length", "MaximumLength", "Buffer");
		
		public short Length = 0;
		public short MaximumLength = 0;
		public Pointer Buffer;
		
		@Override
		protected List<String> getFieldOrder() {
			return FIELDS;
		}
	}

	
	/**
	 *
	 * @param type
	 * @param buffer
	 * @param bufferLength
	 * @param returnLength
	 * @return
	 */
	int NtQuerySystemInformation(int type, Pointer buffer, int bufferLength,
								 WinDef.ULONGByReference returnLength);
	
	/**
	 *
	 * @param sourceProcessHandle
	 * @param sourceHandle
	 * @param targetProcessHandle
	 * @param targetHandle
	 * @param desiredAccess
	 * @param attributes
	 * @param options
	 * @return
	 */
	int NtDuplicateObject(WinNT.HANDLE sourceProcessHandle, WinNT.HANDLE sourceHandle,
						  WinNT.HANDLE targetProcessHandle, WinNT.HANDLEByReference targetHandle,
						  int desiredAccess, int attributes, int options);
	
	/**
	 *
	 * @param objectHandle
	 * @param objectInformationClass
	 * @param buffer
	 * @param bufferSize
	 * @param returnLength
	 * @return
	 */
	int NtQueryObject(WinNT.HANDLE objectHandle, int objectInformationClass, Pointer buffer, int bufferSize,
					  WinDef.ULONGByReference returnLength);
}
