package ro.bithat.dms.mvp.component.verticallayout;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.HasItems;
import ro.bithat.dms.mvp.FlowEntityPresenter;

import javax.annotation.PostConstruct;

@Deprecated
public abstract class VerticalLayoutCrudFlowEntityView<P extends FlowEntityPresenter,T> extends VerticalLayoutFlowEntityView<P> implements HasItems<T> {

    private Button cancel = new Button("Inchide");
    private Button delete = new Button("Sterge", VaadinIcon.TRASH.create());

    private HorizontalLayout actions = new HorizontalLayout(getSaveButton(), cancel, delete);


    @PostConstruct
    public void init() {
        getEditorForm().getStyle().set("overflow", "auto");
        super.init();
        add(actions);
        actions.setWidthFull();
        cancel.addClickListener(this::cancelBtnFired);
        delete.addClickListener(this::deleteBtnFired);
    }

    protected void deleteBtnFired(ClickEvent<Button> buttonClickEvent) {
        getPresenter().delete();
        setEditorFormVisibility(false);
        setItems(getPresenter().getItems());
    }

    protected void cancelBtnFired(ClickEvent<Button> buttonClickEvent) {
        setEditorFormVisibility(false);
    }

    @Override
    public void setEditorFormVisibility(Boolean visible) {
        super.setEditorFormVisibility(visible);
        actions.setVisible(visible);
    }

    protected HorizontalLayout getActionsHorizontalLayout() {
        return actions;
    }

    public Button getCancelButton() {
        return cancel;
    }

    public Button getDeleteButton() {
        return delete;
    }
}
