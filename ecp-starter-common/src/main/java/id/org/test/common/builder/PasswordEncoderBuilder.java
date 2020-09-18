package id.org.test.common.builder;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderBuilder {
	
	public static DelegatingPasswordEncoder build() {

		final String idDefaultPasswordEncoder = "bcrypt";
		final BCryptPasswordEncoder defaultPasswordEncoder = new BCryptPasswordEncoder(11, new SecureRandom("WheeAr3Th3Ch4mPi0ns19".getBytes()));
		
		final Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(idDefaultPasswordEncoder, defaultPasswordEncoder);
		
		final DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(idDefaultPasswordEncoder, encoders);
		
		return passwordEncoder;
		
	}

}
