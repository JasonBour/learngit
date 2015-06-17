package com.music.entity;

public class MusicUserAddress {
	private int addressId;
	private double lat = 0;// Î¬¶È
	private double lon = 0;// ¾­¶È

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "MusicUserAddress [addressId=" + addressId + ", lat=" + lat
				+ ", lon=" + lon + "]";
	}

}
