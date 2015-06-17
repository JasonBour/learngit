package com.music.entity;

public class MusicEntity {
	private String album;
	private String picture;
	private String ssid;
	private String artist;
	private String url;
	private String company;
	private String title;
	private int length;
	private String subtype;
	private String public_time;
	private String songlists_count;
	private String sid;
	private String aid;
	private String kbps;
	private String albumtitle;

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getPublic_time() {
		return public_time;
	}

	public void setPublic_time(String public_time) {
		this.public_time = public_time;
	}

	public String getSonglists_count() {
		return songlists_count;
	}

	public void setSonglists_count(String songlists_count) {
		this.songlists_count = songlists_count;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getKbps() {
		return kbps;
	}

	public void setKbps(String kbps) {
		this.kbps = kbps;
	}

	public String getAlbumtitle() {
		return albumtitle;
	}

	public void setAlbumtitle(String albumtitle) {
		this.albumtitle = albumtitle;
	}

	@Override
	public String toString() {
		return "MusicEntity [album=" + album + ", picture=" + picture
				+ ", ssid=" + ssid + ", artist=" + artist + ", url=" + url
				+ ", company=" + company + ", title=" + title + ", length="
				+ length + ", subtype=" + subtype + ", public_time="
				+ public_time + ", songlists_count=" + songlists_count
				+ ", sid=" + sid + ", aid=" + aid + ", kbps=" + kbps
				+ ", albumtitle=" + albumtitle + "]";
	}

}
