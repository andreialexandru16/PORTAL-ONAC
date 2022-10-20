package ro.bithat.dms.smartform.gui.attribute.binder;

import ro.bithat.dms.microservices.dmsws.metadata.AttributeLink;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class LocalDateAttributeBinderBean extends ValueAttributeBinderBean<LocalDate> {
    public LocalDateAttributeBinderBean(String smartFormId, AttributeLink attributeLink) {
        super(smartFormId, attributeLink);
        if(Optional.ofNullable(attributeLink.getValue()).isPresent()
                && !attributeLink.getValue().isEmpty()) {
            setValue(LocalDate.parse(attributeLink.getValue(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        }

    }
}
