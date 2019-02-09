package io.xunyss.scbreaker;

/**
 *
 * @author XUNYSS
 */
public class ScFileTypes {
	
	/**
	 *
	 */
	private static class Type {
		
		private String processName;
		private String[] exts;
		
		private Type(String processName, String... exts) {
			this.processName = processName;
			this.exts = exts;
		}
	}
	
	/**
	 *
	 */
	private static Type[] TYPES;
	static {
		TYPES = new Type[] {
				new Type("WINWORD.EXE",		"doc", "docx"),
				new Type("EXCEL.EXE",		"xls", "xlsx"),
				new Type("POWERPNT.EXE",	"ppt", "pptx"),
				new Type("AcroRd32.exe",	"pdf")
		};
	}
	
	
	/**
	 *
	 */
	private static String[] processNames = null;
	
	/**
	 *
	 * @return
	 */
	public static String[] getProcessNames() {
		if (processNames == null) {
			processNames = new String[TYPES.length];
			for (int idx = 0; idx < TYPES.length; idx++) {
				processNames[idx] = TYPES[idx].processName;
			}
		}
		return processNames;
	}
	
	/**
	 *
	 * @param processName
	 * @param filepath
	 * @return
	 */
	public static boolean isAllowedExt(String processName, String filepath) {
		for (Type type : TYPES) {
			if (type.processName.equalsIgnoreCase(processName)) {
				for (String ext : type.exts) {
					if (filepath.endsWith(ext)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
