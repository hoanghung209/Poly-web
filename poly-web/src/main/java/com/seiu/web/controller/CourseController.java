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
public class CourseController {
	
	@RequestMapping(value = "/chuongtrinhhoc", method = RequestMethod.GET)
	public String chuongtrinhhoc(ModelMap model, HttpServletRequest request, HttpServletResponse response) {	
		
		String menuId = "3";		
		String activeId = request.getParameter("act");	
		//{{ left menu
			String rs = MenuHelper.leftMenu(model, menuId, activeId);
			if(!rs.equals("")){
				return rs;
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
		
		List<Map<String,String>> lstStep = ContentDAO.getContentByMenu(activeId,"9");
		for(Map<String,String> map:lstStep){
			map.put("content", map.get("content_"+MenuHelper.getLang()));
		}
		
		if(lstStep.size()>0){
			model.addAttribute("showStep", "0");
		}
		model.addAttribute("lstStep", lstStep);
		return "course";
	}
	
	@RequestMapping(value = "/giaoducsom", method = RequestMethod.GET)
	public String giaoducsom(ModelMap model, HttpServletRequest request, HttpServletResponse response) {	
		String menuId = "3";		
		String activeId = request.getParameter("act");	
		//{{ left menu
			String rs = MenuHelper.leftMenu(model, menuId, activeId);
			if(!rs.equals("")){
				return rs;
			}
		//}}	
		
		String classCss = "lopchoi";
		String color = "blue";
		if(activeId.equals("38")){
			classCss = "lopmam";
			color = "orange";
		}else if(activeId.equals("40")){
			classCss = "lopla";
			color = "green";
		}
		
		model.addAttribute("classCss", classCss);
		model.addAttribute("color", color);
		
		List<Map<String,String>> lstContent = ContentDAO.getContentByMenu(activeId,"1,2,3,5");
		for(Map<String,String> map:lstContent){
			map.put("content", map.get("content_"+MenuHelper.getLang()));
		}
		model.addAttribute("lstContent", lstContent);
		List<Map<String,String>> lstSubject = ContentDAO.getContentByMenu(activeId,"4");
		for(Map<String,String> map:lstSubject){
			map.put("title", map.get("title_"+MenuHelper.getLang()));
			map.put("content", map.get("content_"+MenuHelper.getLang()));
		}
		model.addAttribute("lstSubject", lstSubject);
		return "ecd-lop";
	}
	
}
