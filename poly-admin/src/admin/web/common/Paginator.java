package admin.web.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Paginator implements Serializable {
	private static final long serialVersionUID = 7462648039377304388L;
	public static int DEFAULT_BAR_SIZE = 4;
	public static int DEFAULT_PAGE_SIZE = 20;

	public static List<Map<String, String>> getPageBar(int currentPage, int recordTotal, String url) {
		return getPageBar(currentPage, recordTotal, url, DEFAULT_PAGE_SIZE, DEFAULT_BAR_SIZE);
	}	
	public static List<Map<String, String>> getPageBar(int currentPage, int recordTotal, String url, int pageSize) {
		return getPageBar(currentPage, recordTotal, url, pageSize, DEFAULT_BAR_SIZE);
	}
	public static List<Map<String, String>> getPageBar(int currentPage, int recordTotal, String url, int pageSize, int barSize) {
		List<Map<String, String>> lstMap = new ArrayList<Map<String,String>>();
		
        int pageTotal = (int)Math.ceil((double)recordTotal / pageSize);
        if (pageTotal > 1)
        {
            boolean existParam = url.indexOf("?") > -1 ? true : false;
            String newUrl = "";
            
            if (existParam) {
                newUrl = url + "&Page=";
            } else {
                newUrl = url + "?Page=";
            }
            
            int minPage = currentPage - barSize/2;
            int maxPage = currentPage + barSize/2;
            minPage = minPage < 1 ? 1 : minPage;
            if (minPage == 1) {
                maxPage = minPage + barSize < pageTotal ? minPage + barSize : pageTotal;
            }
            maxPage = maxPage > pageTotal ? pageTotal : maxPage;
            if (maxPage == pageTotal) {
                minPage = maxPage - barSize > 1 ? maxPage - barSize : 1;
            }
                        
            if (minPage > 1)
            {
            	Map<String, String> firstMap = new HashMap<String, String>();
            	firstMap.put("class", "");
            	firstMap.put("href", newUrl + "1");
            	firstMap.put("text", "|<");
            	firstMap.put("page", "1");
            	lstMap.add(firstMap); //first page     
            	
            	Map<String, String> preMap = new HashMap<String, String>();
            	preMap.put("class", "");
            	preMap.put("href", newUrl + String.valueOf(currentPage - 1));
            	preMap.put("text", "<");    
            	preMap.put("page", String.valueOf(currentPage - 1));
            	lstMap.add(preMap); //previous page                  
            }
            
            for (int i = minPage; i <= maxPage; i++) {
            	Map<String, String> map = new HashMap<String, String>();            	
                if (i == currentPage) {
                	map.put("class", "current");
                	map.put("href", "");
                	map.put("text", String.valueOf(i));    
                	map.put("page", String.valueOf(i));    
                } else {
                	map.put("class", "");
                	map.put("href", newUrl + String.valueOf(i));
                	map.put("text", String.valueOf(i));
                	map.put("page", String.valueOf(i));     
                }
                lstMap.add(map); //number page
            }

            if (maxPage < pageTotal)
            {
            	Map<String, String> nextMap = new HashMap<String, String>();
            	nextMap.put("class", "");
            	nextMap.put("href", newUrl + String.valueOf(currentPage + 1));
            	nextMap.put("text", ">");
            	nextMap.put("page", String.valueOf(currentPage + 1));
            	lstMap.add(nextMap); //next page
            	
            	Map<String, String> lastMap = new HashMap<String, String>();
            	lastMap.put("class", "");
            	lastMap.put("href", newUrl + String.valueOf(pageTotal));
            	lastMap.put("text", ">|");
            	lastMap.put("page", String.valueOf(pageTotal));
            	lstMap.add(lastMap); //last page               	            	           
            }
        }
        return lstMap;
	}	   
}
