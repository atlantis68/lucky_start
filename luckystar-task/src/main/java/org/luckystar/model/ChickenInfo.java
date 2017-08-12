package org.luckystar.model;

public class ChickenInfo {

	private long id;

	private long starId;

	private int lId;

	private String userName;

	private String nickName;

	private String phoneNumber;

	private String QQ;

	private String weChat;

	private String regDate;

	private String loginName;

	private String password;

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getWeChat() {
		return weChat;
	}

	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
				+ ", nickName=" + nickName + ", phoneNumber=" + phoneNumber + ", QQ=" + QQ + ", weChat=" + weChat
				+ ", regDate=" + regDate + ", loginName=" + loginName + ", password=" + password + ", cookie=" + cookie
				+ ", timeRate=" + timeRate + ", beanRate=" + beanRate + "]";
	}

}
