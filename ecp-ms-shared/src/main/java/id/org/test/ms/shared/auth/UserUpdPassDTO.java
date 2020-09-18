package id.org.test.ms.shared.auth;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserUpdPassDTO implements Serializable {

	private static final long serialVersionUID = -6017535286631322662L;

	private String username;
	private String password;

}
