package id.org.test.ms.shared.mobile;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountVDTO implements Serializable{

	private static final long serialVersionUID = 3765758391621780400L;
	
	private Long id;
	private String fullName;
	private String email;
	private String province;
	private String district;
	private String subDistrict;
	private String village;
	private String zipCode;
	private String address;
	private Long provinceId;
	private Long districtId;
	private Long subDistrictId;
	private Long villageId;
	private String timezone;
}