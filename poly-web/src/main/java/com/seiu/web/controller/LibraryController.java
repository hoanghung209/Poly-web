package com.seiu.web.controller;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.seiu.web.dao.AlbumDAO;
import com.seiu.web.dao.NewsDAO;
import com.seiu.web.utils.MenuHelper;

@Controller
public class LibraryController {
	@RequestMapping(value = "/thuvien", method = RequestMethod.GET)
	public String library(ModelMap model,HttpServletRequest request, HttpServletResponse response)
	        throws Exception {				
		MenuHelper.leftMenu(model, "5", "57");
		
		List<Map<String,String>> lstImage = AlbumDAO.getList("0");
		for(Map<String,String> map:lstImage){
			map.put("title", map.get("title_"+MenuHelper.getLang())); 
		}
		List<Map<String,String>> lstVideo = AlbumDAO.getList("1");
		for(Map<String,String> map:lstVideo){
			map.put("title", map.get("title_"+MenuHelper.getLang())); 
		}
		model.addAttribute("lstImage", lstImage);
		model.addAttribute("lstVideo", lstVideo);
		return "thuvien";
		
	}
	@RequestMapping(value = "/image", method = RequestMethod.GET)
	public String image(ModelMap model,HttpServletRequest request, HttpServletResponse response)
	        throws Exception {				
		Map<String,String> menuChinh = MenuHelper.getById("5");		
		Map<String,String> menuCap = MenuHelper.getById("59");
		
		String id = request.getParameter("id");
		List<Map<String,String>> lstAlbum = AlbumDAO.getListNolimit("");
		for(Map<String,String> album :lstAlbum){
			album.put("title", album.get("title_"+MenuHelper.getLang()));
		}
		if(id==null||"".equals(id)){			
			for(Map<String,String> album : lstAlbum){
				if("0".equals(album.get("is_video"))){
					id = album.get("id");
					break;
				}
			}
		}
		Map<String,String> map = AlbumDAO.getAlbumById(id);
		if(map==null){
			return "404";
		}
		
		map.put("title", map.get("title_"+MenuHelper.getLang()));
		List<Map<String,String>> lstImage = AlbumDAO.getFile("0",id);
		
		model.addAttribute("cat", "57");
		model.addAttribute("catSub", "59");
		model.addAttribute("cur_id", id);
		model.addAttribute("mapImage", map);
		model.addAttribute("lstImage", lstImage);
		model.addAttribute("lstAlbum", lstAlbum);	
		model.addAttribute("menuChinh", menuChinh);
		model.addAttribute("menuCap", menuCap);
		return "image";
	}
	
	@RequestMapping(value = "/video", method = RequestMethod.GET)
	public String video(ModelMap model,HttpServletRequest request, HttpServletResponse response)
	        throws Exception {
		Map<String,String> menuChinh = MenuHelper.getById("5");
		Map<String,String> menuCap = MenuHelper.getById("61");
		
		String id = request.getParameter("id");
		List<Map<String,String>> lstAlbum = AlbumDAO.getListNolimit("");
		for(Map<String,String> album :lstAlbum){
			album.put("title", album.get("title_"+MenuHelper.getLang()));
		}
		if(id==null||"".equals(id)){			
			for(Map<String,String> album : lstAlbum){
				if("1".equals(album.get("is_video"))){
					id = album.get("id");
					break;
				}
			}
		}
		Map<String,String> map = AlbumDAO.getAlbumById(id);
		if(map==null){
			return "404";
		}
		
		map.put("title", map.get("title_"+MenuHelper.getLang()));
		List<Map<String,String>> lstVideo = AlbumDAO.getFile("1",id);	
		
		model.addAttribute("cat", "57");
		model.addAttribute("catSub", "61");
		model.addAttribute("cur_id", id);
		model.addAttribute("mapVideo", map);
		model.addAttribute("lstVideo", lstVideo);
		model.addAttribute("lstAlbum", lstAlbum);
		model.addAttribute("menuChinh", menuChinh);
		model.addAttribute("menuCap", menuCap);
		return "video";
	}
	
	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public String news(ModelMap model,HttpServletRequest request, HttpServletResponse response)
	        throws Exception {			
		
		String id = request.getParameter("id");
		List<Map<String,String>> listNews = NewsDAO.getList("1");		
		if(id!=null&&!"".equals(id)){
			listNews = NewsDAO.getById(id);
		}
		if(listNews.size()>0){
			model.addAttribute("content", listNews.get(0).get("content_"+MenuHelper.getLang()));
		}
		List<Map<String,String>> dsNews = NewsDAO.getListRelated(listNews.get(0).get("id"),"5");
		for(Map<String,String> map: dsNews){
			map.put("title", map.get("title_"+MenuHelper.getLang()));
		}
		
		Map<String,String> menuChinh = MenuHelper.getById("5");		
		Map<String,String> menuCap = MenuHelper.getById("58");
		List<Map<String,String>> lstAlbum = AlbumDAO.getListNolimit("");
		for(Map<String,String> album :lstAlbum){
			album.put("title", album.get("title_"+MenuHelper.getLang()));
		}
		
		model.addAttribute("lstAlbum", lstAlbum);
		model.addAttribute("menuChinh", menuChinh);
		model.addAttribute("menuCap", menuCap);
		model.addAttribute("dsNews", dsNews);
		model.addAttribute("menuChinh", menuChinh);
		model.addAttribute("menuName", "news");
		model.addAttribute("cur_id", id);
		return "news";
	}
	
	
}
