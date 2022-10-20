package ro.bithat.dms.service;

import java.io.Serializable;
import java.util.Optional;

public interface CrudRepositoryService<T, ID extends Serializable> extends RepositoryService<T, ID> {

    <S extends T> S save(S entity);

    Optional<T> findById(ID id);

    <S extends T> void delete(S entity);

}
