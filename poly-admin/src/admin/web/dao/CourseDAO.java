package admin.web.dao;
import java.util.Map;



import java.util.List;

import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;
public class CourseDAO {
	public static List<Map<String,String>> getAll(){	
			String sqlCommand = "SELECT * FROM course WHERE status = 0 ORDER BY menu_id,ord";
			String[] params = {};
			List<Map<String,String>> lstRs = DbProxy.executeQuery(sqlCommand, params);
			
			return lstRs;		
	}
	
	public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder){
		int offset = (page - 1) * pageSize;
		int limit =  pageSize;
		
		String sqlCommand = String.format("SELECT * FROM course {WHERE} {ORDER} LIMIT %s, %s", offset, limit);
		sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
		sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);		

		String[] sqlParameters = {};
		//System.out.println(sqlCommand);
		return DbProxy.executeQuery(sqlCommand, sqlParameters);
	}
	public static int countList(String sqlWhere){		
		String sqlCommand = "SELECT COUNT(id) FROM course {WHERE}";
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
		
		String sqlCommand = "INSERT INTO course(name_vn,name_en,content_vn,content_en,ord,menu_id,status,updated_by,updated_time) VALUES(?,?,?,?,?,?,?,?,NOW())";
		String[] param = {detail.get("name_vn"),detail.get("name_en"),detail.get("content_vn"),detail.get("content_en"),detail.get("ord"),detail.get("menu_id"),detail.get("status"),detail.get("updated_by")};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	public static boolean update(Map<String,String> detail){
		
		String sqlCommand = "UPDATE course SET name_vn=?,name_en=?,content_vn=?,content_en=?,ord=?,menu_id=?,status=?,updated_by=?,updated_time=NOW() WHERE id=?";
		String[] param = {detail.get("name_vn"),detail.get("name_en"),detail.get("content_vn"),detail.get("content_en"),detail.get("ord"),detail.get("menu_id"),detail.get("status"),detail.get("updated_by"),detail.get("id")};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	
	public static boolean deleteLogic(String id,String userId){
		
		String sqlCommand = "UPDATE course SET status = -1,updated_by=?,updated_time=NOW() WHERE id=?";
		String[] param ={userId,id};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	
}
