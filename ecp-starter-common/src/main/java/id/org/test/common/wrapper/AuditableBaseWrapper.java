package id.org.test.common.wrapper;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuditableBaseWrapper extends EntityBaseWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7166805811572031141L;

	@JsonIgnore
	private String createdBy;

	private Date createdDate;

	@JsonIgnore
	private String updatedBy;

	private Date updatedDate;

}
