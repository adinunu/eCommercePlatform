package id.org.test.ms.auth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import id.org.test.common.base.EntityID;
import id.org.test.ms.shared.auth.UserRole;
import lombok.Data;

@Entity
@Table(name = "AU_ROLE")
@Data
public class Role implements EntityID<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = SEQ_GEN_NATIVE)
	@GenericGenerator(name = SEQ_GEN_NATIVE, strategy = SEQ_GEN_NATIVE)
	@Column(name = "ID")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "NAME", length = 64, unique = true, nullable = false)
	private UserRole name;

}
