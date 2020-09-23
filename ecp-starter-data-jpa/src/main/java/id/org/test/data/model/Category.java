package id.org.test.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import id.org.test.common.base.ReferenceBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "category")
@Data
@EqualsAndHashCode(callSuper = false)
public class Category extends ReferenceBase {

	private static final long serialVersionUID = 7068429436948637109L;
	
	@Column(name = "CATEGORY_NAME", nullable = false)
	private String categoryName;

	@OneToOne
	@JoinColumn(name = "MEMBER_ID", nullable = false)
	private Member member;

}
