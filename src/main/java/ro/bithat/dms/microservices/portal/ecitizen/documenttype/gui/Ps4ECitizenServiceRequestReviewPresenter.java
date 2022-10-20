package ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui;
//13.07.2021 - NG - ANRE - daca tabelul este gol-> nu se mai afiseaza deloc
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.RouteConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.server.ServerWebInputException;
import ro.bithat.dms.microservices.dmsws.colaboration.InfoMesaje;
import ro.bithat.dms.microservices.dmsws.email.Email;
import ro.bithat.dms.microservices.dmsws.file.DmswsFileService;
import ro.bithat.dms.microservices.dmsws.file.DocAttrLink;
import ro.bithat.dms.microservices.dmsws.metadata.AttributeLinkList;
import ro.bithat.dms.microservices.dmsws.payment.BTPaymentResponse;
import ro.bithat.dms.microservices.dmsws.payment.PaymentService;
import ro.bithat.dms.microservices.dmsws.ps4.DmswsPS4Service;
import ro.bithat.dms.microservices.dmsws.ps4.documents.DocObligatoriuExtra;
import ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.EuPlatescRo;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.SolicitareService;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.backend.DmswsDocumentService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.ColaborationService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsInboxService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.Ps4ECitizenMyRequestsRoute;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.RunJasperByTipDocAndSaveReq;
import ro.bithat.dms.microservices.portal.ecitizen.website.models.RunJasperByTipDocAndSaveResp;
import ro.bithat.dms.passiveview.QueryParameterUtil;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.security.SecurityUtils;
import ro.bithat.dms.service.URLUtil;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Ps4ECitizenServiceRequestReviewPresenter extends DocumentTypePresenter<Ps4ECitizenServiceRequestReviewView> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${experimental.demo.profile:false}")
	private boolean experimentalDemoProfile;

    @Value("${ps4.ecitizen.class.urbanism.id}")
    private Integer classUrbanismId;
    @Value("${doc.type.with.payment:0}")
    private String docTypeWithPayment;

    @Value("${ps4.ecitizen.class.reabilitare.id}")
    private Integer classReabilitareId;

    @Value("${ps4.ecitizen.workflow.status.request.completed}")
    private Integer idWorkflowStatusRequestCompleted;

    @Value("${dmsws.anonymous.token}")
    private String anonymousToken;

    @Value("${google.recaptcha.sitekey:}")
    private String websiteKey;

    @Value("${google.recaptcha.secretkey:}")
    private String secretKey;

    @Value("${payment.processator:euPlatesc}")
    private String paymentProcessator;



    @Autowired
	private URLUtil urlUtil;
	
	@Autowired
	private DmswsDocumentService documentService;
	
	@Autowired
	private SolicitareService solictiareService;

    @Autowired
    private DmswsPS4Service dmswsPS4Service;

    @Autowired
    private DmswsInboxService inboxService;

    @Autowired
    private ColaborationService colaborationService;
    
    @Autowired
    private EuPlatescRo euPlatescRo;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DmswsFileService fileService;

    private Boolean paymentProof = false;

	private AttributeLinkList attrs;
	
	private Double totalAmmount = new Double(0);
    List<String> servicesListWithPayment = new ArrayList<String>();
    @Override
    public void afterPrepareModel(String state) {

        if(docTypeWithPayment != null && !docTypeWithPayment.isEmpty()){
            if(docTypeWithPayment.contains(",")){
                servicesListWithPayment = Arrays.asList(docTypeWithPayment.split(","));
            } else {
                servicesListWithPayment.add(docTypeWithPayment.trim());
            }
        }
        if(!getFromMainScreen().isPresent() || getFromMainScreen().get()==null){
            getView().buildBreadcrumbsWizard();

        }

        String idAtributeAscunsePortal= dmswsPS4Service.getSysParam(SecurityUtils.getToken(),"ID_ATRIBUTE_ASCUNS_PORTAL").getDescriere();
        long documentId = new Long(getDocumentId().get());
        getView().setServiceNameAndRegisterPreviousStep(getDocument().get().getDenumire());
        List<DocObligatoriuExtra> attachmentFiles = getPs4Service().getDocumenteObligatoriiServiciu(documentId, getFileId());

        for(DocObligatoriuExtra docObligatoriuExtra: attachmentFiles) {
            if(Optional.ofNullable(docObligatoriuExtra.getDocObligatoriu()).isPresent()) {
                if(Optional.ofNullable(docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat()).isPresent()
                        && !docObligatoriuExtra.getDocObligatoriu().getLinkFisierAnexat().isEmpty()) {
                    if(Optional.ofNullable(docObligatoriuExtra.getDocObligatoriu().getDocumentPlata()).isPresent()
                            && docObligatoriuExtra.getDocObligatoriu().getDocumentPlata() == 1) {
                        paymentProof = true;
                    }
                }
            }
        }
        getView().setNeededDocumentsTable(attachmentFiles);
        List<InfoMesaje> listaNotif= colaborationService.getLastColaborationMessagesByFile(getFileId().get());
        //13.07.2021 - NG - ANRE - daca tabelul este gol-> nu se mai afiseaza deloc
        if(listaNotif!=null && listaNotif.size()!=0){
            getView().setNotificationContainer(listaNotif);

        }
        String  nrCorespondentaList= documentService.getNrCorespondentaList(SecurityUtils.getToken(),getFileId().get().toString()).getInfo();
        //13.07.2021 - NG - ANRE - daca tabelul este gol-> nu se mai afiseaza deloc
        if(nrCorespondentaList!=null && nrCorespondentaList!="0"){
            getView().setNotificationFilesContainer(nrCorespondentaList);

        }
        attrs = getPs4Service().getAttributeLinkListByFileId(getFileId().get());
        if (Optional.ofNullable(attrs.getAttributeLink()).isPresent()) {
	        Map<String, String> keyValues = new HashMap<>(); 
	        attrs.getAttributeLink().stream().forEach(al-> keyValues.put(al.getName()+"", al.getValue()==null?"":al.getValue()));
	                
	        List<DocObligatoriuExtra> result = documentService.getDocumenteObligatoriiServiciuDePlatit(documentId, getFileId(), keyValues);
	        totalAmmount = result.stream().
	        		filter(d->d.getDocObligatoriu().getCostD()!=null).
			        map(d->d.getDocObligatoriu().getCostD()).
			        collect(Collectors.summingDouble(Double::doubleValue));
	        getLogger().info("Cererea are dovada platii:\t"  + paymentProof);
            getView().setTaxesTableContainer(result);
            getView().setAttrDocumentaSmartForm(idAtributeAscunsePortal);
	        getView().buildSmartForm(attrs);
        }
        getView().setResponseFileTable(dmswsPS4Service.getDocRaspunsByFileId(SecurityUtils.getToken(), getFileId().get()));
        getView().setInboxTable(inboxService.getSysEmailsByFisier(SecurityUtils.getToken(), getFileId().get()).getEmailList());
        if(getDocument().isPresent() && getDocument().get().isCaptcha()){
            getView().setCaptcha();
        }
    }
    
    public void onPreviousBtnAction(ClickEvent<ClickNotifierAnchor> clickEvent) {
        //13.07.2021 - NG - ANRE - resetare contor schimbari facute pentru a nu afisa dialog de confirmare iesire din pagina
        UI.getCurrent().getPage().executeJavaScript("resetChanges();");
        getLogger().info("filter previous button");
        Map<String, Object> filterPageParameters = new HashMap<>();
        filterPageParameters.put("tipDocument", getDocumentType());
        filterPageParameters.put("document", getDocumentId().get());
        filterPageParameters.put("request", getFileId().get());
        if(getFromMainScreen().isPresent()){
            filterPageParameters.put("fromMainScreen", getFromMainScreen().get());

            if (getFromMainScreen().get().equals("GN")) {
                VaadinClientUrlUtil.setLocation("/PISC/PORTAL/main-screen-operator-gn.html?&idClasaDoc=" + getDocumentType());

            } else if (getFromMainScreen().get().equals("EE")) {
                VaadinClientUrlUtil.setLocation("/PISC/PORTAL/main-screen-operator.html?&idClasaDoc=" + getDocumentType());
            } else if (getFromMainScreen().get().equals("ET")) {
                VaadinClientUrlUtil.setLocation("/PISC/PORTAL/main-screen-operator-et.html?&idClasaDoc=" + getDocumentType());

            } else if (getFromMainScreen().get().equals("COMP")) {
                VaadinClientUrlUtil.setLocation("/PISC/PORTAL/main-screen-operator-compensari.html?&idClasaDoc=" + getDocumentType());
            }
            else if (getFromMainScreen().get().equals("GN-VIEW")) {
                VaadinClientUrlUtil.setLocation("/PISC/PORTAL/main-screen-operator-gn.html?&idClasaDoc=" + getDocumentType());

            } else if (getFromMainScreen().get().equals("EE-VIEW")) {
                VaadinClientUrlUtil.setLocation("/PISC/PORTAL/main-screen-operator.html?&idClasaDoc=" + getDocumentType());

            } else if (getFromMainScreen().get().equals("ET-VIEW")) {
                VaadinClientUrlUtil.setLocation("/PISC/PORTAL/main-screen-operator-et.html?&idClasaDoc=" + getDocumentType());


            } else if (getFromMainScreen().get().equals("COMP-VIEW")) {
                VaadinClientUrlUtil.setLocation("/PISC/PORTAL/main-screen-operator-compensari.html?&idClasaDoc=" + getDocumentType());
            }
            else {
                VaadinClientUrlUtil.setLocation("/PISC/PORTAL/main-screen-operator.html?&idClasaDoc=" + getDocumentType());
            }
        }else{
            VaadinClientUrlUtil.setLocation(QueryParameterUtil
                    .getRelativePathWithQueryParameters(filterPageParameters, Ps4ECitizenServiceAttacheFileRoute.class));
        }

    }

    public Boolean getPaymentProof() {
        return paymentProof;
    }

    public void onRespondBtnClick(ClickEvent<NativeButton> clickEvent, Email email, TextArea messageToSend, Div messageContainer){
        getLogger().info("email send by:\t " + email.getCreatDe()+ " email to send :	 " + messageToSend.getValue());

        if( (email.getCreatDeEmail()==null|| email.getCreatDeEmail().isEmpty()) & (email.getCreatDe()==null|| email.getCreatDe().isEmpty()) ){
            if(email.getCreatDe()==null|| email.getCreatDe().isEmpty()) {
                UI.getCurrent().getPage().executeJs("swalError($0)",
                        I18NProviderStatic.getTranslation("ps4.ecetatean.inbox.send.email.swal.err"));
                if (messageContainer.hasClassName("reply")) {
                    messageContainer.removeClassNames("reply");
                }
            }
        }
        else if (messageToSend==null|| messageToSend.isEmpty()){
            UI.getCurrent().getPage().executeJs("swalError($0)",
                    I18NProviderStatic.getTranslation("ps4.ecetatean.inbox.send.email.swal.info.not.empty"));
        }
        else {
            inboxService.sendEmail(SecurityUtils.getToken(), email.getCreatDeEmail()==null || email.getCreatDeEmail().isEmpty()?email.getCreatDe():email.getCreatDeEmail(),SecurityUtils.getFullName(),
                    email.getTitlu(),
                    "\r\nTrimis de:\t" + SecurityUtils.getEmail()+
                            "\r\nMesaj:\t"+messageToSend.getValue());
            UI.getCurrent().getPage().executeJs("swalInfo($0)",
                    I18NProviderStatic.getTranslation("ps4.ecetatean.inbox.send.email.swal.info"));
            if( messageContainer.hasClassName("reply")){
                messageContainer.removeClassNames("reply");
            }
        }

    }

    public void onShowEmailBtnClick(ClickEvent<NativeButton> clickEvent, HtmlContainer iconMessageEnvelope, Email email, Div messageContainer){
        getLogger().info("email send by:\t " + email.getCreatDe());

        if(email.getId()!=null && email.getCitit()!=1) {
            inboxService.setEmailReaded(SecurityUtils.getToken(),email.getId());
            if(iconMessageEnvelope.hasClassName("fa-envelope")){
                iconMessageEnvelope.removeClassName("fa-envelope");
                messageContainer.removeClassName("unread");
                iconMessageEnvelope.addClassName("fa-envelope-open");
            }
        }
    }
    public void onNextBtnAction(ClickEvent<ClickNotifierAnchor> clickEvent) {
        //06.01.2022 - CA -ANRE -  swal de loading
        //03.03.2022 - CA - CAPTCHA
        if(getDocument().isPresent() && getDocument().get().isCaptcha()){
            if(getView().getReCaptcha()!=null && !getView().getReCaptcha().isValid()){
                UI.getCurrent().getPage().executeJs("swalInfoBottomError($0)",
                        I18NProviderStatic.getTranslation("recaptcha.invalid"));
            }else{
                UI.getCurrent().getPage().executeJs("displayLoadingSpinner();").then(Integer.class, value -> nextStep(clickEvent));

            }
        }else {
            UI.getCurrent().getPage().executeJs("displayLoadingSpinner();").then(Integer.class, value -> nextStep(clickEvent));
        }
    }

    public void redirectToPaymentProvider() {
        Long fileId = new Long(getFileId().get());

        Optional<DocAttrLink> emailDocAttrLink = getView().getDocAttrLinkList().getDocAttrLink().stream().filter(dl -> dl.getDataType() != null && dl.getDataType().equalsIgnoreCase("email")).findFirst();
        String email = emailDocAttrLink.isPresent() ? emailDocAttrLink.get().getValue() : SecurityUtils.getEmail();

        Optional<DocAttrLink> subsemnatDocAttrLink = getView().getDocAttrLinkList().getDocAttrLink().stream().filter(dl -> dl.getDataType() != null && dl.getDataType().equalsIgnoreCase("nume")).findFirst();
        String subsemnatFieldValue = subsemnatDocAttrLink.isPresent() ? subsemnatDocAttrLink.get().getValue() : SecurityUtils.getFullName();
        String firstName = SecurityUtils.getFirstName();
        String lastName = SecurityUtils.getLastName();

        if (!subsemnatFieldValue.trim().toLowerCase().equals(SecurityUtils.getFullName().trim().toLowerCase())) {
            if (subsemnatFieldValue.contains(" ")){
                lastName = subsemnatFieldValue.substring(0, subsemnatFieldValue.indexOf(" "));
                firstName = subsemnatFieldValue.substring(subsemnatFieldValue.indexOf(" ")+1);
            }
        }
        logger.info("Starting payment for: {} {} amount:{} fileId:{} documentId:{} documentType:{}", lastName, firstName, email, fileId, getDocumentId().get(), getDocumentType() );
        if (experimentalDemoProfile) {
            logger.error("Ëxperimental parameter. Allways ussing 0.1 as amount!!!!!!!!!!!!!!!!!!!!!!!!1");
            totalAmmount = new Double(0.1);
        }

        if(paymentProcessator.equals("euPlatesc")) {
            String urlToRedirectToPayment = euPlatescRo.redirectToPayment(totalAmmount.toString(),
                    lastName, firstName, email,
                    fileId.toString(), getDocumentId().get().toString(), getDocumentType().toString());
            VaadinClientUrlUtil.setLocation(urlToRedirectToPayment);
        }else if(paymentProcessator.equals("BT")){
            BTPaymentResponse btPaymentResponse = paymentService.getBTPaymentUrl(SecurityUtils.getToken(),fileId,String.valueOf(totalAmmount.intValue()),getDocumentType().toString());
            if(btPaymentResponse.getFormUrl()!=null && !btPaymentResponse.getFormUrl().isEmpty()){
                VaadinClientUrlUtil.setLocation(btPaymentResponse.getFormUrl());
            }else{
                UI.getCurrent().getPage().executeJs("swalInfoBottomError($0)",
                        btPaymentResponse.getErrorMessage());
            }
        }
    }

    public String getAnonymousToken() {
        return anonymousToken;
    }

    public void setAnonymousToken(String anonymousToken) {
        this.anonymousToken = anonymousToken;
    }

    public Integer nextStep(ClickEvent<ClickNotifierAnchor> clickEvent){
        //Dupa primul click -> btn ul devine indisponibil (nu mai poate fi apasat)
        clickEvent.getSource().getElement().getStyle().set("pointer-events","none");
        clickEvent.getSource().getElement().getStyle().set("cursor","default");
        clickEvent.getSource().getElement().getStyle().set("background-color","#BFBFBF");
        clickEvent.getSource().getElement().getStyle().set("color","#000");

        //13.07.2021 - NG - ANRE - resetare contor schimbari facute pentru a nu afisa dialog de confirmare iesire din pagina
        UI.getCurrent().getPage().executeJavaScript("resetChanges();");
//    	sendFluxByIdFisier

//        UI.getCurrent().getPage().executeJs("print($0);", getView().getSmartFormId());
//

        getLogger().info("onNextBtnAction: Revizie finala");
        Long fileId = new Long(getFileId().get());

        if(!isPortalFileEditable() && !portalFileNeedChanges()) {
            VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenMyRequestsRoute.class));


            return 1;
        }
        else if(isPortalFileEditable()) {

            Optional<DocAttrLink> emailDocAttrLink = getView().getDocAttrLinkList().getDocAttrLink().stream().filter(dl -> (dl.getDataType() != null && dl.getDataType().equalsIgnoreCase("email")||  (dl.getKeyCode() != null && dl.getKeyCode().equalsIgnoreCase("email")))).findFirst();
            String email = SecurityUtils.getEmail();

            if(emailDocAttrLink.isPresent() && emailDocAttrLink.get()!=null && emailDocAttrLink.get().getValue()!=null && !emailDocAttrLink.get().getValue().isEmpty()){
                email=emailDocAttrLink.get().getValue();
            }


            if(getDocumentType().equals(classUrbanismId) || (servicesListWithPayment!=null && servicesListWithPayment.size()!=0 && servicesListWithPayment.contains(getDocumentId().get().toString())) ){
                if(paymentProof) {
                    if (new EmailValidator("Email invalid").apply(email, null).isError()) {
                        UI.getCurrent().getPage().executeJs("hideLoadingSpinner();");
                        UI.getCurrent().getPage().executeJs("swalError($0);", "Email invalid");
                        return 1;
                    }
                    solictiareService.send(SecurityUtils.getToken(), fileId, email, urlUtil.getPathIfVaadin(),true, Optional.ofNullable(totalAmmount.toString()),Optional.empty(),Optional.ofNullable(getView().getDocAttrLinkList().getDocAttrLink()));
                    getLogger().info("trimite pe flux solicitarea");
                    UI.getCurrent().getPage().executeJs("hideLoadingSpinner();");
                    UI.getCurrent().getPage().executeJs("swalInfo($0, $1);", "Cererea a fost trimisă cu dovada plății!", getView());
                } else {
                    UI.getCurrent().getPage().executeJs("hideLoadingSpinner();");
                    UI.getCurrent().getPage().executeJs("swalPaymentInfoConfirmation($0, $1);", "Întrucât nu ați încărcat documetul de plată veți fi redirectat către operatorul de plăți în vederea efectuării plății aferente serviciului.", getView());

                }
            }
            else if   (getDocumentType().equals(classReabilitareId)){
                if (new EmailValidator("Email invalid").apply(email, null).isError()) {
                    UI.getCurrent().getPage().executeJs("hideLoadingSpinner();");
                    UI.getCurrent().getPage().executeJs("swalError($0);", "Email invalid");
                    return 1;
                }
                solictiareService.send(SecurityUtils.getToken(), fileId, email, urlUtil.getPathIfVaadin(),false,Optional.empty(),Optional.empty(),Optional.empty());
                getLogger().info("trimite pe flux solicitarea");
                UI.getCurrent().getPage().executeJs("hideLoadingSpinner();");
                UI.getCurrent().getPage().executeJs("swalInfo($0, $1);", I18NProviderStatic.getTranslation("cerere_completata_reabilitare"), getView());
            }
            else{
                if (new EmailValidator("Email invalid").apply(email, null).isError()) {
                    UI.getCurrent().getPage().executeJs("hideLoadingSpinner();");
                    UI.getCurrent().getPage().executeJs("swalError($0);", I18NProviderStatic.getTranslation("email_invalid"));
                    return 1;
                }
                try{
                    solictiareService.send(SecurityUtils.getToken(), fileId, email, urlUtil.getPathIfVaadin(),false,Optional.empty(),Optional.empty(),Optional.ofNullable(getView().getDocAttrLinkList().getDocAttrLink()));

                    getLogger().info("trimite pe flux solicitarea");
                    UI.getCurrent().getPage().executeJs("hideLoadingSpinner();");
                    UI.getCurrent().getPage().executeJs("swalInfoBottom($0, $1);", I18NProviderStatic.getTranslation("cerere_trimisa_spre_aprobare"), getView());

                } catch (Throwable th){
                    try {
                        UI.getCurrent().getPage().executeJs("hideLoadingSpinner();");
                        UI.getCurrent().getPage().executeJs("swalErrorBottom($0);", ((ServerWebInputException) th).getReason());
                    } catch (Exception e){
                        UI.getCurrent().getPage().executeJs("hideLoadingSpinner();");
                        UI.getCurrent().getPage().executeJs("swalErrorBottom($0);", "A aparut o eroare la transmiterea cererii!");
                    }
                }

            }


//        Notification.show("Solictiare timirsa cu succes!", 20000,
//                Notification.Position.BOTTOM_END);
//		UI.getCurrent().navigate("/ps4/ecetatean/contul-meu/cererile-mele");
//        VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenMyRequestsRoute.class));
        } else if (portalFileNeedChanges()){
            if(getFromMainScreen().isPresent() &&getFromMainScreen().get()!=null){
                UI.getCurrent().getPage().executeJs("hideLoadingSpinner();");

                if (getFromMainScreen().get().equals("GN")) {
                    UI.getCurrent().getPage().executeJs("swalInfoParam2Top($0, $1,$2);", I18NProviderStatic.getTranslation("request.saved"), getView(), "/PISC/PORTAL/main-screen-operator-gn.html?&idClasaDoc=" + getDocumentType());

                } else if (getFromMainScreen().get().equals("EE")) {
                    UI.getCurrent().getPage().executeJs("swalInfoParam2Top($0, $1,$2);", I18NProviderStatic.getTranslation("request.saved"), getView(), "/PISC/PORTAL/main-screen-operator.html?&idClasaDoc=" + getDocumentType());

                } else if (getFromMainScreen().get().equals("ET")) {
                    UI.getCurrent().getPage().executeJs("swalInfoParam2Top($0, $1,$2);", I18NProviderStatic.getTranslation("request.saved"), getView(), "/PISC/PORTAL/main-screen-operator-et.html?&idClasaDoc=" + getDocumentType());

                } else if (getFromMainScreen().get().equals("COMP")) {
                    UI.getCurrent().getPage().executeJs("swalInfoParam2Top($0, $1,$2);", I18NProviderStatic.getTranslation("request.saved"), getView(), "/PISC/PORTAL/main-screen-operator-compensari.html?&idClasaDoc=" + getDocumentType());

                }

                else {
                    UI.getCurrent().getPage().executeJs("swalInfoParam2Top($0, $1,$2);", I18NProviderStatic.getTranslation("request.saved"), getView(), "/PISC/PORTAL/main-screen-operator.html?&idClasaDoc=" + getDocumentType());

                }


            }
            else{
                if(idWorkflowStatusRequestCompleted!=null){
                    solictiareService.setWorflowStatus(fileId,idWorkflowStatusRequestCompleted);
                }
                UI.getCurrent().getPage().executeJs("dhideLoadingSpinner();");
                UI.getCurrent().getPage().executeJs("swalInfo($0, $1);", I18NProviderStatic.getTranslation("cerere_completata"), getView());

            }

        }
        UI.getCurrent().getPage().executeJs("hideLoadingSpinner();");
        return 0;
    }
    public RunJasperByTipDocAndSaveResp getIdFisierJasper() {
        RunJasperByTipDocAndSaveReq runJasperByTipDocAndSaveReq = new RunJasperByTipDocAndSaveReq();
        runJasperByTipDocAndSaveReq.setIdDocument(getDocumentId().get());
        runJasperByTipDocAndSaveReq.setMainId(getFileId().get());
        String fileName=getDocument().get().getDenumire() + " " + (SecurityUtils.getFullName()) + " " + (new SimpleDateFormat("dd.MM.yyyy").format(new Date()).toString());
        runJasperByTipDocAndSaveReq.setOutputName(fileName);
        RunJasperByTipDocAndSaveResp resp =  fileService.getIdFisierJasper(runJasperByTipDocAndSaveReq);
        resp.setFileName(fileName+".pdf");
        return resp;
    }

    public String getWebsiteKey() {
        return websiteKey;
    }

    public void setWebsiteKey(String websiteKey) {
        this.websiteKey = websiteKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
