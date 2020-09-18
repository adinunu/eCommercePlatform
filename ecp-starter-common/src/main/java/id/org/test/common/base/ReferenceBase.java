package id.org.test.common.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.Version;

import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
@QueryEntity
public class ReferenceBase extends AuditableBase {

	/**
	 * 	
	 */
	private static final long serialVersionUID = 2503283693126355325L;

	@Version
	@Column(name = "VERSION", length = 10)
	private long version;

	@Column(name = "IS_DELETED", length = 1)
	private int deleted;

}