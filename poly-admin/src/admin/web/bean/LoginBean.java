package admin.web.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {
        
	private String username = "";
	private String password = "";
	private String capcha = "";
	private String csrfPreventionSalt = "";
	private boolean useCaptcha = true;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isUseCaptcha() {
		return useCaptcha;
	}
	public void setUseCaptcha(boolean useCaptcha) {
		this.useCaptcha = useCaptcha;
	}
	public String getCapcha() {
		return capcha;
	}
	public void setCapcha(String capcha) {
		this.capcha = capcha;
	}
	public String getCsrfPreventionSalt() {
		return csrfPreventionSalt;
	}
	public void setCsrfPreventionSalt(String csrfPreventionSalt) {
		this.csrfPreventionSalt = csrfPreventionSalt;
	}
}