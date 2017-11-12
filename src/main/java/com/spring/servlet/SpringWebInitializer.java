package com.spring.servlet;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.spring.config.SpringWebConfig;
 
public class SpringWebInitializer extends 
	AbstractAnnotationConfigDispatcherServletInitializer {
  
	  @Override
	    protected Class[] getServletConfigClasses() {
	        return new Class[] { SpringWebConfig.class };
	    }

	    @Override
	    protected String[] getServletMappings() {
	        return new String[] { "/" };
	    }

	    @Override
	    protected Class[] getRootConfigClasses() {
	        return new Class[] {};
	    }

  
}