package id.org.test.data.service.wrapper;

import id.org.test.common.wrapper.ReferenceBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MemberWrapper extends ReferenceBaseWrapper {

	private static final long serialVersionUID = -1086317603526979307L;

	private String firstName;
	private String lastName;
	private String birthDate;
	private String gender;
	private String mobile;
	private String email;
	// spring sec area
	private String username;
	private String password;
	private boolean enabled;

}
