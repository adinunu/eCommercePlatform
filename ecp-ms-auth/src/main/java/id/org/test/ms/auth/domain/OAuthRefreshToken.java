package id.org.test.ms.auth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "OAUTH_REFRESH_TOKEN")
@Data
public class OAuthRefreshToken {

	@Id
	@Column(name = "TOKEN_ID", length = 191)
	private String tokenId;

	@Lob
	@Column(name = "TOKEN")
	private byte[] token;

	@Lob
	@Column(name = "AUTHENTICATION")
	private byte[] authentication;

}
