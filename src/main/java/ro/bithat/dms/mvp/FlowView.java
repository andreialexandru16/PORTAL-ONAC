package ro.bithat.dms.mvp;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.EventObject;

@Deprecated
public interface FlowView<P extends FlowPresenter> extends BeforeEnterObserver,BeforeLeaveObserver, Serializable {

	default Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}

	@PostConstruct
	default void init() {
		getPresenter().setView(this);
		buildView();
	}

	default void buildView() {
	}


	default void onAttach(AttachEvent attachEvent) {
		getPresenter().prepareModelAndView(attachEvent);
	}

	@Override
	default void beforeEnter(BeforeEnterEvent event) {
		UI.getCurrent().getSession().getCurrent().setErrorHandler(errorEvent -> {
			getLogger().error(errorEvent.getThrowable().getMessage(), errorEvent.getThrowable());
			Notification.show("ERR: "+ errorEvent.getThrowable().getMessage(), 10000,
					Notification.Position.BOTTOM_END);
		});
	}

	@Override
	default void beforeLeave(BeforeLeaveEvent event) {
		getPresenter().beforeLeavingView(event);
	}
	
	P getPresenter();
		
	default void beforePrepareView(EventObject event){
	}
	
	default void prepareView() {
	}
	
	default void afterPrepareView() {
	}

}
