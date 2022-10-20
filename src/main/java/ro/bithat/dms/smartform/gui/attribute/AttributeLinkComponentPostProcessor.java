package ro.bithat.dms.smartform.gui.attribute;

import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;

public interface AttributeLinkComponentPostProcessor extends AttributeLinkPostProcessor {

    boolean canPostProcess(AttributeLink attributeLink);

    boolean isLovAttribute(AttributeLink attributeLink);

    boolean hasLabel(AttributeLink attributeLink);


}
