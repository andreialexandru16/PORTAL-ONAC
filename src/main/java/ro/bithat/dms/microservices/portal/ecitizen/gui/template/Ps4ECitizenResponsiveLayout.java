package ro.bithat.dms.microservices.portal.ecitizen.gui.template;

//12.07.2021 - Neata Georgiana -ANRE- adaugare param unitateName pentru externalizare CSS.  Sa poata fi modificat de la un client la altul.

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ReconnectDialogConfiguration;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.BrowserWindowResizeEvent;
import com.vaadin.flow.component.page.BrowserWindowResizeListener;
import com.vaadin.flow.component.page.ExtendedClientDetails;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.shared.ui.LoadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.portal.ecitizen.gui.*;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.FlowComponentBuilder;
import ro.bithat.dms.passiveview.FlowRouteObserver;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.i18n.flow.InternationalizeViewEngine;
import ro.bithat.dms.security.SecurityUtils;

import javax.annotation.PostConstruct;
import java.util.Optional;

//@Viewport("width=device-width, initial-scale=1, shrink-to-fit=no")
//@Meta(name = "charset", content = "utf-8")
//@CssImport(value = "ps4/defaults.css")
@CssImport(value = "ps4/upload-file.css", themeFor = "vaadin-upload-file")
@CssImport(value = "ps4/text-field.css", themeFor = "vaadin-text-field")
@CssImport(value = "ps4/text-area.css", themeFor = "vaadin-text-area")
@CssImport(value = "ps4/combobox.css", themeFor = "vaadin-combo-box")
@CssImport(value = "ps4/date-picker.css", themeFor = "vaadin-date-picker")
@CssImport(value = "ps4/checkbox.css", themeFor = "vaadin-checkbox")
@StyleSheet("https://fonts.googleapis.com/css?family=Open+Sans:400,600,700,800&display=swap")
@StyleSheet("PORTAL/assets/css/font-awesome-all.min.css")
@StyleSheet("PORTAL/assets/css/jquery-ui.min.css")
@StyleSheet("PORTAL/assets/css/slick.css")
@StyleSheet("PORTAL/assets/css/bootstrap.min.css")
@StyleSheet("PORTAL/assets/css/sweetalert2.min.css")
@StyleSheet("PORTAL/assets/css/documentcategory.css")
@StyleSheet("PORTAL/assets/css/jquery.fancybox.min.css")
@StyleSheet("PORTAL/assets/css/cc.min.css")
@StyleSheet("https://printjs-4de6.kxcdn.com/print.min.css")

public abstract class Ps4ECitizenResponsiveLayout extends Div implements FlowRouteObserver, Page.ExtendedClientDetailsReceiver, BrowserWindowResizeListener {


    private Logger logger = LoggerFactory.getLogger(Ps4ECitizenResponsiveLayout.class);
    //12.07.2021 - Neata Georgiana -ANRE- adaugare param unitateName pentru externalizare CSS.  Sa poata fi modificat de la un client la altul.

    @Value("${dmsws.unitname:}")
    private String unitate;
    @Value("${flowView.clientUpdate.refreshInterval:1000}")
    private int refreshIntervalMs;


    @Value("${show.header:true}")
    private boolean showHeader;

    @Value("${show.footer:true}")
    private boolean showFooter;

    @Value("${show.cookie:true}")
    private boolean showCookie;
    @Autowired
    private FlowComponentBuilder componentBuilder;

    @FlowComponent
    private CookieConsentView cookieConsentView;

    private Header header = new Header();

    private Div contentHeaderContainer = new Div();

    private Div contentContainer = new Div();

    private Div content = new Div(contentContainer);

    private Div footerVisualIdentityContainer = new Div();

    private Div footerVisualIdentity = new Div(footerVisualIdentityContainer);

    // Footer *****
    private Image logoFooterLinkImage = new Image("PORTAL/assets/images/logos/logo.png", "logo");

    private ClickNotifierAnchor copyrightLink = new ClickNotifierAnchor();

    private ClickNotifierAnchor termsAndConditionLink = new ClickNotifierAnchor();

    private ClickNotifierAnchor confidentialityPolicyLink = new ClickNotifierAnchor();

    private ClickNotifierAnchor cookiePolicyLink = new ClickNotifierAnchor();

    private ClickNotifierAnchor logoHomeLink = new ClickNotifierAnchor();

    private Div copyrightDiv = new Div(copyrightLink);

    private Div termsAndConditionDiv = new Div(termsAndConditionLink);

    private Div confidentialityPolicyDiv = new Div(confidentialityPolicyLink);

    private Div cookiePolicyDiv = new Div(cookiePolicyLink);

    private Div footerLinksRow = new Div(termsAndConditionDiv, confidentialityPolicyDiv, cookiePolicyDiv);

    private Div footerLinksDiv = new Div(footerLinksRow);


    //

//    private Paragraph footerLeftRowP = new Paragraph("footer.left.row.p");
//
//    private Div footerRowLeft = new Div(footerLeftRowP);


//    private Anchor logoFooterLink = new Anchor(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class), logoFooterLinkImage);

    private Div logoFooter = new Div(logoHomeLink);

    private Div footerRowRight = new Div(logoFooter);

    private Div footerRow = new Div(/*footerRowLeft,*/copyrightDiv, footerLinksDiv, footerRowRight);

    private Div footerContainer = new Div(footerRow);

    private Footer footer = new Footer(footerContainer);

    private boolean routeLoaded = false;

    private Optional<ExtendedClientDetails> extendedClientDetails = Optional.empty();

    @Override
    public FlowComponentBuilder getComponentBuilder() {
        return componentBuilder;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        //12.07.2021 - Neata Georgiana -ANRE- adaugare param unitateName pentru externalizare CSS.  Sa poata fi modificat de la un client la altul.
        if(unitate!=null && !unitate.isEmpty()){
            UI.getCurrent().getPage().addStyleSheet("PORTAL/assets/css/main_"+unitate+".css", LoadMode.LAZY);
        }else{
            UI.getCurrent().getPage().addStyleSheet("PORTAL/assets/css/main.css", LoadMode.LAZY);
        }
        logger.info("Before enter route:\t" + getClass().getCanonicalName());
        UI.getCurrent().getSession().getCurrent().setErrorHandler(errorEvent -> {
            logger.error(errorEvent.getThrowable().getMessage(), errorEvent.getThrowable());
            Notification.show("ERR: "+errorEvent.getThrowable().getMessage(), 10000,
                    Notification.Position.BOTTOM_END);
        });
//        UI.getCurrent().getPage().setTitle(I18NProviderStatic.getTranslation("ps4.ecitizen.page.title"));

//        componentBuilder.prepareModelAndView();

        //For enable client refresh callback
//        UI.getCurrent().getPage().executeJs("passiveViewClientUpdate($0, $1);", getElement(), refreshIntervalMs);


    }

    @ClientCallable
    public void swalErrorAck() {
        UI.getCurrent().getPage().setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        logger.info("Before leave route:\t" + getClass().getCanonicalName());
    }

//    @ClientCallable
//    public void setCookieConsent(String status) {
//        logger.info("set cookie:" + status);
//    }

    @ClientCallable
    public void clientUpdate() {
        logger.debug("Client refresh callback");
        componentBuilder.bindModelEventListener(this, "passiveViewClientUpdate");
//        throw new IllegalStateException();
    }

    @Override
    public void localeChange(LocaleChangeEvent event) {
        logger.info("Locale observer i18n route:\t" + getClass().getCanonicalName());
        if(routeLoaded) {
            internationalize();
        }
    }

    private void internationalize() {
        InternationalizeViewEngine.internationalize(this, UI.getCurrent().getLocale());
//        componentBuilder.internationalize();
    }

    @Override
    public void receiveDetails(ExtendedClientDetails extendedClientDetails) {
        try {
            logger.info("extendedClientDetails:\t"+ new ObjectMapper().writeValueAsString(extendedClientDetails));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.extendedClientDetails = Optional.ofNullable(extendedClientDetails);
        if(this.extendedClientDetails.isPresent()) {
            componentBuilder.getCompositeView().stream()
                    .filter(flowView -> ClientDetailsObserver.class.isAssignableFrom(flowView.getClass()))
                    .map(flowView -> (ClientDetailsObserver)flowView)
                    .forEach(clientDetailsObserver -> clientDetailsObserver.receiveClientDetails(extendedClientDetails));
        }
    }

    @Override
    public void browserWindowResized(BrowserWindowResizeEvent event) {
        logger.info("page:\t"+event.getSource().getHistory().getUI().getInternals().getActiveViewLocation().getPathWithQueryParameters()
                +"\twhidth:\t"+event.getWidth()+"\theight:\t"+event.getHeight());
        componentBuilder.getCompositeView().stream()
                .filter(flowView -> WindowResizeObserver.class.isAssignableFrom(flowView.getClass()))
                .map(flowView -> (WindowResizeObserver)flowView)
                .forEach(windowResizeObserver -> windowResizeObserver.browserWindowResized(event));
    }


    @PostConstruct
    protected void setup() {

        UI.getCurrent().getPage().retrieveExtendedClientDetails(this);
        UI.getCurrent().getPage().addBrowserWindowResizeListener(this);
        UI.getCurrent().getPage().addJavaScript("frontend/js/passive-view-client-update-callback.js");



        statelessReconnect();
        componentBuilder.buildRoute(this);
        setupLayout();
        setupClickNotifierLinks();
    }

    private void setupLayout() {

        header.setId("main-header");
//        contentHeaderContainer.setId("home-banner");
//        contentHeaderContainer.addClassName("homepage_banner");
        content.setId("main-content");
        content.addClassName("main-content");
        contentContainer.addClassName("container");
        footerVisualIdentity.addClassNames("footer_visual_identity", "text-center");
        footerVisualIdentityContainer.addClassName("container");
        footer.setId("main-footer");
        footerContainer.addClassName("container");
        footerRow.addClassName("row");
//        footerRowLeft.addClassNames("col-sm-6", "copyright");
        copyrightDiv.addClassNames("col-sm-12", "col-md-4", "col-lg-2", "copyright");
        footerLinksDiv.addClassNames("col-sm-12", "col-md-8", "col-lg-8", "links_footer");
        footerLinksRow.addClassName("row");
        logoHomeLink.add(logoFooterLinkImage);
        termsAndConditionDiv.addClassNames("col-sm-4", "col-md-4");
        confidentialityPolicyDiv.addClassNames("col-sm-4", "col-md-4");
        cookiePolicyDiv.addClassNames("col-sm-4", "col-md-4");
        footerRowRight.addClassNames("col-sm-12", "col-md-12", "col-lg-2");
        logoFooter.addClassNames("logo_footer", "text-right");
        //add(header, contentHeaderContainer, content, footerVisualIdentity, footer);
        if(showHeader){
            add(header, contentHeaderContainer);
        }
        add( content);
        if(showFooter){
            add(footerVisualIdentity, footer);
        }


    }

    private void setupClickNotifierLinks() {
        copyrightLink.setHref("https://www.documenta.ro/");
        copyrightLink.setTarget("_blank");
        copyrightLink.setText("footer.left.row.p");
//        termsAndConditionLink.setHref("#");

        logoHomeLink.getStyle().set("cursor", "pointer");
        logoHomeLink.addClickListener(e
                -> VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class)));
    }

    protected abstract boolean buildLayout();

    private void statelessReconnect() {
        ReconnectDialogConfiguration configuration = UI.getCurrent().getReconnectDialogConfiguration();
        configuration.setDialogText(I18NProviderStatic.getTranslation("message.reconnect.dialog"));
        configuration.setReconnectInterval(1000);
    }

    public void addHeaderContainer(Component... components) {
        header.add(components);
    }

    public void addContentHeaderContainer(String id, String[] classNames, Component... components) {
        contentHeaderContainer.setId(id);
        contentHeaderContainer.addClassNames(classNames);
        contentHeaderContainer.add(components);
    }

    public void addContentContainer(Component... components) {
        contentContainer.add(components);
    }

    //use this when view is the content
    public void addContent(Component component) {
        content.remove(contentContainer);
        content.addClassName("mt-0");
        content.add(component);
    }

    public Div getContent() {
        return content;
    }

    public void setFooterVisualIdentityContainerClassNames(String... classNames) {
        footerVisualIdentityContainer.addClassNames(classNames);
    }

    public void setFooterVisualIdentityClassNames(String... classNames) {
        footerVisualIdentity.addClassNames(classNames);
    }

    public void addFooterVisualIdentityContainer(Component... components) {
        footerVisualIdentityContainer.add(components);
    }

    public void addFooterContent(Component... components) {
        footerContainer.add(components);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        logger.info("After route:\t" + getClass().getCanonicalName()+"\tloaded");
        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/sweetalert2.min.js");



        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/jquery-3.5.1.min.js");
        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/jquery-ui.min.js");
        UI.getCurrent().getPage().addJavaScript("https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js");
//        UI.getCurrent().getPage().addJavaScript("https://cdnjs.cloudflare.com/ajax/libs/cookieconsent2/3.1.1/cookieconsent.min.js");
        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/bootstrap.min.js");
//        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/cc.min.js");
        //        UI.getCurrent().getPage().addJavaScript("https://cdnjs.cloudflare.com/ajax/libs/slick-carousel/1.9.0/slick.min.js");
        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/slick.min.js");
        UI.getCurrent().getPage().addJavaScript("https://cdnjs.cloudflare.com/ajax/libs/fabric.js/3.6.3/fabric.min.js");
        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/jquery.fancybox.min.js");
        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/main.js");
        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/leave_page.js");
        UI.getCurrent().getPage().addJavaScript("frontend/js/holographic_attribute_link_component.js");
        UI.getCurrent().getPage().addJavaScript("website/assets/js/documentaPortal-util.js");


//        UI.getCurrent().getPage().addJavaScript("https://code.jquery.com/jquery-1.12.4.js");
//
        UI.getCurrent().getPage().addJavaScript("https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.min.js");
        UI.getCurrent().getPage().addJavaScript("https://cdnjs.cloudflare.com/ajax/libs/dom-to-image/2.6.0/dom-to-image.min.js");
//        UI.getCurrent().getPage().addJavaScript("https://printjs-4de6.kxcdn.com/print.min.js");
        UI.getCurrent().getPage().addJavaScript("frontend/js/pdfjs.js");
        UI.getCurrent().getPage().addJavaScript("frontend/js/dateHourPicker.js");

        //        UI.getCurrent().getPage().addJavaScript("PORTAL/assets/js/testing.js");
        //        UI.getCurrent().getPage().executeJs("initCarouselSlider();");
        if(buildLayout()) {
            componentBuilder.prepareModelAndView();
        } else {
            UI.getCurrent().getPage().executeJs("swalError($0, $1)", "Pagină indisponibilă momentan. Vă rugăm reveniți mai târziu.", getElement());
            UI.getCurrent().getPage().executeJs("$('#resize_iframe', window.parent.document).trigger('click')");

        }
        //TODO enable this in only in production
//        boolean cookieAccepted = SecurityUtils.getCookieAccepted() != null && SecurityUtils.getCookieAccepted() == 1;
//        if(!cookieAccepted) {
//            UI.getCurrent().getPage()
//                    .executeJs("cookieConsent($0,$1,$2,$3,$4,$5,$6, $7);",
//                            I18NProviderStatic.getTranslation("ps4.ecetatean.cookie.policy.tilte"),
//                            I18NProviderStatic.getTranslation("ps4.ecetatean.cookie.policy.header"),
//                            I18NProviderStatic.getTranslation("ps4.ecetatean.cookie.policy.info"),
//                            I18NProviderStatic.getTranslation("ps4.ecetatean.cookie.policy.link.title"),
//                            I18NProviderStatic.getTranslation("ps4.ecetatean.cookie.policy.ack.title"),
//                            I18NProviderStatic.getTranslation("ps4.ecetatean.cookie.policy.accept.title"),
//                            I18NProviderStatic.getTranslation("ps4.ecetatean.cookie.policy.decline.title"),
//                            this.getElement());
//        }

        if(showCookie){
            if(!userDismissedCookieConsentInfo()) {
                cookieConsentView.show();
                if(cookieConsentView.clientDismissed()) {
                    cookieConsentView.getPresenter().userDismissed();
                }
            }
        }

        routeLoaded = true;
        internationalize();
        UI.getCurrent().getPage().executeJs("toggleDisplayState($0,$1);", "v-system-error", "none");
    }

    private boolean userDismissedCookieConsentInfo() {
        return !SecurityUtils.getUsername().equalsIgnoreCase("nouser")
                && Optional.ofNullable(SecurityUtils.getCookieAccepted()).isPresent()
                && SecurityUtils.getCookieAccepted() == 1;
    }
}
