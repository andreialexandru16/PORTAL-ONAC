package ro.bithat.dms.smartform.gui.attribute;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;
import ro.bithat.dms.smartform.gui.SmartForm;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
@UIScope
public class AttributeLinkService implements AttributeLinkPostProcessor {

    @Autowired
    private List<AttributeLinkComponentPostProcessor> attributeLinkComponentPostProcessors;

    @Autowired
    private DefaultAttributeLinkComponent defaultAttributeLinkDataPostProcessor;

    @Autowired
    private TextAttributeLinkComponent textAttributeLinkComponent;
    @PostConstruct
    public void test() {
        System.out.println("test");
    }

    @Override
    public void attributeLinkDataPostProcessor(SmartForm smartForm, AttributeLink attributeLink, Component layout) {
        Optional<AttributeLinkComponentPostProcessor> attributeLinkDataPostProcessor = getAttributeLinkPostProcessor(attributeLink);
        if(attributeLinkDataPostProcessor.isPresent()) {
            attributeLinkDataPostProcessor.get().attributeLinkDataPostProcessor(smartForm, attributeLink, layout);
        } else  {
            textAttributeLinkComponent.attributeLinkDataPostProcessor(smartForm, attributeLink, layout);
        }
    }

    private Optional<AttributeLinkComponentPostProcessor> getAttributeLinkPostProcessor(AttributeLink attributeLink) {
        return attributeLinkComponentPostProcessors.stream().filter(p -> p.canPostProcess(attributeLink)).findFirst();
    }



}
