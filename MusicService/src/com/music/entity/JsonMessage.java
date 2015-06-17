package com.music.entity;

import com.google.gson.annotations.SerializedName;

public class JsonMessage {
	@SerializedName("status")
	private String status;
	@SerializedName("content")
	private String content;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "JsonMessage [status=" + status + ", content=" + content + "]";
	}
}
