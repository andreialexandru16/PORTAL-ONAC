package ro.bithat.dms.microservices.portal.ecitizen.gui.header;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotatedElementUtils;
import ro.bithat.dms.microservices.portal.ecitizen.contact.gui.Ps4ECitizenContactRoute;
import ro.bithat.dms.microservices.portal.ecitizen.faq.gui.Ps4ECitizenFaqRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui.Ps4ECitizenOnlineServicesRoute;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;
import ro.bithat.dms.security.SecurityUtils;

public class Ps4ECitizenMainHeaderMainMenuView extends DivFlowViewBuilder<Ps4ECitizenMainHeaderMainMenuPresenter> {


    private ClickNotifierAnchor logoutServicesRouteNavItemLink =  new ClickNotifierAnchor();//new Anchor(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenContactRoute.class));

    private ListItem logoutRouteNavItem = new ListItem(logoutServicesRouteNavItemLink);

    private ClickNotifierAnchor contactServicesRouteNavItemLink =  new ClickNotifierAnchor();//new Anchor(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenContactRoute.class));

    private ListItem contactRouteNavItem = new ListItem(contactServicesRouteNavItemLink);

    private ClickNotifierAnchor faqServicesRouteNavItemLink =  new ClickNotifierAnchor();//(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenFaqRoute.class));//new Anchor(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenFaqRoute.class));

    private ListItem faqRouteNavItem = new ListItem(faqServicesRouteNavItemLink);

    private ClickNotifierAnchor onlineServicesRouteNavItemLink =  new ClickNotifierAnchor();//;(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenOnlineServicesRoute.class));//new Anchor(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenOnlineServicesRoute.class));

    private ListItem onlineServicesRouteNavItem = new ListItem(onlineServicesRouteNavItemLink);

    private ClickNotifierAnchor homeRouteNavItemLink = new ClickNotifierAnchor();//(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenHomeRoute.class));

    private ListItem homeRouteNavItem = new ListItem(homeRouteNavItemLink);

    //private UnorderedList navbarNav = new UnorderedList(homeRouteNavItem, onlineServicesRouteNavItem, faqRouteNavItem, contactRouteNavItem);
    private UnorderedList navbarNav = new UnorderedList();

    private Div mainMenu = new Div(navbarNav);

    @Override
    protected void buildView() {
        if(getPresenter().getShowHeader()){
            navbarNav.add(homeRouteNavItem);
//            , onlineServicesRouteNavItem, faqRouteNavItem, contactRouteNavItem);
        }
        addClassName("col-xl-8");
        mainMenu.addClassName("main_menu");
        navbarNav.addClassName("navbar-nav");
        homeRouteNavItem.addClassName("nav-item");
        homeRouteNavItemLink.getStyle().set("cursor", "pointer");
        homeRouteNavItemLink.setText("main.header.main.menu.view.home.nav.item.link.text");
        homeRouteNavItemLink.addClassName("nav-link");
        if(VaadinClientUrlUtil.getRouteRelativePath()
                .equalsIgnoreCase(AnnotatedElementUtils.getMergedAnnotation(Ps4ECitizenAnonymousHomeRoute.class, Route.class).value())) {
            homeRouteNavItem.addClassName("active");
        }
//        onlineServicesRouteNavItem.addClassName("nav-item");
//        onlineServicesRouteNavItemLink.getStyle().set("cursor", "pointer");
//        onlineServicesRouteNavItemLink.setText("main.header.main.menu.view.onlineservices.nav.item.link.text");
//        onlineServicesRouteNavItemLink.addClassName("nav-link");
//        if(VaadinClientUrlUtil.getRouteRelativePath()
//                .equalsIgnoreCase(AnnotatedElementUtils.getMergedAnnotation(Ps4ECitizenOnlineServicesRoute.class, Route.class).value())) {
//            onlineServicesRouteNavItem.addClassName("active");
//        }
//        faqRouteNavItem.addClassName("nav-item");
//        faqServicesRouteNavItemLink.getStyle().set("cursor", "pointer");
//        faqServicesRouteNavItemLink.setText("main.header.main.menu.view.faq.nav.item.link.text");
//        faqServicesRouteNavItemLink.addClassName("nav-link");
//        if(VaadinClientUrlUtil.getRouteRelativePath()
//                .equalsIgnoreCase(AnnotatedElementUtils.getMergedAnnotation(Ps4ECitizenFaqRoute.class, Route.class).value())) {
//            faqRouteNavItem.addClassName("active");
//        }
//        contactRouteNavItem.addClassName("nav-item");
//        contactServicesRouteNavItemLink.getStyle().set("cursor", "pointer");
//        contactServicesRouteNavItemLink.setText("main.header.main.menu.view.contact.nav.item.link.text");
//        contactServicesRouteNavItemLink.addClassName("nav-link");
//        if(VaadinClientUrlUtil.getRouteRelativePath()
//                .equalsIgnoreCase(AnnotatedElementUtils.getMergedAnnotation(Ps4ECitizenContactRoute.class, Route.class).value())) {
//            contactRouteNavItem.addClassName("active");
//        }
        add(mainMenu);
        logoutRouteNavItem.addClassName("nav-item");
        logoutServicesRouteNavItemLink.getStyle().set("cursor", "pointer");
        logoutServicesRouteNavItemLink.setText("Logout");
        logoutServicesRouteNavItemLink.addClassName("nav-link");
        logoutServicesRouteNavItemLink.addClickListener(e->
                VaadinClientUrlUtil.setLocation("/logout")
        );
        if(!SecurityUtils.getUsername().equalsIgnoreCase("nouser")) {
            if(getPresenter().getShowHeader()){
                navbarNav.add(logoutRouteNavItem);
            }else{
                navbarNav.add();
            }

        }

        homeRouteNavItemLink.addClickListener(e
                -> VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class)));
        contactServicesRouteNavItemLink.addClickListener(e
                -> VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenContactRoute.class)));
        faqServicesRouteNavItemLink.addClickListener(e
                -> VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenFaqRoute.class)));
        onlineServicesRouteNavItemLink.addClickListener(e
                -> VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenOnlineServicesRoute.class)));
    }

}
