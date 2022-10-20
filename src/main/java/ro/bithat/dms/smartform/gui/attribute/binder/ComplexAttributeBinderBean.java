package ro.bithat.dms.smartform.gui.attribute.binder;

import com.vaadin.flow.component.Component;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.ComplexTableComponent;

import java.util.Optional;

public class ComplexAttributeBinderBean extends ValueAttributeBinderBean<String> {

    public ComplexAttributeBinderBean(String smartFormId, AttributeLink attributeLink, Component component) {
        super(smartFormId, attributeLink);
        if(!((ComplexTableComponent) component).getTableContainerBody().getElement().toString().equals("<tbody></tbody>"))
        {
            setValue("1");
        }
    }

}
