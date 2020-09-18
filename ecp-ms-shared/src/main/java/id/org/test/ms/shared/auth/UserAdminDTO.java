package id.org.test.ms.shared.auth;

import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserAdminDTO {

	private Long no;
	private String username;
	private String email;
	private String mobile;
	private String password;
    private boolean enabled;
    private Set<String> roles;
    private String role;
}
