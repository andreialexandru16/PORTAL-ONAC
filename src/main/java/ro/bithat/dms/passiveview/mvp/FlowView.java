package ro.bithat.dms.passiveview.mvp;

import com.vaadin.flow.component.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;

public interface FlowView<P extends FlowPresenter> {

    default Logger getLogger() {
        return LoggerFactory.getLogger(getClass());
    }

    P getPresenter();

    default void init(String instanceId, P presenter) {
        presenter.setView(this);
        if(Component.class.isAssignableFrom(getClass())) {
            ((Component)this).setId(instanceId);
        }
        if(FlowViewBuilder.class.isAssignableFrom(getClass())) {
            ((FlowViewBuilder)this).buildView(presenter);
        }
    }

    default Class<P> getPresenterType() {
        return (Class<P>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
