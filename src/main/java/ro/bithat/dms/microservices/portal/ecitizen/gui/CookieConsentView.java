package ro.bithat.dms.microservices.portal.ecitizen.gui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.view.DivFlowViewBuilder;
import ro.bithat.dms.passiveview.i18n.flow.InternationalizeViewEngine;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;

public class CookieConsentView extends DivFlowViewBuilder<CookieConsentPresenter> {


    private ClickNotifierAnchor dismissBtn = new ClickNotifierAnchor();

    private Div cookieConsentCompliance = new Div(dismissBtn);

    private ClickNotifierAnchor infoLink = new ClickNotifierAnchor();

    private Span cookieConsentDesc = new Span(new Text("ps4.ecetatean.cookie.policy.info"), infoLink);

    public void show() {
        if(!clientDismissed() && !getParent().isPresent()) {
            UI.getCurrent().add(this);
        }
    }


    public boolean clientDismissed() {
        HttpServletRequest request =
                (HttpServletRequest) VaadinRequest.getCurrent();
        Cookie[] cookies = request.getCookies();
        return Stream.of(cookies).filter(c -> c.getName().equalsIgnoreCase("cc-DISMISS")).count() > 0l;
    }

    public void dismiss() {
        HttpServletResponse response = (HttpServletResponse) VaadinResponse.getCurrent();
        Cookie cookie = new Cookie("cc-DISMISS", "true");
        response.addCookie(cookie);
        UI.getCurrent().remove(this);
    }

    @Override
    protected void buildView() {
        getElement().setAttribute("role", "dialog");
        getElement().setAttribute("aria-live", "polite");
        getElement().setAttribute("aria-label", "cookieconsent");
        getElement().setAttribute("aria-describedby", "cookieconsent:desc");
        addClassNames("cc-window", "cc-banner", "cc-type-info", "cc-theme-block", "cc-bottom", "cc-color-override--1608664607");
        getElement().setAttribute("style", "");
        getStyle().set("color", "rgb(92, 114, 145)");
        getStyle().set("background-color", "rgb(234, 247, 247)");
        add(cookieConsentDesc, cookieConsentCompliance);

        cookieConsentDesc.setId("cookieconsent:desc");
        cookieConsentDesc.addClassName("cc-message");

        infoLink.getStyle().set("cursor", "pointer");
        infoLink.getStyle().set("padding-left", "5px");
        infoLink.add(new Text("ps4.ecetatean.cookie.policy.link.title"));
        infoLink.addClickListener(e
                -> VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenCookieConsentInfoRoute.class)));

        cookieConsentCompliance.addClassName("cc-compliance");


        dismissBtn.getElement().setAttribute("arria-label", "dismiss cookie message");
        dismissBtn.getElement().setAttribute("role", "button");
        dismissBtn.getElement().setAttribute("tabindex", "0");
        dismissBtn.addClassNames("cc-btn", "cc-DISMISS");
        dismissBtn.add(new Text("ps4.ecetatean.cookie.policy.ack.title"));
        InternationalizeViewEngine.internationalize(this);
    }
}
