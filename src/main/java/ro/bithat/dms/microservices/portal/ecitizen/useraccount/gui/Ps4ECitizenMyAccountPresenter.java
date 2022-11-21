package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.RouteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ro.bithat.dms.microservices.dmsws.colaboration.AmenziResponse;
import ro.bithat.dms.microservices.dmsws.email.Email;
import ro.bithat.dms.microservices.dmsws.ps4.paymentintegration.DmswsBankingService;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.backend.DmswsControlService;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.backend.DmswsPetitiiService;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECorespondentaControlRoute;
import ro.bithat.dms.microservices.portal.ecitizen.documenttype.gui.Ps4ECorespondentaPetitiiRoute;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.*;
import ro.bithat.dms.passiveview.ClickEventPresenterMethod;
import ro.bithat.dms.passiveview.VaadinClientUrlUtil;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.html.ClickNotifierAnchor;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;
import sun.awt.image.ImageWatched;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Ps4ECitizenMyAccountPresenter extends PrepareModelFlowPresenter<Ps4ECitizenMyAccountView> {

    @Autowired
    private DmswsUtilizatorService myAccountService;

    @Autowired
    private DmswsMyDocumentsService myDocumentsService;

    @Autowired
    private DmswsMyRequestsService myRequestsService;

    @Autowired
    private DmswsControlService myControlService;

    @Autowired
    private DmswsPetitiiService myPetitiiService;

    @Autowired
    private DmswsBankingService dmswsBankingService;

    @Autowired
    private InboxService inboxService;

    @Autowired
    private DmswsInboxService dmswsInboxService;

    @Value("${show.nonstandard.buttons:true}")
    private String showNonStandardButtons;

    @Value("${my.account.show.edit.buttons:true}")
    private boolean showEditButtons;

    @Value("${notif.amenzi:false}")
    private boolean notifAmenzi;


    @Autowired
    private ColaborationService colaborationService;
    private Integer noDocumentsShown= 5;
    private Integer noPaymentsShown= 5;
    private Integer noRequestsShown= 5;
    private Integer noEmailsShown= 10;
    @Override
    public void prepareModel(String state) {
        getView().setFormDetails(myAccountService.getPersoanaFizicaJuridica(SecurityUtils.getToken(),SecurityUtils.getUserId().intValue()));
        if(showNonStandardButtons!=null && showNonStandardButtons.equals("true")) {

        }else{
            getView().hideNonstandardButtons();
        }

        getView().setMyRequestsTable(myRequestsService.getLimitedFilesOnWorkflowByUser(String.valueOf(noRequestsShown)));
        getView().setMyDocumentsTable(myDocumentsService.getLimitedDocumentsByUser(String.valueOf(noDocumentsShown)));
        getView().setInboxTable(inboxService.getSysEmailsByUser(SecurityUtils.getToken(),noEmailsShown.toString(),null));
        getView().setColaborationMessagesTable(colaborationService.getLastColaborationMessagesByUser());

    }
    @ClickEventPresenterMethod(viewProperty = "anchorEditBtn")
    public void onRedirectToAccountDetailClicked(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("redirect -> account detail");
        VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenAccountDetailRoute.class));
    }
    @ClickEventPresenterMethod(viewProperty = "anchorDocumentsBtn")
    public void onRedirectToAccountDocumentsClicked(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("redirect -> account detail");
        VaadinClientUrlUtil.setLocation("PORTAL/documente_cont.html");
    }
    @ClickEventPresenterMethod(viewProperty = "schimbaParolaBtn")
    public void onRedirectToSchimbareParolaClicked(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("redirect -> schimba parola");
        VaadinClientUrlUtil.setLocation("PORTAL/schimba-parola.html");
    }
    @ClickEventPresenterMethod(viewProperty = "studiiPage")
    public void onRedirectToAccountStudiesClicked(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("redirect -> account detail");
        VaadinClientUrlUtil.setLocation("PORTAL/studii.html");
    }
    @ClickEventPresenterMethod(viewProperty = "contactePage")
    public void onRedirectToAccountContactClicked(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("redirect -> account detail");
        VaadinClientUrlUtil.setLocation("PORTAL/persoane_contact.html");
    }
    @ClickEventPresenterMethod(viewProperty = "anchorAllMessages")
    public void onRedirectToEmailInboxClicked(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("redirect -> email inbox");
        VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenEmailInboxRoute.class));
    }
    @ClickEventPresenterMethod(viewProperty = "anchorButtonAllRequests")
    public void onRedirectToMyRequestsClicked(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("redirect -> my request");
        VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenMyRequestsRoute.class));
    }
    @ClickEventPresenterMethod(viewProperty = "anchorButtonAllControl")
    public void onRedirectToMyControlClicked(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("redirect -> my control");
        VaadinClientUrlUtil.setLocation("PORTAL/corespondenta_ctpt.html?zona=CONTROL");
    }
    @ClickEventPresenterMethod(viewProperty = "anchorButtonAllPetitii")
    public void onRedirectToMyPetitiiClicked(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("redirect -> my petitii");
        VaadinClientUrlUtil.setLocation("PORTAL/corespondenta_ctpt.html?zona=PETITII");
    }
    @ClickEventPresenterMethod(viewProperty = "anchorButtonAllDocuments")
    public void onRedirectToMyDocumentsClicked(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("redirect -> my documents");
        VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenMyDocumentsRoute.class));
    }
    @ClickEventPresenterMethod(viewProperty = "anchorButtonAllPayments")
    public void onRedirectToMyPaymentsClicked(ClickEvent<ClickNotifierAnchor> clickEvent) {
        getLogger().info("redirect -> my payments");
        VaadinClientUrlUtil.setLocation(RouteConfiguration.forApplicationScope().getUrl(Ps4ECitizenMyPaymentsRoute.class));
    }
//    @ClickEventPresenterMethod(viewProperty = "buttonSearch")
    public void onInboxSearchBtnClick(ClickEvent<NativeButton> clickEvent){
        getLogger().info("search text:\t "+getView().getSearchTextValue());

        String searchStr=getView().getSearchTextValue();
        List<Email> myMessages=inboxService.getSysEmailsByUser(SecurityUtils.getToken(),noEmailsShown.toString(),searchStr);

        getView().setInboxTable(myMessages);
        getView().i18nInboxContainer();

    }

    public void onInboxSearchTextChanged(AbstractField.ComponentValueChangeEvent<TextField, String> textChangeEvent) {
        getLogger().info("search text:\t "+getView().getSearchTextValue());

        String searchStr=getView().getSearchTextValue();
        List<Email> myMessages=inboxService.getSysEmailsByUser(SecurityUtils.getToken(),noEmailsShown.toString(),searchStr);

        getView().setInboxTable(myMessages);
        getView().i18nInboxContainer();

    }

    public void onRespondBtnClick(ClickEvent<NativeButton> clickEvent, Email email, TextArea messageToSend, Div messageContainer){
        getLogger().info("email send by:\t " + email.getCreatDe()+ " email to send :	 " + messageToSend.getValue());

        if( (email.getCreatDeEmail()==null|| email.getCreatDeEmail().isEmpty()) & (email.getCreatDe()==null|| email.getCreatDe().isEmpty()) ){
                UI.getCurrent().getPage().executeJs("swalError($0)",
                        I18NProviderStatic.getTranslation("ps4.ecetatean.inbox.send.email.swal.err"));
                if (messageContainer.hasClassName("reply")) {
                    messageContainer.removeClassNames("reply");
                }

        }
        else if (messageToSend==null|| messageToSend.isEmpty()){
            UI.getCurrent().getPage().executeJs("swalError($0)",
                    I18NProviderStatic.getTranslation("ps4.ecetatean.inbox.send.email.swal.info.not.empty"));
        }
        else {
            dmswsInboxService.sendEmail(SecurityUtils.getToken(), email.getCreatDeEmail()==null || email.getCreatDeEmail().isEmpty()?email.getCreatDe():email.getCreatDeEmail(),SecurityUtils.getFullName(),
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
    public void onShowEmailBtnClick(ClickEvent<NativeButton> clickEvent,HtmlContainer iconMessageEnvelope, Email email,  Div messageContainer){
        getLogger().info("email send by:\t " + email.getCreatDe());

        if(email.getId()!=null && email.getCitit()!=1){
            dmswsInboxService.setEmailReaded(SecurityUtils.getToken(),email.getId());
            if(iconMessageEnvelope.hasClassName("fa-envelope")){
                iconMessageEnvelope.removeClassName("fa-envelope");
                messageContainer.removeClassName("unread");
                iconMessageEnvelope.addClassName("fa-envelope-open");
            }
        }
    }

    public Integer getNoDocumentsShown() {
        return noDocumentsShown;
    }

    public Integer getNoRequestsShown() {
        return noRequestsShown;
    }

    public boolean isShowEditButtons() {
        return showEditButtons;
    }

    public void setShowEditButtons(boolean showEditButtons) {
        this.showEditButtons = showEditButtons;
    }

    public void getAmenzi(){
        if(notifAmenzi){
            try {
                AmenziResponse amenziResponse = myAccountService.getAmenzi(SecurityUtils.getToken());
                if(amenziResponse.getAmenda().size()!=0){
                    StringBuilder mesaj = new StringBuilder();
                    mesaj.append("Au fost indentificate in baza de date amenzile: </br>");

                    for(int i=0;i<amenziResponse.getAmenda().size();i++){
                        if(amenziResponse.getAmenda().get(i) instanceof LinkedHashMap && amenziResponse.getAmenda().get(i) instanceof HashMap) {
                            mesaj.append(String.valueOf(i+1));
                            mesaj.append(". ");
                            mesaj.append(String.valueOf(((LinkedHashMap<String, String>) (amenziResponse.getAmenda().get(i))).get("denumire")));
                            mesaj.append(" suma: ");
                            mesaj.append(String.valueOf(((LinkedHashMap<String, String>) (amenziResponse.getAmenda().get(i))).get("suma")));
                            mesaj.append(" din anul ");
                            mesaj.append(String.valueOf(((LinkedHashMap<String, String>) (amenziResponse.getAmenda().get(i))).get("an")));
                            mesaj.append("</br>");
                        }
                    }
                    if(!mesaj.toString().isEmpty()){
                        UI.getCurrent().getPage().executeJs("swalInfoWithoutBtn($0)",
                                mesaj.toString());
                    }
                }
            }catch(Exception e){
                if(e.getMessage()!=null && e.getMessage().contains("MISSING_CNP")) {
                    UI.getCurrent().getPage().executeJs("swalInfoWithoutBtn($0)",
                            "Notificarea de amenzi functioneaza doar pe baza de CNP.</br>" +
                                    "Pentru a seta aceasta informatie in cont, este necesara validarea documentelor de identitate.</br>" +
                                    "Pentru nelamuriri va rugam sa accesati urmatorul <a href='https://primariapn.ro/procedura-creare-cont-online' target=\"_blank\">link</a>\n");
                }else{
                    UI.getCurrent().getPage().executeJs("swalInfoWithoutBtn($0)",
                            "Nu au fost identificate amenzi");
                }

            }
        }
    }



}
