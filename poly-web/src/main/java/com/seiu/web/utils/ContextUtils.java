package com.seiu.web.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ContextUtils {
	public static String getCurrentPath(HttpServletRequest request)
	{
		String query = request.getQueryString();
		if(query!=null&&query!=""){
			query = "?" +query;
		}else{
			query = "";
		}
	    return request.getRequestURL().toString() + query ;
	}
	public static boolean isBlank(String str){
		if(str!=null){
			if(!str.trim().equals("")){
				return false;
			}
		}
		return true;
	}
	public static void systemOutPrint(List<Map<String, String>> lstMap) {
		
			if (lstMap != null) {
				// Tinh do dai max cua moi cot
				Map<String, String> mapSize = new HashMap<String, String>();
				if (lstMap.size() > 0) {
					for (Map.Entry<String, String> entry : lstMap.get(0)
							.entrySet()) {
						mapSize.put(entry.getKey(), entry.getKey().length()
								+ "");
					}
					for (Map<String, String> map : lstMap) {
						for (Map.Entry<String, String> entry : map.entrySet()) {
							if(entry.getValue()==null){
								continue;
							}
							if (entry.getValue().length() > Integer
									.valueOf(mapSize.get(entry.getKey()))) {
								mapSize.put(entry.getKey(), entry.getValue()
										.length() + "");
							}
						}
					}

					// In ra

					for (Map.Entry<String, String> entry : lstMap.get(0)
							.entrySet()) {
						System.out.print(String.format(
								"%" + mapSize.get(entry.getKey()) + "s",
								entry.getKey())
								+ " | ");
					}
					System.out.println();
					for (Map<String, String> map : lstMap) {
						for (Map.Entry<String, String> entry : map.entrySet()) {
							System.out.print(String.format(
									"%" + mapSize.get(entry.getKey()) + "s",
									entry.getValue()) + " | ");
						}
						System.out.println();
					}
				}
			} else {
				System.out.println("List is null.");
			}
		
	}
 	
 	public static void systemOutPrint(Map<String, String> map) {
 		int dodai=0;
			if (map != null) {				
				for (String key : map.keySet()){
					if(dodai<key.length()){
						dodai = key.length();
					}
				}
					
					// In ra					
					
				for (Map.Entry<String, String> entry : map.entrySet()) {
					System.out.println(String.format("%" + dodai + "s:%s",entry.getKey(),entry.getValue()));
				}
			}		
		
	}
	
}
