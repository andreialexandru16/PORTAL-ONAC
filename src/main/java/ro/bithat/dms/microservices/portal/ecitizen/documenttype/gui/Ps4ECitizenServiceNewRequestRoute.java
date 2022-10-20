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

@Route(value = "solicitare-noua")
@PageTitle(" Solicitare nouă")

public class Ps4ECitizenServiceNewRequestRoute extends DocumentTypeRoute {


    @FlowComponent
    private Ps4ECitizenServiceNewRequestView serviceNewRequestView;

    @Override
    protected boolean buildPortalRoute() {

        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames, getBreadcrumbView());
        getBreadcrumbView().clearCrumbs();
        /*getBreadcrumbView().addCrumb("ps4.ecetatean.breadcrumb.home.page.title",
                RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));*/
        Optional<Integer> paramDocumentType = QueryParameterUtil.getQueryParameter("tipDocument", Integer.class);
        Optional<Integer> paramDocumentId = QueryParameterUtil.getQueryParameter("document", Integer.class);
        Optional<String> fromMainScreen = QueryParameterUtil.getQueryParameter("fromMainScreen", String.class);
        if(paramDocumentType.isPresent()) {
            Optional<TipDocument> documentClass = getPs4Service().getDocumentTypeById(paramDocumentType.get());
            Map<String, Object> breadcrumbParameters = new HashMap<>();

            if(documentClass.isPresent()) {
                breadcrumbParameters.put("tipDocument", paramDocumentType.get());
                getBreadcrumbView().addCrumb(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.document.type.title")
                        + documentClass.get().getDenumire().toLowerCase(),
                        QueryParameterUtil.getRelativePathWithQueryParameters(breadcrumbParameters, Ps4ECitizenServiceRoute.class));
                if(paramDocumentId.isPresent()) {
                    Optional<Document> document = getPs4Service().getDocumentByIdAndClasa(paramDocumentType.get(),paramDocumentId.get());
                    if(document.isPresent()) {
                        breadcrumbParameters.put("document", paramDocumentId.get());
                        getBreadcrumbView().addCrumb(document.get().getDenumire(),
                                QueryParameterUtil.getRelativePathWithQueryParameters(breadcrumbParameters, Ps4ECitizenServiceRequestRoute.class));
//                        getBreadcrumbView().setCurrentPageTitle(serviceNewRequestView.getPresenter().getPortalFileTitle());
                        addContentContainer(serviceNewRequestView);
                        return true;
                    }
                    //daca nu se gaseste tipul de doc in lista => reciteste lista tipuri/clase documente si reincearca

                    else{
                        SecurityUtils.forceGetAllDocumentTypes(SecurityUtils.getToken());
                        document = getPs4Service().getDocumentByIdAndClasa(paramDocumentType.get(),paramDocumentId.get());
                        if(document.isPresent()) {
                            breadcrumbParameters.put("document", paramDocumentId.get());
                            getBreadcrumbView().addCrumb(document.get().getDenumire(),
                                    QueryParameterUtil.getRelativePathWithQueryParameters(breadcrumbParameters, Ps4ECitizenServiceRequestRoute.class));
//                        getBreadcrumbView().setCurrentPageTitle(serviceNewRequestView.getPresenter().getPortalFileTitle());
                            addContentContainer(serviceNewRequestView);
                            return true;
                        }
                    }
                }
            }
            //daca nu se gaseste clasa in lista => reciteste lista tipuri/clase documente si reincearca
            else{
                SecurityUtils.forceGetAllDocumentTypes(SecurityUtils.getToken());
                documentClass = getPs4Service().getDocumentTypeById(paramDocumentType.get());

                if(documentClass.isPresent()) {
                    breadcrumbParameters.put("tipDocument", paramDocumentType.get());
                    getBreadcrumbView().addCrumb(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.document.type.title")
                                    + documentClass.get().getDenumire().toLowerCase(),
                            QueryParameterUtil.getRelativePathWithQueryParameters(breadcrumbParameters, Ps4ECitizenServiceRoute.class));
                    if(paramDocumentId.isPresent()) {
                        Optional<Document> document = getPs4Service().getDocumentByIdAndClasa(paramDocumentType.get(),paramDocumentId.get());
                        if(document.isPresent()) {
                            breadcrumbParameters.put("document", paramDocumentId.get());
                            getBreadcrumbView().addCrumb(document.get().getDenumire(),
                                    QueryParameterUtil.getRelativePathWithQueryParameters(breadcrumbParameters, Ps4ECitizenServiceRequestRoute.class));
//                        getBreadcrumbView().setCurrentPageTitle(serviceNewRequestView.getPresenter().getPortalFileTitle());
                            addContentContainer(serviceNewRequestView);
                            return true;
                        }

                    }
                }
            }

        }
        return false;
    }


}
