package com.blogspot.sgdev.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Utils {

	
	public static String getFileContentsFromClassPath(String file) {
    	InputStream in = Utils.class.getResourceAsStream(file);
    	String body = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));
    	return body;	
	}
	
	public static boolean isEmpty(String s) {
		return (s == null) ? true : s.isEmpty();		
	}
	
}
