package id.org.test.data.service.mobile.wrapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class UserMobileWrapper {

	private String username;

	private String password;

	public List<GrantedAuthority> roles = new ArrayList<>();

	public UserMobileWrapper() {
	}

}
