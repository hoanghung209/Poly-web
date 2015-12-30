package admin.web.uc;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import admin.web.bc.LanguageBC;
import admin.web.common.HandleCache;
import admin.web.dao.MenuDAO;
import admin.web.util.ContextUtils;
import vg.core.common.StringUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MenuTreeUC implements Serializable {
	private String parentId = ContextUtils.getRequest("parentId", "0");
	private String suffixLanguage = ContextUtils.getSession("suffixLanguage", "_vn");
	
	public String build(String action) {		
		String result = "";

		String key = "MenuDAO.buildTreeFromUC:"+action ;
		Object obj = HandleCache.get(key);
		
		if(obj != null) {
			result = (String) obj; 
		} else {
			List<Map<String, String>> lstMap = MenuDAO.getAll();
			
            result += "<li><span id=\"tree-span-0\""
                       + "class=\"empty\" onclick=\"return OnChangeMenu(this,'-10" 
                       + "','" + LanguageBC.getValue("label.selectAll")                       
                       + "','"                       
                       + "','0" 
                       + "','0" 
                       + "','0"  
                       + "','"
                       + "','"+action
                       + "')\">"
                       + LanguageBC.getValue("label.selectAll")
                       + "</span>"
                       + "</li>";
            
            String root = "-2";            
            for (Map<String, String> map : lstMap) {
				if(map.get("parent").equals(root)) {
                    result += "<li><span id=\"tree-span-" + map.get("id") + "\""
                            + "class=\"empty\" onclick=\"return OnChangeMenu(this,'" + map.get("id")
                            + "','" + map.get(String.format("name%s", suffixLanguage))                           
                            + "','" + map.get("url") 
                            + "','" + map.get("parent")
                            + "','" + map.get("ord")                           
                            + "','" + map.get("position")
                            + "','" + map.get(String.format("slug%s", suffixLanguage))
                            + "','"+action
                            + "')\">";

                       result += map.get(String.format("name%s", suffixLanguage));
                       result += "</span>";
                       result += buildTreeSub(lstMap, map.get("id"),action);
                       result += "</li>";                   
				}				
			}

            if(!StringUtils.isBlank(result)) {
            	HandleCache.put(key, result);
            } 
        }

        return result;
	}	
	
	private String buildTreeSub(List<Map<String, String>> lstMap, String parent,String action) {
		String result = "";
		boolean haveSub = false;

		result += "<ul>";

		for (Map<String, String> map : lstMap) {
			if (map.get("parent").equals(parent)) {
				haveSub = true;
				
                result += "<li><span id=\"tree-span-" + map.get("id") + "\""
                        + "class=\"empty\" onclick=\"return OnChangeMenu(this,'" + map.get("id")
                          + "','" + map.get(String.format("name%s", suffixLanguage))                           
                            + "','" + map.get("url") 
                            + "','" + map.get("parent")
                            + "','" + map.get("ord")                           
                            + "','" + map.get("position")
                            + "','" + map.get(String.format("slug%s", suffixLanguage))
                            + "','"+action
                            + "')\">";
               
				result += map.get(String.format("name%s", suffixLanguage));
				result += "</span>";
				result += buildTreeSub(lstMap, map.get("id"),action);
				result += "</li>";
			}
		}

		if (!haveSub) {
			return "";
		}
		result += "</ul>";
		return result;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}