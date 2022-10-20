package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;
//22.06.2021 # Neata Georgiana # ANRE #  adaugat atrComplexReadonly; daca e pus pe true=> nu afisez coloana de actiune (adauga rand); ex utilizare in pagina de revizie finala
//13.07.2021 # Neata Georgiana # ANRE #   daca tabelul este gol-> nu se mai afiseaza deloc
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RouteConfiguration;
import ro.bithat.dms.microservices.dmsws.colaboration.InfoMesaje;
import ro.bithat.dms.microservices.dmsws.email.Email;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLinkList;
import ro.bithat.dms.microservices.dmsws.file.PortalFileList;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.dmsws.ps4.documents.imported.Corespondenta;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.component.*;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.BreadcrumbWizardRequestView;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.LoadingSpinner;
import ro.bithat.dms.microservices.portal.ecitizen.onlineservices.gui.Ps4ECitizenOnlineServicesRoute;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.Ps4ECitizenMyRequestsRoute;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.component.EmailInboxComponent;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.RunJasperByTipDocAndSaveResp;
import ro.bithat.dms.passiveview.FlowComponent;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.captcha.ReCaptcha;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.smartform.gui.DocumentaSmartForm;
import ro.bithat.dms.smartform.gui.SmartFormSupport;

import java.util.List;

public class Ps4ECitizenServiceRequestReviewView extends DocumentTypeView<Ps4ECitizenServiceRequestReviewPresenter> {

    @FlowComponent("request-wizard-breadcrumb")
    private BreadcrumbWizardRequestView breadcrumbRequestWizardView;

    @FlowComponent("online-service-breadcrumb")
    private BreadcrumbView breadcrumbView;

    ///Btn footer container
    private ServiceRequestStepsFooterButtonController btnFooterContainer = new ServiceRequestStepsFooterButtonController(this);
    ///Btn footer container

    //Needed Document table
    private DocObligatoriuExtraTableContainer serviceNeededDocumentsContainer = new DocObligatoriuExtraTableContainer(this);
    //Needed Document table
    
    private TaxesTableContainer taxesContainer = new TaxesTableContainer(this);

    private ResponseFilesTableContainer responseFilesTableContainer = new ResponseFilesTableContainer(this);

    private DocumentaSmartForm smartForm = new DocumentaSmartForm();

    private EmailInboxComponent inboxComponent = new EmailInboxComponent(this);

    private Div captchaContainer = new Div();
    private NotificationContainer notificationContainer = new NotificationContainer(this);
    private NotificationContainer notificationFilesContainer = new NotificationContainer(this);
    private LoadingSpinner loadingSpinner = new LoadingSpinner();
    private ReCaptcha reCaptcha;

    @Override
    public void beforeBinding() {
        SmartFormSupport.setIsReadonly(true);
        addServiceListContent();
        smartForm.addClassName("canvas_div_pdf");
        getServiceListContainer().addClassNames("detaliu-serviciu", "solicitare-revizie-finala", "profile_container");
        inboxComponent.setEnableSearch(false);
        inboxComponent.addClassName("profile_inbox");
        Div clearFix = new Div();
        clearFix.addClassNames("clearfix", "gap-20");
        getServiceListContainer().add(serviceNeededDocumentsContainer, taxesContainer, responseFilesTableContainer, inboxComponent, notificationContainer, notificationFilesContainer,clearFix,captchaContainer, btnFooterContainer);
        Button printTopBtn = new Button("Print");
        printTopBtn.getStyle().set("position", "absolute");
        printTopBtn.getStyle().set("display", "block");
        printTopBtn.getStyle().set("right", "25px");
        printTopBtn.getStyle().set("top", "5px");
        printTopBtn.addClickListener(e
                -> {
            btnPrintSmartFormPdf();

        });
        getServiceListHeader().getStyle().set("position", "relative");
        getServiceListHeader().add(printTopBtn);
        getServiceListContent().add(smartForm);
        loadingSpinner.show();
        loadingSpinner.setId("loading-spinner");
        loadingSpinner.getStyle().set("display", "none");


    }
    public void btnPrintSmartFormPdf() {
        getLogger().info("printSmartFormPdf command sending to client");
        if(getPresenter().getFileId().isPresent()){
            RunJasperByTipDocAndSaveResp runJasperByTipDocAndSaveResp = getPresenter().getIdFisierJasper();
            if (runJasperByTipDocAndSaveResp.getStatus()!=null && runJasperByTipDocAndSaveResp.getStatus().equals("OK")) {
                UI.getCurrent().getPage().executeJs("  $UTIL.downloadFileByVaadin($0,$1)", runJasperByTipDocAndSaveResp.getFileName(),
                        runJasperByTipDocAndSaveResp.getDownloadLink());

            }
            else {
                SmartFormSupport.makeSmartFormPrintable(smartForm);
                UI.getCurrent().getPage().executeJs("printSmartFormPdf($0, $1, $2);",
                        smartForm.getElement(), getElement(), getPresenter().getDocument().get().getDenumire()+".pdf");
            }
        }
        else {
            SmartFormSupport.makeSmartFormPrintable(smartForm);
            UI.getCurrent().getPage().executeJs("printSmartFormPdf($0, $1, $2);",
                    smartForm.getElement(), getElement(), getPresenter().getDocument().get().getDenumire()+".pdf");
        }
    }
    @ClientCallable
    public void afterPrintSmartForm(String pdfData) {
        SmartFormSupport.exitPrintingMode(smartForm);
    }

    @ClientCallable
    public void swalInfoParam2Ack(String url) {

        VaadinClientUrlUtil.setLocation(url);
    }
    @ClientCallable
    public void swalInfoAck() {

        if(SecurityUtils.getToken().equals(getPresenter().getAnonymousToken())){
            VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenOnlineServicesRoute.class));
        }
        else{
            VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenMyRequestsRoute.class));

        }
    }

    @ClientCallable
    public void swalPaymentInfoConfirmation() {
        getPresenter().redirectToPaymentProvider();
    }

    public void setServiceNameAndRegisterPreviousStep(String serviceName) {
        setContentPageTile(I18NProviderStatic.getTranslation("document.type.service.request.view.review.title")
                + "\t" + getPresenter().getWorkflowStateTitle());
        breadcrumbView.setCurrentPageTitle(getPresenter().getPortalFileTitle());
        if (getPresenter().getFromMainScreen().isPresent() && getPresenter().getFromMainScreen().get().contains("-VIEW")){
            btnFooterContainer.registerPresenterPreviousStepMethod("document.type.service.request.previous.step.title", serviceName);
        }
        else if(getPresenter().isPortalFileEditable() || getPresenter().portalFileNeedChanges()){
            if(!getPresenter().getWorkflowStateTitle().isEmpty()) {
                btnFooterContainer.registerPresenterNextStepMethod("document.type.service.request.view.save.action.label", "document.type.service.request.next.step.title", "document.type.service.request.view.review.title");
            }else {
                btnFooterContainer.registerPresenterNextStepMethod("document.type.service.reviewrequest.view.next.action.label","document.type.service.request.next.step.title", "document.type.service.request.view.review.title");
            }
            btnFooterContainer.registerPresenterPreviousStepMethod("document.type.service.request.previous.step.title", serviceName);
        }else if (getPresenter().getFromMainScreen().isPresent() && !getPresenter().getFromMainScreen().get().isEmpty()){
            btnFooterContainer.registerPresenterPreviousStepMethod("document.type.service.request.previous.step.title", serviceName);
        }
        else {
            btnFooterContainer.registerPresenterNextStepMethod("document.type.service.request.view.ack.action.label", "document.type.service.request.previous.step.title", serviceName);
        }
    }

    //Document inbox

    public void setInboxTable(List<Email> myMessages) {
        //13.07.2021 - NG - ANRE - daca tabelul este gol-> nu se mai afiseaza deloc
        if(myMessages==null || myMessages.size()==0){
            getServiceListContainer().remove(inboxComponent);
        }
        else{
            inboxComponent.setInboxTable(myMessages);
        }

    }

    //Document inbox


    //Needed Document table

    public void setNeededDocumentsTable(List<DocObligatoriuExtra> documenteObligatoriiServiciu) {
        //13.07.2021 - NG - ANRE - daca tabelul este gol-> nu se mai afiseaza deloc
        if(documenteObligatoriiServiciu==null || documenteObligatoriiServiciu.size()==0){
            getServiceListContainer().remove(serviceNeededDocumentsContainer);
        }
        else{
            serviceNeededDocumentsContainer.setNeededDocumentsTable(documenteObligatoriiServiciu,  false/*getPresenter().isPortalFileEditable()*/);

        }
    }

    public void buildSmartForm(AttributeLinkList attributeLinkList) {
        //22.06.2021 # Neata Georgiana # ANRE #  adaugat atrComplexReadonly; daca e pus pe true=> nu afisez coloana de actiune (adauga rand); ex utilizare in pagina de revizie finala
        smartForm.setAtrComplexReadonly(true);
        smartForm.buildSmartForm(attributeLinkList);
        smartForm.setReadOnly(true);
        smartForm.setLabelsClassNames("pr-2 mb-0 font-weight-bold");
    }
    
    public void setTaxesTableContainer(List<DocObligatoriuExtra> documenteObligatoriiServiciu) {
        //13.07.2021 - NG - ANRE - daca tabelul este gol-> nu se mai afiseaza deloc
        if(documenteObligatoriiServiciu==null || documenteObligatoriiServiciu.size()==0){
            getServiceListContainer().remove(taxesContainer);
        }
        else{
            taxesContainer.setNeededDocumentsTable(documenteObligatoriiServiciu, true);

        }
    }
    public void setNotificationContainer(List<InfoMesaje> myMessages) {
        notificationContainer.setInfoNotification(myMessages);
    }

    public void setNotificationFilesContainer(String nrCorespondentaList) {
        notificationFilesContainer.setInfoNotificationFiles(nrCorespondentaList,getPresenter().getFileId() );
    }
    //Needed Document table

    public DocAttrLinkList getDocAttrLinkList() {
        return smartForm.getInitialDocAttrLinkList();
    }

    public String getSmartFormId(){
        return smartForm.getId().get();
    }

    public void setResponseFileTable(PortalFileList docRaspunsByFileId) {
        //13.07.2021 - NG - ANRE - daca tabelul este gol-> nu se mai afiseaza deloc
        if(docRaspunsByFileId.getPortalFileList()==null || docRaspunsByFileId.getPortalFileList().size()==0){
            getServiceListContainer().remove(responseFilesTableContainer);
        }
        else{
            responseFilesTableContainer.setResponseFilesTable(docRaspunsByFileId.getPortalFileList(), true,getPresenter().getDocument().get().getId(),getPresenter().getDocumentType());
        }
    }

    protected void displayForWidth(int width) {
        if(width <= 700) {
            serviceNeededDocumentsContainer.displayForMobile();
            taxesContainer.displayForMobile();
            responseFilesTableContainer.displayForMobile();
        }else {
            serviceNeededDocumentsContainer.displayForDesktop();
            taxesContainer.displayForDesktop();
            responseFilesTableContainer.displayForDesktop();
        }
    }

    public void setAttrDocumentaSmartForm(String idAtributeAscunse){
        smartForm.setParamAtributeAscunsePortal(idAtributeAscunse);
    }

    public void buildBreadcrumbsWizard() {
        getServiceListContainer().getStyle().set("margin-top","150px");
        breadcrumbRequestWizardView.setCurrentPageActive( RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenServiceRequestReviewRoute.class));
        addComponentAsFirst(breadcrumbRequestWizardView);
    }

    public void setCaptcha() {
        if((getPresenter().isPortalFileEditable() || getPresenter().portalFileNeedChanges())||(getPresenter().getFromMainScreen().isPresent() && !getPresenter().getFromMainScreen().get().isEmpty())){
            //preluam siteKey si webSiteKey din presenter
            String secretKey = getPresenter().getSecretKey();
            String websiteKey = getPresenter().getWebsiteKey();
            if (secretKey != null && websiteKey != null) {
                reCaptcha = new ReCaptcha(websiteKey, secretKey);
                captchaContainer.add(reCaptcha);
            }
        }else{
           captchaContainer.getStyle().set("display","none");
        }
    }

    public ReCaptcha getReCaptcha() {
        return reCaptcha;
    }
}
