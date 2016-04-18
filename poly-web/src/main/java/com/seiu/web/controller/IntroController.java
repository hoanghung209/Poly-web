package com.seiu.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seiu.web.dao.ContentDAO;
import com.seiu.web.utils.ContextUtils;
import com.seiu.web.utils.MenuHelper;

@Controller
public class IntroController
{
  @RequestMapping(value={"/intro/{symbolicName:[a-zA-Z0-9-]+}-{newsIdHash:[a-zA-Z0-9]+}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String intro(@PathVariable String newsIdHash, ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    String activeId = ContextUtils.deHash(newsIdHash);
    if (ContextUtils.isBlank(activeId)) {
      return "404";
    }
    Map<String, String> menuCap = MenuHelper.getById(activeId);
    if (menuCap == null) {
      return "404";
    }
    Map<String, String> menuChinh = MenuHelper.getById((String)menuCap.get("parent"));
    if (menuChinh != null)
    {
      menuChinh.put("name", (String)menuChinh.get("name_" + MenuHelper.getLang()));
      menuCap.put("name", (String)menuCap.get("name_" + MenuHelper.getLang()));
      
      model.addAttribute("menuChinh", menuChinh);
      model.addAttribute("menuCap", menuCap);
    }
    else
    {
      return "404";
    }
    List<Map<String, String>> lstContent = ContentDAO.getContentByMenu(activeId, "");
    int img_w;
    for (Map<String, String> map : lstContent)
    {
      map.put("content", (String)map.get("content_" + MenuHelper.getLang()));
      if ("8".equals(map.get("type")))
      {
        String content = (String)map.get("content_" + MenuHelper.getLang());
        content = content.replace("</p>", ";hmh;");
        content = content.replace("<p>", "");
        content = content.replace("&nbsp;", "");
        String[] arrcontent = content.split(";hmh;");
        String rss = "";
        for (String str : arrcontent) {
          if (!ContextUtils.isBlank(str)) {
            rss = rss + ";hmh;" + str;
          }
        }
        if (!ContextUtils.isBlank(rss)) {
          rss = rss.substring(6);
        }
        map.put("content", rss);
      }
      else if ("10".equals(map.get("type")))
      {
        String width = (String)map.get("width");
        if (ContextUtils.isBlank(width)) {
          width = "0";
        }
        img_w = 0;
        try
        {
          img_w = Integer.valueOf(width).intValue();
        }
        catch (Exception localException) {}
        map.put("width", String.valueOf(img_w));
        map.put("text_width", String.valueOf(12 - img_w));
      }
    }
    Map<String, String> mapType = ContentDAO.countType(activeId);
    if (mapType != null) {
      model.addAttribute("mapType", mapType);
    }
    model.addAttribute("lstContent", lstContent);
    
    List<Map<String, String>> lstStep = ContentDAO.getContentByMenu(activeId, "9");
    for (Map<String, String> map : lstStep) {
      map.put("content", (String)map.get("content_" + MenuHelper.getLang()));
    }
    if (lstStep.size() > 0) {
      model.addAttribute("showStep", "0");
    }
    model.addAttribute("lstStep", lstStep);
    

    List<Map<String, String>> lstCont11 = ContentDAO.getContentByMenu(activeId, "11");
    if (lstCont11.size() > 0)
    {
      model.addAttribute("cnt11", Integer.valueOf(1));
      model.addAttribute("size11", Integer.valueOf(lstCont11.size()));
    }
    List<Map<String, String>> lstCont12 = ContentDAO.getContentByMenu(activeId, "12");
    if (lstCont12.size() > 0)
    {
      model.addAttribute("cnt12", Integer.valueOf(1));
      model.addAttribute("size12", Integer.valueOf(lstCont12.size()));
    }
    return "intro";
  }
  
  @RequestMapping(value={"/clear"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String clear(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    MenuHelper.clear();
    return "";
  }
}
