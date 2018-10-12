package com.blogspot.sgdev.blog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.SpringBootTest;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GrantByResourceOwnerPasswordCredentialTest{ 

    @Value("${local.server.port}")
    private int port;

 



    //@Test
    public void accessProtectedResourceByJwtTokenForUser() throws JsonParseException, JsonMappingException, IOException {
        ResponseEntity<String> response = new TestRestTemplate().getForEntity("http://localhost:" + port + "/spring-security-oauth-resource/admin", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        response = new TestRestTemplate("trusted-app", "secret").postForEntity("http://localhost:" + "8080" + "/spring-security-oauth-server/oauth/token?grant_type=password&username=admin&password=nimda", null, String.class);
        String responseText = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        HashMap jwtMap = new ObjectMapper().readValue(responseText, HashMap.class);
        String accessToken = (String) jwtMap.get("access_token");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        response = new TestRestTemplate().exchange("http://localhost:" + port + "/spring-security-oauth-resource/admin", HttpMethod.GET, new HttpEntity<>(null, headers), String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

       
    }

  

}
