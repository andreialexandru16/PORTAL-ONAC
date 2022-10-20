package ro.bithat.dms.microservices.portal.ecitizen.gui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.html.Div;
import ro.bithat.dms.passiveview.DomEventViewSupport;
import ro.bithat.dms.passiveview.mvp.FlowView;

public abstract class FlowViewDivContainer extends Div {


    private final FlowView view;

    public FlowViewDivContainer(FlowView view) {
        this.view = view;
    }

    protected void registerClickEvent(String presenterMethod, Component component, Object... params) {
        DomEventViewSupport.registerClickEvent(view, presenterMethod, component, params);
    }

    protected void registerUploadSucceededEvent(String presenterMethod, Component component, Object... params) {
        DomEventViewSupport.registerUploadSucceededEvent(view, presenterMethod, component, params);
    }

    protected void registerUploadStartedEvent(String presenterMethod, Component component, Object... params) {
        DomEventViewSupport.registerUploadStartedEvent(view, presenterMethod, component, params);
    }

    protected void registerUploadFinishedEvent(String presenterMethod, Component component, Object... params) {
        DomEventViewSupport.registerUploadFinishedEvent(view, presenterMethod, component, params);
    }

    protected void registerComponentValueChangeEventEvent(String presenterMethod, Component component, Object... params) {
        DomEventViewSupport.registerComponentValueChangeEvent(view, presenterMethod, component, params);
    }

    protected void registerDomEvent(String presenterMethod, Component component, Class<? extends ComponentEvent> eventType, Object... params) {
        DomEventViewSupport.registerDomEvent(view, presenterMethod, component, eventType, params);
    }

    public FlowView getView() {
        return view;
    }
}
