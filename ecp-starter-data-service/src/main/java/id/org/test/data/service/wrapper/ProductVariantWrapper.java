package id.org.test.data.service.wrapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import id.org.test.common.wrapper.ReferenceBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductVariantWrapper extends ReferenceBaseWrapper {

	private static final long serialVersionUID = -4945053723720771453L;
	
	private List<ProductStoreWrapper> productStoreWrapperList = new ArrayList<>();
    private String variantOptionName;
    private String variantOptionValue;
    private String productVariantSku;
    private String productVariantBarcode;
    private BigDecimal productVariantSupplyPrice;
    private BigDecimal productVariantRetailPrice;
    private String productVariantImage;

    public void addProductStoreWrapper(ProductStoreWrapper productStoreWrapper){
        if(productStoreWrapperList == null){
            productStoreWrapperList = new ArrayList<>();
        }
        productStoreWrapperList.add(productStoreWrapper);
    }

}
