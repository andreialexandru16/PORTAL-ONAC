package ro.bithat.dms.mvp.component.horizontallayout;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.mvp.FlowEntityPresenter;
import ro.bithat.dms.mvp.FlowEntityView;

import javax.annotation.PostConstruct;

@Deprecated
public abstract class HorizontalLayoutFlowEntityView<P extends FlowEntityPresenter> extends HorizontalLayout implements FlowEntityView<P, VerticalLayout> {

    @Autowired
    private P presenter;

    private Button save = new Button("button.save.label", VaadinIcon.CHECK.create());

    private VerticalLayout editorForm = new VerticalLayout();

    @PostConstruct
    public void init() {
        setSizeFull();
        getStyle().set("overflow", "auto");
        editorForm.setHeightFull();
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
    public VerticalLayout getEditorForm() {
        return editorForm;
    }

    @Override
    public Button getSaveButton() {
        return save;
    }

}
