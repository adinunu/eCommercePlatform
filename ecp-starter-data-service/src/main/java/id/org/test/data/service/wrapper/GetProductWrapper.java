package id.org.test.data.service.wrapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import id.org.test.common.wrapper.ReferenceBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GetProductWrapper extends ReferenceBaseWrapper {

	private static final long serialVersionUID = 9139940364376332606L;
	
	private Long productId;
	private String productName;
	private String productHandle;
	private String barcode;
	private String sku;
	private Boolean displayFlag;
	private BigDecimal supplyPrice;
	private BigDecimal retailPrice;
	private Boolean hasVariant;
	private String variantName;
	private String variantValue;

	private Long categoryId;
	private String categoryName;
	private String productTags;
	private Long brandId;
	private String brandName;
	private Long supplierId;
	private String supplierName;
	private String generalStatus;
	private String image;
	private String productInitials;

	private Long accountId;
	private String accountName;
	private Boolean trackInventoryFlag;
	private Boolean emptyNearEmpty;
	
	List<ProductInventoryWrapper> productInventory = new ArrayList<>();
}
