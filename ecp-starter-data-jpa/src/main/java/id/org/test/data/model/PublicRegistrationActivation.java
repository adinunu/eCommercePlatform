package id.org.test.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import id.org.test.common.base.AuditableBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "public_registration")
@Data
@EqualsAndHashCode(callSuper = false)
public class PublicRegistrationActivation extends AuditableBase {

	private static final long serialVersionUID = 3656948201295782339L;

	@Column(name = "BUSINESS_NAME", length = 35, unique = true, nullable = false)
	private String businessName;

	@Column(name = "BUSINESS_SUB_DOMAIN", nullable = true)
	private String businessSubDomain;

	@Column(name = "WEBSITE")
	private String website;


	/**
	 * this will be username combine with bussiness name
	 */
	@Column(name = "MAIL_REGISTRANT", length = 100, nullable = false, unique = true)
	private String mailRegistrant;

	@Column(name = "PASSWORD", length = 100, nullable = false)
	private String password;

	@Column(name = "ACTIVATION_CODE", nullable = false, unique = true)
	private String activationCode;

	@Column(name = "ACTIVATED", nullable = false)
	private Boolean activated;

	@Column(name = "NUMBER_OF_STORE", nullable = false)
	private String numberOfStore;

	@Column(name = "FIRST_NAME", length = 100)
	private String firstName;

	@Column(name = "LAST_NAME", length = 100)
	private String lastName;

	@Column(name = "CITY")
	private String city;

	@Column(name = "PHONE_NO", length = 30)
	private String phoneNum;
	
	@Column(name = "REF_ID")
	private Long referralId;
	
	@Lob
	@Column(name = "ADATA")
	private String adata;

	@Column(name = "LAST_STEP", length = 6)
	private String lastStep;
}
