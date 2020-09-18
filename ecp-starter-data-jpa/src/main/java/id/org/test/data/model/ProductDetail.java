package id.org.test.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import id.org.test.common.base.AuditableBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "product_detail")
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductDetail extends AuditableBase {
	
	private static final long serialVersionUID = 4899427143136478539L;

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product product; // master of product composite
	
	@ManyToOne
	@JoinColumn(name = "PRODUCT_REF_ID", nullable = false)
	private Product productReference; // ProductReference

	@Column(name = "PRODUCT_QTY", nullable = false, length = 5)
	private Integer quantity;

}
