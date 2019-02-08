package io.xunyss.scbreaker.handle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.xunyss.commons.io.FileUtils;
import io.xunyss.scbreaker.handle.jna.WinHandle;

/**
 *
 * @author XUNYSS
 */
public class ScHandles extends ArrayList<ScHandles.ScHandle> {
	
	private static final String TEMP_DIR_PATH = FileUtils.getTempDirectory().toString();
	
	
	private long snapshotTime = 0L;
	
	
	private ScHandles() {
	}
	
	public long getSnapshotTime() {
		return snapshotTime;
	}
	
	private void setSnapshotTime(long snapshotTime) {
		this.snapshotTime = snapshotTime;
	}
	
	
	/**
	 *
	 * @param pnames
	 * @return
	 */
	public static ScHandles snapshot(String[] pnames) {
		List<Handle> handles = new ArrayList<>();
		List<Object[]> winHandles = WinHandle.list(pnames);
		for (Object[] handle : winHandles) {
			handles.add(new Handle(
					(int) handle[0],
					(long) handle[1],
					(String) handle[2],
					Handle.Type.valueOf((String) handle[3]),
					((String) handle[4]).substring(4)	// "\\?\C:\" 로 시작 함.
			));
		}
		
		Set<Integer> pids = new HashSet<>();
		for (Handle handle : handles) {
			pids.add(handle.getProcessId());
		}
		
		ScHandles scHandles = new ScHandles();
		scHandles.setSnapshotTime(System.currentTimeMillis());
		for (int pid : pids) {
			boolean multiOpened = false;
			String processName = null;
			String originFilepath = null;
			String tempFilepath = null;
			for (Handle handle : handles) {
				// pid 일치
				if (pid == handle.getProcessId()) {
					// process name
					if (processName == null) {
						processName = handle.getProcessShortName();
					}
					// origin file path
					if (handle.getHandlePath().endsWith(".xlsx")) {
						String filename = handle.getHandleFilename();
						if (!filename.startsWith("~$")) {
							originFilepath = handle.getHandlePath();
							continue;
						}
					}
					// temp file path
					if (handle.getHandlePath().startsWith(TEMP_DIR_PATH) && handle.getHandlePath().endsWith(".tmp")) {
						if (tempFilepath != null) {
							multiOpened = true;
							break;
						}
						tempFilepath = handle.getHandlePath();
						continue;
					}
				}
			}
			
			if (multiOpened) {
				// error
				scHandles.add(new ScHandle(pid, "multi files opened!"));
			}
			else {
				// success
				scHandles.add(new ScHandle(pid, processName, originFilepath, tempFilepath));
			}
		}
		
		return scHandles;
	}
	
	
	/**
	 *
	 */
	public static class ScHandle {
		
		private int pid;
		private String pname;
		private String originFilepath;
		private String tempFilepath;
		private String message;
		
		public ScHandle(int pid, String pname, String originFilepath, String tempFilepath) {
			this.pid = pid;
			this.pname = pname;
			this.originFilepath = originFilepath;
			this.tempFilepath = tempFilepath;
		}
		
		public ScHandle(int pid, String message) {
			this.pid = pid;
			this.message = message;
		}
		
		public int getPid() {
			return pid;
		}
		
		public void setPid(int pid) {
			this.pid = pid;
		}
		
		public String getPname() {
			return pname;
		}
		
		public void setPname(String pname) {
			this.pname = pname;
		}
		
		public String getOriginFilepath() {
			return originFilepath;
		}
		
		public void setOriginFilepath(String originFilepath) {
			this.originFilepath = originFilepath;
		}
		
		public String getTempFilepath() {
			return tempFilepath;
		}
		
		public void setTempFilepath(String tempFilepath) {
			this.tempFilepath = tempFilepath;
		}
		
		public String getMessage() {
			return message;
		}
		
		public void setMessage(String message) {
			this.message = message;
		}
		
		@Override
		public String toString() {
			return "ScHandle{" +
					"pid=" + pid +
					", pname='" + pname + '\'' +
					", originFilepath='" + originFilepath + '\'' +
					", tempFilepath='" + tempFilepath + '\'' +
					'}';
		}
	}
}
