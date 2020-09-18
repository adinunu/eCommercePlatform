package id.org.test.ms.shared.auth;

import java.io.Serializable;

import lombok.Data;

@Data
public class OAuthAccessTokenDTO implements Serializable {

	private static final long serialVersionUID = -5142572683970031630L;

	private String tokenId;
	private String userName;
	private String clientId;

}
