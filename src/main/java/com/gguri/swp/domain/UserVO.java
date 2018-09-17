package com.gguri.swp.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class UserVO {
	private String uid;
	private String upw;
	private String uname;
	private String upoint;
}
