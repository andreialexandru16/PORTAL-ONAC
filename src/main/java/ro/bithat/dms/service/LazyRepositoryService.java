package ro.bithat.dms.service;

import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.data.provider.QuerySortOrder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface LazyRepositoryService<T, ID extends Serializable> extends RepositoryService {

//    List<T> findAllByFilter(String searchText);

//    Collection<T> fetch(String searchText, int offset, int limit);

    Integer count(String searchText);

    Collection<T> fetch(String searchText, List<QuerySortOrder> sortOrders, int offset, int limit);

    Collection<T> findAllSort(String searchText, List<GridSortOrder<T>> sortOrders);
}
