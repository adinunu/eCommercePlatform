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
public class ProductCompositeWrapper extends ReferenceBaseWrapper {

	private static final long serialVersionUID = 5137775728395428003L;
	
	private String productName;
    private String productHandle;
    private String barcode;
    private String sku;
    private Boolean displayFlag;
    private BigDecimal supplyPrice;
    private BigDecimal retailPrice;
    private Boolean isComposite;
    private Boolean hasVariant;
    private String productType; //atomic or composite //if null is atomic
    private String variantName;
    private String variantValue;

    private Long categoryId;
    private String categoryName;
    private String productTags;
    private String generalStatus;
    private String image;
	private String imageToReplace;
	private MultipartFile imageFile;

    private Long accountId;
    private String accountName;
    private Boolean trackInventoryFlag;

    private List<ProductDetailWrapper> productDetailList = new ArrayList<>();
    
	public MultipartFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}

    public void addProductDetail(ProductDetailWrapper productDetail) {
        if (productDetailList == null) {
            productDetailList = new ArrayList<>();
        }
        productDetailList.add(productDetail);
    }
    
    public Boolean getComposite() {
    	return isComposite;
    }
    
    public void setComposite(Boolean composite) {
        isComposite = composite;
    }

}
