package ro.bithat.dms.mvp.component.verticallayout;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.mvp.FlowPresenter;
import ro.bithat.dms.mvp.FlowView;

@Deprecated
public abstract class VerticalLayoutFlowView<P extends FlowPresenter> extends VerticalLayout implements FlowView<P> {

    @Autowired
    private P presenter;


    @Override
    public void onAttach(AttachEvent attachEvent) {
        FlowView.super.onAttach(attachEvent);
    }

    @Override
    public P getPresenter() {
        return presenter;
    }

}
