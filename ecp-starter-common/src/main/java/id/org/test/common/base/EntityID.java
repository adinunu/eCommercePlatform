package id.org.test.common.base;

import java.io.Serializable;

/**
 * 
 * @author Sal Prima
 *
 * @param <T>
 */
public interface EntityID<T extends Serializable> {

	/**
	 * @see https://vladmihalcea.com/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
	 */
	public static final String SEQ_GEN_NATIVE = "native";

	T getId();

	void setId(T id);

}
