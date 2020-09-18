package id.org.test.ms.shared.auth;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UsersInquiryDTO implements Serializable {

	private static final long serialVersionUID = -5142572683970031630L;

	private Long id;
	private String response;
	private String userName;
	private String password;


}
