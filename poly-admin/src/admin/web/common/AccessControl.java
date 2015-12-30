package admin.web.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import admin.web.dao.UserDAO;

public class AccessControl {
	public static boolean IsLoggedIn(HttpServletRequest request){
		HttpSession session = request.getSession();
		Object user = session.getAttribute("user");
		if(user!=null&&!user.toString().equals("")){
			return true;
		}
		return false;
	}
	public static void SignIn(String user,String pass,HttpServletRequest request){
		Map<String,String> mapUser = UserDAO.signin(user, pass);
		if(mapUser!=null){
			HttpSession session = request.getSession();
			session.setAttribute("user", mapUser.get("user"));
			session.setAttribute("userId", mapUser.get("id"));
			session.setAttribute("fullname", mapUser.get("fullname"));
		}
	}
	public static String getName(HttpServletRequest request){
		HttpSession session = request.getSession();
		Object user = session.getAttribute("fullname");
		if(user!=null){
			return user.toString();
		}
		return "null";
	}
	public static String getUserid(HttpServletRequest request){
		HttpSession session = request.getSession();
		Object user = session.getAttribute("userId");
		if(user!=null){
			return user.toString();
		}
		return "0";
	}
}
