package ro.bithat.dms.passiveview;


import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@EventBusPresenterMethod("passiveViewClientUpdate")
@Documented
public @interface ClientAutorefresh {

}
