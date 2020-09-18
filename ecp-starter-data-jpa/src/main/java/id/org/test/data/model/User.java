package id.org.test.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = false)
public class User {

	@Id
	@Column(name = "USERNAME", length = 100, unique = true)
	private String username;

	@Column(name = "PASSWORD", length = 100)
	private String password;

	@Column(name = "ENABLED")
	private Boolean enabled;

	@Column(name = "ROLE")
	private String role;
	
	public Boolean isEnabled(){
		return enabled;
	}

}
