package ro.bithat.dms.service;

import java.io.Serializable;
import java.util.List;

public interface EagerRepositoryService<T, ID extends Serializable> extends CrudRepositoryService<T,ID>  {

    <S extends T> List<S> findAll();

}
