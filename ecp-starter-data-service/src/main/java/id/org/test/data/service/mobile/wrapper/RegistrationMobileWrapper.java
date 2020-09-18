package id.org.test.data.service.mobile.wrapper;

import id.org.test.common.wrapper.ReferenceBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RegistrationMobileWrapper extends ReferenceBaseWrapper {

	private static final long serialVersionUID = -5475206648963830024L;
	private String businessName;
	private String businessSubDomain;
	private String website;
	private String businessType;
	private String mailRegistrant;
	private String password;
	private String activationCode;
	private Boolean activated;
	private String numberOfStore;
	private String firstName;
	private String lastName;
	private String city;
	private String phoneNum;
	
	@Override
	public String toString() {
		return "RegistrationMobileWrapper [businessName=" + businessName + ", businessSubDomain=" + businessSubDomain
				+ ", website=" + website + ", businessType=" + businessType + ", mailRegistrant=" + mailRegistrant
				+ ", password=" + password + ", activationCode=" + activationCode + ", activated=" + activated
				+ ", numberOfStore=" + numberOfStore + ", firstName=" + firstName + ", lastName=" + lastName + ", city="
				+ city + ", phoneNum=" + phoneNum + "]";
	}

}
