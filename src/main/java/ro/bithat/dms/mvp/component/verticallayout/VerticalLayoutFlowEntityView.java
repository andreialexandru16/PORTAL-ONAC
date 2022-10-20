package ro.bithat.dms.mvp.component.verticallayout;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.mvp.FlowEntityPresenter;
import ro.bithat.dms.mvp.FlowEntityView;

import javax.annotation.PostConstruct;

@Deprecated
public abstract class VerticalLayoutFlowEntityView<P extends FlowEntityPresenter> extends VerticalLayout implements FlowEntityView<P, FormLayout> {

    @Autowired
    private P presenter;

    private Button save = new Button("Salveaza", VaadinIcon.DISC.create());

    private FormLayout editorForm = new FormLayout();

    @PostConstruct
    public void init() {
        setSizeFull();
        getStyle().set("overflow", "auto");
        editorForm.setWidthFull();
        editorForm.getStyle().set("overflow", "auto");
        add(editorForm);
        FlowEntityView.super.init();
    }

    @Override
    public void onAttach(AttachEvent attachEvent) {
        FlowEntityView.super.onAttach(attachEvent);
    }

    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public FormLayout getEditorForm() {
        return editorForm;
    }

    @Override
    public Button getSaveButton() {
        return save;
    }

}
