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
import com.seiu.web.dao.CourseDAO;
import com.seiu.web.dao.SchedulerDAO;
import com.seiu.web.utils.MenuHelper;

@Controller
public class SchedulerController {
	 
public String leftMenu(ModelMap model,String menuId,String activeId){
		
		Map<String,String> menuChinh = MenuHelper.getById(menuId);	
		Map<String,String> menuLop = MenuHelper.getById(activeId);	
		if(menuLop==null||menuChinh==null){
			return "404";
		}else{
			
			Map<String,String> menuCap = MenuHelper.getById(menuLop.get("parent"));
			Map<String,String> menuChuongtrinh = MenuHelper.getById(menuCap.get("parent"));			
			
			menuChinh.put("name",menuChinh.get("name_"+MenuHelper.getLang()));
			menuChuongtrinh.put("name",menuChuongtrinh.get("name_"+MenuHelper.getLang()));
			menuCap.put("name",menuCap.get("name_"+MenuHelper.getLang()));
			menuLop.put("name",menuLop.get("name_"+MenuHelper.getLang()));
			
			
			model.addAttribute("menuChinh", menuChinh);
			model.addAttribute("menuChuongtrinh", menuChuongtrinh);
			model.addAttribute("menuCap", menuCap);
			model.addAttribute("menuLop", menuLop);			
		}
		return "";
	}
	@RequestMapping(value = "/scheduler", method = RequestMethod.GET)
	public String intro(ModelMap model, HttpServletRequest request, HttpServletResponse response) {	
		//{{ left menu		
		String activeId = request.getParameter("act");
		leftMenu(model, "4", activeId);
		
		//}}		
		//get content
		List<Map<String,String>> lstContent = ContentDAO.getContentByMenu(activeId,"");
		for(Map<String,String> map:lstContent){
			map.put("content", map.get("content_"+MenuHelper.getLang()));
		}
					
		//get course		
		List<Map<String,String>> lstCourse = CourseDAO.getList(activeId); 		
		for(Map<String,String> map: lstCourse){
			map.put("name", map.get("name_"+MenuHelper.getLang()));
			map.put("content", map.get("content_"+MenuHelper.getLang()));
		}
		if(lstCourse.size()>0){
			model.addAttribute("firstCourseId", lstCourse.get(0).get("id"));
		}
		//get header
		List<Map<String,String>> lstHeader = SchedulerDAO.getHeader();
		for(Map<String,String> map: lstHeader){			
			map.put("content", map.get("content_"+MenuHelper.getLang()));
		}
		
		//get scheduler
		List<Map<String,String>> lstScheduler = SchedulerDAO.getScheduler();
		for(Map<String,String> map: lstScheduler){			
			map.put("content", map.get("content_"+MenuHelper.getLang()));
		}
		
		
		model.addAttribute("lstContent", lstContent);
		model.addAttribute("lstCourse", lstCourse);
		model.addAttribute("lstHeader", lstHeader);
		model.addAttribute("countCol", lstHeader.size());
		model.addAttribute("lstScheduler", lstScheduler);
		return "scheduler";
	}
	
}
