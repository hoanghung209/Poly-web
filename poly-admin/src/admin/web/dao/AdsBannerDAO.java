package admin.web.dao;

import java.util.List;
import java.util.Map;
import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;

public class AdsBannerDAO
{
  public static List<Map<String, String>> getAll()
  {
    String sqlCommand = String.format("SELECT * FROM ads_banner WHERE is_deleted = 0 ORDER BY updated_time DESC", new Object[0]);
    
    String[] sqlParameters = new String[0];
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder)
  {
    int offset = (page - 1) * pageSize;
    int limit = pageSize;
    
    String sqlCommand = String.format("SELECT * FROM ads_banner {WHERE} {ORDER} LIMIT %s, %s", new Object[] { Integer.valueOf(offset), Integer.valueOf(limit) });
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);
    
    String[] sqlParameters = new String[0];
    
    return DbProxy.executeQuery(sqlCommand, sqlParameters);
  }
  
  public static int countList(String sqlWhere)
  {
    String sqlCommand = "SELECT COUNT(id) FROM ads_banner {WHERE}";
    sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
    
    String[] sqlParameters = new String[0];
    String count = DbProxy.executeScalar(sqlCommand, sqlParameters);
    if (StringUtils.isBlank(count)) {
      return 0;
    }
    return Integer.valueOf(count).intValue();
  }
  
  public static boolean insert(Map<String, String> detail)
  {
    String sqlCommand = "INSERT INTO ads_banner(name,image,url,desc_vn,desc_en,more_vn,more_en,target,desc_top,desc_left,more_top,more_right,text_align,date_start,date_end,is_show,is_once,type,is_deleted,updated_by,updated_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW())";
    


    String[] param = { (String)detail.get("name"), (String)detail.get("image"), (String)detail.get("url"), 
      (String)detail.get("desc_vn"), (String)detail.get("desc_en"), (String)detail.get("more_vn"), (String)detail.get("more_en"), (String)detail.get("target"), 
      (String)detail.get("desc_top"), (String)detail.get("desc_left"), (String)detail.get("more_top"), (String)detail.get("more_right"), (String)detail.get("text_align"), 
      (String)detail.get("date_start"), (String)detail.get("date_end"), (String)detail.get("is_show"), (String)detail.get("is_once"), (String)detail.get("type"), (String)detail.get("is_deleted"), (String)detail.get("updated_by") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean update(Map<String, String> detail)
  {
    String sqlCommand = "UPDATE ads_banner SET name=?,image=?,url=?,desc_vn=?,desc_en=?,more_vn=?,more_en=?,target=?,desc_top=?,desc_left=?,more_top=?,more_right=?,text_align=?,date_start=?,date_end=?,is_show=?,is_once=?,type=?,is_deleted=?,updated_by=?,updated_time=NOW() WHERE id=?";
    


    String[] param = { (String)detail.get("name"), (String)detail.get("image"), (String)detail.get("url"), 
      (String)detail.get("desc_vn"), (String)detail.get("desc_en"), (String)detail.get("more_vn"), (String)detail.get("more_en"), (String)detail.get("target"), 
      (String)detail.get("desc_top"), (String)detail.get("desc_left"), (String)detail.get("more_top"), (String)detail.get("more_right"), (String)detail.get("text_align"), 
      (String)detail.get("date_start"), (String)detail.get("date_end"), (String)detail.get("is_show"), (String)detail.get("is_once"), (String)detail.get("type"), (String)detail.get("is_deleted"), (String)detail.get("updated_by"), (String)detail.get("id") };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
  
  public static boolean deleteLogic(String id, String userId)
  {
    String sqlCommand = "UPDATE ads_banner SET is_deleted = 1,updated_by=?,updated_time=NOW() WHERE id=?";
    String[] param = { userId, id };
    return DbProxy.executeUpdate(sqlCommand, param);
  }
}
