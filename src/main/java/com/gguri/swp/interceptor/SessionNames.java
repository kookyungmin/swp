package com.gguri.swp.interceptor;

public interface SessionNames {
	final String LOGIN = "loginUser";
	final String LOGIN_COOKIE = "loginCookie";
	final String ATTEMPTED =  "attemptedLocation";
	static final int EXPIRE = 7 * 24 * 60 * 60;
}
