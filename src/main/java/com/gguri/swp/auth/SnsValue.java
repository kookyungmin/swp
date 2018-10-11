package com.gguri.swp.auth;

import org.apache.commons.lang3.StringUtils;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.api.DefaultApi20;

import lombok.Data;

@Data
public class SnsValue implements SnsUrls{
	private String service;
	private String clientId;
	private String clientSecret;
	private String redirectUrl;
	private DefaultApi20 api20Instance;
	private String profileUrl;
	private boolean isNaver;
	private boolean isGoogle;
	
	public SnsValue(String service, String clientId, String clientSecret, String redirectUrl) {
		this.service = service;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUrl = redirectUrl;
		isNaver = StringUtils.equalsIgnoreCase("naver", this.service);
		isGoogle = StringUtils.equalsIgnoreCase("google", this.service);
		if(isNaver) {
			this.api20Instance = NaverAPI20.Instance();
			this.profileUrl = NAVER_PROFILE_URL;
		}else if(isGoogle) {
			this.api20Instance = GoogleApi20.instance();
			this.profileUrl = GOOGLE_PROFILE_URL;
		}
	}

	public boolean getIsGoogle() {
		return this.isGoogle;
	}

	public boolean getIsNaver() {
		return this.isNaver;
	}
}
