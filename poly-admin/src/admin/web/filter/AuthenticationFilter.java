package admin.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import vg.core.configproxy.ConfigProxy;
import admin.web.common.AccessControl;

public class AuthenticationFilter implements Filter{
	public Map<String, String> appConf = ConfigProxy.categories.get("app");
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		//ContextUtils.debugSession(session); //debugSession			
		
		String queryString = req.getQueryString();
		String requestURL = StringUtils.isBlank(queryString) ? req.getRequestURL().toString() : String.format("%s?%s", req.getRequestURL().toString(), queryString); 			
		
		HttpServletResponse res = (HttpServletResponse) response;
		if(req.getMethod().equalsIgnoreCase("GET")) {
			session.setAttribute("redirect_path", requestURL);	
		}		
		
		if (AccessControl.IsLoggedIn(req)) {	
				chain.doFilter(request, response);
		} else {

			res.sendRedirect(req.getContextPath() + "/index.xhtml");
		}	
	}
	
	
	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}
}
