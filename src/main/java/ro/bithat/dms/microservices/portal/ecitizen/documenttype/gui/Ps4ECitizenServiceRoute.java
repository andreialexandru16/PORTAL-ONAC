package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.TipDocument;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.security.SecurityUtils;

import java.util.Optional;

@Route(value = "lista-servicii")
@PageTitle(" Listă Servicii")

public class Ps4ECitizenServiceRoute extends DocumentTypeRoute {

    @FlowComponent
    private Ps4ECitizenServiceView documentTypeServiceView;

    @Override
    protected boolean buildPortalRoute() {
        String[] classNames = {"breadcrumbs_container"};
        addContentHeaderContainer("", classNames);
        //addContentHeaderContainer("", classNames, getBreadcrumbView());
        getBreadcrumbView().clearCrumbs();
        getBreadcrumbView().addCrumb("ps4.ecetatean.breadcrumb.home.page.title", RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAnonymousHomeRoute.class));
        Optional<Long> idUser = QueryParameterUtil.getQueryParameter("idUser", Long.class);
        Optional<String> token = QueryParameterUtil.getQueryParameter("token", String.class);
//        if(idUser.isPresent()){
//            SecurityUtils.setUserId(idUser.get());
//
//        }
//        if(token.isPresent()){
//            SecurityUtils.setToken(token.get());
//        }
        Optional<Integer> documentType = QueryParameterUtil.getQueryParameter("tipDocument", Integer.class);
        if(documentType.isPresent()) {
            Optional<TipDocument> documentClass = getPs4Service().getDocumentTypeById(documentType.get());
            if(documentClass.isPresent()) {
                getBreadcrumbView().setCurrentPageTitle(I18NProviderStatic.getTranslation("ps4.ecetatean.breadcrumb.document.type.title")
                        + documentClass.get().getDenumire().toLowerCase());
                addContentContainer(documentTypeServiceView);
                return true;
           }

        }
        return false;
    }

}
