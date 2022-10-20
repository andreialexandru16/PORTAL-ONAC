package ro.bithat.dms.smartform.gui.attribute.component;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import ro.bithat.dms.service.StreamToStringUtil;

public class AddrMapComponent extends Div {
    private Div addrMap = new Div();

    private Div mapContainer = new Div(addrMap);

    private Div contentModal = new Div(mapContainer);

    private Div modalBody = new Div(contentModal);

    private Div nameTop = new Div();

    private Div subjectTop = new Div();

    private Div modalName = new Div(nameTop, subjectTop);

    private Div dateTop = new Div();

    private Div modalTop = new Div(modalName, dateTop);

    private NativeButton closeBtn = new NativeButton();

    private Div closeModal = new Div(closeBtn);

    private Div modalHeader = new Div(closeModal, modalTop);

    private Div modalContent = new Div(modalHeader, modalBody);

    private Div modalDialog = new Div(modalContent);

    public AddrMapComponent() {
        addClassNames("modal", "fade");
        getElement().setAttribute("tabindex", "-1");
        getElement().setAttribute("role", "dialog");
        getElement().setAttribute("aria-hidden", "true");
        getElement().setAttribute("aria-labelledby", "exampleModalLabel");
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
        dateTop.addClassName("date");
        modalName.addClassName("modal_name");
        nameTop.addClassName("name");
        subjectTop.addClassName("subject");
        contentModal.addClassName("content_modal");

        mapContainer.addClassName("map_container");
        addrMap.setId("map_addr");


//        budgetMap.getStyle().set("min-height", "600px");
        addrMap.getElement().setProperty("innerHTML",
                StreamToStringUtil.fileToString("static/PORTAL/google_map_addr.html"));
    }
}
