package org.sdr.security;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionChecker {	
	@Bean
	public FilterRegistrationBean registerFilter() {

	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(getAccessFilter());
	    registration.addUrlPatterns(
	    		"/home",
	    		"/play",
	    		"/upload",
	    		"/scores");
	    registration.setName("accessFilter");
	    registration.setOrder(1);
	    return registration;
	} 

	public Filter getAccessFilter() {
	    return new AccessFilter();
	}

}
