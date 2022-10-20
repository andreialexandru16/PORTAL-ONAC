package ro.bithat.dms.passiveview;

import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StatelessFlow {

    String value() default "";

}
