package ro.bithat.dms.microservices.portal.ecitizen.home.gui;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Input;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.PS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Document;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.backend.DmswsDocumentService;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECitizenServiceRequestReviewRoute;
import ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui.Ps4ECitizenOnlineServicesRoute;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsUtilizatorService;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Ps4ECitizenHomeDocumentClassPresenter
        extends PrepareModelFlowPresenter<Ps4ECitizenHomeDocumentClassView> {

    @Autowired
    private DmswsDocumentService documentService;

    @Autowired
    private PS4Service ps4Service;
    @Autowired
    private DmswsUtilizatorService utilizatorService;

    @Autowired
    private DmswsPS4Service dmswsPS4Service;

    @Value("${show.header:true}")
    private Boolean showHeader;

    @Value("${show.extra.services:true}")
    private Boolean showExtraServices;

    @Value("${custom.dms.url}")
    private String dmsUrl;

    @Value("${dmsws.anonymous.token}")
    private String anonymousToken;

    private String requestsClassId;

    public String getDmsUrl(){
        return this.dmsUrl;
    }

    public String getAnonymousToken(){
        return this.anonymousToken;
    }

    public String getRequestsClassId(){
        return this.requestsClassId;
    }

    @Override
    public void prepareModel(String state) {
        this.requestsClassId = dmswsPS4Service.getSysParam(SecurityUtils.getToken(), "REQUESTS_CLASS_ID").getDescriere();
        getView().buildDocumentClassRows();
//        if(showExtraServices){
//            getView().buildExtraServicesRows(utilizatorService.getLinkuriUtile(SecurityUtils.getToken()).getLinkUtilList());
//        }

//        if(!showHeader){
//            List<Document> allDocuments = documentService.getListaTipuriDocumente();
//
////            getView().addSearchFormContainer();
////            getView().getSearchFormContainer().enablePresenterSearchEvents(allDocuments);
////            getView().getSearchFormContainer().removeSpeachBtn();
//
//
//        }

    }

//    public void onSearchBtnClick(ClickEvent<Input> clickEvent) {
//        doSearch(getView().getSearchFormContainer().getSearchTextValue());
//    }


//    private void doSearch(String searchText) {
//        Map<String, Object> filterPageParameters = new HashMap<>();
//        try{
//            searchText=searchText.split(":")[1];
//        }catch (Exception e){
//
//        }
//        filterPageParameters.put("filtru", searchText);
//
//
//        VaadinClientUrlUtil.setLocation(QueryParameterUtil.getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenOnlineServicesRoute.class));
//
//    }

}
