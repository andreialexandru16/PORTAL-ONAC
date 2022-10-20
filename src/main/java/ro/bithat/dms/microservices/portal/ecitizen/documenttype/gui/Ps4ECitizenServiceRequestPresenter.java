package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.RouteConfiguration;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.ps4.detaliiserviciu.ElectronicService;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbWizardRequestView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.Ps4ECitizenAnonymousHomeRoute;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.Ps4ECitizenMyAccountRoute;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.Ps4ECitizenMyAccountView;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Ps4ECitizenServiceRequestPresenter extends DocumentTypePresenter<Ps4ECitizenServiceRequestView> {


    @Value("${ps4.ecitizen.class.reabilitare.id}")
    private Integer classReabilitareId;
    @Value("${show.documente.necesare.detaliu.serviciu:true}")
    private String showDocumenteNecesare;




    @Override
    public void afterPrepareModel(String state) {
        long documentId = new Long(getDocumentId().get());
        long classDocumentId = new Long(getDocumentId().get());
        ElectronicService electronicService = getPs4Service().getDetaliiServiciu(documentId);
        if(!getFromMainScreen().isPresent() || getFromMainScreen().get()==null){
            getView().buildBreadcrumbsWizard();

        }
        getView().setPersonTypeValue(electronicService.getServicePersTypeValue());
        getView().setServiceNameValue(electronicService.getServiceNameValue());
        getView().setServiceDescriptionValue(electronicService.getServiceDescriptionValue());
        getView().setServiceResponsibleValue(electronicService.getCompartiment());
        getView().setServiceAnswerTimeValue(electronicService.getServiceTimpSolutionareValue());
        getView().setServiceArchiveTimeValue(electronicService.getTermenArhivare());
        getView().setServiceWaitingTimeValue(electronicService.getServiceTermenCompletareValue());
        getView().setServiceContactValue(electronicService.getAdresaPrestator());
        getView().setServiceFormulaCost(electronicService.getServiceFormulaCost());
        getView().setServiceWorkingProgramValue(electronicService.getProgramFunctionare());
        getView().setServiceCostValue(electronicService.getServiceCostValue());
        getView().setServiceNameAndRegisterPreviousStep(electronicService.getServiceNameValue());
        if(showDocumenteNecesare!=null && showDocumenteNecesare.equals("true")){
            getView().setNeededDocumentsTable(getPs4Service().getDocumenteObligatoriiServiciu(documentId, getFileId()));
        }else{
            getView().hideNeededDocuments();
        }
        getView().setLegislationDocumentsTable(getPs4Service().getActeNormativeByDocTypeId(documentId));
        if(getDocumentType().equals(classReabilitareId)){
            getView().setNotesTable();
        }
    }



    public void onPreviousBtnAction(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("filter previous button");
        Map<String, Object> filterPageParameters = new HashMap<>();
        filterPageParameters.put("tipDocument", getDocumentType());
        VaadinClientUrlUtil.setLocation(QueryParameterUtil
                .getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceRoute.class));
    }

    public void onNextBtnAction(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("on lanseaza comanda");
        Map<String, Object> filterPageParameters = new HashMap<>();
        filterPageParameters.put("tipDocument", getDocumentType());
        filterPageParameters.put("document", getDocumentId().get());
        if(getDocument().get().getPortalTextMessage()!=null&&!getDocument().get().getPortalTextMessage().isEmpty()){
            UI.getCurrent().getPage().executeJs(" swalInfoMessage($0,$1);",getDocument().get().getPortalTextMessage() ,getView());

        }else {
            VaadinClientUrlUtil.setLocation(QueryParameterUtil
                    .getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceNewRequestRoute.class));
        }
    }

    public void getProp(){
        Optional<Long> documentId = QueryParameterUtil.getQueryParameter("document", Long.class);
        if(documentId.isPresent()){
        AttributeLinkList electronicService = getPs4Service().getDetaliiServicii(documentId.get());
        getView().setPropList(electronicService);}
    }


}
