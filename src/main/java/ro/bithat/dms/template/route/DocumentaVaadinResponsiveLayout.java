package ro.bithat.dms.template.route;

import com.vaadin.flow.component.ReconnectDialogConfiguration;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.mvp.FlowView;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;

import javax.annotation.PostConstruct;

@Theme(value = Lumo.class, variant = Lumo.LIGHT)
//@Viewport(value = Viewport.DEVICE_DIMENSIONS)
//@StyleSheet("frontend/css/dms-monday.css")
//@StyleSheet("frontend/css/view-style.css")
//@CssImport(value = "text-field.css", themeFor = "vaadin-text-field")
//@JavaScript("https://kit.fontawesome.com/fc89db6bcc.js")
//@JavaScript("https://code.jquery.com/jquery-3.5.1.min.js")
@Deprecated
public abstract class DocumentaVaadinResponsiveLayout extends HorizontalLayout  {


    @Autowired
    private DocumentaVaadinResponsiveView dmsApplicationLayers;

    @PostConstruct
    protected void setup() {
//        UI.getCurrent().getPage().addJavaScript("frontend/js/dms-draggable-element.js");
//        UI.getCurrent().getPage().addJavaScript("frontend/js/dms-draggable-node-dialogs.js");
//        UI.getCurrent().getPage().addJavaScript("frontend/js/dms-client-callback.js");
        statelessReconnect();
        TemplateUtil.setComponentIdAndClassName(this, "dms-application", "dms-application");
        TemplateUtil.setNoSpacingAndPaddingLayout(this);
        add(dmsApplicationLayers);
        buildLayout();
    }

    protected abstract void buildLayout();

    private void statelessReconnect() {
        ReconnectDialogConfiguration configuration = UI.getCurrent().getReconnectDialogConfiguration();
        configuration.setDialogText(I18NProviderStatic.getTranslation("message.reconnect.dialog"));
        configuration.setReconnectInterval(1000);
    }

    protected void addCustomizationNavigationView(FlowView flowView) {
        dmsApplicationLayers.addCustomizationNavigationView(flowView);
    }

    protected void addPermanentNavigationView(FlowView flowView) {
        dmsApplicationLayers.addPermanentNavigationView(flowView);
    }

    protected void addSearchSlideView(FlowView flowView) {
        dmsApplicationLayers.addSearchSlideView(flowView);
    }

    protected void addFirstLevelContentView(FlowView flowView) {
        dmsApplicationLayers.addFirstLevelContentView(flowView);
    }

    protected void addFirstLevelControllerView(FlowView flowView) {
        dmsApplicationLayers.addFirstLevelControllerView(flowView);
    }

}
