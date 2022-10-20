package ro.bithat.dms.smartform.gui.attribute;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.smartform.backend.AttributeLinkDataType;
import ro.bithat.dms.smartform.gui.SmartForm;
import ro.bithat.dms.smartform.gui.SmartFormSupport;
import ro.bithat.dms.smartform.gui.attribute.binder.BooleanAttributeBinderBean;

import java.util.Arrays;

@SpringComponent
@UIScope
public class CheckboxAttributeLinkComponent extends AttributeLinkGenericComponent {
    @Override
    public boolean canPostProcess(AttributeLink attributeLink) {
        return SmartFormSupport.getAttributeLinkDataType(attributeLink).equals(AttributeLinkDataType.BOOLEAN);
    }

    @Override
    public void attributeLinkDataPostProcessor(SmartForm smartForm, AttributeLink attributeLink, Component layout) {
        Checkbox component = new Checkbox();
//        Optional<String> value = Optional.ofNullable(attributeLink.getValue());
//        if(value.isPresent() && !value.get().isEmpty()) {
//            component.setValue(Integer.valueOf(value.get()) == 1);
//        }
        smartForm.addAttributeLinkComponent(attributeLink, Arrays.asList(component), layout, true);
        SmartFormSupport.bind(((Component)smartForm).getId().get(), new BooleanAttributeBinderBean(((Component) smartForm).getId().get(), attributeLink), component);
    }


}
