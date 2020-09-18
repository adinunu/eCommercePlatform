package id.org.test.common.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;

@Data
@MappedSuperclass
@QueryEntity
public class EntityBase implements Serializable {

	private static final long serialVersionUID = 6363188823277644613L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", length = 30)
	private Long id;

	@Column(name = "DESCRIPTION", length = 500)
	private String description;

}
