package com.seiu.web.controller;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.seiu.web.dao.AdsDAO;
import com.seiu.web.dao.AlbumDAO;
import com.seiu.web.dao.ContentDAO;
import com.seiu.web.dao.FeedBackDAO;
import com.seiu.web.utils.ContextUtils;
import com.seiu.web.utils.MenuHelper;

@Controller
public class HomeController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
	        throws Exception {	
		ModelAndView mv = new ModelAndView("index");		
		List<Map<String,String>> lstTxt = ContentDAO.getContentByMenu("10", "2");
		if(lstTxt.size()>0){
			mv.addObject("caption", lstTxt.get(0).get("content_"+MenuHelper.getLang())); 
		}
		List<Map<String,String>> lstIcon = ContentDAO.getContentByMenu("10", "12");
		
		for(Map<String,String> map:lstIcon){
			map.put("content", map.get("content_"+MenuHelper.getLang()));
		}
		mv.addObject("lstIcon", lstIcon);
		
		List<Map<String,String>> lst13 = ContentDAO.getContentByMenu("10", "13");
		for(Map<String,String> map:lst13){
			map.put("content", map.get("content_"+MenuHelper.getLang()));
		}
		mv.addObject("lst13", lst13);
		
		
		List<Map<String,String>> lstImage = AlbumDAO.getList("0");
		for(Map<String,String> map:lstImage){
			map.put("title", map.get("title_"+MenuHelper.getLang())); 
		}
		List<Map<String,String>> lstVideo = AlbumDAO.getList("1");

		mv.addObject("lstImage", lstImage);
		mv.addObject("lstVideo", lstVideo);
		
		List<Map<String,String>> lstFeedback = FeedBackDAO.getList();
		for(Map<String,String> map:lstFeedback){
			if(ContextUtils.isBlank(map.get("avatar"))){
				map.put("avatart", "./resources/core/images/sider.png");
			}
		}
		mv.addObject("lstFeedback", lstFeedback);
		
		List<Map<String,String>> lstBanner = AdsDAO.getList("0");
		for(Map<String,String> map:lstBanner){
			map.put("url", map.get("url_"+MenuHelper.getLang())); 
			map.put("desc", map.get("desc_"+MenuHelper.getLang())); 
		}
		mv.addObject("lstBanner", lstBanner);
		
		List<Map<String,String>> lstPopup = AdsDAO.getList("1");
		String popuphtml = "Some text in the modal.";
		if(lstPopup.size()>0){
			String url = lstPopup.get(0).get("url_"+MenuHelper.getLang());
			popuphtml = "<img src='"+url+"'/>";
			if(url==null ||"".equals(url)){
				String desc = lstPopup.get(0).get("desc_"+MenuHelper.getLang());
				if(desc!=null &&!"".equals(desc)){
					popuphtml = desc;
				}
			}
		}
		mv.addObject("popuphtml", popuphtml);
		return mv;
		
	}

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String intro(ModelMap model, HttpServletRequest request, HttpServletResponse response) {		
		String ip ="localhost";
		try {
			ip = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(ip); 
		
		model.addAttribute("msg", "Xin chào bạn, tôi đến từ "+ ip);
		return "hello";
	}
}
