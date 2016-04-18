package com.seiu.web.controller;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.seiu.web.common.MailThread;
import com.seiu.web.dao.AdsDAO;
import com.seiu.web.dao.AlbumDAO;
import com.seiu.web.dao.ContentDAO;
import com.seiu.web.dao.CustomerDAO;
import com.seiu.web.dao.FeedBackDAO;
import com.seiu.web.dao.NewsDAO;
import com.seiu.web.dao.TemplateDAO;
import com.seiu.web.utils.ContextUtils;
import com.seiu.web.utils.Hashids;
import com.seiu.web.utils.MenuHelper;

@Controller
public class HomeController
{
  @RequestMapping(value={"/sitemap"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView sitemap(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView("sitemap");
    return mv;
  }
  
  @RequestMapping(value={"/robots.txt"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  @ResponseBody
  public ModelAndView robos(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView("robo");
    return mv;
  }
  
  @RequestMapping(value={"/"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    ModelAndView mv = new ModelAndView("index");
    Hashids hash = new Hashids();
    List<Map<String, String>> lstTxt = ContentDAO.getContentByMenu("10", "2");
    if (lstTxt.size() > 0) {
      mv.addObject("caption", (lstTxt.get(0)).get("content_" + MenuHelper.getLang()));
    }
    List<Map<String, String>> lstIcon = ContentDAO.getContentByMenu("10", "12");
    for (Map<String, String> map : lstIcon) {
      map.put("content", map.get("content_" + MenuHelper.getLang()));
    }
    mv.addObject("lstIcon", lstIcon);
    
    List<Map<String, String>> lst13 = ContentDAO.getContentByMenu("10", "13");
    for (Map<String, String> map : lst13) {
      map.put("content", map.get("content_" + MenuHelper.getLang()));
    }
    mv.addObject("lst13", lst13);
    

    List<Map<String, String>> lstImage = AlbumDAO.getList("0");
    for (Map<String, String> map : lstImage)
    {
      map.put("title", map.get("title_" + MenuHelper.getLang()));
      map.put("slug", ContextUtils.syncSlug(map.get("slug_" + MenuHelper.getLang())));
      map.put("hashid", hash.encodeHex(map.get("id")));
    }
    List<Map<String, String>> lstVideo = AlbumDAO.getList("1");
    for (Map<String, String> map : lstVideo)
    {
     map.put("slug", ContextUtils.syncSlug(map.get("slug_" + MenuHelper.getLang())));
     map.put("hashid", hash.encodeHex(map.get("id")));
    }
    mv.addObject("lstImage", lstImage);
    mv.addObject("lstVideo", lstVideo);
    
    List<Map<String, String>> lstFeedback = FeedBackDAO.getList();
    for (Map<String, String> map : lstFeedback) {
      if (ContextUtils.isBlank(map.get("avatar"))) {
        map.put("avatart", "./resources/core/images/sider.png");
      }
    }
    mv.addObject("lstFeedback", lstFeedback);
    
    List<Map<String, String>> lstBanner = AdsDAO.getList("0");
    for (Map<String, String> map : lstBanner)
    {
      map.put("more", map.get("more_" + MenuHelper.getLang()));
      map.put("desc", map.get("desc_" + MenuHelper.getLang()));
    }
    mv.addObject("lstBanner", lstBanner);
    
    return mv;
  }
  
  @RequestMapping(value={"/home/{symbolicName:[a-zA-Z0-9-]*}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public ModelAndView home(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return handleRequest(request, response);
  }
  
  @RequestMapping(value={"/hello"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String intro(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    String ip = "localhost";
    try
    {
      ip = Inet4Address.getLocalHost().getHostAddress();
    }
    catch (UnknownHostException e)
    {
      e.printStackTrace();
    }
    if (MenuHelper.getLang().equals("vn")) {
      model.addAttribute("msg", "Xin chào bạn, tôi đến từ " + ip);
    } else {
      model.addAttribute("msg", "Welcome " + ip);
    }
    return "hello";
  }
  
  @RequestMapping(value={"/search"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String search(@RequestParam String s, ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    try
    {
      request.setCharacterEncoding("UTF-8");
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    System.out.println(request.getParameter("s"));
    String lang = MenuHelper.getLang();
    String resultTitle = "Kết quả";
    if (lang.equals("en")) {
      resultTitle = "Result";
    }
    List<Map<String, String>> lstResult = new ArrayList<Map<String, String>>();
    List<Map<String, String>> lstContent = ContentDAO.search(s, lang);
    List<String> lstMenu = new ArrayList<String>();
    for (Map<String, String> map : lstContent) {
      if (!lstMenu.contains(map.get("menu_id"))) {
        lstMenu.add(map.get("menu_id"));
      }
    }
    Map<String, String> mapContent;
    for (String menu_id : lstMenu) {
      if ((!menu_id.equals("6")) && (!menu_id.equals("10")))
      {
        mapContent = getContent(menu_id);
        lstResult.add(mapContent);
      }
    }
    List<Map<String, String>> lstNews = NewsDAO.search(s, lang);
    for (Map<String, String> map : lstNews) {
      map.put("href", "news?id=" + map.get("id"));
    }
    lstResult.addAll(lstNews);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();
    for (Map<String, String> map : lstResult)
    {
      Date date = new Date();
      try
      {
        date = sdf.parse(map.get("updated_time"));
      }
      catch (Exception localException) {}
      cal.setTime(date);
      map.put("day", String.valueOf(cal.get(5)));
      map.put("month", ContextUtils.getMonth(cal.get(2) + 1));
      String content = map.get("content_" + lang);
      content = ContextUtils.removeHtml(content, 200);
      if (content.length() > 200) {
        content = content.substring(0, 200) + "..";
      }
      map.put("content", content);
      map.put("title", map.get("title_" + lang));
    }
    Map<String, String> menuChinh = new HashMap<String, String>();
    menuChinh.put("name", "Tìm kiếm");
    model.addAttribute("menuChinh", menuChinh);
    model.addAttribute("resultTitle", resultTitle);
    model.addAttribute("lstResult", lstResult);
    return "search";
  }
  
  @RequestMapping(value={"/registerCus"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public String registerCus(@RequestParam String txtEmail, @RequestParam String txtPassword, @RequestParam String txtName, @RequestParam String txtPhone, ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    if ((!ContextUtils.isBlank(txtEmail)) && (!ContextUtils.isBlank(txtPassword))) {
      if (!CustomerDAO.checkEmail(txtEmail))
      {
        int rs = CustomerDAO.insert(txtEmail, txtPassword, txtName, txtPhone);
        if (rs > 0)
        {
          String subject = TemplateDAO.getTemplate("email_reg_success_subject", MenuHelper.getLang());
          String content = TemplateDAO.getTemplate("email_reg_success_content", MenuHelper.getLang());
          new MailThread(txtEmail, subject, content, "").start();
          return "1";
        }
      }
      else
      {
        return "2";
      }
    }
    return "0";
  }
  
  @RequestMapping(value={"/checkEmail"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public String checkMail(@RequestParam String txtEmail, ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    if (CustomerDAO.checkEmail(txtEmail)) {
      return "1";
    }
    return "0";
  }
  
  @RequestMapping(value={"/login"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public String login(@RequestParam String txtEmail, @RequestParam String txtPassword, ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    if (CustomerDAO.login(txtEmail, txtPassword))
    {
      HttpSession session = request.getSession();
      session.setAttribute("email", txtEmail);
      return "1";
    }
    return "0";
  }
  
  @RequestMapping(value={"/logout"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public String logout(ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    HttpSession session = request.getSession();
    session.invalidate();
    return "";
  }
  
  @RequestMapping(value={"/forgot"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public String forgot(@RequestParam String txtEmail, ModelMap model, HttpServletRequest request, HttpServletResponse response)
  {
    if (CustomerDAO.checkEmail(txtEmail))
    {
      String newpass = "";
      Random r = new Random();
      for (int i = 0; i < 8; i++) {
        newpass = newpass + String.valueOf(r.nextInt(10));
      }
      System.out.println(newpass);
      String subject = TemplateDAO.getTemplate("email_reset_subject", MenuHelper.getLang());
      String content = TemplateDAO.getTemplate("email_reset_content", MenuHelper.getLang());
      new MailThread(txtEmail, subject, content.replace("{PASSWORD}", newpass), newpass).start();
      return "1";
    }
    return "0";
  }
  
  private Map<String, String> getContent(String menu_id)
  {
    List<Map<String, String>> lstContent = ContentDAO.getContentByMenu(menu_id, "");
    String content_vn = "";
    String content_en = "";
    for (Map<String, String> map : lstContent) {
      if (!"5".equals(map.get("type")))
      {
        content_vn = content_vn + map.get("content_vn");
        content_en = content_en + map.get("content_en");
      }
    }
    Map<String, String> mapMenu = MenuHelper.getById(menu_id);
    Map<String, String> result = new HashMap<String, String>();
    result.put("href", mapMenu.get("url"));
    result.put("title_vn", mapMenu.get("name_vn"));
    result.put("title_en", mapMenu.get("name_en"));
    result.put("content_vn", content_vn);
    result.put("content_en", content_en);
    return result;
  }
}
