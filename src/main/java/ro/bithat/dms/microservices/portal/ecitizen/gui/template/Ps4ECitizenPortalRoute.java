package ro.bithat.dms.microservices.portal.ecitizen.gui.template;

import ro.bithat.dms.microservices.portal.ecitizen.gui.header.Ps4ECitizenHeaderVisualIdentityView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.header.Ps4ECitizenMainHeaderContainerView;
import ro.bithat.dms.passiveview.FlowComponent;

public abstract class Ps4ECitizenPortalRoute extends Ps4ECitizenResponsiveLayout {

    @FlowComponent
    private Ps4ECitizenHeaderVisualIdentityView headerVisualIdentityView;

    @FlowComponent
    private Ps4ECitizenMainHeaderContainerView mainHeaderContainerView;

    @Override
    protected boolean buildLayout() {
        addHeaderContainer(headerVisualIdentityView, mainHeaderContainerView);
        return buildPortalRoute();
    }

    protected abstract boolean buildPortalRoute();
}
