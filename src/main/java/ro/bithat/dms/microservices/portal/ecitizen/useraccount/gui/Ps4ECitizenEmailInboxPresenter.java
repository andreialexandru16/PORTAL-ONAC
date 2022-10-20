package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.microservices.dmsws.email.Email;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.DmswsInboxService;
import ro.bithat.dms.microservices.portal.ecitizen.useraccount.backend.InboxService;
import ro.bithat.dms.passiveview.boot.I18NProviderStatic;
import ro.bithat.dms.passiveview.component.presenter.PrepareModelFlowPresenter;
import ro.bithat.dms.security.SecurityUtils;

import java.util.List;

public class Ps4ECitizenEmailInboxPresenter extends PrepareModelFlowPresenter<Ps4ECitizenEmailInboxView> {

    @Autowired
    private InboxService inboxService;

    @Autowired
    private DmswsInboxService dmswsInboxService;

    private Integer noEmailsShown= 100;

    @Override
    public void prepareModel(String state) {
        getView().setInboxTable(inboxService.getSysEmailsByUser(SecurityUtils.getToken(),noEmailsShown.toString(),null));

    }

    public void onInboxSearchBtnClick(ClickEvent<NativeButton> clickEvent){
        getLogger().info("search text:\t "+getView().getSearchTextValue());

        String searchStr=getView().getSearchTextValue();
        List<Email> myMessages=inboxService.getSysEmailsByUser(SecurityUtils.getToken(),noEmailsShown.toString(),searchStr);

        getView().setInboxTable(myMessages);
        getView().i18nInboxContainer();

    }

    public void onInboxSearchTextChanged(AbstractField.ComponentValueChangeEvent<TextField, String> textChangeEvent){
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
    public void onShowEmailBtnClick(ClickEvent<NativeButton> clickEvent, HtmlContainer iconMessageEnvelope, Email email,Div messageContainer){
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


}
