package ro.bithat.dms.smartform.gui.attribute;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import ro.bithat.dms.boot.BeanUtil;
import ro.bithat.dms.microservices.dmsws.file.DmswsFileService;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.smartform.backend.AttributeLinkDataType;
import ro.bithat.dms.smartform.gui.SmartForm;
import ro.bithat.dms.smartform.gui.SmartFormSupport;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@SpringComponent
@UIScope
public class ImageAttributeLinkComponent extends AttributeLinkGenericComponent {

    private HashMap<String, String> signatures;

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

        src.append("data:").append(getMimeType(Base64.getEncoder().encodeToString(bytes))).append(";base64,").append(Base64.getEncoder().encodeToString(bytes));
        component.setSrc(src.toString());
        smartForm.addAttributeLinkComponent(attributeLink, Arrays.asList(component), layout, true);
    }

    private void populateSignatures(){
        signatures = new HashMap<>();
        signatures.put("JVBERi0","application/pdf");
        signatures.put("R0lGODdh","image/gif");
        signatures.put("R0lGODlh","image/gif");
        signatures.put("iVBORw0KGgo","image/png");
        signatures.put("/9j/","image/jpg");
    }

    private String getMimeType(String base64Bytes){
        String mimeType = null;
        populateSignatures();
        for(Map.Entry<String, String> entry : signatures.entrySet()) {
            if(base64Bytes.indexOf(entry.getKey()) == 0) {
                    mimeType = entry.getValue();
                }
        }
        return mimeType;
    }



}
