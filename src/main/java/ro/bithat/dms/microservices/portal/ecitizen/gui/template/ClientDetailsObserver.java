package ro.bithat.dms.microservices.portal.ecitizen.gui.template;

import com.vaadin.flow.component.page.ExtendedClientDetails;

public interface ClientDetailsObserver {

    void receiveClientDetails(ExtendedClientDetails extendedClientDetails);

}
