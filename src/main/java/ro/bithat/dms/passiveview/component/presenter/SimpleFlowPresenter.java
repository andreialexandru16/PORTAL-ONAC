package ro.bithat.dms.passiveview.component.presenter;

import ro.bithat.dms.passiveview.mvp.FlowPresenter;
import ro.bithat.dms.passiveview.mvp.FlowView;

public abstract class SimpleFlowPresenter<V extends FlowView> implements FlowPresenter<V> {

    private V view;

    @Override
    public V getView() {
        return view;
    }

    @Override
    public void setView(V view) {
        this.view = view;
    }

}
