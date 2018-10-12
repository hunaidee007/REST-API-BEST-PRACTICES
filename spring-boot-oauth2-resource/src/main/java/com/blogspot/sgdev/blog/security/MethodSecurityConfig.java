package com.blogspot.sgdev.blog.security;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

/**
 * A security expression handler that can handle default method security expressions plus the 
 * set provided by OAuth2SecurityExpressionMethods using the variable oauth2 to access the methods.
 * For example, the expression #oauth2.clientHasRole('ROLE_ADMIN') would invoke OAuth2SecurityExpressionMethods.clientHasRole(java.lang.String)
 * @author s981549
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return new OAuth2MethodSecurityExpressionHandler();
    }

}