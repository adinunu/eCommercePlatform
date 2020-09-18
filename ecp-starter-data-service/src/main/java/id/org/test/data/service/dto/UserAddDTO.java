package id.org.test.data.service.dto;

import lombok.Data;

@Data
public class UserAddDTO {
	
	private String username;
	private String email;
	private String mobile;
	private String password;
	private Long accountId;
	private Long storeId;

}
