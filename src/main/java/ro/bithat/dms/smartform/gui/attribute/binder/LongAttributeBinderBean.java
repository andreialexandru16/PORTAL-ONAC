package ro.bithat.dms.smartform.gui.attribute.binder;

import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;

import java.util.Optional;

@Deprecated
public class LongAttributeBinderBean extends ValueAttributeBinderBean<Long> {
    public LongAttributeBinderBean(String smartFormId, AttributeLink attributeLink) {
        super(smartFormId, attributeLink);
        if(Optional.ofNullable(attributeLink.getValue()).isPresent()
                && !attributeLink.getValue().isEmpty()) {
            String valueStr = String.valueOf(attributeLink.getValue());
            setValue(Long.parseLong(valueStr.substring(0, valueStr.indexOf("."))));
//            setValue(Long.parseLong(attributeLink.getValue()));
        }
    }
}
