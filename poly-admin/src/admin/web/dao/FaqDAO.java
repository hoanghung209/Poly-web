package admin.web.dao;
import java.util.Map;

import java.util.List;

import vg.core.common.StringUtils;
import vg.core.dbproxy.DbProxy;
public class FaqDAO {

	public static List<Map<String,String>> getAll(){		
			String sqlCommand = "SELECT * FROM faq WHERE status = 0 ORDER BY parent,ord";
			String[] params = {};
			List<Map<String,String>> lstRs = DbProxy.executeQuery(sqlCommand, params);			
			return lstRs;
		
	}
	
	public static List<Map<String, String>> getList(int page, int pageSize, String sqlWhere, String sqlOrder){
		int offset = (page - 1) * pageSize;
		int limit =  pageSize;
		
		String sqlCommand = String.format("SELECT * FROM faq {WHERE} {ORDER} LIMIT %s, %s", offset, limit);
		sqlCommand = sqlCommand.replace(" {WHERE}", sqlWhere);
		sqlCommand = sqlCommand.replace(" {ORDER}", sqlOrder);		

		String[] sqlParameters = {};
		//System.out.println(sqlCommand);
		return DbProxy.executeQuery(sqlCommand, sqlParameters);
	}
	public static int countList(String sqlWhere){		
		String sqlCommand = "SELECT COUNT(id) FROM faq {WHERE}";
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
		String sqlCommand = "INSERT INTO faq(title,content,ord,status,language) VALUES(?,?,?,?,?)";
		String[] param = {detail.get("title"),detail.get("content"),detail.get("ord"),detail.get("status"),detail.get("language")};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	public static boolean update(Map<String,String> detail){		
		String sqlCommand = "UPDATE faq SET title=?,content=?,ord=?,status=?,language=? WHERE id=?";
		String[] param = {detail.get("title"),detail.get("content"),detail.get("ord"),detail.get("status"),detail.get("language"),detail.get("id")};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	
	public static boolean deleteLogic(String id){		
		String sqlCommand = "UPDATE faq SET status = -1 WHERE id=?";
		String[] param ={id};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	
}
