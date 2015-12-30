package com.seiu.web.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import com.seiu.web.common.HttpUtils;

public class HttpUtils {
		public static String getFullURL(HttpServletRequest request) {
		StringBuffer requestURL = request.getRequestURL();
		String queryString = request.getQueryString();

		if (queryString == null) {
			return requestURL.toString();
		} else {
			return requestURL.append('?').append(queryString).toString();
		}
	}

	public static String getQueryStringWithOutKey(HttpServletRequest request, String key) {
		try {
			String string = request.getQueryString();
			if (StringUtils.isEmpty(key)) {
				return string;
			}
			String querys[] = string.split("&");
			StringBuilder builder = new StringBuilder();
			for (String query : querys) {
				if (!query.startsWith(key)) {
					builder.append(query).append("&");
				}
			}
			String rs = builder.toString();
			if (rs.endsWith("&")) {
				return rs.substring(0, rs.length() - 1);
			}
			return rs;
		} catch (Exception ex) {
			return null;
		}
	}

	public static void setCookie(String key, String value, int expiredTime,
	        HttpServletResponse response, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookies[i].setValue("");
				cookies[i].setPath("/");
				cookies[i].setMaxAge(expiredTime);
				response.addCookie(cookies[i]);
			}
		}
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(expiredTime);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static String getCookie(String key, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if (c.getName().equals(key)) {
					return c.getValue();
				}
			}
		}
		return null;
	}

	

	public static String buildParams(Map<String, String> params)
	        throws UnsupportedEncodingException {
		if (params == null || params.size() == 0) {
			return "";
		} else {
			StringBuilder builder = new StringBuilder();
			boolean first = true;
			for (String param : params.keySet()) {
				if (first) {
					builder.append("?");
					first = false;
				} else {
					builder.append("&");
				}
				builder.append(param + "=" + URLEncoder.encode(params.get(param), "utf8"));
			}
			return builder.toString();
		}
	}

}
