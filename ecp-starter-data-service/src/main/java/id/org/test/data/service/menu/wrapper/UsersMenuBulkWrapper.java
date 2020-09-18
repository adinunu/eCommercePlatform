package id.org.test.data.service.menu.wrapper;

import id.org.test.common.wrapper.AuditableBaseWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UsersMenuBulkWrapper extends AuditableBaseWrapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4832217346369622224L;

	private String username;

	private Long userMenuForListId;
	private Long menuForListId;
	private String menuForList;
	private String urlForList;
	private Boolean urlForListActive;

	private Long userMenuForAddId;
	private Long menuForAddId;
	private String menuForAdd;
	private String urlForAdd;
	private Boolean urlForAddActive;

	private Long userMenuForEditId;
	private Long menuForEditId;
	private String menuForEdit;
	private String urlForEdit;
	private Boolean urlForEditActive;

	private Long userMenuForDeleteId;
	private Long menuForDeleteId;
	private String menuForDelete;
	private String urlForDelete;
	private Boolean urlForDeleteActive;

	private Long userMenuForInsertId;
	private Long menuForInsertId;
	private String menuForInsert;
	private String urlForInsert;
	private Boolean urlForInsertActive;

	private Long userMenuForUpdateId;
	private Long menuForUpdateId;
	private String menuForUpdate;
	private String urlForUpdate;
	private Boolean urlForUpdateActive;

}
