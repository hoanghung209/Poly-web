package com.seiu.web.utils;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class StringHelper {

	public static String getSlug(String input, String id) {
		String str = StringUtils.stripAccents(input);
		str = str.replaceAll("\\s+", "-");
		str += ("_" + id);
		str = str.replaceAll("�?", "D");
		str = str.replaceAll("đ", "d");
		return str;
	}

	public static String convertToSlug(String input) {
		String str = StringUtils.stripAccents(input);
		str = str.toLowerCase();
		str = str.replaceAll("-", "");
		str = str.replaceAll("\\s+", "-");
		str = str.replaceAll("�?", "D");
		str = str.replaceAll("đ", "d");
		return str;
	}	
	
	public static boolean isBlank(String str){
		if(str!=null&&!str.trim().isEmpty()){
			return false;
		}
		return true;
	}
	public static List<String> getmenu(){
		List<String> cat = new ArrayList<String>();
		cat.add("Ngo");
		cat.add("Chuoi");
		cat.add("Tao");
		cat.add("Na");
		return cat;
	}
}
