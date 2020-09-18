package id.org.test.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import id.org.test.common.base.ReferenceBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "user_token")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserToken extends ReferenceBase {

	private static final long serialVersionUID = 7068429436948637109L;

	@OneToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column(name = "TOKEN")
	private String token;

}
