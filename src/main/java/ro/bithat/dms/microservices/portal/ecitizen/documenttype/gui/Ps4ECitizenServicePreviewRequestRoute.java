package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.security.SecurityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Route(value = "previzualizare-formular")
@PageTitle("Previzualizare formular")

public class Ps4ECitizenServicePreviewRequestRoute extends DocumentTypeRoute {


    @FlowComponent
    private Ps4ECitizenServicePreviewRequestView servicePreviewRequestView;

    @Override
    protected boolean buildPortalRoute() {


        Optional<Integer> paramDocumentType = QueryParameterUtil.getQueryParameter("tipDocument", Integer.class);
        Optional<Integer> paramDocumentId = QueryParameterUtil.getQueryParameter("document", Integer.class);

        Optional<TipDocument> documentClass = getPs4Service().getDocumentTypeById(paramDocumentType.get());

        //daca nu se gaseste clasa in lista => reciteste lista tipuri/clase documente si reincearca

        SecurityUtils.forceGetAllDocumentTypes(SecurityUtils.getToken());
        documentClass = getPs4Service().getDocumentTypeById(paramDocumentType.get());

        if (documentClass.isPresent()) {
            if (paramDocumentId.isPresent()) {
                Optional<Document> document = getPs4Service().getDocumentByIdAndClasa(paramDocumentType.get(), paramDocumentId.get());
                if (document.isPresent()) {
                    addContentContainer(servicePreviewRequestView);
                    return true;
                }

            }
        }

        return false;


    }


}
