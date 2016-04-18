package admin.web.common;

import admin.web.util.ContextUtils;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import vg.core.common.StringUtils;

@ManagedBean
@ViewScoped
public class RewriteURL
  implements Serializable
{
  private static final long serialVersionUID = 8015718636471609893L;
  
  public static String makeAccessForbiddenURL()
  {
    return String.format("%s/error/access-forbidden", new Object[] { ContextUtils.getContextPath() });
  }
  
  public static String makeFileNotFoundURL()
  {
    return String.format("%s/error/file-not-found", new Object[] { ContextUtils.getContextPath() });
  }
  
  public static String makeInternalServerURL()
  {
    return String.format("%s/error/internal-server", new Object[] { ContextUtils.getContextPath() });
  }
  
  public static String makeHomeURL()
  {
    return String.format("%s/pages/welcome/index.xhtml", new Object[] { ContextUtils.getContextPath() });
  }
  
  public static String makeLoginURL()
  {
    return String.format("%s/index.xhtml", new Object[] { ContextUtils.getContextPath() });
  }
  
  public static String makeMenuURL(String tabid, String iconid, String parentId)
  {
    return String.format("%s/pages/menu/index.xhtml?tabid=%s&iconid=%s&parentId=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid, parentId });
  }
  
  public static String makeFaqURL(String tabid, String iconid)
  {
    return String.format("%s/pages/cskh/index.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
  
  public static String makeContentURL(String tabid, String iconid, String id)
  {
    return String.format("%s/pages/menu/content.xhtml?tabid=%s&iconid=%s&id=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid, id });
  }
  
  public static String makeContentListURL(String tabid, String iconid)
  {
    return String.format("%s/pages/menu/list.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
  
  public static String makeCourseURL(String tabid, String iconid)
  {
    return String.format("%s/pages/course/index.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
  
  public static String makeSchedulerURL(String tabid, String iconid)
  {
    return String.format("%s/pages/course/scheduler.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
  
  public static String makeAlbumURL(String tabid, String iconid)
  {
    return String.format("%s/pages/media/album.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
  
  public static String makeFileURL(String tabid, String iconid)
  {
    return String.format("%s/pages/media/file.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
  
  public static String makeUserURL(String tabid, String iconid)
  {
    return String.format("%s/pages/user/index.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
  
  public static String makeCustomerURL(String tabid, String iconid)
  {
    return String.format("%s/pages/cskh/customer.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
  
  public static String makeFeedbackURL(String tabid, String iconid)
  {
    return String.format("%s/pages/cskh/feedback.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
  
  public static String makeAdsURL(String tabid, String iconid)
  {
    return String.format("%s/pages/media/ads.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
  
  public static String makeNewsURL(String tabid, String iconid, String id)
  {
    return String.format("%s/pages/news/index.xhtml?tabid=%s&iconid=%s&id=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid, id });
  }
  
  public static String makeNewsListURL(String tabid, String iconid)
  {
    return String.format("%s/pages/news/list.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
  
  public static String makeRegisterURL(String tabid, String iconid)
  {
    return String.format("%s/pages/cskh/register.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
  
  public static String makeAclUserURL(String tabid, String iconid)
  {
    return String.format("%s/pages/user/index.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
  
  public static String makeAclUserGroupURL(String userId)
  {
    return String.format("%s/pages/user/group.xhtml?userId=%s", new Object[] { ContextUtils.getContextPath(), userId });
  }
  
  public static String makeAclUserPasswordURL(String tabid, String iconid)
  {
    return makeAclUserPasswordURL(tabid, iconid, "", "");
  }
  
  public static String makeAclUserPasswordURL(String tabid, String iconid, String userId, String template)
  {
    String url = String.format("%s/pages/user/password.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
    if (!StringUtils.isBlank(userId)) {
      url = String.format("%s&userId=%s", new Object[] { url, userId });
    }
    if (!StringUtils.isBlank(template)) {
      url = String.format("%s&template=%s", new Object[] { url, template });
    }
    return url;
  }
  
  public static String makeAclUserChannelURL(String userId)
  {
    return String.format("%s/pages/user/channel.xhtml?userId=%s", new Object[] { ContextUtils.getContextPath(), userId });
  }
  
  public static String makeAclUserActionURL(String userId)
  {
    return String.format("%s/pages/user/action.xhtml?userId=%s", new Object[] { ContextUtils.getContextPath(), userId });
  }
  
  public static String makeAclGroupURL(String tabid, String iconid)
  {
    return String.format("%s/pages/group/index.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
  
  public static String makeAclGroupUserURL(String groupId)
  {
    return String.format("%s/pages/group/user.xhtml?groupId=%s", new Object[] { ContextUtils.getContextPath(), groupId });
  }
  
  public static String makeAclGroupWorkflowURL(String groupId)
  {
    return String.format("%s/pages/group/workflow.xhtml?groupId=%s", new Object[] { ContextUtils.getContextPath(), groupId });
  }
  
  public static String makeAclGroupActionURL(String groupId)
  {
    return String.format("%s/pages/group/action.xhtml?groupId=%s", new Object[] { ContextUtils.getContextPath(), groupId });
  }
  
  public static String makeAclActionURL(String tabid, String iconid)
  {
    return String.format("%s/pages/action/index.xhtml?tabid=%s&iconid=%s", new Object[] { ContextUtils.getContextPath(), tabid, iconid });
  }
}
