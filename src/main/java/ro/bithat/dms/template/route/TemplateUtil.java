package ro.bithat.dms.template.route;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;
import org.springframework.util.Assert;

@Deprecated
public final class TemplateUtil {

    public static void setComponentIdAndClassName(Component component, String id, String className) {
        Assert.isAssignable(HasStyle.class, component.getClass());
        component.setId(id);
        ((HasStyle)component).addClassName(className);
    }

    public static void setComponentIdAndClassNames(Component component, String id, String... className) {
        Assert.isAssignable(HasStyle.class, component.getClass());
        component.setId(id);
        ((HasStyle)component).addClassNames(className);
    }

    public static void setNoSpacingAndPaddingLayout(ThemableLayout layout) {
        layout.setSpacing(false);
        layout.setPadding(false);
    }

}
