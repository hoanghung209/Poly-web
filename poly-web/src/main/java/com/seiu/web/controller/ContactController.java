package com.seiu.web.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seiu.web.dao.ContentDAO;
import com.seiu.web.dao.FeedBackDAO;
import com.seiu.web.utils.ContextUtils;
import com.seiu.web.utils.MenuHelper;

@Controller
public class ContactController
{
  @RequestMapping(value={"/lienhe"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String lienhe(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    String menuId = "6";
    Map<String, String> menuChinh = MenuHelper.getById(menuId);
    if (menuChinh != null)
    {
      menuChinh.put("name", (String)menuChinh.get("name_" + MenuHelper.getLang()));
      
      model.addAttribute("menuChinh", menuChinh);
    }
    else
    {
      return "404";
    }
    List<Map<String, String>> lstContent = ContentDAO.getContentByMenu(menuId, "");
    for (Map<String, String> map : lstContent) {
      map.put("content", (String)map.get("content_" + MenuHelper.getLang()));
    }
    model.addAttribute("lstContent", lstContent);
    
    return "contact";
  }
  
  @RequestMapping(value={"/feedback"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public String feedback(ModelMap model, HttpServletRequest request)
  {
    String txtName = request.getParameter("txtName");
    String txtPhone = request.getParameter("txtPhone");
    String txtMess = request.getParameter("txtMes");
    if ((!ContextUtils.isBlank(txtName)) || (!ContextUtils.isBlank(txtMess)) || (!ContextUtils.isBlank(txtPhone)))
    {
      int rs = FeedBackDAO.insert(txtName, txtPhone, txtMess);
      if (rs > 0) {
        return "1";
      }
    }
    return "0";
  }
  
  @RequestMapping(value={"/lienhe/{symbolicName:[a-zA-Z0-9-]*}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String lienhepersonal(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    return lienhe(model, request, response);
  }
}
