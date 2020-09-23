package id.org.test.data.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import id.org.test.common.base.ReferenceBase;
import id.org.test.common.constant.GeneralStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "product")
@Where(clause = "IS_DELETED=false")
@Data
@EqualsAndHashCode(callSuper = false)
public class Product extends ReferenceBase {
	
	private static final long serialVersionUID = -2689796925871071980L;
	
	@Column(name = "PRODUCT_NAME", length = 100, nullable = false)
	private String productName;
	
	@Column(name = "PRODUCT_CODE", length = 100, nullable = false)
	private String productCode;

	@Column(name = "PRODUCT_PRICE", length = 50)
	private BigDecimal productPrice;
	
	@Column(name = "STORE_NAME", length = 100)
	private String storeName;
	
	@Column(name = "BRAND_NAME", length = 100)
	private String brandName;

	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID", nullable = false)
	private Category productCategory;

	@Column(name = "STATUS", length = 15)
	private GeneralStatus generalStatus;

	@OneToOne
	@JoinColumn(name = "MEMBER_ID", nullable = false)
	private Member member;


}
