package id.org.test.data.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import id.org.test.common.base.ReferenceBase;
import id.org.test.common.constant.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "member")
@Data
@EqualsAndHashCode(callSuper = false)
public class Member extends ReferenceBase {

	private static final long serialVersionUID = -2676966819941408208L;

	@OneToOne
	@JoinColumn(name = "USER", nullable = false)
	private User user;

	@Column(name = "FIRST_NAME", length = 100)
	private String firstName;

	@Column(name = "LAST_NAME", length = 100)
	private String lastName;

	@Column(name = "BIRTH_DATE")
	private Date birthDate;

	@Column(name = "GENDER", length = 8)
	private Gender gender;

	@Column(name = "MOBILE", length = 20)
	private String mobile;

	@Column(name = "EMAIL", length = 100)
	private String email;

}
