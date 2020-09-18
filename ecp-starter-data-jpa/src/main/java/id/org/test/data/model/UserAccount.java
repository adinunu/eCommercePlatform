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
@Table(name = "user_account")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserAccount extends ReferenceBase {

	private static final long serialVersionUID = -4994270313451974110L;

	@Column(name = "FIRST_NAME", length = 100)
	private String firstName;

	@Column(name = "LAST_NAME", length = 100)
	private String lastName;

	@Column(name = "NICK_NAME", length = 100)
	private String nickName;

	@Column(name = "AGE", length = 5)
	private Integer accountAge;

	@Column(name = "SEX", length = 15)
	private String accountSex;

	@Column(name = "ADDRESS", length = 4999)
	private String accountAddress;

	@Column(name = "CONTACT_NO", length = 50)
	private String accountContactNo;

	@Column(name = "EMAIL", length = 120)
	private String accountEmail;

	@OneToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

}
