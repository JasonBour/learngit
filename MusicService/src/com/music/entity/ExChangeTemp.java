package com.music.entity;


/**
 * 
 * @author tdy
 *
 */
public class ExChangeTemp {
	private int requestUserId;
	private String requestUserName;
	private int aimUserId;
	private String aimUserName;
	private int requestMusicId;
	private String requestMusicName;
	private int aimMusicId;
	private String aimMusicName;
	public int getRequestUserId() {
		return requestUserId;
	}
	public void setRequestUserId(int requestUserId) {
		this.requestUserId = requestUserId;
	}
	public String getRequestUserName() {
		return requestUserName;
	}
	public void setRequestUserName(String requestUserName) {
		this.requestUserName = requestUserName;
	}
	public int getAimUserId() {
		return aimUserId;
	}
	public void setAimUserId(int aimUserId) {
		this.aimUserId = aimUserId;
	}
	public String getAimUserName() {
		return aimUserName;
	}
	public void setAimUserName(String aimUserName) {
		this.aimUserName = aimUserName;
	}
	public int getRequestMusicId() {
		return requestMusicId;
	}
	public void setRequestMusicId(int requestMusicId) {
		this.requestMusicId = requestMusicId;
	}
	public String getRequestMusicName() {
		return requestMusicName;
	}
	public void setRequestMusicName(String requestMusicName) {
		this.requestMusicName = requestMusicName;
	}
	public int getAimMusicId() {
		return aimMusicId;
	}
	public void setAimMusicId(int aimMusicId) {
		this.aimMusicId = aimMusicId;
	}
	public String getAimMusicName() {
		return aimMusicName;
	}
	public void setAimMusicName(String aimMusicName) {
		this.aimMusicName = aimMusicName;
	}
	@Override
	public String toString() {
		return "ExChangeTemp [requestUserId=" + requestUserId
				+ ", requestUserName=" + requestUserName + ", aimUserId="
				+ aimUserId + ", aimUserName=" + aimUserName
				+ ", requestMusicId=" + requestMusicId + ", requestMusicName="
				+ requestMusicName + ", aimMusicId=" + aimMusicId
				+ ", aimMusicName=" + aimMusicName + "]";
	}
	
}
