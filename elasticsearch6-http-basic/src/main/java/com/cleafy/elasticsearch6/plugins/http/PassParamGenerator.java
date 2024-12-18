package com.cleafy.elasticsearch6.plugins.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.Scanner;

import com.cleafy.elasticsearch6.plugins.http.utils.HexBin;

public class PassParamGenerator {

    private static final String DIGEST_ALGORITHM = "SHA-256";
    private static final String allowed_string = "only a-z A-Z 0-9 !@-+_= allowed, no space, max length 10";
	public static void main(String[] args) throws Exception {
		String inputPass = getSthFromStdin("input the password, "+ allowed_string + ":");
		String pattern = "^[a-zA-Z0-9!@\\-+_=]{1,10}$";
		if(! inputPass.matches(pattern)) {
			throw new RuntimeException("invalid input, please check, " + allowed_string);
		}
		
		MessageDigest digester = MessageDigest.getInstance(DIGEST_ALGORITHM);
        byte[] internalPasswordHash = digester.digest(inputPass.getBytes("UTF-8"));
        String passHashHex = HexBin.encode(internalPasswordHash);
		System.out.println("write the following parameter into elasticsearch.yml, using key: http.basic.password");
		System.out.println(passHashHex);
	}
	
    public static String getSthFromStdin(String prompt) {
        String sth = null;
        
        try {
            System.out.println(prompt);
            sth =
                new BufferedReader(new InputStreamReader(System.in))
                    .readLine()
                    .trim();
            if(sth == null || sth.equals(""))
                throw new RuntimeException("input can not be empty");
            
        } catch (Exception e2) {
        	System.out.println("input error:" + e2);
        	e2.printStackTrace();
            throw new RuntimeException("io exception");
        }

        return sth;
    }
	
}
