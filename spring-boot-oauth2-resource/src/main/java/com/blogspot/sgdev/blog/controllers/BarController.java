package com.blogspot.sgdev.blog.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class BarController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    public BarController() {
        super();
    }

    /**
     * REST controller for user having BAR and WRITE scope and TRUSTED_CLIENT authority
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('write') and  hasRole('ROLE_TRUSTED_CLIENT')")
	@RequestMapping(value="/trusted_client", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String helloTrustedClient() {
    	logger.info(" *** in helloTrustedClient");
        return "hello user authenticated by trusted client";
    }
    
    /**
     * REST controller for user having BAR and WRITE scope and ROLE_ADMIN authority
     * @return
     */
    @PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('write') and hasRole('ROLE_ADMIN')")
    @RequestMapping(value="admin", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String helloAdmin() {
        return "hello admin";
    }

	/**
	 * REST controller for printing all the ROLES of logged in user 
	 * @return
	 */
	@RequestMapping(value="/roles", method=RequestMethod.GET)
    public Object getRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

}