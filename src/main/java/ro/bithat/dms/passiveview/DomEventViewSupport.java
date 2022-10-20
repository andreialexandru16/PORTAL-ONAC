package ro.bithat.dms.passiveview;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.upload.FinishedEvent;
import com.vaadin.flow.component.upload.StartedEvent;
import com.vaadin.flow.component.upload.SucceededEvent;
import org.springframework.util.Assert;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.passiveview.mvp.FlowView;

public final class DomEventViewSupport {

    public static void registerClickEvent(FlowView view, String presenterMethod, Component component, Object... params) {
        registerDomEvent(view, presenterMethod, component, ClickEvent.class, params);
    }

    public static  void registerUploadSucceededEvent(FlowView view, String presenterMethod, Component component, Object... params) {
        registerDomEvent(view, presenterMethod, component, SucceededEvent.class, params);
    }

    public static  void registerUploadStartedEvent(FlowView view, String presenterMethod, Component component, Object... params) {
        registerDomEvent(view, presenterMethod, component, StartedEvent.class, params);
    }

    public static  void registerUploadFinishedEvent(FlowView view, String presenterMethod, Component component, Object... params) {
        registerDomEvent(view, presenterMethod, component, FinishedEvent.class, params);
    }

    public static  void registerComponentValueChangeEvent(FlowView view, String presenterMethod, Component component, Object... params) {
        registerDomEvent(view, presenterMethod, component, AbstractField.ComponentValueChangeEvent.class, params);
    }

    public static  void registerDomEvent(FlowView view, String presenterMethod, Component component, Class<? extends ComponentEvent> eventType, Object... params) {
        Assert.isAssignable(Component.class, view.getClass());
        BeanUtil.getBean(FlowComponentBuilder.class).registerDomEvent(((Component)view).getId().get(), presenterMethod, component, eventType, params);
    }
}
