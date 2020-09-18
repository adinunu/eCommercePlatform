package id.org.test.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "authorities", uniqueConstraints = @UniqueConstraint(columnNames = { "USERNAME", "AUTHORITY" }))
@Data
public class Authorities {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", length = 30)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "USERNAME", nullable = false)
	private User user;

	@Column(name = "AUTHORITY", length = 30, nullable = false)
	private String authority;

}
