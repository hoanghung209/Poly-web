package admin.web.dao;
import java.util.Map;

import java.util.List;

import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;
public class NewsDAO {
	
	public static List<Map<String, String>> getAll(){				
		String sqlCommand = String.format("SELECT * FROM news WHERE is_deleted = 0 ORDER BY updated_time DESC");		

		String[] sqlParameters = {};		
		return DbProxy.executeQuery(sqlCommand, sqlParameters);
	}
	public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder){
		int offset = (page - 1) * pageSize;
		int limit =  pageSize;
		
		String sqlCommand = String.format("SELECT * FROM news {WHERE} {ORDER} LIMIT %s, %s", offset, limit);
		sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
		sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);		

		String[] sqlParameters = {};
		//System.out.println(sqlCommand);
		return DbProxy.executeQuery(sqlCommand, sqlParameters);
	}
	public static int countList(String sqlWhere){		
		String sqlCommand = "SELECT COUNT(id) FROM news {WHERE}";
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
		String sqlCommand = "INSERT INTO news(title_vn,title_en,content_vn,content_en,is_show,is_deleted,updated_by,updated_time) VALUES(?,?,?,?,?,0,?,NOW())";
		String[] param = {detail.get("title_vn"),detail.get("title_en"),detail.get("content_vn"),detail.get("content_en"),detail.get("is_show"),detail.get("updated_by")};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	public static boolean update(Map<String,String> detail){		
		String sqlCommand = "UPDATE news SET title_vn=?,title_en=?,content_vn=?,content_en=?,is_deleted=?,is_show=?,updated_by=?,updated_time=NOW() WHERE id=?";
		String[] param = {detail.get("title_vn"),detail.get("title_en"),detail.get("content_vn"),detail.get("content_en"),detail.get("is_deleted"),detail.get("is_show"),detail.get("updated_by"),detail.get("id")};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	
	public static boolean deleteLogic(String id,String userId){		
		String sqlCommand = "UPDATE news SET is_deleted = 1,updated_by=?,updated_time=NOW() WHERE id=?";
		String[] param ={userId,id};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	
}
