package ro.bithat.dms.smartform.gui.attribute;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.smartform.gui.SmartForm;

import java.util.Arrays;

@SpringComponent
@UIScope
public class DefaultAttributeLinkComponent extends AttributeLinkGenericComponent {
    @Override
    public boolean canPostProcess(AttributeLink attributeLink) {
        return false;
    }

    @Override
    public void attributeLinkDataPostProcessor(SmartForm smartForm, AttributeLink attributeLink, Component layout) {
        if(!hasLabel(attributeLink)) {
            TextField defaultValue = new TextField();
            defaultValue.setValue(attributeLink.getValoareImplicita());
            defaultValue.setReadOnly(true);
            smartForm.addAttributeLinkComponent(attributeLink, Arrays.asList(defaultValue), layout, false);
        } else {
            smartForm.addAttributeLinkComponent(attributeLink, Arrays.asList(new Label(attributeLink.getLabel())), layout, false);
        }

    }
}
