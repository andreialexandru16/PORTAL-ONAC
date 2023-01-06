package ro.bithat.dms.microservices.portal.ecitizen.gui.header;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.i18n.LocaleChangeObserver;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.portal.ecitizen.home.gui.Ps4ECitizenHomeRoute;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.Ps4ECitizenMyAccountRoute;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;
import ro.bithat.dms.security.SecurityUtils;

public class Ps4ECitizenMainHeaderSecondaryMenuView extends DivFlowViewBuilder<Ps4ECitizenMainHeaderSecondaryMenuPresenter> implements LocaleChangeObserver {


    private Label i18nSelectorNavItemEnglishLanguageLabel = new Label("main.header.secondary.menu.view.english.language.label");

    private Image i18nSelectorNavItemEnglishLanguageImage = new Image("PORTAL/assets/images/flags/flag_EN.png", "english flag");

    private Anchor i18nSelectorNavItemEnglishLanguageLink = new Anchor("javascript:window.location.reload(true)", i18nSelectorNavItemEnglishLanguageImage, i18nSelectorNavItemEnglishLanguageLabel);

    private Div i18nSelectorNavItemSecondSplitter= new Div();

    private Label i18nSelectorNavItemRomanianLanguageLabel = new Label("main.header.secondary.menu.view.romanian.language.label");

    private Image i18nSelectorNavItemRomanianLanguageImage = new Image("PORTAL/assets/images/flags/flag_RO.png", "romanian flag");

    private Anchor i18nSelectorNavItemRomanianLanguageLink = new Anchor("javascript:window.location.reload(true)", i18nSelectorNavItemRomanianLanguageImage, i18nSelectorNavItemRomanianLanguageLabel);

    private Div i18nSelectorNavItemFirstSplitter= new Div();

    private Div i18nSelectorNavItemLanguageDropdownMenu= new Div(i18nSelectorNavItemFirstSplitter,
            i18nSelectorNavItemRomanianLanguageLink, i18nSelectorNavItemSecondSplitter,
            i18nSelectorNavItemEnglishLanguageLink);

    private Label i18nSelectorNavItemSelectedLanguageLabel = new Label();

    private Image i18nSelectorNavItemSelectedLanguageImage = new Image();

    private Anchor i18nSelectorNavItemSelectedLanguageLink = new Anchor("javascript:void(0)", i18nSelectorNavItemSelectedLanguageImage, i18nSelectorNavItemSelectedLanguageLabel);

    private ListItem i18nSelectorNavItem = new ListItem(i18nSelectorNavItemSelectedLanguageLink, i18nSelectorNavItemLanguageDropdownMenu);

    private HtmlContainer authenticationNavItemIcon = new HtmlContainer("i");

    private Label authenticationNavItemLinkLabel = new Label("main.header.secondary.menu.view.authentication.nav.item.link.text");

    private ClickNotifierAnchor authenticationNavItemLink = new ClickNotifierAnchor();

    private ClickNotifierAnchor authenticationNavItemNotificationLink = new ClickNotifierAnchor();

    private ListItem authenticationNavItem = new ListItem(authenticationNavItemLink);

    private ListItem authenticationNavItemNotification = new ListItem(authenticationNavItemNotificationLink);

    private UnorderedList navbarNav = new UnorderedList(authenticationNavItem);

    private Nav secondaryTopMenu = new Nav(navbarNav);

    @Override
    protected void buildView() {
        Boolean ffI18n = false;
        if(ffI18n) {
            navbarNav.add(i18nSelectorNavItem);
        }
        addClassName("col-xl-4");
        secondaryTopMenu.addClassName("secondary_top_menu");
        navbarNav.addClassName("navbar-nav");
        authenticationNavItem.addClassName("nav-item");
        authenticationNavItemLink.add(authenticationNavItemIcon, authenticationNavItemLinkLabel);
        //"/frontend/ps4/PORTAL/autentificare.html",
        authenticationNavItemLink.setTarget("_self");
        authenticationNavItemLink.addClassNames("nav-link", "login-icon", "text-nowrap");
        authenticationNavItemLink.getStyle().set("cursor", "pointer");

        authenticationNavItemIcon.addClassNames("fas", "fa-user");
        i18nSelectorNavItem.addClassNames("nav-item", "dropdown", "language-select");
        i18nSelectorNavItemSelectedLanguageLink.addClassNames("nav-link", "dropdown-toggle");
        i18nSelectorNavItemSelectedLanguageLink.setId("languageSelect");
        i18nSelectorNavItemSelectedLanguageLink.getElement().setAttribute("role", "button");
        i18nSelectorNavItemSelectedLanguageLink.getElement().setAttribute("data-toggle", "dropdown");
        i18nSelectorNavItemSelectedLanguageLink.getElement().setAttribute("aria-haspopup", true);
        i18nSelectorNavItemSelectedLanguageLink.getElement().setAttribute("aria-expanded", false);
        i18nSelectorNavItemLanguageDropdownMenu.addClassName("dropdown-menu");
        i18nSelectorNavItemLanguageDropdownMenu.getElement().setAttribute("aria-labelledby", "languageSelect");
        i18nSelectorNavItemRomanianLanguageLink.addClassName("dropdown-item");
        i18nSelectorNavItemEnglishLanguageLink.addClassName("dropdown-item");
        authenticationNavItemLinkLabel.getStyle().set("margin-bottom", "0px");
        authenticationNavItemLinkLabel.getStyle().set("cursor", "pointer");
        authenticationNavItemNotificationLink.getStyle().set("cursor", "pointer");
        authenticationNavItemNotification.getStyle().set("cursor", "pointer");
        navbarNav.add(authenticationNavItemNotification);

        add(secondaryTopMenu);
        if(!SecurityUtils.getUsername().equalsIgnoreCase("nouser")) {
            authenticationNavItemLinkLabel.setText("Buna, " + SecurityUtils.getLastName());

        }
        authenticationNavItemLink.addClickListener(e -> {
            VaadinClientUrlUtil
                    .setLocation(SecurityUtils.getUsername().equalsIgnoreCase("nouser") ?
                            RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenHomeRoute.class) :
                            RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenMyAccountRoute.class));
        });

    }



    @Override
    public void localeChange(LocaleChangeEvent event) {
        i18nSelectorNavItemSelectedLanguageImage.setSrc("frontend/ps4/ecitizen/assets/images/flags/flag_" + event.getLocale().getCountry() + ".png");
        i18nSelectorNavItemSelectedLanguageImage.setAlt(event.getLocale().getLanguage() + "flag");
        i18nSelectorNavItemSelectedLanguageLabel.setText(event.getLocale().getCountry());
    }

    public void setNotificationContainer(String notificationsNumber) {

        if(!notificationsNumber.equals("0")){
            authenticationNavItemNotificationLink.removeAll();
            authenticationNavItemNotification.addClassNames("nav-item");

            HtmlContainer iconBell = new HtmlContainer("i");
            iconBell.addClassNames("fas","fa-bell");

            Span spanNrNotif= new Span(notificationsNumber);
            authenticationNavItemNotificationLink.add(iconBell,spanNrNotif);
            authenticationNavItemNotificationLink.addClickListener(e -> {
                VaadinClientUrlUtil
                        .setLocation(SecurityUtils.getUsername().equalsIgnoreCase("nouser") ?
                                RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenHomeRoute.class) :
                                RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenMyAccountRoute.class));
            });
            authenticationNavItemNotificationLink.addClassNames("nav-link","notification");

        }else{
            navbarNav.remove(authenticationNavItemNotification);
        }
    }


}
