package ro.bithat.dms.smartform.gui.attribute.binder;

import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;

import java.util.Optional;

@Deprecated
public class DoubleAttributeBinderBean extends ValueAttributeBinderBean<Double> {
    public DoubleAttributeBinderBean(String smartFormId, AttributeLink attributeLink) {
        super(smartFormId, attributeLink);
        if(Optional.ofNullable(attributeLink.getValue()).isPresent()
                && !attributeLink.getValue().isEmpty()) {
            setValue(Double.parseDouble(attributeLink.getValue()));
        }
    }

}
