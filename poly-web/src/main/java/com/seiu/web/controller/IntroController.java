package com.seiu.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.seiu.web.dao.ContentDAO;

import com.seiu.web.utils.ContextUtils;
import com.seiu.web.utils.MenuHelper;

@Controller
public class IntroController {
	 
	@RequestMapping(value = "/intro", method = RequestMethod.GET)
	public String intro(ModelMap model, HttpServletRequest request, HttpServletResponse response) {	
		//{{ left menu		
		String activeId = request.getParameter("act");
		Map<String,String> menuCap = MenuHelper.getById(activeId);			
		if(menuCap==null){
			return "404";
		}else{
			Map<String,String> menuChinh = MenuHelper.getById(menuCap.get("parent"));
			if(menuChinh!=null){				
				menuChinh.put("name", menuChinh.get("name_"+MenuHelper.getLang()));
				menuCap.put("name", menuCap.get("name_"+MenuHelper.getLang()));
				
				model.addAttribute("menuChinh", menuChinh);
				model.addAttribute("menuCap", menuCap);
			}else{
				return "404";
			}
		}
		
		//}}			
					
				
		List<Map<String,String>> lstContent = ContentDAO.getContentByMenu(activeId,"");
		for(Map<String,String> map:lstContent){
			map.put("content", map.get("content_"+MenuHelper.getLang()));
			if("8".equals(map.get("type"))){
				String content = map.get("content_"+MenuHelper.getLang());
				content = content.replace("</p>", ";hmh;");
				content = content.replace("<p>", "");
				content = content.replace("&nbsp;", "");
				String[] arrcontent = content.split(";hmh;");
				String rss = "";
				for(String str : arrcontent){
					if(!ContextUtils.isBlank(str)){
						rss +=";hmh;"+str;
					}
				}
				if(!ContextUtils.isBlank(rss)){
					rss = rss.substring(6);
				}
				
				map.put("content", rss);
			}else if("10".equals(map.get("type"))){
				String width = map.get("width");
				if(ContextUtils.isBlank(width)){
					width = "0";
				}
				int img_w = 0;
				try{
					img_w = Integer.valueOf(width);
				}catch(Exception ex){					
				}
				map.put("width", String.valueOf(img_w));
				map.put("text_width", String.valueOf(12-img_w));
			}
		}
		
		Map<String,String> mapType = ContentDAO.countType(activeId);
		if(mapType!=null){
			model.addAttribute("mapType", mapType);
		}
		model.addAttribute("lstContent", lstContent);
		//get List rieng cho type = 9 
		List<Map<String,String>> lstStep = ContentDAO.getContentByMenu(activeId,"9");
		for(Map<String,String> map:lstStep){
			map.put("content", map.get("content_"+MenuHelper.getLang()));
		}
		
		if(lstStep.size()>0){
			model.addAttribute("showStep", "0");
		}
		model.addAttribute("lstStep", lstStep);
		
		
		List<Map<String,String>> lstCont11 = ContentDAO.getContentByMenu(activeId,"11");
		if(lstCont11.size()>0){
			model.addAttribute("cnt11", 1);
			model.addAttribute("size11", lstCont11.size());
		}
		
		List<Map<String,String>> lstCont12 = ContentDAO.getContentByMenu(activeId,"12");
		if(lstCont12.size()>0){
			model.addAttribute("cnt12", 1);
			model.addAttribute("size12", lstCont12.size());
		}
		return "intro";
	}
	
	
	@RequestMapping(value = "/clear", method = RequestMethod.GET)
	public String clear(ModelMap model, HttpServletRequest request, HttpServletResponse response) {	
		MenuHelper.clear();
		return "";
	}
}
