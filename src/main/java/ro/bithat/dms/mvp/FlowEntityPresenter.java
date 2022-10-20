package ro.bithat.dms.mvp;

import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.StatusChangeEvent;
import org.springframework.util.Assert;
import ro.bithat.dms.service.CrudRepositoryService;
import ro.bithat.dms.service.EagerRepositoryService;
import ro.bithat.dms.service.RepositoryService;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.EventObject;
import java.util.List;


@Deprecated
public interface FlowEntityPresenter<V extends FlowView,T, ID extends Serializable,
		S extends RepositoryService<T, ID>> extends FlowPresenter<V> {

	S getService();

	/*
	 *
	 * use #getBinder.getBean for unbuffered mode
	 * Synchronize local entity for buffered mode
	 *
	 */
	default void setEntity(T entity) {
		getBinder().setBean(entity);
	}

	/*
	 *
	 * use #getBinder.getBean for unbuffered mode
	 * Synchronize local entity for buffered mode
	 *
	 */
	default T getEntity() {
		return getBinder().getBean();
	}

	default void save() {
		Assert.isAssignable(CrudRepositoryService.class, getService().getClass());
		beforeSave();

		if (!getBinder().isValid()) {
			getBinder().validate();
			return;
		}

		T localEntity = null;
		try {
			localEntity =  ((CrudRepositoryService<T,ID>)getService()).save(getBinder().getBean());
		} catch (RuntimeException ee) {
			ee.printStackTrace();
			throw ee;
		} catch (Exception e) {
			e.printStackTrace();
		}

		setEntity(localEntity);

		afterSave();
	}

	/**
	 * used to change entity programmatically
	 */
	default void beforeSave() {}

	default void afterSave() {}

	default void delete() {
		Assert.isAssignable(CrudRepositoryService.class, getService().getClass());
		((CrudRepositoryService<T,ID>)getService()).delete(getEntity());
	}

	default Class<T> getEntityType() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1];
	}

	@Override
	default void prepareModel(EventObject event) {
		//creates entity binder
		if(getBinder() == null) {
			setBinder(new BeanValidationBinder<>(getEntityType()));

			//add binder form listeners
			getBinder().addStatusChangeListener(this::onFormStatusChange);

			// bind Form fields with entity fields
			try {
				bindFor();
				getBinder().bindInstanceFields(getView());
			}catch (IllegalStateException e) {
				//nothing to bind!!!
	//			e.printStackTrace();
				System.err.println(e.getMessage());
			}

			// get Entity from backend. Bind entity to binder
			loadEntity(getInitialEntityId());

			//unbuffered mode. The model is updated after every change if the field is valid
			//have to manually trigger binder,validate() to check for validation errors
//			getBinder().setBean(getEntity());
		}
	}

	default void bindFor() {

	}

	ID getInitialEntityId();

	default void loadEntity(ID entityId) {
		if(CrudRepositoryService.class.isAssignableFrom(getService().getClass())) {

			T c = null;
			if (entityId == null) {
				try {
					c = (T) getEntityType().newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					throw new UnsupportedOperationException("Entity of type " + getEntityType().getName() + " is missing a public no-args constructor", e);
				}
				// try to populate fields with parent fields if necessary
				fillEntityAfterCreate(c);
			} else {
				try {
					c = ((CrudRepositoryService<T, ID>) getService()).findById(entityId).orElse((T) getEntityType().newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					throw new UnsupportedOperationException("Entity of type " + getEntityType().getName() + " is missing a public no-args constructor", e);
				}
			}
			if (c != null) {
				setEntity(c);
			}
		}
	}

	default void fillEntityAfterCreate(T c) {}

	default void onFormStatusChange(StatusChangeEvent event) {}

	BeanValidationBinder<T> getBinder();

	void setBinder(BeanValidationBinder<T> binder);

	default List<T> getItems() {
		Assert.isAssignable(EagerRepositoryService.class, getService().getClass());
		return ((EagerRepositoryService<T, ID>)getService()).findAll();
	}
}
