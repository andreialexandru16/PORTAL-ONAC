package ro.bithat.dms.smartform.gui.attribute.binder;

import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.smartform.gui.SmartFormSupport;

public abstract class ValueAttributeBinderBean<T> implements AttributeBinderBean<T> {

    private AttributeLink attributeLink;

    private String smartFormId;

    private T value;

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        SmartFormSupport.addSubjectValueAttributeLink(smartFormId, attributeLink);
        this.value = value;
    }

    public AttributeLink getAttributeLink() {
        return attributeLink;
    }

    public ValueAttributeBinderBean(String smartFormId, AttributeLink attributeLink) {
        this.attributeLink = attributeLink;
        this.smartFormId = smartFormId;
    }

    public String getSmartFormId() {
        return smartFormId;
    }
}

