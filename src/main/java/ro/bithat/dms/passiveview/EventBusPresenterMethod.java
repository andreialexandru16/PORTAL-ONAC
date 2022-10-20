package ro.bithat.dms.passiveview;


import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventBusPresenterMethod {

    //nume metoda
    String value() default "";

    //atribute de selectie

}
