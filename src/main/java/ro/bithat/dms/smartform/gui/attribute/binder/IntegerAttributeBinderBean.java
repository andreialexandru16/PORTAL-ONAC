package ro.bithat.dms.smartform.gui.attribute.binder;

import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;

import java.util.Optional;

@Deprecated
public class IntegerAttributeBinderBean extends ValueAttributeBinderBean<Integer> {
    public IntegerAttributeBinderBean(String smartFormId, AttributeLink attributeLink) {
        super(smartFormId, attributeLink);
        if(Optional.ofNullable(attributeLink.getValue()).isPresent()
                && !attributeLink.getValue().isEmpty()) {
            String valueStr = String.valueOf(attributeLink.getValue());
            setValue(Integer.parseInt(valueStr.substring(0, valueStr.indexOf("."))));
        }
    }
}
