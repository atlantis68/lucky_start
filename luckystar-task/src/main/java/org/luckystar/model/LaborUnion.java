package org.luckystar.model;

public class LaborUnion {

	private int lId;

	private String name;

	private String regDate;

	private String type;

	private String leaderEmail;

	private String workerEmail;

	private int minTask;

	private int maxTask;

	private int boundaryValue;

	private boolean autoExchange;

	public int getlId() {
		return lId;
	}

	public void setlId(int lId) {
		this.lId = lId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLeaderEmail() {
		return leaderEmail;
	}

	public void setLeaderEmail(String leaderEmail) {
		this.leaderEmail = leaderEmail;
	}

	public String getWorkerEmail() {
		return workerEmail;
	}

	public void setWorkerEmail(String workerEmail) {
		this.workerEmail = workerEmail;
	}

	public int getMinTask() {
		return minTask;
	}

	public void setMinTask(int minTask) {
		this.minTask = minTask;
	}

	public int getMaxTask() {
		return maxTask;
	}

	public void setMaxTask(int maxTask) {
		this.maxTask = maxTask;
	}

	public int getBoundaryValue() {
		return boundaryValue;
	}

	public void setBoundaryValue(int boundaryValue) {
		this.boundaryValue = boundaryValue;
	}

	public boolean isAutoExchange() {
		return autoExchange;
	}

	public void setAutoExchange(boolean autoExchange) {
		this.autoExchange = autoExchange;
	}

	@Override
	public String toString() {
		return "LaborUnion [lId=" + lId + ", name=" + name + ", regDate=" + regDate + ", type=" + type
				+ ", leaderEmail=" + leaderEmail + ", workerEmail=" + workerEmail + ", minTask=" + minTask
				+ ", maxTask=" + maxTask + ", boundaryValue=" + boundaryValue + ", autoExchange=" + autoExchange + "]";
	}

}
