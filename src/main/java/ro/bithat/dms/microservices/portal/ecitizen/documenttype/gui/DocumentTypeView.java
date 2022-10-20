package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;
import ro.bithat.dms.microservices.portal.ecitizen.home.gui.Ps4ECitizenHomeRoute;
import ro.bithat.dms.microservices.portal.ecitizen.gui.ContentContainerView;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;

import java.util.Optional;

public abstract class DocumentTypeView<P extends DocumentTypePresenter> extends ContentContainerView<P> {


    public void setContentPageTile(TipDocument documentClass, Optional<Document> document) {
        setServicesListHeaderIcon("/icons/documentcategory/" +
                documentClass.getDenumire().toLowerCase().replace(" ", "_") + ".png");

                ///documentClass.getIconFilenamePath());
        StringBuilder title = new StringBuilder(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.document.type.title") + documentClass.getDenumire());
        if(document.isPresent()) {
            title.append(" - " + document.get().getDenumire());
        }
        setContentPageTile(title.toString());
    }


    @ClientCallable
    public void swalErrorAck() {
        VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenHomeRoute.class));
    }


}
