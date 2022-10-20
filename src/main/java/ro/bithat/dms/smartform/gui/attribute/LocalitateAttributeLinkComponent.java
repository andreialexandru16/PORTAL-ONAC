package ro.bithat.dms.smartform.gui.attribute;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
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
import ro.bithat.dms.smartform.gui.attribute.binder.LovAttributeBinderBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringComponent
@UIScope
public class LocalitateAttributeLinkComponent extends AttributeLinkGenericComponent {
    @Override
    public boolean canPostProcess(AttributeLink attributeLink) {
        return SmartFormSupport.getAttributeLinkDataType(attributeLink).equals(AttributeLinkDataType.LOCALITATE);
    }

    @Override
    public void attributeLinkDataPostProcessor(SmartForm smartForm, AttributeLink attributeLink, Component layout) {
        LovList lovList = BeanUtil.getBean(PS4Service.class).getLovList(attributeLink.getLovId());
        List<Lov> backendLov = lovList.getLov();
        SmartFormSupport.addLovListForAttributeLink(((Component)smartForm).getId().get(), attributeLink, lovList);
        ComboBox<Lov> lovComboBox = new ComboBox<>();
        lovComboBox.setItems(backendLov);
        lovComboBox.setAllowCustomValue(false);
        lovComboBox.setItemLabelGenerator(lov -> lov.getValoare());
        Optional<Lov> selected = Optional.empty();
        if(Optional.ofNullable(attributeLink.getValueForLov()).isPresent() && !attributeLink.getValueForLov().isEmpty()) {
            selected = backendLov.stream()
                    .filter(bv -> attributeLink.getValueForLov().stream()
                            .filter(v -> v.getId().equalsIgnoreCase(bv.getId())).count() > 0)
                    .findFirst();
//            if(selected.isPresent()) {
//                lovComboBox.setValue(selected.get());
//            }
        }

        smartForm.addAttributeLinkComponent(attributeLink, Arrays.asList(lovComboBox), layout, true);
        SmartFormSupport.bind(((Component)smartForm).getId().get(),
                new LovAttributeBinderBean(((Component)smartForm).getId().get(), attributeLink,
                        selected.isPresent() ? selected.get() : null), lovComboBox);
    }


}
