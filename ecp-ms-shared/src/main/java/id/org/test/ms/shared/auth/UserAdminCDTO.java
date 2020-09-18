package id.org.test.ms.shared.auth;

import id.org.test.common.wrapper.ReferenceBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserAdminCDTO {

	private String username;
	private String email;
	private String mobile;
	private String password;
    private boolean enabled;
    private String role;
    private String confirmPassword;
}
