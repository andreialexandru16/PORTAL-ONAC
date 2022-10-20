package ro.bithat.dms.passiveview;

import com.vaadin.flow.component.ClickEvent;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@DomEventPresenterMethod(
        viewProperty = "",
        eventType = ClickEvent.class
)
@Documented
public @interface ClickEventPresenterMethod {

    @AliasFor(attribute = "viewProperty", annotation = DomEventPresenterMethod.class)
    String viewProperty();


}
