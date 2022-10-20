package ro.bithat.dms.microservices.portal.ecitizen.faq.gui;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.portal.ecitizen.contact.gui.Ps4ECitizenContactRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.service.StreamToStringUtil;

public class Ps4ECitizenFaqView extends ContentContainerView<Ps4ECitizenFaqPresenter> {

    private ClickNotifierAnchor anchorContainer = new ClickNotifierAnchor();
    private NativeButton buttonContainer = new NativeButton();

    @Override
    public void beforeBinding() {
        setServicesListHeaderFontAwesomeIcon("fas", "fa-user");
        setContentPageTile(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.faq.detail.title"));
        HtmlContainer htmlContainer = new HtmlContainer(Tag.DIV);
        htmlContainer.getElement().setProperty("innerHTML",
                StreamToStringUtil.fileToString("static/PORTAL/faq_" +UI.getCurrent().getLocale().getCountry().toLowerCase()+".html"));
        add(htmlContainer, anchorContainer);

        //New faq //
//        buttonContainer.add(anchorContainer);
//        buttonContainer.addClassNames("new_request");

        anchorContainer.addClassNames("btn","btn-secondary","btn-block","font-weight-bold");
        anchorContainer.add(new Text("ps4.ecetatean.faq.button.label"));
        HtmlContainer iconArrowNext= new HtmlContainer("i");
        iconArrowNext.addClassNames("fas","fa-arrow-alt-circle-right");
        anchorContainer.add(iconArrowNext);
        anchorContainer.setHref("javascript:void(0);");
        anchorContainer.addClickListener(e ->
                UI.getCurrent().getPage().setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenContactRoute.class)));

        anchorContainer.addClassNames("btn","btn-secondary","btn-block","font-weight-bold");
        //New faq //
    }
}
