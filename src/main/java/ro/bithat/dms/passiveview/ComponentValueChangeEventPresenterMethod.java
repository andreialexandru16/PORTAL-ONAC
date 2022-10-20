package ro.bithat.dms.passiveview;

import com.vaadin.flow.component.AbstractField;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@DomEventPresenterMethod(
        viewProperty = "",
        eventType = AbstractField.ComponentValueChangeEvent.class
)
@Documented
public @interface ComponentValueChangeEventPresenterMethod {

    @AliasFor(attribute = "viewProperty", annotation = DomEventPresenterMethod.class)
    String viewProperty();

}
