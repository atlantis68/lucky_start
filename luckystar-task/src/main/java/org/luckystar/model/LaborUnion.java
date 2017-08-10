package org.luckystar.model;

public class LaborUnion {

	private int lId;

	private String name;

	private String regDate;

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

	@Override
	public String toString() {
		return "LaborUnion [lId=" + lId + ", name=" + name + ", regDate=" + regDate + "]";
	}

}
