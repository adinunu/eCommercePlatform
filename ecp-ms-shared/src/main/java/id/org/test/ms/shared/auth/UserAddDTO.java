package id.org.test.ms.shared.auth;

import lombok.Data;

@Data
public class UserAddDTO {
	
	private String username;
	private String email;
	private String mobile;
	private String password;
	private Long memberId;

}
