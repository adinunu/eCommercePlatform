package id.org.test.ms.shared.mobile;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CategoryCDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8466284791518624552L;
	
	private Long id;
	@ApiModelProperty(notes= "Nama Kategori",example= "makanan")
	private String categoryName;
	@ApiModelProperty(notes="Deskripsi Kategori" ,example="ini makanan")
	private String description;
	private Long accountId;
	
}
