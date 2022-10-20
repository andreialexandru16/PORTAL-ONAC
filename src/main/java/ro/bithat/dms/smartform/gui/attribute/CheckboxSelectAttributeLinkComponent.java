package ro.bithat.dms.smartform.gui.attribute;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.Lov;
import ro.bithat.dms.microservices.dmsws.metadata.LovList;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.smartform.backend.AttributeLinkDataType;
import ro.bithat.dms.smartform.gui.SmartForm;
import ro.bithat.dms.smartform.gui.SmartFormSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringComponent
@UIScope
public class CheckboxSelectAttributeLinkComponent extends AttributeLinkGenericComponent {
    @Override
    public boolean canPostProcess(AttributeLink attributeLink) {
        return SmartFormSupport.getAttributeLinkDataType(attributeLink).equals(AttributeLinkDataType.OPT_SELECT_MULTIVALUE);
    }

    @Override
    public void attributeLinkDataPostProcessor(SmartForm smartForm, AttributeLink attributeLink, Component layout) {
        LovList lovList = BeanUtil.getBean(PS4Service.class).getLovList(attributeLink.getLovId());
        List<Lov> backendLov = lovList.getLov();
        SmartFormSupport.addLovListForAttributeLink(((Component) smartForm).getId().get(), attributeLink, lovList);
        smartForm.addAttributeLinkComponent(attributeLink, Arrays.asList(new Label(attributeLink.getLabel())), layout, false);
        List<Component> checkboxes = new ArrayList<>();
        if (attributeLink.getDenumireTipSelectie() != null && attributeLink.getDenumireTipSelectie().equals("LOV static")) {
            backendLov.stream().forEach(lov -> {
                Checkbox lovCheckbox = new Checkbox();
                lovCheckbox.setValue(Optional.ofNullable(attributeLink.getValue()).isPresent() &&
                        !attributeLink.getValue().isEmpty() &&
                        attributeLink.getValue().contains(lov.getValoare()));
                lovCheckbox.setLabel(lov.getValoare());
                checkboxes.add(lovCheckbox);
//            checkboxes.add(new Label(lov.getValoare()));

            });
        } else {
            backendLov.stream().forEach(lov -> {
                Checkbox lovCheckbox = new Checkbox();
                lovCheckbox.setValue(Optional.ofNullable(attributeLink.getValueForLov()).isPresent() &&
                        !attributeLink.getValueForLov().isEmpty() &&
                        attributeLink.getValueForLov().stream()
                                .filter(attrLov -> attrLov.getId().equalsIgnoreCase(lov.getId())).count() > 0);
                lovCheckbox.setLabel(lov.getValoare());
                checkboxes.add(lovCheckbox);
//            checkboxes.add(new Label(lov.getValoare()));

            });
        }
        smartForm.addAttributeLinkComponent(attributeLink, checkboxes, layout, false);

    }


}
