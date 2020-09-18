package id.org.test.common.service;

import java.util.List;

public interface CommonService<T, PK> {

    Long getNum();

    T save(T entity) throws Exception;

    T getById(PK pk) throws Exception;

    Boolean delete(PK PK) throws Exception;

    List<T> getAll() throws Exception;

}
