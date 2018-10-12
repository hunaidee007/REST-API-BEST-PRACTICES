package com.blogspot.sgdev.blog.security;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Outh Resouce Filter. This class is used to verify if the request has required headers and autoirity to access resource.
 * @author s981549
 *
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
    private CustomAccessTokenConverter customAccessTokenConverter;

    /**
     * Use this to configure the access rules for secure resources.
     * By default all resources not in "/oauth/**" are protected (but no specific rules about scopes are given, for instance).
     * You also get an OAuth2WebSecurityExpressionHandler by default.
     */
    @Override
    public void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
                http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests().anyRequest().permitAll();
        // @formatter:on                
    }

    @Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
    }
    
	/**
	 * The JWT token store, is not actually a store since it does not store anything.
	 * It is a handle to the Trust store used in case of a Assymetric key encryption.
	 * If commented out the resource server spits out a Invalid token error even for a valid request.
	 * @return
	 */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * This is the Token Converter that decrypts the token and fetches the information required for Authorization.
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
    	System.out.println(" -- In accessTokenConverter");
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setAccessTokenConverter(customAccessTokenConverter);
	     final Resource resource = new ClassPathResource("public.txt");
	     String publicKey = null;
	     try {
	    	 publicKey = IOUtils.toString(resource.getInputStream());
	     } catch (final IOException e) {
	    	 throw new RuntimeException(e);
	     }
     
        converter.setVerifierKey(publicKey);
         
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
}