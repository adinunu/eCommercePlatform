package id.org.test.data.service.wrapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import id.org.test.common.wrapper.ReferenceBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductWrapper extends ReferenceBaseWrapper {
	
	private static final long serialVersionUID = -4294220440166923375L;
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
	private String imageToReplace;
	private MultipartFile imageFile;
	private String productInitials;
	private String productType;

	private Long accountId;
	private String accountName;
	private Boolean trackInventoryFlag;

	private ProductStandardWrapper productStandardWrapper;
	private List<ProductVariantWrapper> productVariantWrapperList = new ArrayList<>();

	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

	public void addProductVariantWrapper(ProductVariantWrapper productVariantWrapper) {
		if (productVariantWrapperList == null) {
			productVariantWrapperList = new ArrayList<>();
		}
		productVariantWrapperList.add(productVariantWrapper);
	}
	
	private String status;
}
