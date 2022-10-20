package ro.bithat.dms.template.component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import ro.bithat.dms.template.route.TemplateUtil;

import java.util.UUID;

@Deprecated
public class DraggableDialog extends Div {

    private VerticalLayout content = new VerticalLayout();

    public DraggableDialog() {
        TemplateUtil.setComponentIdAndClassName(this,
                "draggable-dialog-node-"+ UUID.randomUUID().toString(), "dialog-node");
        HorizontalLayout header = new HorizontalLayout();
        TemplateUtil.setNoSpacingAndPaddingLayout(header);
        TemplateUtil.setComponentIdAndClassName(header, getId().get() + "header", "draggable-dialog-nodeheader");
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        Div close = new Div();
        close.setClassName("dms-surface-navigation-action-item-component");
        Span closeIcon = new Span();
        closeIcon.addClassNames("dms-navigation-action-item-icon", "fa", "fa-close");
        close.add(closeIcon);
        close.addClickListener(event -> this.close());
        header.add(close);
        header.getStyle().set("min-width", "300px");
        add(header);
        TemplateUtil.setNoSpacingAndPaddingLayout(content);
        add(content);
    }

    public void open() {
        UI.getCurrent().add(this);
        UI.getCurrent().getPage().executeJs("dmsDragNodeDialogElement($0);", this.getElement());
    }

    public void close() {
        UI.getCurrent().remove(this);
    }

    public VerticalLayout getContent() {
        return content;
    }

}
