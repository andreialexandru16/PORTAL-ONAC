package ro.bithat.dms.passiveview;

import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveObserver;

public interface FlowRouteObserver extends BeforeEnterObserver, AfterNavigationObserver, BeforeLeaveObserver, LocaleChangeObserver {

    FlowComponentBuilder getComponentBuilder();


}
