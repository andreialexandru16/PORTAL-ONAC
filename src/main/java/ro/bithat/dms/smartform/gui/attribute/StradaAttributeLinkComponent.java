package ro.bithat.dms.smartform.gui.attribute;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.smartform.backend.AttributeLinkDataType;
import ro.bithat.dms.smartform.gui.SmartForm;
import ro.bithat.dms.smartform.gui.SmartFormSupport;
import ro.bithat.dms.smartform.gui.attribute.binder.StringAttributeBinderBean;

import java.util.Arrays;
import java.util.Optional;

@SpringComponent
@UIScope
public class StradaAttributeLinkComponent extends AttributeLinkGenericComponent {
    @Override
    public boolean canPostProcess(AttributeLink attributeLink) {
        return SmartFormSupport.getAttributeLinkDataType(attributeLink).equals(AttributeLinkDataType.STRADA);
    }

    @Override
    public void attributeLinkDataPostProcessor(SmartForm smartForm, AttributeLink attributeLink, Component layout) {
        if(!hasLabel(attributeLink)) {
            smartForm.addAttributeLinkComponent(attributeLink, Arrays.asList(new Label(attributeLink.getValoareImplicita())), layout, false);
        } else {
            Optional<String> value = Optional.ofNullable(attributeLink.getValue());
            TextField component = new TextField();
//            if(value.isPresent()) {
//                component.setValue(value.get());
//            }
            smartForm.addAttributeLinkComponent(attributeLink, Arrays.asList(component), layout, true);
            SmartFormSupport.bind(((Component)smartForm).getId().get(), new StringAttributeBinderBean(((Component)smartForm).getId().get(), attributeLink), component);
        }
    }
}
