package id.org.test.data.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import id.org.test.common.base.ReferenceBase;
import id.org.test.common.constant.GeneralStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "product")
@Data
@EqualsAndHashCode(callSuper = false)
public class Product extends ReferenceBase {
	
	private static final long serialVersionUID = -2689796925871071980L;
	
	@Column(name = "PRODUCT_NAME", length = 100, nullable = false)
	private String productName;

	@Column(name = "SKU", length = 30)
	private String sku;

	@Column(name = "DISPLAY_FLAG")
	private Boolean displayFlag;

	@Column(name = "IS_COMPOSITE")
	private Boolean composite;

	@Column(name = "HAS_VARIANT")
	private Boolean hasVariant;

	@Column(name = "VARIANT_NAME", length = 100)
	private String variantOptionName;

	@Column(name = "VARIANT_VALUE", length = 100)
	private String variantOptionValue;

	@Column(name = "PRODUCT_HANDLE", length = 100, nullable = false)
	private String productHandle;

	@Column(name = "BARCODE")
	private String barcode;

	@Column(name = "SUPPLIER_PRICE", length = 50)
	private BigDecimal supplyPrice;

	@Column(name = "RETAIL_PRICE", length = 50)
	private BigDecimal retailPrice;

	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID", nullable = false)
	private Category productCategory;

	@Column(name = "STATUS", length = 15)
	private GeneralStatus generalStatus;

	@OneToOne
	@JoinColumn(name = "ACCOUNT_ID", nullable = false)
	private Member account;

	@Column(name = "IMAGE", length = 500)
	private String image;

	@Column(name = "TRACK_INVENTORY_FLAG")
	private Boolean trackInventoryFlag;


}
