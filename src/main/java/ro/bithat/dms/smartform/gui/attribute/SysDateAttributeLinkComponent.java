package ro.bithat.dms.smartform.gui.attribute;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.passiveview.i18n.DatePickerI18N;
import ro.bithat.dms.smartform.backend.AttributeLinkDataType;
import ro.bithat.dms.smartform.gui.SmartForm;
import ro.bithat.dms.smartform.gui.SmartFormSupport;

import java.time.LocalDate;
import java.util.Arrays;

@SpringComponent
@UIScope
public class SysDateAttributeLinkComponent extends AttributeLinkGenericComponent {
    @Override
    public boolean canPostProcess(AttributeLink attributeLink) {
        return SmartFormSupport.getAttributeLinkDataType(attributeLink).equals(AttributeLinkDataType.SYSDATE);
    }

    @Override
    public void attributeLinkDataPostProcessor(SmartForm smartForm, AttributeLink attributeLink, Component layout) {
        DatePickerI18N component = new DatePickerI18N();
        component.setLocale(UI.getCurrent().getLocale());
        component.setValue(LocalDate.now());
        SmartFormSupport.addSubjectValueComponent(component);
        component.setReadOnly(true);
//        Optional<String> value = Optional.ofNullable(attributeLink.getValue());
//        if(value.isPresent() && !value.get().isEmpty()) {
//            component.setValue(LocalDate.parse(attributeLink.getValue(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
//        }
        smartForm.addAttributeLinkComponent(attributeLink, Arrays.asList(component), layout, true);
//        SmartFormSupport.bind(((Component)smartForm).getId().get(),
//                new LocalDateAttributeBinderBean(((Component) smartForm).getId().get(), attributeLink), component,
//                new DateRangeValidator("", LocalDate.MIN, LocalDate.MAX));
    }


}
