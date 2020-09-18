package id.org.test.ms.shared.auth;

//import com.pst.whee.core.security.domain.Menu;

public class CrudControlAccessWrapper {
//    private Menu menu;
    private boolean canAdd;
    private boolean canEdit;
    private boolean canDelete;
    private boolean canList;
    private boolean canPrint;
    private boolean canVoid;

//    public Menu getMenu() {
//        return menu;
//    }

//    public void setMenu(Menu menu) {
//        this.menu = menu;
//    }

    public boolean isCanAdd() {
        return canAdd;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public boolean isCanList() {
        return canList;
    }

    public void setCanList(boolean canList) {
        this.canList = canList;
    }
    
    public boolean isCanPrint() {
		return canPrint;
	}

	public void setCanPrint(boolean canPrint) {
		this.canPrint = canPrint;
	}

    public boolean isCanVoid() {
        return canVoid;
    }

    public void setCanVoid(boolean canVoid) {
        this.canVoid = canVoid;
    }

//    @Override
//    public String toString() {
//        return "CrudControlAccessWrapper{" +
//                "menu=" + menu +
//                ", canAdd=" + canAdd +
//                ", canEdit=" + canEdit +
//                ", canDelete=" + canDelete +
//                ", canList=" + canList +
//                ", canPrint=" + canPrint +
//                ", canVoid=" + canVoid +
//                '}';
//    }
}
