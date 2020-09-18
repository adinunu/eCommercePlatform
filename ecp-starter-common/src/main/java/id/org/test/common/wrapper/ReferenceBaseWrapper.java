package id.org.test.common.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReferenceBaseWrapper extends AuditableBaseWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9184196028182656836L;

	@JsonIgnore
	private long version;

	@JsonIgnore
	private int deleted;

}
