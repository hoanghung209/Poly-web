package com.seiu.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seiu.web.dao.AlbumDAO;
import com.seiu.web.dao.NewsDAO;
import com.seiu.web.utils.ContextUtils;
import com.seiu.web.utils.Hashids;
import com.seiu.web.utils.MenuHelper;

@Controller
public class LibraryController
{
  Hashids hash = new Hashids();
  
  @RequestMapping(value={"/thuvien"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String library(ModelMap model, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    MenuHelper.leftMenu(model, "5", "57");
    
    List<Map<String, String>> lstImage = AlbumDAO.getList("0");
    for (Map<String, String> map : lstImage)
    {
      map.put("title", map.get("title_" + MenuHelper.getLang()));
      map.put("slug", syncSlug(map.get("slug_" + MenuHelper.getLang())));
      map.put("hashid", this.hash.encodeHex(map.get("id")));
    }
    List<Map<String, String>> lstVideo = AlbumDAO.getList("1");
    for (Map<String, String> map : lstVideo)
    {
      map.put("title", map.get("title_" + MenuHelper.getLang()));
      map.put("slug", syncSlug(map.get("slug_" + MenuHelper.getLang())));
      map.put("hashid", this.hash.encodeHex(map.get("id")));
    }
    model.addAttribute("lstImage", lstImage);
    model.addAttribute("lstVideo", lstVideo);
    return "thuvien";
  }
  
  @RequestMapping(value={"/thuvien/{symbolicName:[a-zA-Z0-9-]*}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String librarypersonal(ModelMap model, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return library(model, request, response);
  }
  
  @RequestMapping(value={"/image/{symbolicName:[a-zA-Z0-9-]+}-{newsIdHash:[a-zA-Z0-9]+}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String image(@PathVariable String newsIdHash, ModelMap model, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map<String, String> menuChinh = MenuHelper.getById("5");
    Map<String, String> menuCap = MenuHelper.getById("59");
    

    String id = this.hash.decodeHex(newsIdHash);
    List<Map<String, String>> lstAlbum = AlbumDAO.getListNolimit("");
    for (Map<String, String> album : lstAlbum)
    {
      album.put("title", album.get("title_" + MenuHelper.getLang()));
      album.put("slug", syncSlug(album.get("slug_" + MenuHelper.getLang())));
      album.put("hashid", this.hash.encodeHex(album.get("id")));
    }
    Map<String, String> map = AlbumDAO.getAlbumById(id);
    if (map == null)
    {
      map = AlbumDAO.getFirst("0");
      if (map == null) {
        return "404";
      }
      id = map.get("id");
    }
    map.put("title", map.get("title_" + MenuHelper.getLang()));
    Object lstImage = AlbumDAO.getFile("0", id);
    
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
  
  @RequestMapping(value={"/video/{symbolicName:[a-zA-Z0-9-]+}-{newsIdHash:[a-zA-Z0-9]+}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String video(@PathVariable String newsIdHash, ModelMap model, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map<String, String> menuChinh = MenuHelper.getById("5");
    Map<String, String> menuCap = MenuHelper.getById("61");
    
    String id = this.hash.decodeHex(newsIdHash);
    List<Map<String, String>> lstAlbum = AlbumDAO.getListNolimit("");
    for (Map<String, String> album : lstAlbum)
    {
      album.put("title", album.get("title_" + MenuHelper.getLang()));
      album.put("slug", syncSlug(album.get("slug_" + MenuHelper.getLang())));
      album.put("hashid", this.hash.encodeHex(album.get("id")));
    }
    Map<String, String> map = AlbumDAO.getAlbumById(id);
    if (map == null)
    {
      map = AlbumDAO.getFirst("1");
      if (map == null) {
        return "404";
      }
      id = map.get("id");
    }
    map.put("title", map.get("title_" + MenuHelper.getLang()));
    Object lstVideo = AlbumDAO.getFile("1", id);
    
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
  
  @RequestMapping(value={"/news/{slug:[a-zA-Z0-9-]+}-{newsIdHash:[a-zA-Z0-9]+}/{p:[0-9]*}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String news(@PathVariable String slug, @PathVariable String newsIdHash, @PathVariable String p, ModelMap model, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    int page = 1;
    if (!ContextUtils.isBlank(p)) {
      try
      {
        page = Integer.valueOf(p).intValue();
      }
      catch (Exception localException) {}
    }
    String id = this.hash.decodeHex(newsIdHash);
    Map<String, String> mapN = AlbumDAO.getAlbumById(id);
    if (mapN == null)
    {
      mapN = AlbumDAO.getFirst("2");
      if (mapN == null) {
        return "404";
      }
      id = mapN.get("id");
    }
    int totalNews = NewsDAO.countList(id);
    int totalPage = totalNews / 4;
    if (totalPage * 4 < totalNews) {
      totalPage++;
    }
    if (page > totalPage) {
      page = totalPage;
    }
    List<Map<String, String>> listNews = NewsDAO.getList(id, page, 4);
    for (Map<String, String> map : listNews)
    {
      map.put("hashid", this.hash.encodeHex(map.get("id")));
      map.put("title", map.get("title_" + MenuHelper.getLang()));
      map.put("slug", syncSlug(map.get("slug_" + MenuHelper.getLang())));
      String content = map.get("content_" + MenuHelper.getLang());
      content = ContextUtils.removeHtml(content, 300);
      if (content.length() > 200) {
        content = content.substring(0, 300) + "..";
      }
      map.put("content", content);
    }
    String news_header = "tin tức";
    if (MenuHelper.getLang().equals("en")) {
      news_header = "news";
    }
    Object menuChinh = MenuHelper.getById("5");
    Map<String, String> menuCap = MenuHelper.getById("58");
    List<Map<String, String>> lstAlbum = AlbumDAO.getListNolimit("");
    for (Map<String, String> album : lstAlbum)
    {
      album.put("title", album.get("title_" + MenuHelper.getLang()));
      album.put("slug", syncSlug(album.get("slug_" + MenuHelper.getLang())));
      album.put("hashid", this.hash.encodeHex(album.get("id")));
    }
    int firstPage = 1;
    int lastPage = totalPage;
    if (page - 2 > 0) {
      firstPage = page - 2;
    }
    if (page + 2 < totalPage) {
      lastPage = page + 2;
    }
    model.addAttribute("query", MenuHelper.getContextPath() + "/news/" + slug + "-" + newsIdHash);
    model.addAttribute("currIndex", Integer.valueOf(page));
    model.addAttribute("totalPages", Integer.valueOf(totalPage));
    model.addAttribute("beginIndex", Integer.valueOf(firstPage));
    model.addAttribute("endIndex", Integer.valueOf(lastPage));
    


    model.addAttribute("lstAlbum", lstAlbum);
    model.addAttribute("menuChinh", menuChinh);
    model.addAttribute("menuCap", menuCap);
    model.addAttribute("dsNews", listNews);
    model.addAttribute("menuChinh", menuChinh);
    model.addAttribute("news_header", news_header);
    model.addAttribute("menuName", "news");
    model.addAttribute("cur_id", id);
    model.addAttribute("cat", "58");
    return "news";
  }
  
  @RequestMapping(value={"/news/{slug:[a-zA-Z0-9-]+}-{newsIdHash:[a-zA-Z0-9]+}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String newsfirst(@PathVariable String slug, @PathVariable String newsIdHash, ModelMap model, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    return news(slug, newsIdHash, "1", model, request, response);
  }
  
  @RequestMapping(value={"/news"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String newscommon(ModelMap model, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    List<Map<String, String>> listAlbum = AlbumDAO.getList("2");
    if (listAlbum.size() > 0)
    {
      Map<String, String> album = listAlbum.get(0);
      return news(album.get("slug_" + MenuHelper.getLang()), this.hash.encodeHex(album.get("id")), "1", model, request, response);
    }
    return "404";
  }
  
  @RequestMapping(value={"/image"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String imagecommon(ModelMap model, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    List<Map<String, String>> listAlbum = AlbumDAO.getList("0");
    if (listAlbum.size() > 0)
    {
      Map<String, String> album = listAlbum.get(0);
      return image(this.hash.encodeHex(album.get("id")), model, request, response);
    }
    return "404";
  }
  
  @RequestMapping(value={"/video"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String videocommon(ModelMap model, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    List<Map<String, String>> listAlbum = AlbumDAO.getList("1");
    if (listAlbum.size() > 0)
    {
      Map<String, String> album = listAlbum.get(0);
      return video(this.hash.encodeHex(album.get("id")), model, request, response);
    }
    return "404";
  }
  
  @RequestMapping(value={"/news_detail/{symbolicName:[a-zA-Z0-9-]+}-{newsIdHash:[a-zA-Z0-9]+}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String news_detail(@PathVariable String newsIdHash, ModelMap model, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String id = this.hash.decodeHex(newsIdHash);
    Map<String, String> mapNews = NewsDAO.getById(id);
    if (mapNews == null) {
      return "404";
    }
    model.addAttribute("content", mapNews.get("content_" + MenuHelper.getLang()));
    
    List<Map<String, String>> dsNews = NewsDAO.getListRelated(mapNews.get("id"), "5");
    for (Map<String, String> map : dsNews)
    {
      map.put("title", map.get("title_" + MenuHelper.getLang()));
      map.put("slug", map.get("slug_" + MenuHelper.getLang()));
      map.put("hashid", this.hash.encodeHex(map.get("id")));
    }
    Map<String, String> menuChinh = MenuHelper.getById("5");
    Object menuCap = MenuHelper.getById("58");
    List<Map<String, String>> lstAlbum = AlbumDAO.getListNolimit("");
    for (Map<String, String> album : lstAlbum)
    {
      album.put("title", album.get("title_" + MenuHelper.getLang()));
      album.put("slug", syncSlug(album.get("slug_" + MenuHelper.getLang())));
    }
    model.addAttribute("lstAlbum", lstAlbum);
    model.addAttribute("menuChinh", menuChinh);
    model.addAttribute("menuCap", menuCap);
    model.addAttribute("dsNews", dsNews);
    model.addAttribute("menuChinh", menuChinh);
    model.addAttribute("menuName", "news");
    model.addAttribute("cur_id", mapNews.get("album_id"));
    model.addAttribute("cat", "58");
    return "news_detail";
  }
  
  private String syncSlug(String slug)
  {
    if (ContextUtils.isBlank(slug)) {
      return "blank-slug";
    }
    return slug;
  }
}

