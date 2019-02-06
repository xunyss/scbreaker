package io.xunyss.scbreaker.handle;

/**
 *
 * @author XUNYSS
 */
public class Handle {
	
	public static enum Type {
		Directory,
		File
	}
	
	
	private int processId;
	private String processName;
	
	private int handleId;
	private String handleName;
	
	
	public int getProcessId() {
		return processId;
	}
	
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	
	public String getProcessName() {
		return processName;
	}
	
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	
	public int getHandleId() {
		return handleId;
	}
	
	public void setHandleId(int handleId) {
		this.handleId = handleId;
	}
	
	public String getHandleName() {
		return handleName;
	}
	
	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}
}
