package ro.bithat.dms.smartform.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class FormLayoutSmartForm extends FormLayout implements SmartForm {

    private Integer maxColspan = 0;

    @Override
    public File getHtmlFile() {
        return null;
    }

    @Override
    public void buildSmartForm(AttributeLinkList attributeLinkList) {
//        Integer maxColspan =
        register(attributeLinkList);
        maxColspan = getMaxResponsiveSteps();
        FormLayout.ResponsiveStep[] responsiveSteps = new FormLayout.ResponsiveStep[maxColspan];
        Double maxWidth = attributeLinkList.getAttributeLink().stream().sorted((a1, a2) -> a2.getWidthPx().compareTo(a1.getWidthPx())).findFirst().get().getWidthPx();
        int em = 15;
        for (int i = 0; i < maxColspan; ++i) {
            responsiveSteps[i] = new FormLayout.ResponsiveStep(em+"em", i + 1, FormLayout.ResponsiveStep.LabelsPosition.ASIDE);
            em += 15;
        }
        setResponsiveSteps(responsiveSteps);
        attributeLinkList.getAttributeLink().stream()
                .forEach(attributeLink -> SmartFormSupport
                        .getAttributeLinkService().attributeLinkDataPostProcessor(this, attributeLink, this));

    }

    @Override
    public void addAttributeLinkComponent(AttributeLink attributeLink, List<Component> components, Component layout, boolean hasLabel) {
        Integer attributeColspan = SmartFormSupport.getRowResponsiveSteps(getId().get(), attributeLink.getRand().intValue());
        Optional<Integer> colspan = attributeColspan - maxColspan <= 0 ? Optional.empty() : Optional.of(attributeColspan - maxColspan);
        Label attributeLabel = SmartFormSupport.getAttributeLabelAndRegisterAttributeComponents(getId().get(), attributeLink, components);
        if(components.size() == 1) {
            if(hasLabel) {
                Div attributeLayout = new Div();
                attributeLayout.add(attributeLabel, components.get(0));
//                FormItem formItem = addFormItem(components.get(0), attributeLink.getLabel());
                if(colspan.isPresent()) {
                    attributeLayout.getElement().setAttribute("colspan", colspan.get()+ "");
                }
                add(attributeLayout);
            }
        }
    }


    //    @Override
//    public void buildSmartForm(AttributeLinkList attributeLinkList) {
//
//    }
}
