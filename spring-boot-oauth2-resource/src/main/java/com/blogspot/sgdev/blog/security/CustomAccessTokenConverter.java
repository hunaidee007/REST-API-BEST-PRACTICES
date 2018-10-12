package com.blogspot.sgdev.blog.security;

import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

/**
 * Token Converter to use in conjetion with Token Enhancer. Token Enhancer is used to enhance the Token by the Oauth Server.
 * Token Converter at the Resource side is used to fetch the customized token information.
 * @author s981549
 *
 */
@Component
public class CustomAccessTokenConverter  extends DefaultAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        OAuth2Authentication authentication = super.extractAuthentication(claims);
        authentication.setDetails(claims);
        return authentication;
    }

}