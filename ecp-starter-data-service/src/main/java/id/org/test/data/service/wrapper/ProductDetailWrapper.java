package id.org.test.data.service.wrapper;

import java.math.BigDecimal;

import id.org.test.common.wrapper.ReferenceBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDetailWrapper extends ReferenceBaseWrapper {

	private static final long serialVersionUID = -6903696030722795399L;
	private Long productId;
	private String productName;
	private Long productReferenceId;
	private Integer quantity;
	private BigDecimal supplyPrice;

}
