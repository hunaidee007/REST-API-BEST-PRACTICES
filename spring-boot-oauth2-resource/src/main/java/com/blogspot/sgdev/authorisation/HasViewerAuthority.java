package com.blogspot.sgdev.authorisation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.prepost.PreAuthorize;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('write') and  hasRole('ROLE_TRUSTED_CLIENT')")
public @interface HasViewerAuthority {}