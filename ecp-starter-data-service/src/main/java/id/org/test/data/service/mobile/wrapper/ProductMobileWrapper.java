package id.org.test.data.service.mobile.wrapper;

import java.math.BigDecimal;

import id.org.test.common.wrapper.ReferenceBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductMobileWrapper extends ReferenceBaseWrapper {

	private static final long serialVersionUID = 3067060172536225511L;

	private Long accountId;
	private Long productId;
	private String productName;
	private String productHandle;
	private String productType;
	private String productInitials;
	private Long categoryId;
	private String categoryName;
	private Long supplierId;
	private String supplierName;
	private String status;
	private String image;
	private BigDecimal supplyPrice;
	private BigDecimal retailPrice;
	private Boolean trackInventoryFlag;

}
