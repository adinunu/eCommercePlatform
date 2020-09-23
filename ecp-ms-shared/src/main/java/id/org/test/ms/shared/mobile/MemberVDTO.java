package id.org.test.ms.shared.mobile;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MemberVDTO implements Serializable{

	private static final long serialVersionUID = 3765758391621780400L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private String gender;
	private String email;
	private String mobile;
	private String birthDate;
	
}