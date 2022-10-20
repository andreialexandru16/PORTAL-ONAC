package ro.bithat.dms.microservices.portal.ecitizen.gui.header;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.RouteConfiguration;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.html.ClickNotifierHtmlContainer;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;

public class Ps4ECitizenMainHeaderContainerView extends DivFlowViewBuilder<Ps4ECitizenMainHeaderContainerPresenter> {

    @FlowComponent
    private Ps4ECitizenMainHeaderSecondaryMenuView mainHeaderSecondaryMenuView;

    @FlowComponent
    private Ps4ECitizenMainHeaderMainMenuView mainHeaderMainMenuView;

    private Div menuWrap = new Div();

    private Span navbarTogglerButtonHook = new Span();

    private ClickNotifierHtmlContainer navbarTogglerButton = new ClickNotifierHtmlContainer(Tag.BUTTON, navbarTogglerButtonHook);

    private Div emptyElement = new Div();

    private Nav headerNav = new Nav(emptyElement, navbarTogglerButton, menuWrap);

    private Div menuContainer = new Div(headerNav);

    private Image logoImage = new Image("PORTAL/assets/images/logos/logo.png", "logo primaria sectorului 4");

    private ClickNotifierAnchor logoLink = new ClickNotifierAnchor();

    private Div logo = new Div(logoLink);

    private Div row = new Div(logo, menuContainer);

    private Div container = new Div(row);


    @Override
    protected void buildView() {
        addClassName("main_header_container");

        container.addClassName("container");
        row.addClassName("row");
        add(container);
        logo.addClassNames("col-8", "col-sm-6", "col-md-4", "col-lg-2", "logo");
        menuContainer.addClassNames("col-4", "col-sm-6", "col-md-8", "col-lg-10", "menu_container");
        headerNav.addClassNames("navbar", "navbar-expand-xl", "navbar-dark");
        emptyElement.addClassName("empty_element");
        navbarTogglerButton.addClassName("navbar-toggler");
        navbarTogglerButton.getElement().setAttribute("type", "button");
        navbarTogglerButton.getElement().setAttribute("data-toggle", "collapse");
        navbarTogglerButton.getElement().setAttribute("data-target", "#menu-wrap");
        navbarTogglerButton.getElement().setAttribute("aria-controls", "menu-wrap");
        navbarTogglerButton.getElement().setAttribute("aria-expanded", "false");
        navbarTogglerButton.getElement().setAttribute("aria-label", "Toggle navigation");
        navbarTogglerButton.getElement().setAttribute("type", "button");
        navbarTogglerButtonHook.addClassName("navbar-toggler-icon");
        logoLink.setTarget("_blank");
        menuWrap.setId("menu-wrap");
        menuWrap.addClassNames("row", "collapse", "navbar-collapse");
        menuWrap.add(mainHeaderMainMenuView, mainHeaderSecondaryMenuView);
        logoLink.add(logoImage);

        logoLink.setHref(getPresenter().getWordpressUrl());

    }

//    public void setLogoLinkHref(String href) {
//        logoLink.setHref(href);
//    }

}
