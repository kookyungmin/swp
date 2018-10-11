package com.gguri.swp.auth;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class NaverAPI20 extends DefaultApi20 implements SnsUrls{
	private NaverAPI20() {		
	}
	
	private static class InstanceHolder{
		private static final NaverAPI20 INSTANCE = new NaverAPI20();
	}
	
	public static NaverAPI20 Instance() {
		return InstanceHolder.INSTANCE;
	}

	//네이버 endpoint (NAVER_ACCESS_TOKEN)
	@Override
	public String getAccessTokenEndpoint() {
		return NAVER_ACCESS_TOKEN;
	}
	//(NAVER_AUTH)
	@Override
	protected String getAuthorizationBaseUrl() {
		return NAVER_AUTH;
	}
}
