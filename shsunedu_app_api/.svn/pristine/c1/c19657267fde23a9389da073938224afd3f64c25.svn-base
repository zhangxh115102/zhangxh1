package com.shsunedu;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "common", locations = "classpath:config/common.properties")
@Component
public class CommonProperties {
	private int token_deadline;
	private String file_path;
	private String fileKey_prefix;
	
	public int getToken_deadline() {
		return token_deadline;
	}

	public void setToken_deadline(int token_deadline) {
		this.token_deadline = token_deadline;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFileKey_prefix() {
		return fileKey_prefix;
	}

	public void setFileKey_prefix(String fileKey_prefix) {
		this.fileKey_prefix = fileKey_prefix;
	}
	
	
}
