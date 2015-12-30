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
import com.seiu.web.dao.FeedBackDAO;
import com.seiu.web.utils.ContextUtils;
import com.seiu.web.utils.MenuHelper;

@Controller
public class ContactController {
	 
	@RequestMapping(value = "/lienhe", method = RequestMethod.GET)
	public String intro(ModelMap model, HttpServletRequest request, HttpServletResponse response) {	
		//{{ left menu
		String menuId = "6";		
		Map<String,String> menuChinh = MenuHelper.getById(menuId);
		if(menuChinh!=null){
			menuChinh.put("name", menuChinh.get("name_"+MenuHelper.getLang()));
			
			model.addAttribute("menuChinh", menuChinh);					
		}else{
			return "404";
		}
		
		
		//}}			
					
				
		List<Map<String,String>> lstContent = ContentDAO.getContentByMenu(menuId,"");
		
		for(Map<String,String> map: lstContent){
			map.put("content", map.get("content_"+MenuHelper.getLang()));
		}
		model.addAttribute("lstContent", lstContent);
		
		
		String txtName = request.getParameter("txtName");
		String txtPhone = request.getParameter("txtPhone");
		String txtMess = request.getParameter("txtMes");
		
		if(!(ContextUtils.isBlank(txtName)&&ContextUtils.isBlank(txtMess)&&ContextUtils.isBlank(txtPhone))){
			FeedBackDAO.insert(txtName, txtPhone, txtMess); 
			model.addAttribute("messs", "Feedback success!");
		}
		return "contact";
	}	
}
