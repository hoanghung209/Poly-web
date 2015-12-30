package com.seiu.web.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.seiu.web.utils.MenuHelper;
@Controller
public class LanguageController {
	@RequestMapping(value = "/useEnglish", method = RequestMethod.GET)
	@ResponseBody
	public String useEnglish(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		 LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
	     localeResolver.setLocale(request, response, StringUtils.parseLocaleString("en"));
		return "";
	}
	@RequestMapping(value = "/useVietnamese", method = RequestMethod.GET)
	@ResponseBody
	public String useVietnamese(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		 LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
	     localeResolver.setLocale(request, response, StringUtils.parseLocaleString("vn"));
		return "";
	}
	@RequestMapping(value = "/changeLanguage", method = RequestMethod.POST)
	@ResponseBody
	public String changeLanguage(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		 System.out.println("Change lang");
		 try{
		 String lang = request.getParameter("lang");
		 if(lang==null||lang==""){
			 lang = "vn";
		 }
		 LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
	     localeResolver.setLocale(request, response, StringUtils.parseLocaleString(lang));
	     MenuHelper.setLang(lang); 
	     return "0";
		 }catch(Exception ex){
			 ex.printStackTrace();
			 return "1";
		 }		
	}
}
