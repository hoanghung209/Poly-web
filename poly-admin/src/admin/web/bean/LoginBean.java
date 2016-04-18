package admin.web.bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class LoginBean
  implements Serializable
{
  private String username = "";
  private String password = "";
  private String capcha = "";
  private String csrfPreventionSalt = "";
  private boolean useCaptcha = true;
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public boolean isUseCaptcha()
  {
    return this.useCaptcha;
  }
  
  public void setUseCaptcha(boolean useCaptcha)
  {
    this.useCaptcha = useCaptcha;
  }
  
  public String getCapcha()
  {
    return this.capcha;
  }
  
  public void setCapcha(String capcha)
  {
    this.capcha = capcha;
  }
  
  public String getCsrfPreventionSalt()
  {
    return this.csrfPreventionSalt;
  }
  
  public void setCsrfPreventionSalt(String csrfPreventionSalt)
  {
    this.csrfPreventionSalt = csrfPreventionSalt;
  }
}
