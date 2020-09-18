package id.org.test.data.service.wrapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ProductStandardWrapper {
    private String productStandardSku;
    private String productStandardBarcode;
    private BigDecimal productStandardSupplyPrice;
    private BigDecimal productStandardRetailPrice;
    private String productStandardImage;
    private List<ProductStoreWrapper> productStoreWrapperList = new ArrayList<>();
    public void addProductStoreWrapper(ProductStoreWrapper productStoreWrapper){
        if(productStoreWrapperList ==  null){
            productStoreWrapperList = new ArrayList<>();
        }
        productStoreWrapperList.add(productStoreWrapper);
    }

}
