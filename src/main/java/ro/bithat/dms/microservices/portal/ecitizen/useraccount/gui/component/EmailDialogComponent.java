package ro.bithat.dms.microservices.portal.ecitizen.useraccount.gui.component;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import ro.bithat.dms.microservices.dmsws.email.Email;
import ro.bithat.dms.microservices.portal.ecitizen.gui.component.FlowViewDivContainer;
import ro.bithat.dms.passiveview.mvp.FlowView;

public class EmailDialogComponent extends FlowViewDivContainer {


    private Div mailContainer = new Div();

    private Div modalBody = new Div(mailContainer);

    private Div nameTop = new Div();

    private Div subjectTop = new Div();

    private Div dateTop = new Div();

    private Div modalName = new Div(nameTop, dateTop);

    private Div modalTop = new Div(modalName, subjectTop);

    private NativeButton closeBtn = new NativeButton();

    private Div closeModal = new Div(closeBtn);

    private Div modalHeader = new Div(closeModal, modalTop);

    private Div modalContent = new Div(modalHeader, modalBody);

    private Div modalDialog = new Div(modalContent);

    public EmailDialogComponent(FlowView view) {
        super(view);
        addClassNames("modal", "fade");
        getElement().setAttribute("tabindex", "-1");
        getElement().setAttribute("role", "dialog");
        getElement().setAttribute("aria-hidden", "true");
        getElement().setAttribute("aria-labelledby", "exampleModalLabel");
//        getStyle().set("display", "block");
        add(modalDialog);

        modalDialog.addClassName("modal-dialog");
        modalDialog.getElement().setAttribute("role", "document");
        modalContent.addClassName("modal-content");
        modalHeader.addClassName("modal-header");
        modalBody.addClassName("modal-body");
        closeModal.addClassName("close_modal");
        modalTop.addClassName("modal_top");
        closeBtn.addClassName("close");
        closeBtn.getElement().setAttribute("data-dismiss", "modal");
        closeBtn.getElement().setAttribute("aria-label", "Close");
        Span closeBtnSpan = new Span();
        closeBtnSpan.getElement().setProperty("innerHTML", "&times;");
        closeBtn.add(closeBtnSpan);
//        closeBtn.addClickListener(e -> close());
        dateTop.addClassName("date");
        modalName.addClassName("modal_name");
        nameTop.addClassName("name");
        subjectTop.addClassName("subject");
        mailContainer.addClassName("content_modal");
    }

    public void setEmail(Email email) {
        mailContainer.removeAll();
        dateTop.removeAll();
        subjectTop.removeAll();
        nameTop.removeAll();
        nameTop.add(new Text("ps4.ecetatean.breadcrumb.myaccount.page.inbox.message.sendby"));

        nameTop.add(new Text(email.getCreatDe()==null?"":email.getCreatDe()));
        subjectTop.add(new Text(email.getTitlu()==null?"":email.getTitlu()));
        dateTop.add(new Text(email.getCreatLa()==null?"":email.getCreatLa()));
        mailContainer.getElement().setProperty("innerHTML", email.getMesaj()==null?"":email.getMesaj());

    }

//    public void close() {
//        if(getParent().isPresent()) {
//            UI.getCurrent().getElement().removeAttribute("class");
//            ((HasComponents)getParent().get()).remove(this);
//        }
//    }
//
//
//    public void show(Email email, HasComponents component) {
//        mailContainer.removeAll();
//        dateTop.removeAll();
//        subjectTop.removeAll();
//        nameTop.removeAll();
//        nameTop.add(new Text(email.getCreatDe()));
//        subjectTop.add(new Text(email.getTitlu()));
//        dateTop.add(new Text(email.getDataTransmisie()));
//        mailContainer.getElement().setProperty("innerHTML", email.getMesaj());
//        if(!getParent().isPresent()) {
//            UI.getCurrent().getElement().setAttribute("class", "modal-open");
//            component.add(this);
//        }
//    }

}
