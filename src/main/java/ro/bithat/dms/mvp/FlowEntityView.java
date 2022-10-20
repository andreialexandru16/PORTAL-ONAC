package ro.bithat.dms.mvp;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.HasItems;
import ro.bithat.dms.service.CrudRepositoryService;
import ro.bithat.dms.service.EagerRepositoryService;

import javax.annotation.PostConstruct;

@Deprecated
public interface FlowEntityView<P extends FlowEntityPresenter, T extends Component> extends FlowView<P> {

	@PostConstruct
	default void initSaveButton() {
		getSaveButton().addClickListener(this::saveBtnFired);
	}

	default void saveBtnFired(ClickEvent<Button> buttonClickEvent) {
		if(CrudRepositoryService.class.isAssignableFrom(getPresenter().getService().getClass())) {
			getPresenter().save();
			setEditorFormVisibility(false);
			if (HasItems.class.isAssignableFrom(this.getClass())
					&& EagerRepositoryService.class.isAssignableFrom(getPresenter().getService().getClass())) {
				((HasItems) this).setItems(getPresenter().getItems());
			}
		}
	}

	Button getSaveButton();

	default T getEditorForm(){
		return (T) new VerticalLayout();
	}

	default void setEditorFormVisibility(Boolean visible){
		getEditorForm().setVisible(visible);
	}
}
