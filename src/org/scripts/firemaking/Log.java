package org.scripts.firemaking;

public class Log {
	
	private String logName;
	private int logId;
	
	public Log(String logName) {
		this.logName = logName;
	}
	
	public void setLogId(int logId) {
		this.logId = logId;
	}
	
	public int getLogId() {
		return logId;
	}
	
	public String getLogName() {
		return logName;
	}

}
