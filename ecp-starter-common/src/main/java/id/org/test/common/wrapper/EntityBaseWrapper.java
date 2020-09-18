package id.org.test.common.wrapper;

import java.io.Serializable;

import lombok.Data;

@Data
public class EntityBaseWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7965335950158935110L;

	private Long id;
	private String description;

}
