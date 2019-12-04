package com.mm.hh;

import java.util.Calendar;
import java.util.Date;

public class Test {
	public static void main(String[] args) throws Exception {
		
		Calendar cal = Calendar.getInstance(java.util.Locale.CHINA);
		Date d = cal.getTime();
		System.out.println(d);
	}
}



