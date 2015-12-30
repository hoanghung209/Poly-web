package admin.web.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import admin.web.util.ContextUtils;

public class LanguageBundle extends ResourceBundle {
	protected static final String BUNDLE_NAME = "admin.web.resources.language";
	protected static final String BUNDLE_EXTENSION = "properties";
	protected static final String CHARSET = "UTF-8";
	protected static final Control UTF8_CONTROL = new UTF8Control();
	
	private static ResourceBundle currentLanguage;

	public LanguageBundle() {	
		setParent(ResourceBundle.getBundle(BUNDLE_NAME, FacesContext
				.getCurrentInstance().getViewRoot().getLocale(), UTF8_CONTROL));			

		String defaultSuffixLanguage = FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage();
		
		String suffixLanguage = ContextUtils.getSession("suffixLanguage", defaultSuffixLanguage);		
		currentLanguage = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(suffixLanguage.replace("_", "")), UTF8_CONTROL);
	}

	public static void changeLanguage(String suffixLanguage) {
		currentLanguage = ResourceBundle.getBundle(BUNDLE_NAME, new Locale(suffixLanguage.replace("_", "")), UTF8_CONTROL);
	}
	
	@Override
	protected Object handleGetObject(String key) {
		return parent.getObject(key);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Enumeration getKeys() {
		return parent.getKeys();
	}

	public String getValue(String key) {
		String result = null;
		try {
			result = currentLanguage.getString(key);
			//result = getString(key);
		} catch (MissingResourceException e) {
			result = "???" + key + "??? not found";
		}
		return result;
	}

	protected static class UTF8Control extends Control {
		public ResourceBundle newBundle(String baseName, Locale locale,
				String format, ClassLoader loader, boolean reload)
				throws IllegalAccessException, InstantiationException,
				IOException {
			String bundleName = toBundleName(baseName, locale);
			String resourceName = toResourceName(bundleName, BUNDLE_EXTENSION);
			ResourceBundle bundle = null;
			InputStream stream = null;
			if (reload) {
				URL url = loader.getResource(resourceName);
				if (url != null) {
					URLConnection connection = url.openConnection();
					if (connection != null) {
						connection.setUseCaches(false);
						stream = connection.getInputStream();
					}
				}
			} else {
				stream = loader.getResourceAsStream(resourceName);
			}
			if (stream != null) {
				try {
					bundle = new PropertyResourceBundle(new InputStreamReader(
							stream, CHARSET));
				} finally {
					stream.close();
				}
			}
			return bundle;
		}
	}
}
