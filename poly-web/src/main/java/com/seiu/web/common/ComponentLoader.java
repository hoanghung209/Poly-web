package com.seiu.web.common;

import java.util.List;
import java.util.Map;

import com.seiu.web.dao.AdsDAO;
import com.seiu.web.utils.MenuHelper;

public class ComponentLoader {
	public static Map<String, String> PopupLoader()
	  {
	    List<Map<String, String>> lstPopup = AdsDAO.getList("1");
	    String popup_content = "Some text in the modal.";
	    if (lstPopup.size() > 0)
	    {
	      Map<String, String> mapPopup = (Map<String, String>)lstPopup.get(0);
	      String image = (String)mapPopup.get("image");
	      popup_content = image;
	      if ((image == null) || ("".equals(image)))
	      {
	        String desc = (String)mapPopup.get("desc_" + MenuHelper.getLang());
	        if ((desc != null) && (!"".equals(desc))) {
	          popup_content = desc;
	        }
	        mapPopup.put("isImg", "false");
	      }
	      else
	      {
	        mapPopup.put("isImg", "true");
	      }
	      mapPopup.put("content", popup_content);
	      return mapPopup;
	    }
	    return null;
	  }
}
