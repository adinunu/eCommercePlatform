package id.org.test.data.service.wrapper;

import lombok.Data;

@Data
public class ProductStoreWrapper {
	private Long productInventoryId; // id of product Inventory
	private Long storeId;
	private String storeName;
	private Integer stock;
	private Integer reorderPoint;

}
