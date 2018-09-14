package com.gguri.swp.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
	private int id;
	private String name;
	
	public static void main(String[] args) {
		User u1 = new User(1234, "hong");
		System.out.println(u1);
		
		User u2 = User.builder().id(123).name("hong2").build();
		System.out.println(u2);
	}
}
