package admin.web.uc;

import admin.web.bc.LanguageBC;
import admin.web.common.HandleCache;
import admin.web.dao.MenuDAO;
import admin.web.util.ContextUtils;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import vg.core.common.StringUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class MenuTreeUC
  implements Serializable
{
  private String parentId = ContextUtils.getRequest("parentId", "0");
  private String suffixLanguage = ContextUtils.getSession("suffixLanguage", "_vn");
  
  public String build(String action)
  {
    String result = "";
    
    String key = "MenuDAO.buildTreeFromUC:" + action;
    Object obj = HandleCache.get(key);
    if (obj != null)
    {
      result = (String)obj;
    }
    else
    {
      List<Map<String, String>> lstMap = MenuDAO.getAll();
      
      result = result + "<li><span id=\"tree-span-0\"class=\"empty\" onclick=\"return OnChangeMenu(this,'-10','" + 
      
        LanguageBC.getValue("label.selectAll") + 
        "','" + 
        "','0" + 
        "','0" + 
        "','0" + 
        "','" + 
        "','" + action + 
        "')\">" + 
        LanguageBC.getValue("label.selectAll") + 
        "</span>" + 
        "</li>";
      
      String root = "-2";
      for (Map<String, String> map : lstMap) {
        if (((String)map.get("parent")).equals(root))
        {
          result = 
          







            result + "<li><span id=\"tree-span-" + (String)map.get("id") + "\"" + "class=\"empty\" onclick=\"return OnChangeMenu(this,'" + (String)map.get("id") + "','" + (String)map.get(String.format("name%s", new Object[] { this.suffixLanguage })) + "','" + (String)map.get("url") + "','" + (String)map.get("parent") + "','" + (String)map.get("ord") + "','" + (String)map.get("position") + "','" + (String)map.get(String.format("slug%s", new Object[] { this.suffixLanguage })) + "','" + action + "')\">";
          
          result = result + (String)map.get(String.format("name%s", new Object[] { this.suffixLanguage }));
          result = result + "</span>";
          result = result + buildTreeSub(lstMap, (String)map.get("id"), action);
          result = result + "</li>";
        }
      }
      if (!StringUtils.isBlank(result)) {
        HandleCache.put(key, result);
      }
    }
    return result;
  }
  
  private String buildTreeSub(List<Map<String, String>> lstMap, String parent, String action)
  {
    String result = "";
    boolean haveSub = false;
    
    result = result + "<ul>";
    for (Map<String, String> map : lstMap) {
      if (((String)map.get("parent")).equals(parent))
      {
        haveSub = true;
        
        result = result + "<li><span id=\"tree-span-" + (String)map.get("id") + "\"" + 
          "class=\"empty\" onclick=\"return OnChangeMenu(this,'" + (String)map.get("id") + 
          "','" + (String)map.get(String.format("name%s", new Object[] { this.suffixLanguage })) + 
          "','" + (String)map.get("url") + 
          "','" + (String)map.get("parent") + 
          "','" + (String)map.get("ord") + 
          "','" + (String)map.get("position") + 
          "','" + (String)map.get(String.format("slug%s", new Object[] { this.suffixLanguage })) + 
          "','" + action + 
          "')\">";
        
        result = result + (String)map.get(String.format("name%s", new Object[] { this.suffixLanguage }));
        result = result + "</span>";
        result = result + buildTreeSub(lstMap, (String)map.get("id"), action);
        result = result + "</li>";
      }
    }
    if (!haveSub) {
      return "";
    }
    result = result + "</ul>";
    return result;
  }
  
  public String getParentId()
  {
    return this.parentId;
  }
  
  public void setParentId(String parentId)
  {
    this.parentId = parentId;
  }
}
