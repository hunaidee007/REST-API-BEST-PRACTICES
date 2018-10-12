package com.blogspot.sgdev.blog;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;


import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrantByClientCredentialTest {

    @Value("${local.server.port}")
    private int port;

 
    
    //@Test
    public void accessProtectedResourceByJwtToken() throws JsonParseException, JsonMappingException, IOException, JSONException {
        

    	ResponseEntity<String>  response = new TestRestTemplate("trusted-app", "secret").postForEntity("http://localhost:" + "8080" + "/spring-security-oauth-server/oauth/token?client_id=trusted-app&grant_type=client_credentials", null, String.class);
        String responseText = response.getBody();
        System.out.println("-- Printing Response");
       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        HashMap jwtMap = new ObjectMapper().readValue(responseText, HashMap.class);
        String accessToken = (String) jwtMap.get("access_token");
        
        Jwt jwtToken = JwtHelper.decode(accessToken);

        String claims = jwtToken.getClaims();
        System.out.println("-- Printing Claims fomr Token");
       
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

       
        response = new TestRestTemplate().exchange("http://localhost:" + port + "/spring-security-oauth-resource/trusted_client", HttpMethod.GET, new HttpEntity<>(null, headers), String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("---------- : " +response.getBody());

/*        response = new TestRestTemplate().exchange("http://localhost:" + port + "/resources/roles", HttpMethod.GET, new HttpEntity<>(null, headers), String.class);
        assertEquals("[{\"authority\":\"ROLE_TRUSTED_CLIENT\"}]", response.getBody());
        
        response = new TestRestTemplate().exchange("http://localhost:" + port + "/resources/trusted_client", HttpMethod.GET, new HttpEntity<>(null, headers), String.class);
        //;(HttpStatus.UNAUTHORIZED, response.getStatusCode());
*/

    }

}