package ro.bithat.dms.smartform.gui.attribute;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.smartform.backend.AttributeLinkDataType;
import ro.bithat.dms.smartform.gui.SmartForm;
import ro.bithat.dms.smartform.gui.SmartFormSupport;
import ro.bithat.dms.smartform.gui.attribute.binder.StringAttributeBinderBean;
import ro.bithat.dms.smartform.gui.attribute.component.HolographicComponent;

import java.util.Arrays;

@SpringComponent
@UIScope
public class HolographicAttributeLinkComponent extends AttributeLinkGenericComponent {
    @Override
    public boolean canPostProcess(AttributeLink attributeLink) {
        return SmartFormSupport.getAttributeLinkDataType(attributeLink).equals(AttributeLinkDataType.HOLOGRAPH);
    }

    @Override
    public void attributeLinkDataPostProcessor(SmartForm smartForm, AttributeLink attributeLink, Component layout) {
        HolographicComponent component = new HolographicComponent(((Component)smartForm).getId().get(), attributeLink);
        smartForm.addAttributeLinkComponent(attributeLink, Arrays.asList(component), layout, true);
        SmartFormSupport.bind(((Component)smartForm).getId().get(), new StringAttributeBinderBean(((Component)smartForm).getId().get(), attributeLink), component);
        component.init(attributeLink.getValue());
    }


}
