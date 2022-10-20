package ro.bithat.dms.template.route;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.util.Assert;
import ro.bithat.dms.mvp.FlowView;
import ro.bithat.dms.mvp.component.horizontallayout.HorizontalLayoutFlowView;

@SpringComponent
@UIScope
@Deprecated
public class DocumentaVaadinResponsiveView extends HorizontalLayoutFlowView<DocumentaVaadinResponsivePresenter> {

    private Div dmsSurfaceCompanyLogoComponentWrapper = new Div();

    private Div dmsCustomizationNavigationItemsArea = new Div();

    private Div dmsPermanentNavigationItemsArea = new Div();

    private Div dmsSurfaceControlComponent = new Div(dmsSurfaceCompanyLogoComponentWrapper, dmsCustomizationNavigationItemsArea, dmsPermanentNavigationItemsArea);

    private Div dmsSurfaceControl = new Div(dmsSurfaceControlComponent);

    private Div dmsSurfaceContentComponent = new Div();

    private Div dmsSurfaceContent = new Div(dmsSurfaceContentComponent);

    private Div dmsSurface = new Div(dmsSurfaceControl, dmsSurfaceContent);

    private Div dmsFirstLevelExpandButtonComponent = new Div();

    private Div dmsFirstLevelExpandButtonContainer = new Div(dmsFirstLevelExpandButtonComponent);

    private Div dmsFirstLevelControlComponent = new Div();

    private Div dmsFirstLevelControl = new Div(dmsFirstLevelControlComponent);

    private Div dmsFirstLevelContent = new Div();

    private Div dmsFirstLevelContentWrapper = new Div(dmsFirstLevelContent);

    private Div dmsFirstLevel = new Div(dmsFirstLevelExpandButtonContainer, dmsFirstLevelControl, dmsFirstLevelContentWrapper);

    private boolean mouseOnDmsSurfaceControl = false;

    @Override
    public void buildView() {
        TemplateUtil.setComponentIdAndClassName(this, "dms-application-layers", "dms-application-layers");
        TemplateUtil.setNoSpacingAndPaddingLayout(this);
        add(dmsSurface, dmsFirstLevel);
        TemplateUtil.setComponentIdAndClassName(dmsSurface, "dms-surface", "dms-surface");
        TemplateUtil.setComponentIdAndClassName(dmsSurfaceControl, "dms-surface-control", "dms-surface-control");
        TemplateUtil.setComponentIdAndClassName(dmsSurfaceContent, "dms-surface-content", "dms-surface-content");
        dmsSurfaceControlComponent.setClassName("dms-surface-control-component");
        dmsSurfaceCompanyLogoComponentWrapper.setClassName("dms-surface-company-logo-component-wrapper");
        Div dmsSurfaceCompanyLogoComponent = new Div();
        dmsSurfaceCompanyLogoComponentWrapper.add(dmsSurfaceCompanyLogoComponent);
        dmsSurfaceCompanyLogoComponent.addClassName("dms-surface-company-logo-component");
        dmsSurfaceCompanyLogoComponent.addClickListener(this::surfaceCompanyLogoComponentClickEvent);
        Span dmsSurfaceCompanyLogoImageWrapper = new Span();
        dmsSurfaceCompanyLogoComponent.add(dmsSurfaceCompanyLogoImageWrapper);
        Image dmsSurfaceCompanyLogoImage = new Image("frontend/img/documenta_logo_square.png", "");
        dmsSurfaceCompanyLogoImageWrapper.add(dmsSurfaceCompanyLogoImage);
        dmsSurfaceCompanyLogoComponentWrapper.addClassName("dms-surface-company-logo-image");
        dmsCustomizationNavigationItemsArea.setClassName("dms-customization-navigation-items-area");
        dmsPermanentNavigationItemsArea.setClassName("dms-permanent-navigation-items-area");
        dmsSurfaceContentComponent.addClassName("dms-surface-content-component");
        TemplateUtil.setComponentIdAndClassName(dmsFirstLevel, "dms-first-level", "dms-first-level");
        dmsFirstLevelExpandButtonContainer.setId("dms-first-level-expand-button-container");
        dmsFirstLevelExpandButtonContainer.getElement().setAttribute("classname", "dms-first-level-expand-button-container");
        dmsFirstLevelExpandButtonComponent.setClassName("dms-first-level-expand-button-component");
        dmsFirstLevelExpandButtonComponent.setVisible(false);
        dmsFirstLevelExpandButtonComponent.addClickListener(e ->{
            hideSearchSlide();
        });
        Div dmsFirstLevelExpandButton = new Div();
        dmsFirstLevelExpandButton.setClassName("dms-first-level-expand-button");
        dmsFirstLevelExpandButtonComponent.add(dmsFirstLevelExpandButton);
        Span dmsFirstLevelExpandIcon = new Span();
        dmsFirstLevelExpandIcon.addClassNames("dms-first-level-expand-icon", "fa", "fa-angle-left");
        dmsFirstLevelExpandButton.add(dmsFirstLevelExpandIcon);
        TemplateUtil.setComponentIdAndClassName(dmsFirstLevelControl, "dms-first-level-control", "dms-first-level-control");
//        dmsFirstLevelControl.add(dmsFirstLevelControlComponent);
        dmsFirstLevelControlComponent.addClassName("dms-first-level-control-component");

        dmsFirstLevelControlComponent.getElement().addEventListener("mouseover", e -> {
            mouseOnDmsSurfaceControl = true;
//            try {
//                Thread.sleep(3000);
                if(!mouseOnDmsSurfaceControl)
                    return;
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }

            if(!dmsFirstLevelControlComponent.hasClassName("is-pinned")) {
                dmsFirstLevelControlComponent.addClassName("is-expanded");
            }
        });

        dmsFirstLevelControlComponent.getElement().addEventListener("mouseleave", e -> {
            mouseOnDmsSurfaceControl = false;
            if(!dmsFirstLevelControlComponent.hasClassName("is-pinned")) {
                dmsFirstLevelControlComponent.removeClassName("is-expanded");
            }
        });

        Div dmsCollapseFirstLevelButtonComponentWrapper = new Div();
        dmsFirstLevelControlComponent.add(dmsCollapseFirstLevelButtonComponentWrapper);
        dmsCollapseFirstLevelButtonComponentWrapper.addClassName("dms-collapse-first-level-button-component-wrapper");
        Div dmsCollapseFirstLeveButtonComponent = new Div();
        dmsCollapseFirstLevelButtonComponentWrapper.add(dmsCollapseFirstLeveButtonComponent);
        dmsCollapseFirstLeveButtonComponent.addClassName("dms-collapse-first-leve-button-component");
        dmsCollapseFirstLeveButtonComponent.getElement().setAttribute("disabled", true);

        dmsCollapseFirstLeveButtonComponent.addClickListener(e -> {
            if(dmsCollapseFirstLeveButtonComponent.hasClassName("is-pinned")) {
                dmsCollapseFirstLeveButtonComponent.removeClassName("is-pinned");
                dmsFirstLevelControlComponent.removeClassNames("is-expanded", "is-pinned");
                dmsFirstLevelContentWrapper.removeClassName("dms-first-level-control-pinned");
                dmsFirstLevelContentWrapper.addClassName("dms-first-level-control-unpinned");
            }else {
                dmsCollapseFirstLeveButtonComponent.addClassName("is-pinned");
                dmsFirstLevelControlComponent.addClassNames("is-expanded", "is-pinned");
                dmsFirstLevelContentWrapper.removeClassName("dms-first-level-control-unpinned");
                dmsFirstLevelContentWrapper.addClassName("dms-first-level-control-pinned");
            }
        });

        Span dmsCollapseIcon = new Span();
        dmsCollapseFirstLeveButtonComponent.add(dmsCollapseIcon);
        dmsCollapseIcon.addClassNames("dms-collapse-icon", "fa", "fa-angle-right");
        TemplateUtil.setComponentIdAndClassName(dmsFirstLevelContentWrapper, "dms-first-level-content-wrapper", "dms-first-level-content-wrapper");
        dmsFirstLevelContentWrapper.addClassName("dms-first-level-control-unpinned");
        TemplateUtil.setComponentIdAndClassName(dmsFirstLevelContent,"dms-first-level-content", "dms-first-level-content");
        dmsFirstLevelContent.getStyle().set("margin-right", "0px");
    }

    protected void surfaceCompanyLogoComponentClickEvent(ClickEvent<Div> divClickEvent) {
        //TODO navigate to a home/default page
    }

    public void addSearchSlideView(FlowView flowView) {
        Assert.isAssignable(Component.class, flowView.getClass());
        dmsSurfaceContentComponent.add((Component) flowView);
    }

    public void addFirstLevelControllerView(FlowView flowView) {
        Assert.isAssignable(Component.class, flowView.getClass());
        ((HasStyle)flowView).addClassName("dms-home-control-component");
        dmsFirstLevelControlComponent.add((Component) flowView);
    }

    public void addFirstLevelContentView(FlowView flowView) {
        Assert.isAssignable(Component.class, flowView.getClass());
        dmsFirstLevelContent.add((Component) flowView);
    }

    public void addCustomizationNavigationView(FlowView flowView) {
        Assert.isAssignable(Component.class, flowView.getClass());
        dmsCustomizationNavigationItemsArea.add((Component) flowView);
    }

    public void addPermanentNavigationView(FlowView flowView) {
        Assert.isAssignable(Component.class, flowView.getClass());
        dmsPermanentNavigationItemsArea.add((Component) flowView);
    }

    public boolean isSearchSlideVisible() {
        return dmsFirstLevel.hasClassName("is-collapsed");
    }

    public void hideSearchSlide() {
        dmsFirstLevel.removeClassName("is-collapsed");
        dmsFirstLevelExpandButtonComponent.setVisible(false);
    }


    public void showSearchSlide() {
        dmsFirstLevel.addClassName("is-collapsed");
        dmsFirstLevelExpandButtonComponent.setVisible(true);
    }

}
