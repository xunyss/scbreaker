package io.xunyss.scbreaker.handle;

import io.xunyss.commons.io.FileUtils;

/**
 *
 * @author XUNYSS
 */
public class Handle {
	
	public enum Type {
		Directory,
		File
	}
	
	
	private int processId;
	private long handleId;
	private String processName;
	private Handle.Type handleType;
	private String handlePath;
	
	
	public Handle(int processId, long handleId, String processName, Handle.Type handleType, String handlePath) {
		this.processId = processId;
		this.handleId = handleId;
		this.processName = processName;
		this.handleType = handleType;
		this.handlePath = handlePath;
	}
	
	public int getProcessId() {
		return processId;
	}
	
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	
	public long getHandleId() {
		return handleId;
	}
	
	public void setHandleId(long handleId) {
		this.handleId = handleId;
	}
	
	public String getProcessName() {
		return processName;
	}
	
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	public Type getHandleType() {
		return handleType;
	}
	
	public void setHandleType(Type handleType) {
		this.handleType = handleType;
	}
	
	public String getHandlePath() {
		return handlePath;
	}
	
	public void setHandlePath(String handlePath) {
		this.handlePath = handlePath;
	}
	
	
	/**
	 *
	 * @return
	 */
	public String getProcessShortName() {
		return FileUtils.getSimpleFilename(processName);
	}
	
	/**
	 *
	 * @return
	 */
	public String getHandleFilename() {
		return FileUtils.getSimpleFilename(handlePath);
	}
	
	
	/**
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "Handle{" +
				"processId=" + processId +
				", handleId=" + handleId +
				", processName='" + processName + '\'' +
				", handleType=" + handleType +
				", handlePath='" + handlePath + '\'' +
				'}';
	}
}
