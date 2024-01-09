package sru.edu.group1.workorders.services;

import jakarta.servlet.http.HttpServletRequest;
//class to get the site URLs for the email.
public class UtilityServices {
	  public static String getSiteURL(HttpServletRequest request) {
		  String siteURL = request.getRequestURL().toString();
		  return siteURL.replace(request.getServletPath(), "");
	  }
}