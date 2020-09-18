package id.org.test.ms.shared.mobile;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class CategoryVDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8466284791518624552L;
	
	private Long id;
	private String categoryName;
	private String description;
	private Long accountId;
}
