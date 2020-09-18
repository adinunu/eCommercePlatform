package id.org.test.data.service.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PublicRegistrationRDTO implements Serializable {

	private Long id;
	private Date registerDate;
	private String mailRegistrant;
	private String businessSubDomain;
	private Boolean activated;
	private String phoneNum;
	private String city;
	
	private String mailReferral;
}
