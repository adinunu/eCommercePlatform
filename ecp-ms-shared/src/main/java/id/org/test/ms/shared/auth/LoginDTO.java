package id.org.test.ms.shared.auth;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class LoginDTO implements Serializable {

	private static final long serialVersionUID = -1290197734961188195L;

	private String username;
	private String password;

}
