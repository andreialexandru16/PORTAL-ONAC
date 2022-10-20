package ro.bithat.dms.passiveview;

import com.vaadin.flow.component.ComponentEvent;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DomEventPresenterMethod {

    String viewProperty();

    Class<? extends ComponentEvent> eventType();



}
