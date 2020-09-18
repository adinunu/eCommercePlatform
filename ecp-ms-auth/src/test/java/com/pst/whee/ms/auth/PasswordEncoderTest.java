package com.pst.whee.ms.auth;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {
	
	public static void main(String[] args) {
		
		
		PasswordEncoder encode = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		String encoded = encode.encode("K1NGSTON");
		
//		final String idDefaultPasswordEncoder = "bcrypt";
//		final BCryptPasswordEncoder defaultPasswordEncoder = new BCryptPasswordEncoder(11, new SecureRandom("K1NGSTON".getBytes()));
//		
//		final Map<String, PasswordEncoder> encoders = new HashMap<>();
//		encoders.put(idDefaultPasswordEncoder, defaultPasswordEncoder);
//		
//		final DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idDefaultPasswordEncoder, encoders);
//		String encoded = passwordEncoder.encode("roof1access");
		System.out.println(encoded);
		
		
//		String toHash = "cibi@gmail.com|tokocibi";
		
//		int startDigit = 19;
//		long idOtp = 100;
//		String toHash = UUID.randomUUID().toString().replaceAll("\\-", "");
//		System.out.println(toHash);
//		System.out.println(toHash.substring(0, 19));
//		System.out.println(toHash.substring(19));
//		System.out.println(toHash.substring(0, 19) + idOtp + toHash.substring(19) + "|");
//		System.out.println(toHash.length());
		
		//816b0698-d90b-4b63-8716-db1eaeccf9f9
		//3395531a-653d-42ec-b64d-c968e3ae2afa
		
	}

}
