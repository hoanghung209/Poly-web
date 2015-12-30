package admin.web.dao;
import java.util.Map;



import java.util.List;

import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;
public class ContentDAO {
	public static List<Map<String,String>> getAll(){	
			String sqlCommand = "SELECT * FROM menu WHERE status = 0 ORDER BY parent,ord";
			String[] params = {};
			List<Map<String,String>> lstRs = DbProxy.executeQuery(sqlCommand, params);
			
			return lstRs;		
	}
	
	public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder){
		int offset = (page - 1) * pageSize;
		int limit =  pageSize;
		
		String sqlCommand = String.format("SELECT * FROM content {WHERE} {ORDER} LIMIT %s, %s", offset, limit);
		sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
		sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);		

		String[] sqlParameters = {};
		//System.out.println(sqlCommand);
		return DbProxy.executeQuery(sqlCommand, sqlParameters);
	}
	public static int countList(String sqlWhere){		
		String sqlCommand = "SELECT COUNT(id) FROM content {WHERE}";
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
		
		String sqlCommand = "INSERT INTO content(title_vn,title_en,content_vn,content_en,description_vn,description_en,image,img_pos,width,ord,type,menu_id,anchor,status,updated_by,updated_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,0,?,NOW())";
		String[] param = {detail.get("title_vn"),detail.get("title_en"),detail.get("content_vn"),detail.get("content_en"),detail.get("description_vn"),detail.get("description_en"),detail.get("image"),detail.get("img_pos"),detail.get("width"),detail.get("ord"),detail.get("type"),detail.get("menu_id"),detail.get("anchor"),detail.get("updated_by")};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	public static boolean update(Map<String,String> detail){
		
		String sqlCommand = "UPDATE content SET title_vn=?,title_en=?,content_vn=?,content_en=?,description_vn=?,description_en=?,image=?,img_pos=?,width=?,ord=?,type=?,menu_id=?,anchor=?,status=?,updated_by=?,updated_time=NOW() WHERE id=?";
		String[] param = {detail.get("title_vn"),detail.get("title_en"),detail.get("content_vn"),detail.get("content_en"),detail.get("description_vn"),detail.get("description_en"),detail.get("image"),detail.get("img_pos"),detail.get("width"),detail.get("ord"),detail.get("type"),detail.get("menu_id"),detail.get("anchor"),detail.get("status"),detail.get("updated_by"),detail.get("id")};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	
	public static boolean deleteLogic(String id,String userId){
		
		String sqlCommand = "UPDATE content SET status = -1,updated_by=?,updated_time=NOW() WHERE id=?";
		String[] param ={userId,id};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	
}
