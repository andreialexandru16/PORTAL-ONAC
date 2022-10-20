package ro.bithat.dms.smartform.gui;

import com.vaadin.flow.component.Component;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.passiveview.i18n.TableFormI18n;

import java.io.File;
import java.util.List;

public class TwoColumnsTableSmartForm extends TableFormI18n implements SmartForm {


    @Override
    public File getHtmlFile() {
        return null;
    }

    @Override
    public void buildSmartForm(AttributeLinkList attributeLinkList) {
        register(attributeLinkList);
        attributeLinkList.getAttributeLink().stream()
                .forEach(attributeLink -> SmartFormSupport
                        .getAttributeLinkService().attributeLinkDataPostProcessor(this, attributeLink, this));
    }

    @Override
    public void addAttributeLinkComponent(AttributeLink attributeLink, List<Component> components, Component layout, boolean hasLabel) {
        if(hasLabel && components.size() == 1) {
            ((TableFormI18n) layout).addFormRow(components.get(0), attributeLink.getLabel());
        }
        System.out.println("");
    }


    //    @Override
//    public void buildSmartForm(AttributeLinkList attributeLinkList) {
//        register(attributeLinkList);
////        attributeLinkList.getAttributeLink().stream()
////                .forEach();
//    }
//
//    @Override
//    public void addAttributeLinkComponent(AttributeLink attributeLink, Component component, Component layout, boolean addLabel) {
//        if(attributeLink.getLovId() != null && attributeLink.getLovId().compareTo(0) != 0) {
//
//        }
//    }
}
