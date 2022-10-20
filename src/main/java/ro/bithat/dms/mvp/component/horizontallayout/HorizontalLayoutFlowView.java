package ro.bithat.dms.mvp.component.horizontallayout;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import ro.bithat.dms.mvp.FlowPresenter;
import ro.bithat.dms.mvp.FlowView;

@Deprecated
public abstract class HorizontalLayoutFlowView<P extends FlowPresenter> extends HorizontalLayout implements FlowView<P> {

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
