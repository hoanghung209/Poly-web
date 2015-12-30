package com.seiu.web.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String convertDateTime(String dateStr,String newDateformat){
		SimpleDateFormat fm= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date;
		try {
			date = fm.parse(dateStr);
			SimpleDateFormat nfm= new SimpleDateFormat(newDateformat);
			return nfm.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return null;
		
	}
}
