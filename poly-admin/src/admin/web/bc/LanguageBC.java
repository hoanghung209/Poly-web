package admin.web.bc;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import admin.web.resources.LanguageBundle;
import admin.web.util.ContextUtils;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class LanguageBC implements Serializable {	
	private static LanguageBundle lang = new LanguageBundle();
	
	@PostConstruct	
	protected void init() {		
	}
	
	public void useEnglish() {
		Locale locale = new Locale("en");
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		
		ContextUtils.addMessage(lang.getValue("english"));
	}
	public void useVietnamese() {
		Locale locale = new Locale("vn");
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		
		ContextUtils.addMessage(lang.getValue("vietnam"));
	}

	public void testParamter() {
		ContextUtils.addMessage(MessageFormat.format(lang.getString("parms"), "parm 1", "parm 2"));			
	}
	
	public static String getValue(String key) {
		String result = null;
		try {
			result =lang.getValue(key);
		} catch (MissingResourceException e) {
			result = "???" + key + "??? not found";
		}
		return result;
	}
	
	public String getKeywords() {
		return "language-keyword";
	}
	
	
}