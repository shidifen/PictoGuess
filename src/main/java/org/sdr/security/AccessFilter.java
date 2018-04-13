package org.sdr.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AccessFilter implements Filter {
	
	public void init(FilterConfig filterConfig) 
			throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest req,
			ServletResponse res, FilterChain chain) 
					throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);

		boolean loggedIn = (session != null && session.getAttribute("user") != null);

		if (loggedIn) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect(request.getContextPath());
		}
	}
}
