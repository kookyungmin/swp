package com.gguri.swp.util;

import java.text.DecimalFormat;

public class StringUtils {
	//두 자리 수로 만들어 주는 메소드
	public static String len2(int n) {
		return new DecimalFormat("00").format(n).toString();
	}
}
