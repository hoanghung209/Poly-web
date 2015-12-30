package admin.web.common;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import admin.web.util.ContextUtils;

@ManagedBean
@ViewScoped
public class RewriteURL implements Serializable {	
	private static final long serialVersionUID = 8015718636471609893L;
	
	public static String makeAccessForbiddenURL() {
		return String.format("%s/error/access-forbidden", ContextUtils.getContextPath());
	}
	public static String makeFileNotFoundURL() {
		return String.format("%s/error/file-not-found", ContextUtils.getContextPath());
	}
	public static String makeInternalServerURL() {
		return String.format("%s/error/internal-server", ContextUtils.getContextPath());
	}
	
	public static String makeHomeURL() {
		return String.format("%s/pages/welcome/index.xhtml", ContextUtils.getContextPath());
	}		
	public static String makeLoginURL() {
		return String.format("%s/index.xhtml", ContextUtils.getContextPath());
	}
	
	
	
	public static String makeMenuURL(String tabid, String iconid,String parentId){		
			return String.format("%s/pages/menu/index.xhtml?tabid=%s&iconid=%s&parentId=%s", ContextUtils.getContextPath(), tabid, iconid,parentId);	
	}
	public static String makeFaqURL(String tabid, String iconid){		
		return String.format("%s/pages/faq/index.xhtml?tabid=%s&iconid=%s", ContextUtils.getContextPath(), tabid, iconid);	
	}
	public static String makeContentURL(String tabid, String iconid,String id){		
		return String.format("%s/pages/menu/content.xhtml?tabid=%s&iconid=%s&id=%s", ContextUtils.getContextPath(), tabid, iconid,id);	
	}
	public static String makeContentListURL(String tabid, String iconid){		
		return String.format("%s/pages/menu/list.xhtml?tabid=%s&iconid=%s", ContextUtils.getContextPath(), tabid, iconid);	
	}
	public static String makeCourseURL(String tabid, String iconid){		
		return String.format("%s/pages/course/index.xhtml?tabid=%s&iconid=%s", ContextUtils.getContextPath(), tabid, iconid);	
	}
	public static String makeSchedulerURL(String tabid, String iconid){		
		return String.format("%s/pages/course/scheduler.xhtml?tabid=%s&iconid=%s", ContextUtils.getContextPath(), tabid, iconid);	
	}
	public static String makeAlbumURL(String tabid, String iconid){		
		return String.format("%s/pages/media/album.xhtml?tabid=%s&iconid=%s", ContextUtils.getContextPath(), tabid, iconid);	
	}
	public static String makeFileURL(String tabid, String iconid){		
		return String.format("%s/pages/media/file.xhtml?tabid=%s&iconid=%s", ContextUtils.getContextPath(), tabid, iconid);	
	}
	public static String makeUserURL(String tabid, String iconid){		
		return String.format("%s/pages/user/index.xhtml?tabid=%s&iconid=%s", ContextUtils.getContextPath(), tabid, iconid);	
	}
	public static String makeFeedbackURL(String tabid, String iconid){		
		return String.format("%s/pages/faq/feedback.xhtml?tabid=%s&iconid=%s", ContextUtils.getContextPath(), tabid, iconid);	
	}
	public static String makeAdsURL(String tabid, String iconid){		
		return String.format("%s/pages/media/ads.xhtml?tabid=%s&iconid=%s", ContextUtils.getContextPath(), tabid, iconid);	
	}
	public static String makeNewsURL(String tabid, String iconid,String id){		
		return String.format("%s/pages/news/index.xhtml?tabid=%s&iconid=%s&id=%s", ContextUtils.getContextPath(), tabid, iconid,id);	
	}
	public static String makeNewsListURL(String tabid, String iconid){		
		return String.format("%s/pages/news/list.xhtml?tabid=%s&iconid=%s", ContextUtils.getContextPath(), tabid, iconid);	
	}
	public static String makeRegisterURL(String tabid, String iconid){		
		return String.format("%s/pages/faq/register.xhtml?tabid=%s&iconid=%s", ContextUtils.getContextPath(), tabid, iconid);	
	}
}