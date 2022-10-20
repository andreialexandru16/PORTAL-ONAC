package ro.bithat.dms.passiveview.component.presenter;

import ro.bithat.dms.passiveview.mvp.FlowPresenter;
import ro.bithat.dms.passiveview.mvp.FlowView;
import ro.bithat.dms.passiveview.mvp.observer.FlowPresenterPrepareModelObserver;

public abstract class PrepareModelFlowPresenter<V extends FlowView> implements FlowPresenter<V>, FlowPresenterPrepareModelObserver {

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
