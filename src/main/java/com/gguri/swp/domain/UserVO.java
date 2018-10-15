package com.gguri.swp.domain;

import java.util.Date;

import lombok.Data;

@Data
public class UserVO {
	private String uid;
	private String upw;
	private String uname;
	private String upoint;
	private String email;
	private String naverId;
	private String googleId;
	private String nickname;
	private String loginip;
	private Date lastlogin;
}
