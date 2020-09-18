package id.org.test.ms.auth.domain;

import static java.util.Objects.isNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import lombok.Data;

@Entity
@Table(name = "AU_CLIENT_DETAILS")
@Data
public class CustomClientDetails implements ClientDetails {

	private static final long serialVersionUID = 2685734852431856338L;
	private static final String SEQ_GEN = "seq_gen_cl_dtl";
	private static final String SEQ = "seq_cl_dtl";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = SEQ_GEN)
	@SequenceGenerator(name = SEQ_GEN, sequenceName = SEQ)
	@Column(name = "NO")
	private Long no;

	@Column(name = "CLIENT_ID", unique = true, length = 191)
	private String clientId;

	@Column(name = "CLIENT_SECRET")
	private String clientSecret;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "AU_CLIENT_SCOPE", joinColumns = @JoinColumn(name = "CLIENT_NO"))
	@Column(name = "SCOPE")
	private Set<String> scope = new HashSet<>();//Collections.emptySet();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "AU_CLIENT_RES_ID", joinColumns = @JoinColumn(name = "CLIENT_NO"))
	@Column(name = "RESOURCE_ID")
	private Set<String> resourceIds = new HashSet<>();//Collections.emptySet();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "AU_CLIENT_AUTH_GRANT_TYPE", joinColumns = @JoinColumn(name = "CLIENT_NO"))
	@Column(name = "AUTHORIZED_GRANT_TYPE")
	private Set<String> authorizedGrantTypes = new HashSet<>();//Collections.emptySet();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "AU_CLIENT_REG_REDIR_URI", joinColumns = @JoinColumn(name = "CLIENT_NO"))
	@Column(name = "REGISTERED_REDIRECT_URI")
	private Set<String> registeredRedirectUris = new HashSet<>();//Collections.emptySet();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "AU_CLIENT_AUTHORITIES", joinColumns = @JoinColumn(name = "CLIENT_NO"))
	@Column(name = "AUTHORITY")
	private Set<String> authoritiesString = new HashSet<>();//Collections.emptySet();

	@ElementCollection
	@CollectionTable(name = "AU_CLIENT_AUTO_APRV_SCOPE", joinColumns = @JoinColumn(name = "CLIENT_NO"))
	@Column(name = "AUTO_APPROVE_SCOPE")
	private Set<String> autoApproveScopes = new HashSet<>();//Collections.emptySet();

	@Column(name = "ACC_TOKEN_VAL_SEC")
	private Integer accessTokenValiditySeconds;

	@Column(name = "REF_TOKEN_VAL_SEC")
	private Integer refreshTokenValiditySeconds;

	@Transient
	private Set<GrantedAuthority> authorities = new HashSet<>();

	@Transient
	private Map<String, Object> additionalInformation = new LinkedHashMap<>();

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValiditySeconds;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return additionalInformation;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		authorities.clear();
		for (String role : authoritiesString) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public String getClientSecret() {
		return clientSecret;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return registeredRedirectUris;
	}

	@Override
	public Set<String> getResourceIds() {
		return resourceIds;
	}

	@Override
	public Set<String> getScope() {
		return scope;
	}

	@Override
	public boolean isAutoApprove(final String scope) {
		if (isNull(autoApproveScopes)) {
			return false;
		}
		for (String auto : autoApproveScopes) {
			if ("true".equals(auto) || scope.matches(auto)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isScoped() {
		return this.scope != null && !this.scope.isEmpty();
	}

	@Override
	public boolean isSecretRequired() {
		return this.clientSecret != null;
	}

	public void setAuthoritiesString(Set<String> authoritiesString) {
		this.authoritiesString = authoritiesString;
		this.authorities.clear();
		for (String role : authoritiesString) {
			this.authorities.add(new SimpleGrantedAuthority(role));
		}
	}

	public void addScopes(String... scopes) {
		for (String scope : scopes) {
			this.scope.add(scope);
		}
	}

}
