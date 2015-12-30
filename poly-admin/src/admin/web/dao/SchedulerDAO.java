package admin.web.dao;
import java.util.Map;
import java.util.List;
import vg.core.dbproxy.DbProxy;
public class SchedulerDAO {
	public static List<Map<String,String>> getByChuongtrinh(String chuongtrinhId){	
			String sqlCommand = "SELECT * FROM scheduler WHERE status = 0 AND chuongtrinh_id = ? ORDER BY `row`,`column`";
			String[] params = {chuongtrinhId};
			List<Map<String,String>> lstRs = DbProxy.executeQuery(sqlCommand, params);
			
			return lstRs;		
	}
	public static List<Map<String,String>> getByChuongtrinh(String chuongtrinhId,String row){	
		String sqlCommand = "SELECT * FROM scheduler WHERE status = 0 AND chuongtrinh_id = ? AND `row`=? ORDER BY `column`";
		String[] params = {chuongtrinhId,row};
		List<Map<String,String>> lstRs = DbProxy.executeQuery(sqlCommand, params);
		
		return lstRs;		
	}
	
	public static List<Map<String,String>> getHeader(){
			String sqlCommand = "SELECT * FROM scheduler WHERE status = 3 ORDER BY `column`";
			String[] params = {};
			List<Map<String,String>> lstRs = DbProxy.executeQuery(sqlCommand, params);			
			return lstRs;	
	}
	
	public static boolean insert(Map<String,String> detail){
		
		String sqlCommand = "INSERT INTO scheduler(content_vn,content_en,chuongtrinh_id,`row`,`column`,status,updated_by,updated_time) VALUES(?,?,?,?,?,?,?,NOW())";
		String[] param = {detail.get("content_vn"),detail.get("content_en"),detail.get("chuongtrinh_id"),detail.get("row"),detail.get("column"),detail.get("status"),detail.get("updated_by")};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	public static boolean update(Map<String,String> detail){
		
		String sqlCommand = "UPDATE scheduler SET content_vn=?,content_en=?,chuongtrinh_id=?,`row`=?,`column`=?,status=?,updated_by=?,updated_time=NOW() WHERE id=?";
		String[] param = {detail.get("content_vn"),detail.get("content_en"),detail.get("chuongtrinh_id"),detail.get("row"),detail.get("column"),detail.get("status"),detail.get("updated_by"),detail.get("id")};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	
	public static boolean deleteLogic(String chuongtrinhId,String row,String userId){
		
		String sqlCommand = "UPDATE scheduler SET `status` = -1,updated_by=?,updated_time=NOW() WHERE chuongtrinh_id=? AND `row`=?";
		String[] param ={userId,chuongtrinhId,row};
		return DbProxy.executeUpdate(sqlCommand, param);
	}
	
	public static String getMaxRow(String chuongtrinhId){
		String sqlCommand = "select max(`row`) from scheduler where chuongtrinh_id =?";
		String[] param ={chuongtrinhId};
		return DbProxy.executeScalar(sqlCommand, param);
	}
	
}
