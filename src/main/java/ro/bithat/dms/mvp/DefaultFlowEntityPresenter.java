package ro.bithat.dms.mvp;

import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.StatusChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.events.EventBus.UIEventBus;
import ro.bithat.dms.service.RepositoryService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Serializable;

@Deprecated
public abstract class DefaultFlowEntityPresenter<V extends FlowView,T, ID extends Serializable,
		S extends RepositoryService<T, ID>> implements FlowEntityPresenter<V, T, ID, S> {
	private static Logger LOGGER = LoggerFactory.getLogger(DefaultFlowEntityPresenter.class);


	private V view;
	private ID entityId;	
	private BeanValidationBinder<T> binder;

	/**
	 * Used to store old values, before apply binder values. 
	 */
	private T entityValuesBeforeBinderUpdate = null;

	@Autowired
	private S repositoryService;

	@Autowired
    private UIEventBus uIEventBus;
	
	@PostConstruct
    private void initEventBus() {
    	uIEventBus.subscribe(this);
    }
    
    @PreDestroy
    private void predestroyEventBus() {
    	uIEventBus.unsubscribe(this);
    }

	@Override
	public S getService() {
		return repositoryService;
	}

	@Override
    public V getView() {
    	return view;
    }

    @Override
	public void setView(V view) {
		this.view = view;
	}

    @Override
    public ID getInitialEntityId() {
        return entityId;
    }

	@Override
	public void setEntity(T entity) {
		entityValuesBeforeBinderUpdate = cloneEntity(getEntity());
		FlowEntityPresenter.super.setEntity(entity);
	}

	public UIEventBus getUIEventBus() {
    	return uIEventBus;
    }
    
    public void setEntityId(ID entityId) {
    	this.entityId = entityId;
    }

	@Override
	public BeanValidationBinder<T> getBinder() {
		return binder;
	}

	@Override
	public void setBinder(BeanValidationBinder<T> binder) {
		this.binder = binder;
	}
	
	@Override
	public void onFormStatusChange(StatusChangeEvent event) {
		onEntityFormStatusChange(event);
	}

	public T getEntityValuesBeforeBinderUpdate() {
		return entityValuesBeforeBinderUpdate;
	}
	
	public void onEntityFormStatusChange(StatusChangeEvent event) {}
	
	public T cloneEntity(T entity) {
		return null;
	}

}
