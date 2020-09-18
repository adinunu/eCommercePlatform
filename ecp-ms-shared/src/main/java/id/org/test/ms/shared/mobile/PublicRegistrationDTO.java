package id.org.test.ms.shared.mobile;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class PublicRegistrationDTO implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 2999660183320159802L;

	private Long id;
	private String firstName;
	private String lastName;
	private String businessName;
	private String businessSubDomain;
	private String website;
	private String mailRegistrant;
	private String password;
	private String activationCode;
	private Boolean activated;
	private String numberOfStore;
	private String city;
	private String phoneNum;
	private Long referralId;
	private String adata;
	private String fullName;
	private String lastStep;
	private String mailRef;
	private Date createdDate;
	private Boolean deleted;
	
	//@Setter

	public String getFullName() {
		fullName="-";
		if(StringUtils.isNotEmpty(this.firstName)) {
		fullName = this.firstName;
		}
		if(StringUtils.isNotEmpty(this.lastName)) {
		fullName +=" "+this.lastName;
		}
		return fullName;
	}
	
	
}
