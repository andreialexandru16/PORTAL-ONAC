package ro.bithat.dms.smartform.gui.attribute.binder;

import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;

import java.util.Optional;

public class StringAttributeBinderBean extends ValueAttributeBinderBean<String> {

    public StringAttributeBinderBean(String smartFormId, AttributeLink attributeLink) {
        super(smartFormId, attributeLink);
        if(Optional.ofNullable(attributeLink.getValue()).isPresent()
                && !attributeLink.getValue().isEmpty()) {
            setValue(attributeLink.getValue());
        }
    }

}
