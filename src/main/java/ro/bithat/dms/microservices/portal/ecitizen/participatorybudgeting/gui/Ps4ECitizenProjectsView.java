package ro.bithat.dms.microservices.portal.ecitizen.participatorybudgeting.gui;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.NativeButton;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.service.StreamToStringUtil;

public class Ps4ECitizenProjectsView extends ContentContainerView<Ps4ECitizenProjectsPresenter> {

    private NativeButton buttonContainer = new NativeButton();

    @Override
    public void beforeBinding() {
        //setServicesListHeaderFontAwesomeIcon("fas", "fa-user");
        //setContentPageTile(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.faq.detail.title"));
        HtmlContainer htmlContainer = new HtmlContainer(Tag.DIV);
        htmlContainer.getElement().setProperty("innerHTML",
                StreamToStringUtil.fileToString("static/PORTAL/projects.html"));
        add(htmlContainer);
        //addServiceListContent();*/
        //getServiceListContainer().add(htmlContainer);

    }
}
