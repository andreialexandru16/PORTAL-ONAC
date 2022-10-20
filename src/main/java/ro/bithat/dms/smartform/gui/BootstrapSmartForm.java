package ro.bithat.dms.smartform.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class BootstrapSmartForm extends Div implements SmartForm {
    @Override
    public File getHtmlFile() {
        return null;
    }

    @Override
    public void buildSmartForm(AttributeLinkList attributeLinkList) {
        register(attributeLinkList);
        attributeLinkList.getAttributeLink().stream()
                .collect(Collectors.groupingBy(attributeLink -> (attributeLink).getRand().intValue()))
                .forEach((row, attributeLinks) -> {
                    Div rowLayout = new Div();
                    rowLayout.addClassNames("row", "form-group");
                    add(rowLayout);
                    attributeLinks.stream().forEach(attributeLink ->
                            SmartFormSupport
                                    .getAttributeLinkService().attributeLinkDataPostProcessor(this, attributeLink, rowLayout));
                });

//
//        attributeLinkList.getAttributeLink().stream()
//                .forEach(attributeLink -> SmartFormSupport
//                        .getAttributeLinkService().attributeLinkDataPostProcessor(this, attributeLink, row));
    }

    @Override
    public void addAttributeLinkComponent(AttributeLink attributeLink, List<Component> components, Component layout, boolean hasLabel) {
        Integer attributeColspan = SmartFormSupport.getRowResponsiveSteps(getId().get(), attributeLink.getRand().intValue());
        Integer bootstrapCells = 12 / attributeColspan;
        Label attributeLabel = SmartFormSupport.getAttributeLabelAndRegisterAttributeComponents(getId().get(), attributeLink, components);
        if(components.size() == 1) {
            if(hasLabel) {
                Div attributeLayout = new Div(attributeLabel, components.get(0));
                Div attributeCell = new Div(attributeLayout);
                attributeLayout.addClassNames("if_tbl", "pr-12pr");
                attributeCell
                        .addClassNames("col-xl-"+bootstrapCells, // Desktop
                                "col-md-"+bootstrapCells, // pads
                                "col-sm-"+bootstrapCells // smart phone +
                        ,"col-"+bootstrapCells); // mobile
                ((HasComponents)layout).add(attributeCell);
            }
        }

    }
}
