package ro.bithat.dms.smartform.gui.attribute.component;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import ro.bithat.dms.service.StreamToStringUtil;

public class DateHourComponent extends Div {

    private Div modalContent = new Div();

    private Div modalDocument = new Div(modalContent);


    public DateHourComponent() {
        addClassNames("modal", "fade", "box_modal_map");
        getElement().setAttribute("tabindex", "-1");
        getElement().setAttribute("role", "dialog");
        getElement().setAttribute("aria-hidden", "true");
        getElement().setAttribute("aria-labelledby", "exampleModalCenterTitle");
        add(modalDocument);

        modalDocument.addClassNames("modal-dialog", "modal-dialog-centered", "modal_audienta");
        modalDocument.getElement().setAttribute("role", "document");

        modalContent.addClassName("modal-content");
        modalContent.getStyle().set("display", "block !important");


        modalContent.getElement().setProperty("innerHTML",
                StreamToStringUtil.fileToString("static/PORTAL/date_hour_modal.html"));
    }
}
