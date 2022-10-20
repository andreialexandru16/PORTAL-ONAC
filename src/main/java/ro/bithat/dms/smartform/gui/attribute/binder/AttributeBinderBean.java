package ro.bithat.dms.smartform.gui.attribute.binder;

import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;

public interface AttributeBinderBean<T> {

    T getValue();

    void setValue(T value);

    AttributeLink getAttributeLink();

}
