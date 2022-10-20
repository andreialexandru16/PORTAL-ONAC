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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Route(value = "solicitare-revizie-finala")
@PageTitle(" Revizie Finală Solicitare")

public class Ps4ECitizenServiceRequestReviewRoute extends DocumentTypeRoute {


    @FlowComponent
    private Ps4ECitizenServiceRequestReviewView serviceRequestReviewView;

    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, getBreadcrumbView());
        getBreadcrumbView().clearCrumbs();
       /* getBreadcrumbView().addCrumb("ps4.ecetatean.breadcrumb.home.page.title",
                RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));*/
        Optional<Integer> documentType = QueryParameterUtil.getQueryParameter("tipDocument", Integer.class);
        Optional<Integer> documentId = QueryParameterUtil.getQueryParameter("document", Integer.class);
        Optional<Integer> fileId = QueryParameterUtil.getQueryParameter("request", Integer.class);
        if(documentType.isPresent()) {
            Optional<TipDocument> documentClass = getPs4Service().getDocumentTypeById(documentType.get());
            if(documentClass.isPresent()) {
                Map<String, Object> breadcrumbParameters = new HashMap<>();
                breadcrumbParameters.put("tipDocument", documentType.get());
                getBreadcrumbView().addCrumb(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.document.type.title")
                                + documentClass.get().getDenumire().toLowerCase(),
                        QueryParameterUtil.getRelativePathWithQueryParameters(breadcrumbParameters, Ps4ECitizenServiceRoute.class));
                if(documentId.isPresent() && fileId.isPresent()) {
                    Optional<Document> document = getPs4Service().getDocumentByIdAndClasa(documentType.get(),documentId.get());
                    if(document.isPresent() && fileId.isPresent()) {
                        breadcrumbParameters.put("document", documentId.get());
                        getBreadcrumbView().addCrumb(document.get().getDenumire(),
                                QueryParameterUtil.getRelativePathWithQueryParameters(breadcrumbParameters, Ps4ECitizenServiceRequestRoute.class));
//                        getBreadcrumbView().setCurrentPageTitle(serviceRequestReviewView.getPresenter().getPortalFileTitle());
                        addContentContainer(serviceRequestReviewView);
                        return true;
                    }
                }
            }

        }
        return false;
    }

}
