package org.luckystar.model;

public class ChickenInfo {

	private long id;

	private long starId;

	private int lId;

	private String userName;

	private String nickName;

	private String regDate;

	private String cookie;

	private float timeRate;

	private float beanRate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getStarId() {
		return starId;
	}

	public void setStarId(long starId) {
		this.starId = starId;
	}

	public int getlId() {
		return lId;
	}

	public void setlId(int lId) {
		this.lId = lId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	public float getTimeRate() {
		return timeRate;
	}

	public void setTimeRate(float timeRate) {
		this.timeRate = timeRate;
	}

	public float getBeanRate() {
		return beanRate;
	}

	public void setBeanRate(float beanRate) {
		this.beanRate = beanRate;
	}

	@Override
	public String toString() {
		return "ChickenInfo [id=" + id + ", starId=" + starId + ", lId=" + lId + ", userName=" + userName
				+ ", nickName=" + nickName + ", regDate=" + regDate + ", cookie=" + cookie + ", timeRate=" + timeRate
				+ ", beanRate=" + beanRate + "]";
	}

}
