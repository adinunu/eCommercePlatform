package id.org.test.ms.shared.mobile;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SignUpCDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String businessName;
	private String businessSubDomain;
	private String website;
	private String mailRegistrant;
	private String password;
	private String activationCode;
	private Boolean activated;
	private String numberOfStore;
	private String firstName;
	private String lastName;
	private String city;
	private String phoneNum;
	private Long referralId;

}