package id.org.test.data.service.wrapper;

import java.math.BigDecimal;

import id.org.test.common.wrapper.ReferenceBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductMobileWrapper extends ReferenceBaseWrapper {

	private static final long serialVersionUID = 3067060172536225511L;

	private Long memberId;
	private String productName;
	private String productCode;
	private String productDescription;
	private String storeName;
	private String brandName;
	private Long categoryId;
	private String categoryName;
	private String status;
	private BigDecimal productPrice;

}
