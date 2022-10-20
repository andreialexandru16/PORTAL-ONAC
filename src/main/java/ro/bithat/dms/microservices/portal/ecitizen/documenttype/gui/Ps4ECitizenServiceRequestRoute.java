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

@Route(value = "detaliu-serviciu")
@PageTitle(" Detalii Serviciu")

public class Ps4ECitizenServiceRequestRoute extends DocumentTypeRoute {

    @FlowComponent
    private Ps4ECitizenServiceRequestView documentTypeServicedocumentTypeServiceRequestView;

    
    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, getBreadcrumbView());
        getBreadcrumbView().clearCrumbs();
       /* getBreadcrumbView().addCrumb("ps4.ecetatean.breadcrumb.home.page.title",
                RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));*/
        Optional<Integer> paramDocumentType = QueryParameterUtil.getQueryParameter("tipDocument", Integer.class);
        Optional<Integer> paramDocumentId = QueryParameterUtil.getQueryParameter("document", Integer.class);
        if(paramDocumentType.isPresent()) {
            Optional<TipDocument> documentClass = getPs4Service().getDocumentTypeById(paramDocumentType.get());
            if(documentClass.isPresent()) {
                Map<String, Object> breadcrumbParameters = new HashMap<>();
                breadcrumbParameters.put("tipDocument", paramDocumentType.get());
                getBreadcrumbView().addCrumb(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.document.type.title")
                        + documentClass.get().getDenumire().toLowerCase(),
                        QueryParameterUtil.getRelativePathWithQueryParameters(breadcrumbParameters, Ps4ECitizenServiceRoute.class));
                if(paramDocumentId.isPresent()) {
                    Optional<Document> document = getPs4Service().getDocumentByIdAndClasa(paramDocumentType.get(),paramDocumentId.get());
                    if(document.isPresent()) {
                        getBreadcrumbView().setCurrentPageTitle(document.get().getDenumire());
                        addContentContainer(documentTypeServicedocumentTypeServiceRequestView);
                        return true;
                    }else{
                        SecurityUtils.forceGetAllDocumentTypes(SecurityUtils.getToken());
                        document = getPs4Service().getDocumentByIdAndClasa(paramDocumentType.get(),paramDocumentId.get());
                        if(document.isPresent()) {
                            getBreadcrumbView().setCurrentPageTitle(document.get().getDenumire());
                            addContentContainer(documentTypeServicedocumentTypeServiceRequestView);
                            return true;
                        }
                    }
                }
            }else {
                SecurityUtils.forceGetAllDocumentTypes(SecurityUtils.getToken());
                documentClass = getPs4Service().getDocumentTypeById(paramDocumentType.get());
                if(documentClass.isPresent()) {
                    Map<String, Object> breadcrumbParameters = new HashMap<>();
                    breadcrumbParameters.put("tipDocument", paramDocumentType.get());
                    getBreadcrumbView().addCrumb(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.document.type.title")
                                    + documentClass.get().getDenumire().toLowerCase(),
                            QueryParameterUtil.getRelativePathWithQueryParameters(breadcrumbParameters, Ps4ECitizenServiceRoute.class));
                    if(paramDocumentId.isPresent()) {
                        Optional<Document> document = getPs4Service().getDocumentByIdAndClasa(paramDocumentType.get(),paramDocumentId.get());
                        if(document.isPresent()) {
                            getBreadcrumbView().setCurrentPageTitle(document.get().getDenumire());
                            addContentContainer(documentTypeServicedocumentTypeServiceRequestView);
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }


}
