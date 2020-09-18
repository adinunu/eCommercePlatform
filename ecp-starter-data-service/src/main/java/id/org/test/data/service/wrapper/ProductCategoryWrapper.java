package id.org.test.data.service.wrapper;

import id.org.test.common.wrapper.AuditableBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductCategoryWrapper extends AuditableBaseWrapper {
    
	private static final long serialVersionUID = -5660705701454226760L;
	
	private String categoryName;
    private Long storeId;
    private String storeName;
    private String status;

}
