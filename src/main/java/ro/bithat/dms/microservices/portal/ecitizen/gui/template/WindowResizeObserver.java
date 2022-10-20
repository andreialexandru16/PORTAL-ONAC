package ro.bithat.dms.microservices.portal.ecitizen.gui.template;

import com.vaadin.flow.component.page.BrowserWindowResizeEvent;

public interface WindowResizeObserver {

    void browserWindowResized(BrowserWindowResizeEvent event);
}
