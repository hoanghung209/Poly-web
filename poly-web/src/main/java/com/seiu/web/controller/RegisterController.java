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

import com.seiu.web.dao.RegisterDAO;
import com.seiu.web.utils.ContextUtils;

import com.seiu.web.utils.MenuHelper;

@Controller
public class RegisterController
{
  @RequestMapping(value={"/register/{symbolicName:[a-zA-Z0-9-]*}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String register(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    String activeId = "53";
    String rs = MenuHelper.leftMenu(model, "4", activeId);
    if (!rs.equals("")) {
      return rs;
    }
    List<Map<String, String>> lstContent = ContentDAO.getContentByMenu(activeId, "");
    for (Map<String, String> map : lstContent) {
      map.put("content", (String)map.get("content_" + MenuHelper.getLang()));
    }
    Map<String, String> mapType = ContentDAO.countType(activeId);
    if (mapType != null) {
      model.addAttribute("mapType", mapType);
    }
    model.addAttribute("lstContent", lstContent);
    
    List<Map<String, String>> lstStep = ContentDAO.getContentByMenu(activeId, "9");
    for (Map<String, String> map : lstStep)
    {
      map.put("title", (String)map.get("title_" + MenuHelper.getLang()));
      map.put("content", (String)map.get("content_" + MenuHelper.getLang()));
    }
    if (lstStep.size() >= 3) {
      model.addAttribute("lstStep", lstStep);
    }
    return "register";
  }
  
  @RequestMapping(value={"/regtest"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public String regtest(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    String fullname = request.getParameter("fullname");
    String phone = request.getParameter("phone");
    String email = request.getParameter("email");
    String address = request.getParameter("address");
    if (!ContextUtils.isBlank(fullname))
    {
      int rs = RegisterDAO.insert(fullname, phone, email, address);
      if (rs > 0) {
        return "1";
      }
    }
    return "0";
  }
  
  @RequestMapping(value={"/register"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String registercommon(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    return register(model, request, response);
  }
}

