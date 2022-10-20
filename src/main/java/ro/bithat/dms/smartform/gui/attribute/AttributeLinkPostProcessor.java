package ro.bithat.dms.smartform.gui.attribute;

import com.vaadin.flow.component.Component;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.smartform.gui.SmartForm;

public interface AttributeLinkPostProcessor {

    void attributeLinkDataPostProcessor(SmartForm smartForm, AttributeLink attributeLink, Component layout);

//    default String getValue(SmartForm smartForm, DocAttrLink docAttrLink) {
//
//    }

}
