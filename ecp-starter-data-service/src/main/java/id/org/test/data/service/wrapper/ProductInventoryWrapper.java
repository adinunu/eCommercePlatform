package id.org.test.data.service.wrapper;

import java.math.BigDecimal;

import id.org.test.common.wrapper.ReferenceBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductInventoryWrapper extends ReferenceBaseWrapper {

	private static final long serialVersionUID = 3236368400707234557L;
	private Long productId;
	private String productName;
	private Boolean trackInventoryFlag;
	private Long storeId;
	private String storeName;
	private Integer stock;
	private Integer reorderPoint;

	// used for orderStock
	private String productSku;
	private BigDecimal productSupplyPrice;
	private BigDecimal productRetailPrice;

}
