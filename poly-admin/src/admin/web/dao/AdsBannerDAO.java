package admin.web.dao;
import java.util.Map;

import java.util.List;

import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;
public class AdsBannerDAO {
	
	public static List<Map<String, String>> getAll(){				
		String sqlCommand = String.format("SELECT * FROM ads_banner WHERE is_deleted = 0 ORDER BY updated_time DESC");		

		String[] sqlParameters = {};		
		return DbProxy.executeQuery(sqlCommand, sqlParameters);
	}
	public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder){
		int offset = (page - 1) * pageSize;
		int limit =  pageSize;
		
		String sqlCommand = String.format("SELECT * FROM ads_banner {WHERE} {ORDER} LIMIT %s, %s", offset, limit);
		sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
		sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);		

		String[] sqlParameters = {};
		//System.out.println(sqlCommand);
		return DbProxy.executeQuery(sqlCommand, sqlParameters);
	}
	public static int countList(String sqlWhere){		
		String sqlCommand = "SELECT COUNT(id) FROM ads_banner {WHERE}";
		sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
			
		String[] sqlParameters = {};
		String count = DbProxy.executeScalar(sqlCommand, sqlParameters);
		
		if(StringUtils.isBlank(count)) {
			return 0;
		} else {
			return Integer.valueOf(count); 
		}        	
	}
	public static boolean insert(Map<String,String> detail){		
		String sqlCommand = "INSERT INTO ads_banner(name,url_vn,url_en,desc_vn,desc_en,date_start,date_end,is_show,is_once,type,is_deleted,updated_by,updated_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,NOW())";
		String[] param = {detail.get("name"),detail.get("url_vn"),detail.get("url_en"),detail.get("desc_vn"),detail.get("desc_en"),detail.get("date_start"),detail.get("date_end"),detail.get("is_show"),detail.get("is_once"),detail.get("type"),detail.get("is_deleted"),detail.get("updated_by")};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	public static boolean update(Map<String,String> detail){		
		String sqlCommand = "UPDATE ads_banner SET name=?,url_vn=?,url_en=?,desc_vn=?,desc_en=?,date_start=?,date_end=?,is_show=?,is_once=?,type=?,is_deleted=?,updated_by=?,updated_time=NOW() WHERE id=?";
		String[] param = {detail.get("name"),detail.get("url_vn"),detail.get("url_en"),detail.get("desc_vn"),detail.get("desc_en"),detail.get("date_start"),detail.get("date_end"),detail.get("is_show"),detail.get("is_once"),detail.get("type"),detail.get("is_deleted"),detail.get("updated_by"),detail.get("id")};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	
	public static boolean deleteLogic(String id,String userId){		
		String sqlCommand = "UPDATE ads_banner SET is_deleted = 1,updated_by=?,updated_time=NOW() WHERE id=?";
		String[] param ={userId,id};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	
}
