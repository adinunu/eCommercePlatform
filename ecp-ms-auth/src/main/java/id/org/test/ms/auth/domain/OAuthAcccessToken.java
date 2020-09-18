package id.org.test.ms.auth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "OAUTH_ACCESS_TOKEN")
@Data
public class OAuthAcccessToken {

	@Id
	@Column(name = "TOKEN_ID", length = 191)
	private String tokenId;

	@Lob
	@Column(name = "TOKEN")
	private byte[] token;

	@Column(name = "AUTHENTICATION_ID")
	private String authenticationId;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "CLIENT_ID")
	private String clientId;

	@Lob
	@Column(name = "AUTHENTICATION")
	private byte[] authentication;

	@Column(name = "REFRESH_TOKEN")
	private String refreshToken;

}
