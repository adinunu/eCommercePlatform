package id.org.test.ms.shared.auth;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class UserDTO implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 3239721137106932899L;
	
	private Long no;
	private String username;
	private String email;
	private String roles;
	private String password;

}
