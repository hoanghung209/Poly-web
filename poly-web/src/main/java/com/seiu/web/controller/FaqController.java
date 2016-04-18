package com.seiu.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seiu.web.dao.ContentDAO;
import com.seiu.web.dao.FaqDAO;
import com.seiu.web.utils.MenuHelper;

@Controller
public class FaqController
{
  @RequestMapping(value={"/faq"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String faq(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    String activeId = "51";
    MenuHelper.leftMenu(model, "4", activeId);
    


    List<Map<String, String>> lstContent = ContentDAO.getContentByMenu(activeId, "");
    for (Map<String, String> map : lstContent) {
      map.put("content", (String)map.get("content_" + MenuHelper.getLang()));
    }
    List<Map<String, String>> lstFaq = FaqDAO.getList(MenuHelper.getLang());
    if (lstFaq.size() > 0) {
      model.addAttribute("firstFaqId", (lstFaq.get(0)).get("id"));
    }
    model.addAttribute("lstContent", lstContent);
    model.addAttribute("lstFaq", lstFaq);
    return "faq";
  }
  
  @RequestMapping(value={"/faq/{symbolicName:[a-zA-Z0-9-]+}-{newsIdHash:[a-zA-Z0-9]+}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String faqpersonal(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    return faq(model, request, response);
  }
}
