package ro.bithat.dms.passiveview.component.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.passiveview.FlowComponentBuilder;
import ro.bithat.dms.passiveview.mvp.FlowPresenter;
import ro.bithat.dms.passiveview.mvp.FlowViewBuilder;

public abstract class VerticalLayoutFlowViewBuilder<P extends FlowPresenter> extends VerticalLayout implements FlowViewBuilder<P> {

    @Autowired
    private FlowComponentBuilder componentBuilder;

    private P presenter;

    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void buildView(P presenter) {
        this.presenter = presenter;
        buildView();
    }

    public FlowComponentBuilder getComponentBuilder() {
        return componentBuilder;
    }

    protected abstract void buildView();
}
