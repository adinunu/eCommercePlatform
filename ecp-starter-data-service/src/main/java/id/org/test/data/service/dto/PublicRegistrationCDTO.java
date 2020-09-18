package id.org.test.data.service.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PublicRegistrationCDTO implements Serializable {
	
	private static final long serialVersionUID = 1354948364369956014L;
	
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
	private String rid; //referralId encoded
	private String rrc; //referralCode
	private String rhc; //referralHashCode
	private String adata; //additionalData for tracking purpose

}
