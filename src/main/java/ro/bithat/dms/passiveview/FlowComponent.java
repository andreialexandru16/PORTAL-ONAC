package ro.bithat.dms.passiveview;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FlowComponent {

    @AliasFor("instanceId")
    String value() default "";

    @AliasFor("value")
    String instanceId() default "";


}
