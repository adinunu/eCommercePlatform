package id.org.test.data.service.menu.wrapper;

import java.io.Serializable;

import lombok.Data;

@Data
public class CrudControlAccessWrapper implements Serializable {

	private static final long serialVersionUID = 2736055325217693236L;

//	private Menu menu;
	private boolean canAdd;
	private boolean canEdit;
	private boolean canDelete;
	private boolean canList;
	private boolean canPrint;
	private boolean canVoid;
	
	// we gonna make this right in the future
	public static CrudControlAccessWrapper build() {
		CrudControlAccessWrapper crudAccess = new CrudControlAccessWrapper();
		crudAccess.setCanAdd(true);
		crudAccess.setCanEdit(true);
		crudAccess.setCanDelete(true);
		crudAccess.setCanList(true);
		crudAccess.setCanPrint(true);
		crudAccess.setCanVoid(true);
		return crudAccess;
	}

}
