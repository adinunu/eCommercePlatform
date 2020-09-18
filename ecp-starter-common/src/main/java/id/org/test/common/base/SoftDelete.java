package id.org.test.common.base;

import java.io.Serializable;

public interface SoftDelete<T extends Serializable> extends EntityID<T>{
	
	boolean isDeleted();
	
	void setDeleted(boolean deleted);

}
