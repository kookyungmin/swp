package com.gguri.swp;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

public class ApacheCommonTest {
	private String[][] person = new String[][] {
		{"chan", "faithful"},
		{"sanga", "pretty" },
		{"jungsu", "good"},
		{"koo", "smart"}
	};
	private Date today = new Date();
	private int[] nums = new int[] {2, 3, 7, 6 ,8 };
	
	@Test
	public void testArrayUtils() {
		System.out.println(Arrays.toString(person));
		System.out.println(ArrayUtils.toString(person));
		
		Map<Object, Object> map = ArrayUtils.toMap(person);
		System.out.println("Chan is " + map.get("chan"));
		
		Arrays.sort(nums);
		System.out.println("Sort: " + ArrayUtils.toString(nums));
		ArrayUtils.reverse(nums);
		System.out.println("Reverse: " + ArrayUtils.toString(nums));
		
		System.out.println("contain 7 :" + ArrayUtils.contains(nums, 7));
	}
	
	@Test
	public void testDateUtils() {
		Date yesterday = DateUtils.addDays(today, -1);
		Date tommorow = DateUtils.addDays(today, 1);
		Date nextYear = DateUtils.addYears(today, 1);
		
		System.out.println("y=" + yesterday);
		System.out.println("t=" + tommorow);
		System.out.println("ny=" + nextYear);
	}
	
	@Test
	public void testNumberUtils() {
		int min = NumberUtils.min(nums);
		int max = NumberUtils.max(nums);
		System.out.println("min=" + min + " max=" + max);
		String nstr = "1234";
		if(StringUtils.isNumeric(nstr))
			System.out.println("createNumber: " + NumberUtils.createNumber(nstr));
		System.out.println("null:" + NumberUtils.toInt(null,0));
	}
	
	@Test
	public void testRandomStringUtils() {
		System.out.println(RandomStringUtils.randomAlphanumeric(5));
		System.out.println(RandomStringUtils.randomAlphabetic(5));
		System.out.println(RandomStringUtils.randomNumeric(5));
	}
	@Test
	public void testStringUtils() {
		String s = null;
		String[] ss = StringUtils.isNotEmpty(s) ? s.split(",") : null;
		String[] ss2 = StringUtils.split(",");
		
		String s2 = "aababbdbrggjgjlkahjkvlhkjgheiugavdnbjkeuhiarjwrkj";
		
		System.out.println(StringUtils.countMatches(s2, 'a'));
	}
}
