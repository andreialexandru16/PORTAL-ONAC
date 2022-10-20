package ro.bithat.dms.smartform.gui.attribute.binder;

import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.Lov;

import java.util.Optional;


//TODO
public class LovAttributeBinderBean extends ValueAttributeBinderBean<Lov> {

    public LovAttributeBinderBean(String smartFormId, AttributeLink attributeLink, Lov lov) {
        super(smartFormId, attributeLink);
        if(Optional.ofNullable(lov).isPresent()) {
            setValue(lov);
        }
    }


}
