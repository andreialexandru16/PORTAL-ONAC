package ro.bithat.dms.smartform.gui.attribute;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.file.DmswsFileService;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.portal.ecitizen.website.controllers.ANREController;
import ro.bithat.dms.smartform.backend.AttributeLinkDataType;
import ro.bithat.dms.smartform.gui.SmartForm;
import ro.bithat.dms.smartform.gui.SmartFormSupport;
import ro.bithat.dms.smartform.gui.attribute.binder.BooleanAttributeBinderBean;

import java.util.Arrays;
import java.util.Base64;

@SpringComponent
@UIScope
public class ImageAttributeLinkComponent extends AttributeLinkGenericComponent {
    @Override
    public boolean canPostProcess(AttributeLink attributeLink) {
        return SmartFormSupport.getAttributeLinkDataType(attributeLink).equals(AttributeLinkDataType.IMAGE);
    }

    @Override
    public void attributeLinkDataPostProcessor(SmartForm smartForm, AttributeLink attributeLink, Component layout) {
        Image component = new Image();
        StringBuilder src = new StringBuilder();
        byte[] bytes = "".getBytes();
        try{
            bytes = BeanUtil.getBean(DmswsFileService.class).downloadFileById(Integer.parseInt(attributeLink.getValoareImplicita()));
        }catch (Exception e){
            e.printStackTrace();
        }

        src.append("data:image/png;base64,").append(Base64.getEncoder().encodeToString(bytes));
        component.setSrc(src.toString());
        smartForm.addAttributeLinkComponent(attributeLink, Arrays.asList(component), layout, true);
    }


}
