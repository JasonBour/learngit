package com.music.entity;

import com.google.gson.Gson;

public class MusicUser {
	private int id;
	private String username;
	private String password;
	private String level;
	private int collectionNums = 5;// 最大收藏数量
	private int collectionDays = 3;// 最大收藏天数
	private MusicUserAddress musicUserAddress = new MusicUserAddress();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getCollectionNums() {
		return collectionNums;
	}

	public void setCollectionNums(int collectionNums) {
		this.collectionNums = collectionNums;
	}

	public int getCollectionDays() {
		return collectionDays;
	}

	public void setCollectionDays(int collectionDays) {
		this.collectionDays = collectionDays;
	}

	public MusicUserAddress getMusicUserAddress() {
		return musicUserAddress;
	}

	public void setMusicUserAddress(MusicUserAddress musicUserAddress) {
		this.musicUserAddress = musicUserAddress;
	}

	@Override
	public String toString() {
		return "MusicUser [id=" + id + ", username=" + username + ", password="
				+ password + ", level=" + level + ", collectionNums="
				+ collectionNums + ", collectionDays=" + collectionDays
				+ ", musicUserAddress=" + musicUserAddress + "]";
	}

}
